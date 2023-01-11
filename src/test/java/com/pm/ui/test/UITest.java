package com.pm.ui.test;

import com.pm.utils.DataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UITest extends UIBaseTest {
    private static final double MIN_STAKE = 10.0;
    private static final double MAX_STAKE = 20000.0;
    private static final double STAKE = DataManager.getRandomNumber(MIN_STAKE, MAX_STAKE);
    private static final double STAKE_BELOW_LIMIT = MIN_STAKE-0.01;

    @Feature("Desktop")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "errorMessage1", "errorMessage2"})
    @Test
    public void testMultiBet(int quantity, String colour, String message1, String message2) {
        steps.assertLeagueModePageIsOpened();

        steps.selectMatchDay();
        steps.addOddsToBetSlip(quantity);
        steps.assertCorrectQuantityInBetSlip(quantity);
        steps.assertCorrectOddsAreAdded();

        steps.selectMultiCategory();
        steps.assertMultiIsUnderlinedWithYellow(colour);

        steps.setStake(STAKE);
        steps.assertTotalOddsIsCorrect();
        steps.assertPossibleWinningsIsCorrect(STAKE);

        steps.acceptBet();
        steps.assertErrorMessageIsDisplayed(message1, message2);
    }

    @Feature("Desktop")
    @Description("Removing bets from the bet slip")
    @Parameters({"betsToAdd", "betsToRemove"})
    @Test
    public void removeBetsFromBetSlip(int add, int remove) {
        steps.assertLeagueModePageIsOpened();

        steps.selectMatchDay();
        steps.addOddsToBetSlip(add);
        steps.assertCorrectQuantityInBetSlip(add);
        steps.assertCorrectOddsAreAdded();

        steps.removeBetsFromBetSlip(remove);
        steps.assertCorrectQuantityInBetSlip(add - remove);
        steps.assertBetSlipContainsNumOfBets(add - remove);
    }

    @Feature("Desktop")
    @Description("Placing system bet")
    @Parameters({"systemOdd_quantity", "colour", "errorMessage1", "errorMessage2"})
    @Test
    public void testSystem23Bet(int quantity, String colour, String message1, String message2){
        steps.assertLeagueModePageIsOpened();
        steps.selectMatchDay();
        steps.addOddsToBetSlip(quantity);
        steps.assertCorrectQuantityInBetSlip(quantity);
        steps.assertCorrectOddsAreAdded();

        steps.selectSystemCategory();
        steps.assertSystemIsUnderlinedWithYellow(colour);

        steps.setStake(STAKE);
        steps.acceptBet();
        steps.assertErrorMessageIsDisplayed(message1, message2);
    }

    @Feature("Desktop")
    @Description("Make a bet less than 10 UAH")
    @Parameters({"multiOdd_quantity"})
    @Test
    public void makeBetLess10UAH(int number) {
        steps.assertLeagueModePageIsOpened();

        steps.selectMatchDay();
        steps.addOddsToBetSlip(number);
        steps.assertCorrectQuantityInBetSlip(number);
        steps.assertCorrectOddsAreAdded();

        steps.setSinglesStake(STAKE_BELOW_LIMIT);
        steps.checkBetButtonDisabled();

    }
}
