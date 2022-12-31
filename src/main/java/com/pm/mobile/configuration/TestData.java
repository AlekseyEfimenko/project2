package com.pm.mobile.configuration;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.Sources;

@LoadPolicy(Config.LoadType.MERGE)
@Sources({"classpath:data/testdata.properties"})
public interface TestData extends Config {
    // Hub addresses
    @Key("local.ipAddress")
    String ipAddress();

    @Key("local.appiumPort")
    int appiumPort();

    @Key("local.path")
    String localPath();

    // Available branches: 'master', 'staging' or 'develop'
    @DefaultValue(EMPTY)
    String targetBranchName();

    // Title for getting app's slug depending on OS: 'EPM-AMI iOS/Android Maria Putyrskaya'
    @DefaultValue(EMPTY)
    String targetAppTitle();

    // App's name in 'src/main/resources/apps' directory: 'prod.apk', 'staging.apk' or 'qa.apk'
    @DefaultValue(EMPTY)
    String existingAppTitle();

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
}
