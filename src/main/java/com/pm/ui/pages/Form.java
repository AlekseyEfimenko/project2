package com.pm.ui.pages;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import com.pm.utils.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

import java.time.Duration;

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
        return $(locator).shouldBe(visible).isDisplayed();
    }

    public SelenideElement waitForElement(By locator) {
        return $(locator).shouldBe(interactable, Duration.ofSeconds(FileManager.getData().explicitWait()));
    }
}
