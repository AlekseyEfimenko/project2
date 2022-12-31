package com.pm.ui.pages;

import org.openqa.selenium.By;

public class LeagueModePage extends Form {
    private static final By TITLE_PAGE_XPATH = By
            .xpath("//*[@id = 'virtual-sports']//*[text() = 'Virtual Football League Mode']");

    public LeagueModePage() {
        super(TITLE_PAGE_XPATH, "Virtual football league mode page");
    }
}
