package com.pm.mobile.pages;

import static io.appium.java_client.AppiumBy.id;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.xpath;

import org.openqa.selenium.By;

public class LogInPage extends Form {
    public static final By CONTENT_ID = id("com.parimatch:id/rvContentView");
    private static final By SELECT_LOGIN_TYPE_ACC_ID = accessibilityId("Select login type button");
    private static final By SELECT_EMAIL_XPATH = xpath("//*[@resource-id = 'android:id/text1'][last()]");
    private static final By LOGIN_FIELD_ACC_ID = accessibilityId("Login field");
    private static final By PASSWORD_FIELD_ACC_ID = accessibilityId("Password field");
    private static final By ENTER_BUTTON_ID = id("com.parimatch:id/tvButtonTitle");

    public LogInPage() {
        super(CONTENT_ID, "Log In page");
    }

    public void selectEmailType() {
        waitForExpectedElement(SELECT_LOGIN_TYPE_ACC_ID).click();
        waitForExpectedElement(SELECT_EMAIL_XPATH).click();
    }

    public FooterForm logIn(String email, String password) {
        waitForExpectedElement(LOGIN_FIELD_ACC_ID).sendKeys(email);
        waitForExpectedElement(PASSWORD_FIELD_ACC_ID).click();
        waitForExpectedElement(PASSWORD_FIELD_ACC_ID).sendKeys(password);
        waitForExpectedElement(ENTER_BUTTON_ID).click();
        return new FooterForm();
    }
}
