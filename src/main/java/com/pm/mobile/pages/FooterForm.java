package com.pm.mobile.pages;

import static io.appium.java_client.AppiumBy.accessibilityId;

import org.openqa.selenium.By;

public class FooterForm extends Form {
    private static final By FOOTER_ID = accessibilityId("bottomNavigation");
    private static final By MENU_ACC_ID = accessibilityId("menu-tab");
    private static final By VIRTUAL_SPORT_ACC_ID = accessibilityId("virtualSports");

    public FooterForm() {
        super(FOOTER_ID, "Footer navigation form");
    }

    public void clickMenu() {
        waitForExpectedElement(MENU_ACC_ID).click();
    }

    public VirtualSportPage clickVirtualSport() {
        waitForExpectedElement(VIRTUAL_SPORT_ACC_ID).click();
        return new VirtualSportPage();
    }
}
