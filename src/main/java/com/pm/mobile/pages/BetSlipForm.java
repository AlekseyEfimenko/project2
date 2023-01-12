package com.pm.mobile.pages;

import static io.appium.java_client.AppiumBy.xpath;
import static com.pm.mobile.configuration.Direction.UP;

import com.pm.utils.DataManager;
import com.pm.utils.DriverManager;
import org.openqa.selenium.By;

import java.util.List;

public class BetSlipForm extends Form {
    private static final By BET_SLIP_XPATH = xpath("//*[@resource-id = 'betslip']/android.view.View");
    private static final By BET_SLIP_CONTAINER_XPATH = xpath("//*[@resource-id = 'sr-bs-show-betslip']");
    private static final By ODD_XPATH =
            xpath("//*[@resource-id = 'betslip']//android.widget.ListView//(android.widget.TextView)[last()]");
    private static final By MULTI_XPATH =
            xpath("(//*[@resource-id = 'betslip']//android.widget.ListView/parent::*//android.widget.Button)[2]");
    private static final By TICKET_STAKE_XPATH = xpath("//*[@resource-id = 'multi']");
    private static final By SYSTEM_TICKET_STAKE_XPATH = xpath("//*[@resource-id = '2/3']");
    private static final By TOTAL_ODDS_XPATH =
            xpath("((//*[@resource-id = 'betslip']/android.view.View/android.view.View)[last()]/android.widget.TextView)[1]");
    private static final By POSSIBLE_WINNNINGS_XPATH =
            xpath("(//*[@resource-id = 'betslip']//android.widget.TextView)[last()]");
    private static final By PLACE_BET_XPATH = xpath("(//*[@resource-id = 'betslip']//android.widget.Button)[last()]");
    private static final By NOT_ENOUGH_MONEY_MESSAGE_XPATH =
            xpath("(//*[@resource-id = 'betslip']//android.widget.TextView)[last()]");
    private static final By REMOVE_ALL_XPATH =
            xpath("(//*[@resource-id = 'betslip']//android.widget.ListView/parent::*/android.view.View)[2]/android.widget.Button");
    private static final By REMOVE_BET_XPATH =
            xpath("//*[@resource-id = 'betslip']//android.widget.ListView//following-sibling::android.widget.Button");
    private static final By EXIT_BET_SLIP_XPATH =
            xpath("//*[@resource-id = 'sr-bs-show-history']//following-sibling::android.widget.Button");
    private static final By SYSTEM_XPATH =
        xpath("(//*[@resource-id = 'betslip']//android.widget.ListView/parent::*//android.widget.Button)[3]");

    public BetSlipForm() {
        super(BET_SLIP_XPATH, "Bet slip form");
    }

    public int getBetsQuantity() {
        return DataManager.getNumOfBets(waitForExpectedElement(BET_SLIP_CONTAINER_XPATH).getText());
    }

    public List<String> getOdds() {
        return DataManager.getListOfTextsMobile(waitForExpectedElements(ODD_XPATH));
    }

    public void selectMulti() {
        waitForExpectedElement(MULTI_XPATH).click();
    }

    public void setStake(String stake) {
        waitForExpectedElement(TICKET_STAKE_XPATH).sendKeys(stake);
    }

    public String getTotalOdds() {
        return waitForExpectedElement(TOTAL_ODDS_XPATH).getText();
    }

    public String getPossibleWinnings() {
        return waitForExpectedElement(POSSIBLE_WINNNINGS_XPATH).getText();
    }

    public void placeBet() {
        //to close appeared keyboard we must click somewhere on the screen
        selectMulti();

        waitForExpectedElement(PLACE_BET_XPATH).click();
    }

    public String getErrorMessage() {
        return waitForExpectedElement(NOT_ENOUGH_MONEY_MESSAGE_XPATH).getText();
    }

    public void clearBetSlip() {
        waitForExpectedElement(REMOVE_ALL_XPATH).click();
    }

    public void exitBetSlip() {
        waitForExpectedElement(EXIT_BET_SLIP_XPATH).click();
        DriverManager.scrollToEnd(UP);
    }

    public void removeOdd() {
        DataManager.getRandomFromList(waitForExpectedElements(REMOVE_BET_XPATH)).click();
    }

    public int getNumberOfBets() {
        return waitForExpectedElements(ODD_XPATH).size();
    }
    public void selectSystem() {
        waitForExpectedElement(SYSTEM_XPATH).click();
    }

    public void setSystemStake(String stake) {
        waitForExpectedElement(SYSTEM_TICKET_STAKE_XPATH).sendKeys(stake);
    }
}
