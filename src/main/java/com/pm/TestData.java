package com.pm;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({"classpath:data/testdata.properties"})
public interface TestData extends Config {
    @Key("local.path")
    String localPath();

    // Timeouts
    @Key("implicitWait")
    int implicitWait();

    @Key("lowerImplicitWait")
    int lowerImplicitWait();

    @Key("explicitWait")
    int explicitWait();

    @Key("longWait")
    int longWait();

    @Key("newCommandTimeout")
    int newCommandTimeout();

    @Key("pollingValue")
    int pollingValue();

    @Key("ui.startPage")
    String startPage();

    @Key("browser")
    String browser();

    @Key("basePath")
    String basePath();

    @Key("selenideTimeout")
    long selenideTimeout();

    @Key("appium.api.path")
    String appiumApiPath();

    @Key("user-agent")
    String userAgent();

    @Key("browserSize")
    String browserSize();
}
