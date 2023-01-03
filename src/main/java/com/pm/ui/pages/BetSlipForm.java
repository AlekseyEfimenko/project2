package com.pm.ui.pages;

import com.codeborne.selenide.Condition;
import com.pm.utils.BrowserManager;
import com.pm.utils.DataManager;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

import java.util.List;

public class BetSlipForm extends Form {
    private static final By BET_SLIP_ID = By.id("betslip");
    private static final By BET_SLIP_CONTAINER_ID = By.id("sr-bs-show-betslip");
    private static final By ODDS_CLASS = By.className("sr-bs-line__odds");
    private static final By MULTI_XPATH =
            By.xpath("//*[@class ='sr-bs-systems-nav']/button[contains(text(), \"Multi\")]");
    private static final By TICKET_STAKE_ID = By.id("multi");
    private static final By TOTAL_ODDS_XPATH =
            By.xpath("//*[@class = 'sr-bs-footer__information']//span[@class = 'sr-bs-currency-value']");
    private static final By POSSIBLE_WINNNINGS_XPATH =
            By.xpath("//*[@class = 'sr-bs-footer__information']//span[@class = 'sr-bs-currency-value  ']");
    private static final By PLACE_BET_CLASS = By.className("sr-bs-footer__button");
    private static final String MULTI_BUTTON_CSS = ".sr-bs-systems-nav__button--active";
    private static final String SCRIPT = String.format(
            "return window.getComputedStyle(document.querySelector('%1$s'),':after').getPropertyValue('background-color')\";",
            MULTI_BUTTON_CSS);


    public BetSlipForm() {
        super(BET_SLIP_ID, "Bets slip form");
    }

    public int getBetsQuantity() {
        return DataManager.getNumOfBets(waitForElement(BET_SLIP_CONTAINER_ID).text());
    }

    public List<String> getOdds() {
        return DataManager.getListOfTexts($$(ODDS_CLASS));
    }

    public void selectMulti() {
        waitForElement(MULTI_XPATH).shouldHave(Condition.enabled).click();
    }

    public String getMultiUnderlineColor() {
        return BrowserManager.executeScript(SCRIPT);
    }

    public void setStake(String stake) {
        waitForElement(TICKET_STAKE_ID).setValue(stake);
    }

    public String getTotalOdds() {
        return waitForElement(TOTAL_ODDS_XPATH).text();
    }

    public String getPossibleWinnings() {
        return waitForElement(POSSIBLE_WINNNINGS_XPATH).text();
    }

    public void placeBet() {
        waitForElement(PLACE_BET_CLASS).click();
    }
}
