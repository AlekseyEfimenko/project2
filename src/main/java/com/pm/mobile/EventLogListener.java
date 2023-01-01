package com.pm.mobile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

public class EventLogListener implements WebDriverListener {
    private static final Logger LOG = LogManager.getRootLogger();

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        LOG.info("Trying to find element by the locator: {}", locator);
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        LOG.info("Trying to find elements by the locator: {}", locator);
    }

    @Override
    public void beforeQuit(WebDriver driver) {
        LOG.info("Closing the driver {}", driver);
    }

    @Override
    public void afterQuit(WebDriver driver) {
        LOG.info("The driver {} is closed", driver);
    }

    @Override
    public void beforeClick(WebElement element) {
        LOG.info("Clicking on the element: {}", element);
    }

    @Override
    public void afterClick(WebElement element) {
        LOG.info("Element <{}> is clicked", element);
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        LOG.info("Sending the text <{}> to the element <{}>", keysToSend, element);
    }

    @Override
    public void beforeClear(WebElement element) {
        LOG.info("Clearing the textfield in the element <{}>", element);
    }

    @Override
    public void beforeIsDisplayed(WebElement element) {
        LOG.info("Check if element <{}> is displayed", element);
    }
}
