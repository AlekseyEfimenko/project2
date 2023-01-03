package com.pm.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import java.util.List;

public class EventLogListener implements WebDriverListener {
    private static final Logger LOG = LogManager.getRootLogger();
    private boolean isLog = true;

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        if (isLog) {
            LOG.info("Trying to find element {}", locator);
            isLog = false;
        }
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        isLog = true;
    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
        if (isLog) {
            LOG.info("Trying to find elements {}", locator);
            isLog = false;
        }
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
        isLog = true;
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
        LOG.info("Clicking on the element <{}>", getLocator(element));
    }

    @Override
    public void afterClick(WebElement element) {
        LOG.info("Element <{}> is clicked", getLocator(element));
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        LOG.info("Sending the text <{}> to the element <{}>", keysToSend, getLocator(element));
    }

    @Override
    public void beforeClear(WebElement element) {
        LOG.info("Clearing the textfield in the element <{}>", getLocator(element));
    }

    private String getLocator(WebElement element) {
        return element.toString()
                .substring(element.toString().indexOf("->") + 3,
                        element.toString().length() - 1);
    }
}
