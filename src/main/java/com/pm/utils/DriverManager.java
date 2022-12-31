package com.pm.utils;

import com.pm.mobile.EventLogListener;
import com.pm.mobile.configuration.CapabilitiesConfig;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public final class DriverManager {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final String LOCAL_PATH = FileManager.getData().localPath();

    private static final ThreadLocal<AppiumDriver<MobileElement>> threadLocalDriver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static void createDriver(String udid, String deviceName, String platformVersion) {
        AppiumDriver<MobileElement> driver = null;
        try {
            driver = new AndroidDriver<>(new URL(LOCAL_PATH),
                    CapabilitiesConfig.getAndroidLocalCapabilities(udid, deviceName, platformVersion));
        } catch (MalformedURLException ex) {
            LOG.error("Can not create driver. May be {} is null or it can not parse URL with the specific syntax",
                    "LOCAL_PATH");
        }
        driver = EventFiringWebDriverFactory.getEventFiringWebDriver(driver, new EventLogListener());
        threadLocalDriver.set(driver);
        LOG.info("Driver is created");
    }

    public static AppiumDriver<MobileElement> getDriver() {
        return threadLocalDriver.get();
    }

    public static void terminateDriver() {
        Optional.ofNullable(getDriver()).ifPresent((AppiumDriver<MobileElement> driver) -> {
            driver.quit();
            threadLocalDriver.remove();
            LOG.info("Driver is closed");
        });
    }

//    public static void terminateAppium() {
//        AppiumServerConfigurator.stopServer();
//    }
//
//    public static void terminateEmulator(String udid) {
//        new Emulator(udid).close();
//    }
}
