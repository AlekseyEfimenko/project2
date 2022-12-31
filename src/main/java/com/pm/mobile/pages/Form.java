package com.pm.mobile.pages;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;

import com.pm.utils.DriverManager;
import com.pm.utils.FileManager;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Form {
    protected Form() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    public boolean isElementDisplayed(MobileElement element) {
        return waitForExpectedElement(element).isDisplayed();
    }

    public MobileElement waitForExpectedElement(MobileElement mobileElement) {
        return waitForElementExplicitly(FileManager.getData().explicitWait(), visibilityOf(mobileElement));
    }

    private static MobileElement waitForElementExplicitly(int waitValue, ExpectedCondition<?> isTrue) {
        return (MobileElement) new WebDriverWait(DriverManager.getDriver(), waitValue).until(isTrue);
    }
}
