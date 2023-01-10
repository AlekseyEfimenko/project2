package com.pm.mobile.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static com.pm.utils.FileManager.getData;
import static com.pm.utils.DriverManager.getDriver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.List;

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
        return waitForElementExplicitly(getData().explicitWait(),
                getData().pollingValue(),
                presenceOfElementLocated(locator));
    }

    public List<WebElement> waitForExpectedElements(By locator) {
        return waitForElementsExplicitly(getData().explicitWait(),
                getData().pollingValue(),
                presenceOfAllElementsLocatedBy(locator));
    }

    private static WebElement waitForElementExplicitly(int waitValue, int pollyngValue, ExpectedCondition<?> isTrue) {
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(waitValue))
                .pollingEvery(Duration.ofMillis(pollyngValue))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class);
        return (WebElement) wait.until(isTrue);
    }

    private static List<WebElement> waitForElementsExplicitly(int waitValue, int pollyngValue, ExpectedCondition<?> isTrue) {
        Wait<WebDriver> wait = new FluentWait<>(getDriver())
                .withTimeout(Duration.ofSeconds(waitValue))
                .pollingEvery(Duration.ofMillis(pollyngValue))
                .ignoring(NoSuchElementException.class);
        return (List<WebElement>) wait.until(isTrue);
    }
}
