package com.pm.ui.test;

import com.pm.ui.steps.TestSteps;
import com.pm.utils.BrowserManager;
import org.testng.annotations.BeforeTest;

public class UIBaseTest {
    protected final TestSteps steps = new TestSteps();

    @BeforeTest
    public void setUpDriver() {
//        Selenide.clearBrowserCookies();
//        BrowserManager.navigateTo("www.ukr.net");
    }
}
