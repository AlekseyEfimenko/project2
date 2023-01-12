package com.pm.mobile.test;

import com.pm.utils.DataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MobileTest extends MobileBaseTest {
    private static final double MIN_STAKE = 10.0;
    private static final double MAX_STAKE = 20000.0;
    private static final double STAKE = DataManager.getRandomNumber(MIN_STAKE, MAX_STAKE);

    @Feature("Mobile")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colourMobile", "errorMessageMobile1", "errorMessageMobile2"})
    @Test
    public void testMultiBet(int quantity, String colour, String message1, String message2) {
        steps.assertLeagueModePageIsOpened();

        steps.addOddsToBetSlip(quantity);
        steps.assertOddsAreHighlighted(colour);

        steps.navigateToBetSlip();
        steps.assertBetSlipFormIsOpened();
        steps.assertCorrectQuantityInBetSlip(quantity);
        steps.assertCorrectOddsAreAdded();

        steps.selectMultiCategory();
        steps.setStake(STAKE);
        steps.assertTotalOddsIsCorrect();
        steps.assertPossibleWinningsIsCorrect(STAKE);

        steps.acceptBet();
        steps.assertErrorMessageIsDisplayed(message1, message2);
    }

    @Feature("Mobile")
    @Description("Removing bets from the bet slip")
    @Parameters({"betsToAdd", "betsToRemove"})
    @Test
    public void removeBetsFromBetSlip(int add, int remove) {
        steps.assertLeagueModePageIsOpened();

        steps.addOddsToBetSlip(add);

        steps.navigateToBetSlip();
        steps.assertBetSlipFormIsOpened();
        steps.assertCorrectQuantityInBetSlip(add);
        steps.assertCorrectOddsAreAdded();

        steps.removeBetsFromBetSlip(remove);
        steps.assertCorrectQuantityInBetSlip(add - remove);
        steps.assertBetSlipContainsNumOfBets(add - remove);
    }

    @Feature("Mobile")
    @Description("Placing system bet")
    @Parameters({"systemOdd_quantity", "colourMobile", "errorMessageMobile1", "errorMessageMobile2"})
    @Test
    public void testSystemBet(int quantity, String colour, String message1, String message2) {
        steps.assertLeagueModePageIsOpened();

        steps.addOddsToBetSlip(quantity);
        steps.assertOddsAreHighlighted(colour);

        steps.navigateToBetSlip();
        steps.assertBetSlipFormIsOpened();
        steps.assertCorrectQuantityInBetSlip(quantity);
        steps.assertCorrectOddsAreAdded();

        steps.selectSystemCategory();
        steps.setSystemStake(STAKE);


        steps.acceptBet();
        steps.assertErrorMessageIsDisplayed(message1, message2);
    }
}
