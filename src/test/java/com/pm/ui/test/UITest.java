package com.pm.ui.test;

import com.pm.utils.DataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UITest extends UIBaseTest {
    private static final String EMAIL = System.getProperty("email");
    private static final String PASSWORD = System.getProperty("password");
    private static final double MIN_STAKE = 10.0;
    private static final double MAX_STAKE = 20000.0;
    private static final double STAKE = DataManager.getRandomNumber(MIN_STAKE, MAX_STAKE);

    @Feature("Desktop")
    @Description("Log in to the site")
    @Test
    public void logIn() {
        steps.clickLogIn();
        steps.assertLoginFormIsOpened();

        steps.logIn(EMAIL, PASSWORD);
    }

    @Feature("Desktop")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "errorMessage"})
    @Test(dependsOnMethods = {"logIn"})
    public void testMultiBet(@Optional int quantity,
                             @Optional String colour,
                             @Optional String message) {
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
        steps.assertErrorMessageIsDisplayed(message);

        steps.removeAllOddsFromBetSlip();
    }

    @Feature("Desktop")
    @Description("Log in to the site")
    @Parameters({"betsToAdd", "betsToRemove"})
    @Test(dependsOnMethods = {"logIn"})
    public void removeBetsFromBetSlip(int add, int remove) {
        steps.assertLeagueModePageIsOpened();

        steps.selectMatchDay();
        steps.addOddsToBetSlip(add);
        steps.assertCorrectQuantityInBetSlip(add);
        steps.assertCorrectOddsAreAdded();

        steps.removeBetsFromBetSlip(remove);
        steps.assertCorrectQuantityInBetSlip(add - remove);
        steps.assertBetSlipContainsNumOfBets(add - remove);

        steps.removeAllOddsFromBetSlip();
    }
}
