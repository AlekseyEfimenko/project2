package com.pm.utils;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.clearBrowserCookies;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class BrowserManager {
    private static final Logger LOG = LogManager.getRootLogger();

    private BrowserManager() {
    }

    /**
     * Opens the page in the browser
     *
     * @param url The page address to be opened
     */
    public static void navigateTo(String url) {
        LOG.info("Navigate to the {} page", url);
        open(url);
    }

    /**
     * Switches to the iframe by the element
     *
     * @param element The element to switch
     */
    public static void switchToFrame(SelenideElement element) {
        LOG.info("Switching to the iframe {}", element);
        Selenide.switchTo().frame(element);
    }

    /**
     * Switches to the iframe by the index
     *
     * @param id The index of frame to switch
     */
    public static void switchToFrame(String id) {
        LOG.info("Switching to the iframe with id {}", id);
        Selenide.switchTo().frame(id, Duration.ofSeconds(FileManager.getData().explicitWait()));
    }

    /**
     * Gets source of the current page
     *
     * @return Page's source in String format
     */
    public static String getSource() {
        LOG.info("Getting page source");
        return Selenide.webdriver().driver().source();
    }

    /**
     * Exits from the current iframe on the level higher
     */
    public static void exitFrame() {
        LOG.info("Exiting from the current iframe");
        Selenide.switchTo().defaultContent();
    }

    /**
     * Sets the pause in the script
     *
     * @param milliseconds The duration of the pause
     */
    public static void pause(long milliseconds) {
        Selenide.sleep(milliseconds);
    }

    /**
     * Deletes all cookies
     */
    public static void clearAllCookies() {
        LOG.info("Clearing all cookies");
        clearBrowserCookies();
    }

    /**
     * Executes JavaScript command
     *
     * @param script The script to be executed
     * @param <T> The type of Object to be obtained
     * @return Object of type T
     */
    public static <T> T executeScript(String script) {
        return Selenide.executeJavaScript(script);
    }
}
