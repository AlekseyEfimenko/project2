package com.pm.ui.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
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
    private static final String SUCCESS_MESSAGE = String.format("%1$s  SUCCESS  %1$s%n", "=".repeat(50));
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

    public void assertLeagueModePageIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, leagueModePage.getName());
        assertThat(leagueModePage.isDisplayed())
                .as(ERROR_MESSAGE, leagueModePage.getName())
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void addOddsToBetSlip(int quantity) {
        LOG.info("Adding random {} odds to the bet slip from the available match day odds", quantity);
        betSlipForm = leagueModePage.addRandomOdds(quantity);
    }

    public void assertCorrectQuantityInBetSlip(int quantity) {
        LOG.info("Checking if the quantity of bets in the bet slip equals {}, the number of bets we have added",
                quantity);
        assertThat(betSlipForm.getBetsQuantity() == quantity)
                .as("The quantity of bets in the bet slip is not equals {}, the number of bets we have added",
                        quantity)
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    @SuppressWarnings("all")
    public void assertCorrectOddsAreAdded() {
        LOG.info("Checking if the bet slip contains odds we have added");
        assertThat(betSlipForm.getOdds().containsAll((Collection<?>) ScenarioContext.getContext(Context.ODDS)))
                .as("The bet slip contains not the same odds we have added")
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void selectMultiCategory() {
        LOG.info("Selecting the category \"Multi\" in the bet slip");
        betSlipForm.selectMulti();
    }

    public void assertMultiIsUnderlinedWithYellow(String colour) {
        LOG.info("Checking if the \"Multi\" link is underlined in yellow colour");
        assertThat(betSlipForm.getMultiUnderlineColor().equals(colour))
                .as("The \"Multi\" link is not underlined in yellow colour")
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void setStake(double stake) {
        LOG.info("Setting ticket stake to {}", stake);
        betSlipForm.setStake(String.valueOf(stake));
    }

    @SuppressWarnings("all")
    public void assertTotalOddsIsCorrect() {
        LOG.info("Checking if correct total odds are displayed in the bet slip");
        double expectedOdds = DataManager.calculateTotalOdds((List<String>) (ScenarioContext.getContext(Context.ODDS)));
        ScenarioContext.setContext(Context.TOTAL_ODDS, expectedOdds);
        double actualOdds = Double.parseDouble(betSlipForm.getTotalOdds());

        assertThat(actualOdds == expectedOdds)
                .as("The value of total odds in the bet slip is not correct")
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void assertPossibleWinningsIsCorrect(double stake) {
        LOG.info("Checking if possible winnings value is correctly displayed in the bet slip");
        double expectedWinnings = DataManager
                .calculatePossiblePayout((Double) ScenarioContext.getContext(Context.TOTAL_ODDS), stake);
        double actualWinnings = Double.parseDouble(betSlipForm.getPossibleWinnings());

        assertThat(actualWinnings == expectedWinnings)
                .as("The value of possible winnings is incorrectly displayed in the bet slip")
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }

    public void acceptBet() {
        LOG.info("Placing the bet in the bet slip");
        betSlipForm.placeBet();
    }
}
