package com.pm.mobile.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static com.pm.temp.Context.ELEMENT_LOCATION;
import static com.pm.temp.Context.ODDS;
import static com.pm.temp.ScenarioContext.getContext;

import com.pm.mobile.pages.BetSlipForm;
import com.pm.mobile.pages.FooterForm;
import com.pm.mobile.pages.LeagueModePage;
import com.pm.mobile.pages.LogInPage;
import com.pm.mobile.pages.SignUpPage;
import com.pm.mobile.pages.VirtualSportPage;
import com.pm.utils.DataManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Point;

import java.util.Collection;
import java.util.List;

public class TestSteps {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String CHECK_PAGE_MESSAGE = "Checking if {} is opened";
    private static final String ERROR_MESSAGE = "The %1$s is not opened";
    private static final String SUCCESS_MESSAGE = String.format("%1$s  SUCCESS  %1$s%n%n", "=".repeat(50));
    private final SignUpPage signUpPage = new SignUpPage();
    private LogInPage logInPage;
    private FooterForm footerForm;
    private VirtualSportPage virtualSport;
    private LeagueModePage leagueModePage;
    private BetSlipForm betSlipForm;

    public void enterLogInPage() {
        LOG.info("Navigating to the \"Log In\" page");
        logInPage = signUpPage.clickLogInButton();
    }

