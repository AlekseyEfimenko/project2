package com.pm.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
import com.pm.utils.DataManager;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class LeagueModePage extends Form {
    private static final By TITLE_PAGE_XPATH = By
            .xpath("//*[@id = 'virtual-sports']//*[text() = 'Virtual Football League Mode']");
    private static final By CURRENT_MATCH_DAY_CLASS = By.className("currentMatchday ");
    private static final By NEXT_MATCH_DAY_CSS = By.cssSelector("currentMatchday + div");
    private static final By FIRST_MATH_DAY_ID = By.id("md1");
    private static final By ODDS_LIST_CLASS = By.className("sr-oddsButton");
    private static final By GAMES_LIST_CLASS = By.className("sr-matchRow");
    private static final String MAX_MATCH_DAY = "30";

    public LeagueModePage() {
        super(TITLE_PAGE_XPATH, "Virtual football league mode page");
    }

    public void selectNextMatchDay() {
        if (waitForElement(CURRENT_MATCH_DAY_CLASS).text().equals(MAX_MATCH_DAY)) {
            $(FIRST_MATH_DAY_ID).shouldBe(Condition.interactable).click();
        } else {
            $(NEXT_MATCH_DAY_CSS).shouldBe(Condition.interactable).click();
        }
    }

    public BetSlipForm addRandomOdds(int quantity) {
        List<String> odds = new ArrayList<>();
        ScenarioContext.setContext(Context.ODDS, odds);
        DataManager.getRandomList(getListOfMatchDayGames(), quantity).forEach(element -> {
            SelenideElement randomElement = DataManager.getRandomFromList(getListOfAvailableOdds(element));
            odds.add(element.text());
            randomElement.click();
        });
        return new BetSlipForm();
    }

    private List<SelenideElement> getListOfAvailableOdds(SelenideElement element) {
        return element.$$(ODDS_LIST_CLASS);
    }

    private List<SelenideElement> getListOfMatchDayGames() {
        return $$(GAMES_LIST_CLASS);
    }
}
