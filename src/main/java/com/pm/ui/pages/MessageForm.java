package com.pm.ui.pages;

import org.openqa.selenium.By;

public class MessageForm extends Form {
    private static final By AUTHORISATION_MESSAGE_CSS = By.cssSelector("[data-id = dialog-casino]");
    private static final By LOGIN_CSS = By.cssSelector("[data-id = dialog-casino] button:last-of-type");

    public MessageForm() {
        super(AUTHORISATION_MESSAGE_CSS, "Notification message");
    }

    public LoginForm clickLogIn() {
        waitForElement(LOGIN_CSS).click();
        return new LoginForm();
    }

}
