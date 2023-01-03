package com.pm.ui.test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UITest extends UIBaseTest {
    private static final String EMAIL = "";
    private static final String PASSWORD = "";

    @Feature("Desktop")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "stake"})
    @Test
    public void testMultiBet(@Optional int quantity,
                             @Optional String colour,
                             @Optional double stake) {
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

        steps.setStake(stake);
        steps.assertTotalOddsIsCorrect();
    }
}
