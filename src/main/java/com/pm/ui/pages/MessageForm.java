package com.pm.ui.pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MessageForm extends Form {
    private static final By AUTHORISATION_MESSAGE_CSS = By.cssSelector("[data-id = dialog-casino]");
    private static final By LOGIN_CSS = By.cssSelector("[data-id = dialog-casino] button:last-of-type");

    public MessageForm() {
        super(AUTHORISATION_MESSAGE_CSS, "Notification message");
    }

    public LoginForm clickLogIn() {
        $(LOGIN_CSS).shouldBe(Condition.interactable).click();
        return new LoginForm();
    }

}
