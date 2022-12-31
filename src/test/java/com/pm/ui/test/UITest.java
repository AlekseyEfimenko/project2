package com.pm.ui.test;

import org.testng.annotations.Test;

public class UITest extends UIBaseTest {

    @Test
    public void testFramework() {
        steps.clickLogIn();
        steps.assertLoginFormIsOpened();

        System.out.println("UI build is successful!");
    }
}
