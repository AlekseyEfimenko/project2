package com.pm.mobile.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

import com.pm.utils.DriverManager;
import com.pm.utils.FileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

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

    public boolean isElementDisplayed(By locator) {
        LOG.info("Check if element with locator <{}> is displayed", locator);
        return waitForExpectedElement(locator).isDisplayed();
    }

    public WebElement waitForExpectedElement(By locator) {
        return waitForElementExplicitly(FileManager.getData().explicitWait(),
                FileManager.getData().pollingValue(),
                presenceOfElementLocated(locator));
    }

    private static WebElement waitForElementExplicitly(int waitValue, int pollyngValue, ExpectedCondition<?> isTrue) {
        Wait<WebDriver> wait = new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(waitValue))
                .pollingEvery(Duration.ofMillis(pollyngValue))
                .ignoring(NoSuchElementException.class);
        return (WebElement) wait.until(isTrue);
    }
}
