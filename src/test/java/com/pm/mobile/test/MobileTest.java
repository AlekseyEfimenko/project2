package com.pm.mobile.test;

import com.pm.mobile.pages.LoginPage;
import org.testng.annotations.Test;

public class MobileTest extends MobileBaseTest {
    LoginPage loginPage = new LoginPage();

    @Test
    public void testFramework() {
        loginPage.clickButton();
        System.out.println("Mobile build is successful!");
    }
}
