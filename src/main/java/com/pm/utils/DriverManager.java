package com.pm.utils;

import static com.pm.temp.Context.SESSION_ID;

import com.pm.listener.EventLogListener;
import com.pm.mobile.configuration.CapabilitiesConfig;
import com.pm.mobile.configuration.Direction;
import com.pm.temp.ScenarioContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;

public final class DriverManager {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String LOCAL_PATH = FileManager.getData().localPath();

    private static final ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver(String udid, String deviceName, String platformVersion) {
        AppiumDriver driver = null;
        try {
            driver = new AndroidDriver(new URL(LOCAL_PATH),
                    CapabilitiesConfig.getAndroidLocalCapabilities(udid, deviceName, platformVersion));
            ScenarioContext.setContext(SESSION_ID, driver.getSessionId().toString());
            CapabilitiesConfig.setAppiumCapabilities();
        } catch (MalformedURLException ex) {
            LOG.error("Can not create driver. May be {} is null or it can not parse URL with the specific syntax",
                    LOCAL_PATH);
        }
        assert driver != null;
        WebDriver decoratedDriver = new EventFiringDecorator<AppiumDriver>(new EventLogListener()).decorate(driver);
        threadLocalDriver.set(decoratedDriver);
        LOG.info("Driver is created");
    }

    public static WebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public static void terminateDriver() {
        Optional.ofNullable(getDriver()).ifPresent((WebDriver driver) -> {
            driver.quit();
            threadLocalDriver.remove();
        });
    }

    public static boolean scroll(Direction direction) {
        LOG.info("Scrolling {}", direction);
        Dimension dmn = getDriver().manage().window().getSize();
        return (Boolean) ((JavascriptExecutor) getDriver()).executeScript("mobile: scrollGesture", Map.of(
                "left", dmn.width / 2, "top", dmn.height / 2,
                "width", dmn.width / 4, "height", dmn.height / 4,
                "direction", direction.getValue(),
                "percent", 100.0
        ));
    }

    public static void scrollToEnd(Direction direction) {
        boolean scroll;
        do {
            scroll(direction);
            scroll(direction);
            scroll = scroll(direction);
        } while (scroll);
    }

    public static void pause(int seconds) {
        LOG.info("Waiting for {} seconds", seconds);
        try {
            synchronized (getDriver()) {
                getDriver().wait(seconds * 1000L);
            }
        } catch (InterruptedException e) {
            LOG.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