    public void assertLoginPageIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, logInPage.getName());

        assertThat(logInPage.isElementDisplayed(logInPage.getLocator()))
                .as(String.format(ERROR_MESSAGE, logInPage.getName()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void logIn(String email, String password) {
        LOG.info("Logging in to the account");
        logInPage.selectEmailType();
        footerForm = logInPage.logIn(email, password);
    }

    public void assertFooterFormIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, footerForm.getName());

        assertThat(footerForm.isElementDisplayed(footerForm.getLocator()))
                .as(String.format(ERROR_MESSAGE, footerForm.getName()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void navigateToVirtualSpor() {
        LOG.info("Navigation to the virtual sport page");
        footerForm.clickMenu();
        virtualSport = footerForm.clickVirtualSport();
    }

    public void assertVirtualSportPageIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, virtualSport.getName());

        assertThat(virtualSport.isElementDisplayed(virtualSport.getLocator()))
                .as(String.format(ERROR_MESSAGE, virtualSport.getName()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void openLeagueMode() {
        LOG.info("Opening Virtual Football League Mode page");
        leagueModePage = virtualSport.clickLeagueMode();
    }

    public void assertLeagueModePageIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, leagueModePage.getName());

        assertThat(leagueModePage.isElementDisplayed(leagueModePage.getLocator()))
                .as(String.format(ERROR_MESSAGE, leagueModePage.getName()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void addOddsToBetSlip(int quantity) {
        LOG.info("Adding random {} odds to the bet slip from the available match day odds", quantity);
        leagueModePage.addBets(quantity);
    }

    @SuppressWarnings("all")
    public void assertOddsAreHighlighted(String colour) {
        LOG.info("Checking if the odds are highlighted in {} colour", colour);
        ((List<Point>) getContext(ELEMENT_LOCATION)).forEach(element -> {
            String actualColour = DataManager.getElementColor(element);
            assertThat(actualColour)
                    .as(String.format("Expected colour of the element is: %1$s, but was found: %2$s",
                            colour, actualColour))
                    .isEqualTo(colour);
        });
        LOG.info(SUCCESS_MESSAGE);
    }

    public void navigateToBetSlip() {
        LOG.info("Navigating to bet slip");
        betSlipForm = leagueModePage.clickBetSlip();
    }

    public void assertBetSlipFormIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, betSlipForm.getName());

        assertThat(betSlipForm.isElementDisplayed(betSlipForm.getLocator()))
                .as(String.format(ERROR_MESSAGE, betSlipForm.getName()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertCorrectQuantityInBetSlip(int quantity) {
        LOG.info("Checking if the quantity of bets in the bet slip equals {}, the number of bets we have added",
                quantity);

        assertThat(betSlipForm.getBetsQuantity() == quantity)
                .as(String.format("The quantity of bets in the bet slip is %1$s and the number of bets we have added is %2$s",
                        betSlipForm.getBetsQuantity(), quantity))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertCorrectOddsAreAdded() {
        LOG.info("Checking if the bet slip contains odds we have added");

        assertThat(betSlipForm.getOdds().containsAll((Collection<?>) getContext(ODDS)))
                .as(String.format("The bets we have added: %1$s%nThe bets was found in the bet slip: %2$s%n",
                        getContext(ODDS).toString(),
                        betSlipForm.getOdds().toString()))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void selectMultiCategory() {
        LOG.info("Selecting the category \"Multi\" in the bet slip");
        betSlipForm.selectMulti();
    }

    public void setStake(double stake) {
        LOG.info("Setting ticket stake to {}", stake);
        betSlipForm.setStake(String.valueOf(stake).replace('.', ','));
    }

    public void assertTotalOddsIsCorrect() {
        LOG.info("Checking if correct total odds are displayed in the bet slip");
        double expectedOdds = DataManager.calculateTotalOdds((List<String>) (getContext(ODDS)));
        double actualOdds = Double.parseDouble(betSlipForm.getTotalOdds());

        assertThat(actualOdds == expectedOdds)
                .as(String.format("Expected odds in the bet slip is: %1$s, but was found: %2$s",
                        expectedOdds, actualOdds))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertPossibleWinningsIsCorrect(double stake) {
        LOG.info("Checking if possible winnings value is correctly displayed in the bet slip");
        double expectedWinnings = DataManager
                .calculatePossiblePayout((List<String>) (getContext(ODDS)), stake);
        double actualWinnings = Double.parseDouble(betSlipForm.getPossibleWinnings()
                .substring(betSlipForm.getPossibleWinnings().indexOf(" ") + 1)
                .replace(',', '.')
                .replace("\u00A0", ""));

        assertThat(actualWinnings == expectedWinnings)
                .as(String.format("Expected possible winnings in the bet slip is: %1$s, but was found: %2$s",
                        expectedWinnings, actualWinnings))
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void acceptBet() {
        LOG.info("Placing the bet in the bet slip");
        betSlipForm.placeBet();
    }

    public void assertErrorMessageIsDisplayed(String message1, String message2) {
        LOG.info("Checking if error message \"{}\" or \"{}\" is displayed", message1, message2);

        assertThat(betSlipForm.getErrorMessage())
                .as(String.format("None of error messages \"%1$s\" and \"%2$s\" are not displayed", message1, message2))
                .isIn(message1, message2);
        LOG.info(SUCCESS_MESSAGE);
    }

    public void removeAllOddsFromBetSlip() {
        LOG.info("Removing all odds from the bet slip");
        betSlipForm.clearBetSlip();
    }

    public void closeBetSlip() {
        LOG.info("Closing the bet slip");
        betSlipForm.exitBetSlip();
    }

    public void removeBetsFromBetSlip(int numToRemove) {
        LOG.info("Removing random {} bets from the bet slip", numToRemove);
        for (int i = 0; i < numToRemove; i++) {
            betSlipForm.removeOdd();
        }
    }

    public void assertBetSlipContainsNumOfBets(int numOfBets) {
        LOG.info("Checking if the bet slip contains {} bets", numOfBets);

        assertThat(betSlipForm.getNumberOfBets())
                .as(String.format("Expected number of bets in the bet slip is: %1$s, but was found: %2$s",
                        numOfBets,
                        betSlipForm.getNumberOfBets()))
                .isEqualTo(numOfBets);
        LOG.info(SUCCESS_MESSAGE);
    }

    public void selectSystemCategory() {
        LOG.info("Selecting the category \"System\" in the bet slip");
        betSlipForm.selectSystem();
    }
}
