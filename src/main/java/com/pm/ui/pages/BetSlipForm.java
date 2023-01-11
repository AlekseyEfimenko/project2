package com.pm.ui.pages;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.exactText;

import com.pm.utils.BrowserManager;
import com.pm.utils.DataManager;
import org.openqa.selenium.By;

import java.util.List;

public class BetSlipForm extends Form {
    private static final By BET_SLIP_ID = By.id("betslip");
    private static final By BET_SLIP_CONTAINER_ID = By.id("sr-bs-show-betslip");
    private static final By ODDS_CLASS = By.className("sr-bs-line__odds");
    private static final By MULTI_XPATH =
            By.xpath("//*[@class ='sr-bs-systems-nav']/button[contains(text(), \"Multi\")]");
    private static final By TICKET_STAKE_ID = By.id("multi");
    private static final By TICKET_STAKE_SINGLES = By.id("singles");
    private static final By SYSTEM_TICKET_STAKE_ID = By.id("2/3");
    private static final By TOTAL_ODDS_XPATH =
            By.xpath("//*[@class = 'sr-bs-footer__information']//span[@class = 'sr-bs-currency-value']");
    private static final By POSSIBLE_WINNNINGS_XPATH =
            By.xpath("//*[@class = 'sr-bs-footer__information']//span[@class = 'sr-bs-currency-value  ']");
    private static final By PLACE_BET_CLASS = By.className("sr-bs-footer__button");
    private static final By NOT_ENOUGH_MONEY_MESSAGE_CSS = By.cssSelector(".sr-bs-footer .sr-bs-message--error");
    private static final By REMOVE_ALL_CSS = By.cssSelector(".sr-bs-remove-btn--remove-all");
    private static final By REMOVE_SINGLES_ODD =  By.cssSelector(".sr-bs-remove-btn");
    private static final By REMOVE_BET_CSS = By.cssSelector(".sr-bs-line .sr-bs-remove-btn ");
    private static final By BET_CSS = By.cssSelector(".sr-bs-line ");
    private static final String MULTI_BUTTON_CSS = ".sr-bs-systems-nav__button--active";
    private static final String GET_AFTER_SCRIPT = String.format(
            "return window.getComputedStyle(document.querySelector('%1$s'),':after').getPropertyValue('background-color');",
            MULTI_BUTTON_CSS);
    private static final String CLICK_SCRIPT = "arguments[0].click();";
    private static final String SCROLL_SCRIPT = "arguments[0].scrollIntoView();";
    private static final By SYSTEM_XPATH = By.xpath("//*[@class ='sr-bs-systems-nav']/button[contains(text(), \"System\")]");
    private static final String SYSTEM_BUTTON_CSS = ".sr-bs-systems-nav__button--active";
    private static final String GET_AFTER_SCRIPT_SYSTEM = String.format(
        "return window.getComputedStyle(document.querySelector('%1$s'),':after').getPropertyValue('background-color');",
        SYSTEM_BUTTON_CSS);

    //private static final By PLACE_BET_BUTTON = By.cssSelector("#betslip > div > form > footer > div.sr-bs-footer-button-container > button");

    public BetSlipForm() {
        super(BET_SLIP_ID, "Bets slip form");
    }

    public int getBetsQuantity() {
        BrowserManager.executeScript(SCROLL_SCRIPT, waitForElement(BET_SLIP_CONTAINER_ID));
        return DataManager.getNumOfBets(waitForElement(BET_SLIP_CONTAINER_ID).text());
    }

    public List<String> getOdds() {
        return DataManager.getListOfTexts($$(ODDS_CLASS));
    }

    public void selectMulti() {
        BrowserManager.executeScript(CLICK_SCRIPT, waitForElement(MULTI_XPATH));
    }

    public String getMultiUnderlineColor() {
        return BrowserManager.executeScript(GET_AFTER_SCRIPT);
    }

    public void setStake(String stake) {
        waitForElement(TICKET_STAKE_ID).setValue(stake);
    }
    public void setSinglesStake(String stake) {
        waitForElement(TICKET_STAKE_SINGLES).setValue(stake);
    }

    public String getTotalOdds() {
        return waitForElement(TOTAL_ODDS_XPATH).shouldNotBe(exactText("0.00")).text();
    }

    public String getPossibleWinnings() {
        return waitForElement(POSSIBLE_WINNNINGS_XPATH).text();
    }

    public void placeBet() {
        waitForElement(PLACE_BET_CLASS).click();
    }
    public boolean getButtonState() {
        return waitForElement(PLACE_BET_CLASS).isEnabled();
    }

    public String getErrorMessage() {
        return waitForElement(NOT_ENOUGH_MONEY_MESSAGE_CSS).text();
    }

    public void clearBetSlip() {
        waitForElement(REMOVE_ALL_CSS).click();
    }
    public void clearOneOdd() {
        waitForElement(REMOVE_SINGLES_ODD).click();
    }
    public void removeOdd() {
        DataManager.getRandomFromList($$(REMOVE_BET_CSS)).click();
    }

    public int getNumberOfBets() {
        return $$(BET_CSS).size();
    }

    public void selectSystem() {
        BrowserManager.executeScript(CLICK_SCRIPT, waitForElement(SYSTEM_XPATH));
    }

    public String getSystemUnderlineColor() {
        return BrowserManager.executeScript(GET_AFTER_SCRIPT_SYSTEM);
    }

    public void setSystemStake(String stake) {
        waitForElement(SYSTEM_TICKET_STAKE_ID).setValue(stake);
    }

}
