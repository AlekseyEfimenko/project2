package com.pm.ui.pages;

import com.codeborne.selenide.Condition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public abstract class Form {
    private static final Logger LOG = LogManager.getRootLogger();
    private final By locator;
    private final String name;

    protected Form(By locator, String name) {
        this.locator = locator;
        this.name = name;
    }

    public By getLocator() {
        return this.locator;
    }

    public String getName() {
        return this.name;
    }

    public boolean isDisplayed() {
        LOG.info("Check if element with locator <{}> is displayed", locator);
        return $(locator).should(Condition.visible).isDisplayed();
    }
}
