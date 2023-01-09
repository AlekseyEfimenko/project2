package com.pm.mobile.pages;

import static io.appium.java_client.AppiumBy.xpath;

import com.pm.mobile.configuration.Direction;
import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
import com.pm.utils.DataManager;
import com.pm.utils.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class LeagueModePage extends Form {
    private static final Logger LOG = LogManager.getRootLogger();
    private static final By LEAGUE_MODE_XPATH = xpath("//*[@resource-id = 'vsm-videoplayer_vflm']");
    private static final By TIMER_XPATH =
            xpath("//*[@class = 'android.widget.Image']/parent::*//*[@class = 'android.widget.TextView']");
    private static final By ODD_XPATH =
            xpath("//*[contains(@resource-id, 'fulltimeresult')]//*[@class = 'android.widget.TextView'][last()]");
    private static final By MATCH_XPATH =
            xpath("(//*[@resource-id = 'vsm_vflmMatchlistSwiper']/" +
                    "android.view.View/android.view.View)[1]/android.view.View/android.view.View[2]");
    private static final By BET_SLIP_XPATH = xpath("//*[@resource-id = 'betslip']/android.widget.Button");
    private static final int MIN_TIMER = 20;
    private static final int EXTRA_TIME_WAIT = 3;
    private static final int MATCHES_PER_DAY = 8;

    public LeagueModePage() {
        super(LEAGUE_MODE_XPATH, "Virtual Football League Mode page");
    }

    public void addBets(int number) {
        checkTimer();
        DriverManager.scrollToEnd(Direction.DOWN);
        List<String> odds = new ArrayList<>();

        DataManager.getRandomList(getMatches(), number).forEach(element -> {
            WebElement odd = DataManager.getRandomFromList(element.findElements(ODD_XPATH));
            odd.click();
            DriverManager.pause(2);
            odds.add(odd.getText());
        });

        ScenarioContext.setContext(Context.ODDS, odds);
    }

    private List<WebElement> getMatches() {
        return DataManager.getLastElements(waitForExpectedElements(MATCH_XPATH), MATCHES_PER_DAY);
    }

    private void checkTimer() {
        LOG.info("Checking if timer value is not less then {}", MIN_TIMER);
        try {
            WebElement timer = DriverManager.getDriver().findElement(TIMER_XPATH);
            int timeLeft = Integer.parseInt(timer.getText());
            if (timeLeft < MIN_TIMER) {
                DriverManager.pause(timeLeft + EXTRA_TIME_WAIT);
            }
        } catch (NoSuchElementException | StaleElementReferenceException | NumberFormatException ex) {
            LOG.info("Timer is absent");
        }
    }

    public BetSlipForm clickBetSlip() {
        waitForExpectedElement(BET_SLIP_XPATH).click();
        return new BetSlipForm();
    }
}
