package com.pm.ui.pages;

import static com.codeborne.selenide.Condition.attribute;

import org.openqa.selenium.By;

public class LoginForm extends Form {
    private static final By LOGIN_FORM_CSS = By.cssSelector("[data-id=login-page]");
    private static final By LOGIN_LIST_CSS = By.cssSelector("[data-id = list]");
    private static final By EMAIL_XPATH = By.xpath("//form[@data-id='login-form']/div/div//*[@value='email']");
    private static final By LOGIN_ID = By.id("email");
    private static final By PASSWORD_ID = By.id("password");
    private static final By LOGIN_BUTTON_CSS = By.cssSelector("[data-id = login-button]");

    public LoginForm() {
        super(LOGIN_FORM_CSS, "Parimatch registration form");
    }

    public void selectEmail() {
        waitForElement(LOGIN_LIST_CSS).click();
        waitForElement(EMAIL_XPATH).click();
    }

    public LeagueModePage login(String email, String password) {
        waitForElement(LOGIN_ID).setValue(email).shouldHave(attribute("value", email));
        waitForElement(PASSWORD_ID).setValue(password).shouldHave(attribute("value", password));
        waitForElement(LOGIN_BUTTON_CSS).click();
        return new LeagueModePage();
    }
}
