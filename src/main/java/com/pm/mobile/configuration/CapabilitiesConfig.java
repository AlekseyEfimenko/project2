package com.pm.mobile.configuration;

import static com.pm.mobile.configuration.AppiumCapabilities.ALLOW_INVISIBLE_ELEMENTS;
import static com.pm.mobile.configuration.AppiumCapabilities.ENABLE_MULTI_WINDOWS;
import static com.pm.mobile.configuration.AppiumCapabilities.SETTINGS;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.AVD;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.IGNORE_UNIMPORTANT_VIEWS;
import static io.appium.java_client.remote.MobileCapabilityType.APP;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.NEW_COMMAND_TIMEOUT;
import static io.appium.java_client.remote.MobileCapabilityType.NO_RESET;
import static io.appium.java_client.remote.MobileCapabilityType.PLATFORM_VERSION;
import static io.appium.java_client.remote.MobileCapabilityType.UDID;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;
import static com.pm.temp.Context.SESSION_ID;

import com.google.gson.JsonObject;
import com.pm.temp.ScenarioContext;
import com.pm.utils.ApiManager;
import com.pm.utils.FileManager;
import org.openqa.selenium.remote.DesiredCapabilities;

public final class CapabilitiesConfig {
    private static final String KEEP_DEVICE = "keepDevice";

    private CapabilitiesConfig() {
    }

    public static DesiredCapabilities getAndroidLocalCapabilities() {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability(APP, FileManager.getAppLocation());
        capabilities.setCapability(AVD, FileManager.getCapability().deviceName());
        setAndroidCommonCapabilities(capabilities);
        setCommonCapabilities(capabilities);
        return capabilities;
    }

    private static void setCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(DEVICE_NAME, FileManager.getCapability().deviceName());
        capabilities.setCapability(PLATFORM_NAME, FileManager.getCapability().platformName());
        capabilities.setCapability(PLATFORM_VERSION, FileManager.getCapability().platformVersion());
        capabilities.setCapability(UDID, FileManager.getCapability().udid());
        capabilities.setCapability(NO_RESET, false);
        capabilities.setCapability(KEEP_DEVICE, true);
        capabilities.setCapability(NEW_COMMAND_TIMEOUT, FileManager.getData().newCommandTimeout());
        capabilities.setCapability(AUTO_GRANT_PERMISSIONS, FileManager.getCapability().autoGrantPermissions());
        capabilities.setCapability(IGNORE_UNIMPORTANT_VIEWS, FileManager.getCapability().ignoreUnimportantViews());
    }

    private static void setAndroidCommonCapabilities(DesiredCapabilities capabilities) {
        capabilities.setCapability(APP_PACKAGE, FileManager.getCapability().appPackage());
    }

    public static void setAppiumCapabilities() {
        JsonObject capabilities = new JsonObject();
        capabilities.addProperty(ENABLE_MULTI_WINDOWS.getValue(), FileManager.getCapability().enableMultiWindows());
        capabilities.addProperty(ALLOW_INVISIBLE_ELEMENTS.getValue(), FileManager.getCapability().allowInvisibleElements());

        JsonObject settings = new JsonObject();
        settings.add(SETTINGS.getValue(), capabilities);

        ApiManager.getInstance()
                .postRequestMobile(String.format(FileManager.getData().appiumApiPath(),
                                ScenarioContext.getContext(SESSION_ID)),
                        settings);
    }
}
