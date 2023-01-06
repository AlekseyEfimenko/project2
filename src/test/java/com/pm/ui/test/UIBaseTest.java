package com.pm.ui.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.pm.listener.EventLogListener;
import com.pm.ui.steps.TestSteps;
import com.pm.utils.BrowserManager;
import com.pm.utils.FileManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class UIBaseTest {
    protected final TestSteps steps = new TestSteps();

    @BeforeTest
    public void setUp() {
        WebDriverRunner.addListener(new EventLogListener());
//        Configuration.browser = System.getProperty("browser");
        Configuration.timeout = FileManager.getData().selenideTimeout();
        Configuration.browserSize = "1920x1080";
//        Configuration.headless = true;
//        Configuration.holdBrowserOpen = true;
        System.setProperty("chromeoptions.args", "--user-agent=ParimatchTechAcademy/89870edc1aaea008bd3a519c");
    }

    @BeforeMethod
    public void openStartPage() {
        BrowserManager.navigateTo(FileManager.getData().startPage());
    }

    @AfterMethod
    public void clearCookies() {
        BrowserManager.clearAllCookies();
    }
}
