package com.pm.mobile.configuration;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:data/capability.properties"})
public interface Capabilities extends Config {

    // Desired capabilities
    @Key("app")
    String app();

    @Key("platformName")
    String platformName();

    @Key("platformVersion")
    String platformVersion();

    @Key("deviceName")
    String deviceName();

    @Key("appPackage")
    String appPackage();

    @Key("udid")
    String udid();

    @Key("autoGrantPermissions")
    boolean autoGrantPermissions();

    @Key("enableMultiWindows")
    boolean enableMultiWindows();

    @Key("allowInvisibleElements")
    boolean allowInvisibleElements();

    @Key("ignoreUnimportantViews")
    boolean ignoreUnimportantViews();
}
