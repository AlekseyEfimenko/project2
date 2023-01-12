package com.pm.ui.test;

import static com.pm.utils.FileManager.getData;
import static com.pm.ui.configuration.Properties.CHROME_OPTIONS_ARGS;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.pm.listener.EventLogListener;
import com.pm.ui.steps.TestSteps;
import com.pm.utils.BrowserManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class UIBaseTest {
    private static final String EMAIL = "mr.ren1one@gmail.com"; //System.getProperty("email");
    private static final String PASSWORD = "R.kvashuk106072"; //System.getProperty("password");
    protected final TestSteps steps = new TestSteps();

    @BeforeTest
    public void setUp() {
        WebDriverRunner.addListener(new EventLogListener());
//        Configuration.browser = System.getProperty("browser");
        Configuration.timeout = getData().selenideTimeout();
        Configuration.browserSize = getData().browserSize();
        System.setProperty(CHROME_OPTIONS_ARGS.getValue(), getData().userAgent());
    }

    @Feature("Desktop")
    @Description("Logging in to the site and opening \"League Mode\" page")
    @BeforeClass
    public void setPrecondition() {
        BrowserManager.navigateTo(getData().startPage());
        steps.clickLogIn();
        steps.assertLoginFormIsOpened();

        steps.logIn(EMAIL, PASSWORD);
    }

    @Feature("Desktop")
    @Description("Clearing bet slip and refreshing the page")
    @AfterMethod
    public void resetPrecondition() {
        //steps.removeAllOddsFromBetSlip();
        BrowserManager.clearAllCookies();
        BrowserManager.refreshThePage();
    }
}
