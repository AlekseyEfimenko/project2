package com.pm.ui.steps;

import static org.assertj.core.api.Assertions.assertThat;

import com.pm.ui.pages.LoginForm;
import com.pm.ui.pages.MessageForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestSteps {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String CHECK_PAGE_MESSAGE = "Checking if {} is opened";
    private static final String ERROR_MESSAGE = "The {} is not opened";
    private static final String SUCCESS_MESSAGE = String.format("%1$s  SUCCESS  %1$s%n", "=".repeat(50));
    private final MessageForm messageForm = new MessageForm();
    private LoginForm loginForm;

    public void clickLogIn() {
        LOG.info("Clicking the \"Log In\" button");
        loginForm = messageForm.clickLogIn();
    }

    public void assertLoginFormIsOpened() {
        LOG.info(CHECK_PAGE_MESSAGE, loginForm.getName());
        assertThat(loginForm.isDisplayed())
                .as(ERROR_MESSAGE, loginForm.getName())
                .isTrue();
        LOG.info(SUCCESS_MESSAGE);
    }
}
