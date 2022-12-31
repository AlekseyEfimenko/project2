package com.pm.mobile.test;

import com.pm.mobile.steps.TestSteps;
import com.pm.utils.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;


public class MobileBaseTest {
    protected TestSteps steps = new TestSteps();

    @BeforeTest(alwaysRun = true)
    @Parameters({"UDID", "DeviceName", "PlatformVersion"})
    public void setupSession(@Optional String udid,
                             @Optional String deviceName,
                             @Optional String platformVersion) {
        DriverManager.createDriver(udid, deviceName, platformVersion);
    }

    @AfterTest(alwaysRun = true)
    @Parameters("UDID")
    public void closeSession(@Optional String udid) {
        DriverManager.terminateDriver();
//        DriverManager.terminateAppium();
//        DriverManager.terminateEmulator(udid);
    }

    @AfterMethod(alwaysRun = true)
    public void resetApp() {
        DriverManager.getDriver();
    }
}
