package com.pm.mobile.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;

import org.openqa.selenium.By;

public class VirtualSportPage extends Form {
    private static final By VIRTUAL_SPORTS_ACC_ID = accessibilityId("virtual-sports");
    private static final By LEAGUE_MODE_ACC_ID = accessibilityId("Virtual Football League Mode");

    public VirtualSportPage() {
        super(VIRTUAL_SPORTS_ACC_ID, "Virtual sport page");
    }

    public LeagueModePage clickLeagueMode() {
        waitForExpectedElement(LEAGUE_MODE_ACC_ID).click();
        return new LeagueModePage();
    }
}
