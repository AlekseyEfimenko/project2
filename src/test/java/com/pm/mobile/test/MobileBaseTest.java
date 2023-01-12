package com.pm.mobile.test;

import com.pm.mobile.steps.TestSteps;
import com.pm.utils.DriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class MobileBaseTest {
    private static final String EMAIL = "mr.ren1one@gmail.com"; //System.getProperty("email");
    private static final String PASSWORD = "R.kvashuk106072"; //System.getProperty("password");
    protected TestSteps steps = new TestSteps();

    @BeforeTest(alwaysRun = true)
    public void setupSession() {
        DriverManager.createDriver();
    }

    @Feature("Mobile")
    @Description("Logging in to the app and opening \"League Mode\" page")
    @BeforeClass
    public void setPrecondition() {
        steps.enterLogInPage();
        steps.assertLoginPageIsOpened();

        steps.logIn(EMAIL, PASSWORD);
        steps.assertFooterFormIsOpened();

        steps.navigateToVirtualSpor();
        steps.assertVirtualSportPageIsOpened();

        steps.openLeagueMode();
    }

    @Feature("Mobile")
    @Description("Clearing and closing bet slip")
    @AfterMethod
    public void resetPrecondition() {
        steps.removeAllOddsFromBetSlip();
        steps.closeBetSlip();
    }

    @AfterTest(alwaysRun = true)
    public void closeSession() {
        DriverManager.terminateDriver();
    }
}
