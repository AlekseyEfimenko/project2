package com.pm.ui.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.pm.listener.EventLogListener;
import com.pm.ui.steps.TestSteps;
import com.pm.utils.BrowserManager;
import com.pm.utils.FileManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class UIBaseTest {
    protected final TestSteps steps = new TestSteps();

    @BeforeTest
    public void setUp() {
        WebDriverRunner.addListener(new EventLogListener());
        Configuration.browser = FileManager.getData().browser();
        BrowserManager.navigateTo(FileManager.getData().startPage());
    }

    @AfterTest
    public void teerDown() {
        BrowserManager.clearAllCookies();
    }
}
