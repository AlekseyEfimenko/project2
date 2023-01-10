package com.pm.ui.steps;

import static org.assertj.core.api.Assertions.assertThat;
import static com.pm.temp.Context.ODDS;
import static com.pm.temp.ScenarioContext.getContext;

import com.pm.ui.pages.BetSlipForm;
import com.pm.ui.pages.LeagueModePage;
import com.pm.ui.pages.LoginForm;
import com.pm.ui.pages.MessageForm;
import com.pm.utils.DataManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;

public class TestSteps {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String CHECK_PAGE_MESSAGE = "Checking if {} is opened";
    private static final String ERROR_MESSAGE = "The {} is not opened";
    private static final String SUCCESS_MESSAGE = String.format("%1$s  SUCCESS  %1$s%n%n", "=".repeat(50));
    private final MessageForm messageForm = new MessageForm();
    private final LeagueModePage leagueModePage = new LeagueModePage();
    private LoginForm loginForm;
    private BetSlipForm betSlipForm;

    public void clickLogIn() {
        LOG.info("Clicking the \"Log In\" button");
        loginForm = messageForm.clickLogIn();
    }

    public void assertLoginFormIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, loginForm.getName());

        assertThat(loginForm.isDisplayed())
                .as(ERROR_MESSAGE, loginForm.getName())
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void logIn(String email, String password) {
        loginForm.selectEmail();
        loginForm.login(email, password);
    }

    public void assertLeagueModePageIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, leagueModePage.getName());

        assertThat(leagueModePage.isDisplayed())
                .as(ERROR_MESSAGE, leagueModePage.getName())
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void selectMatchDay() {
        LOG.info("Selecting the next match day");
        leagueModePage.selectNextMatchDay();
    }

    public void addOddsToBetSlip(int quantity) {
        LOG.info("Adding random {} odds to the bet slip from the available match day odds", quantity);
        betSlipForm = leagueModePage.addRandomOdds(quantity);
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

    @SuppressWarnings("all")
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

    public void assertMultiIsUnderlinedWithYellow(String colour) {
        LOG.info("Checking if the \"Multi\" link is underlined in yellow colour");
        String actualColor = betSlipForm.getMultiUnderlineColor();

        assertThat(actualColor.equals(colour))
                .as("The \"Multi\" link is not underlined in yellow colour")
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void setStake(double stake) {
        LOG.info("Setting ticket stake to {}", stake);
        betSlipForm.setStake(String.valueOf(stake).replace('.', ','));
    }

    @SuppressWarnings("all")
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

    @SuppressWarnings("all")
    public void assertPossibleWinningsIsCorrect(double stake) {
        LOG.info("Checking if possible winnings value is correctly displayed in the bet slip");
        double expectedWinnings = DataManager
                .calculatePossiblePayout((List<String>) (getContext(ODDS)), stake);
        double actualWinnings = Double.parseDouble(betSlipForm.getPossibleWinnings()
                .substring(betSlipForm.getPossibleWinnings().indexOf(" ") + 1)
                .replace(',', '.')
                .replace(" ", ""));

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
}
