package com.pm.mobile.test;

import com.pm.utils.DataManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class MobileTest extends MobileBaseTest {
    private static final String EMAIL = "l2kirs@gmail.com";//System.getProperty("email");
    private static final String PASSWORD = "!Qw23er4";//System.getProperty("password");
    private static final double MIN_STAKE = 10.0;
    private static final double MAX_STAKE = 20000.0;
    private static final double STAKE = DataManager.getRandomNumber(MIN_STAKE, MAX_STAKE);

    @Feature("Mobile")
    @Description("Logging in to the app")
    @Test
    public void logIn() {
        steps.enterLogInPage();
        steps.assertLoginPageIsOpened();

        steps.logIn(EMAIL, PASSWORD);
        steps.assertFooterFormIsOpened();

        steps.navigateToVirtualSpor();
        steps.assertVirtualSportPageIsOpened();

        steps.openLeagueMode();
    }

    @Feature("Mobile")
    @Description("Placing multi bet")
    @Parameters({"multiOdd_quantity", "colour", "errorMessageMobile1", "errorMessageMobile2"})
    @Test(dependsOnMethods = {"logIn"})
    public void testMultiBet(@Optional int quantity,
                             @Optional String colour,
                             @Optional String message1,
                             @Optional String message2) {
        steps.assertLeagueModePageIsOpened();

        steps.addOddsToBetSlip(quantity);
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

        steps.removeAllOddsFromBetSlip();
        steps.closeBetSlip();
    }

    @Feature("Mobile")
    @Description("Removing bets from the bet slip")
    @Parameters({"betsToAdd", "betsToRemove"})
    @Test(dependsOnMethods = {"logIn"})
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

        steps.removeAllOddsFromBetSlip();
        steps.closeBetSlip();
    }
}
