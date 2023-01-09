package com.pm.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class SignUpPage extends Form {
    private static final By CLOSE_BUTTON_ACC_ID = AppiumBy.accessibilityId("close_button");
    private static final By LOG_IN_ACC_ID = AppiumBy.accessibilityId("Log in button");

    public SignUpPage() {
        super(CLOSE_BUTTON_ACC_ID, "Login Page");
    }

    public LogInPage clickLogInButton() {
        waitForExpectedElement(LOG_IN_ACC_ID).click();
        return new LogInPage();
    }
}
