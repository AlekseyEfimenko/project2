package com.pm.mobile.configuration;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.NEW_COMMAND_TIMEOUT;
import static io.appium.java_client.remote.MobileCapabilityType.NO_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static io.appium.java_client.remote.MobileCapabilityType.UDID;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

import com.pm.utils.FileManager;
import org.openqa.selenium.remote.DesiredCapabilities;

public final class CapabilitiesConfig {
    private static final String KEEP_DEVICE = "keepDevice";

    private CapabilitiesConfig() {
    }

    public static DesiredCapabilities getAndroidLocalCapabilities(String udid, String deviceName, String platformVersion) {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability(APP, FileManager.getAppLocation());
        capabilities.setCapability(AVD, deviceName == null ? FileManager.getCapability().deviceName() : deviceName);
        setAndroidCommonCapabilities(capabilities);
        setCommonCapabilities(capabilities, udid, deviceName, platformVersion);
        return capabilities;
    }

    private static void setCommonCapabilities(DesiredCapabilities capabilities, String udid, String deviceName, String platformVersion) {
        capabilities.setCapability(DEVICE_NAME, deviceName == null ? FileManager.getCapability().deviceName() : deviceName);
        capabilities.setCapability(PLATFORM_NAME, FileManager.getCapability().platformName());
        capabilities.setCapability(PLATFORM_VERSION, platformVersion == null ? FileManager.getCapability().platformVersion() : platformVersion);
        capabilities.setCapability(UDID, udid == null ? FileManager.getCapability().udid() : udid);
        capabilities.setCapability(NO_RESET, false);
        capabilities.setCapability(KEEP_DEVICE, true);
        capabilities.setCapability(NEW_COMMAND_TIMEOUT, FileManager.getData().newCommandTimeout());
        capabilities.setCapability(AUTO_GRANT_PERMISSIONS, FileManager.getCapability().autoGrantPermissions());
    }

    private static void setAndroidCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(APP_PACKAGE, FileManager.getCapability().appPackage());
    }
}
