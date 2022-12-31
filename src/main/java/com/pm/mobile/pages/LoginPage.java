package com.pm.mobile.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginPage extends Form {
    private static final By CLOSE_BUTTON_ACC_ID = AppiumBy.accessibilityId("close_button");

    public LoginPage() {
        super(CLOSE_BUTTON_ACC_ID, "Login Page");
    }

    public void clickButton() {
        waitForExpectedElement(CLOSE_BUTTON_ACC_ID).click();
    }
}
