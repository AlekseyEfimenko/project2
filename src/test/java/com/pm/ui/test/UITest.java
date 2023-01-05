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
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "stake"})
    @Test
    public void testMultiBet(@Optional int quantity,
                             @Optional String colour,
                             @Optional int stake) {
        steps.clickLogIn();
        steps.assertLoginFormIsOpened();

        steps.logIn(EMAIL, PASSWORD);
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
    }
}
