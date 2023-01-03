package com.pm.ui.test;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UITest extends UIBaseTest {

    @Test
    public void testFramework() {
        steps.clickLogIn();
        steps.assertLoginFormIsOpened();

        System.out.println("UI build is successful!");
    }

    @Feature("Desktop")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "stake"})
    @Test
    public void testMultiBet(int quantity, String colour, double stake) {
        steps.assertLeagueModePageIsOpened();

        steps.addOddsToBetSlip(quantity);
        steps.assertCorrectQuantityInBetSlip(quantity);
        steps.assertCorrectOddsAreAdded();

        steps.selectMultiCategory();
        steps.assertMultiIsUnderlinedWithYellow(colour);

        steps.setStake(stake);
        steps.assertTotalOddsIsCorrect();

    }
}
