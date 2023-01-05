package com.pm.ui.pages;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.interactable;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
import com.pm.utils.BrowserManager;
import com.pm.utils.DataManager;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class LeagueModePage extends Form {
    private static final By TITLE_PAGE_XPATH = By
            .xpath("//*[@id = 'virtual-sports']//*[text() = 'Virtual Football League Mode']");
    private static final By CURRENT_MATCH_DAY_CLASS = By.className("currentMatchday");
    private static final By NEXT_MATCH_DAY_CSS = By.cssSelector(".currentMatchday + div");
    private static final By FIRST_MATCH_DAY_ID = By.id("md31");
    private static final By ODDS_LIST_CLASS = By.className("sr-oddsButton");
    private static final By GAMES_LIST_CLASS = By.className("sr-matchRow");
    private static final By SELECTED_ODDS_CSS = By.cssSelector(".sr-selected");
    private static final By DEFAULT_IFRAME_CSS = By.cssSelector("#virtual-sports iframe");
    private static final String VISUAL_IFRAME_ID = "vsm-ea-iframe";
    private static final String MAX_MATCH_DAY = "30";
    private static final String SCROLL_SCRIPT = "arguments[0].scrollIntoView();";

    public LeagueModePage() {
        super(TITLE_PAGE_XPATH, "Virtual football league mode page");
    }

    public void selectNextMatchDay() {
        switchToDefaultIframe();
        switchToVisualIframe();
        if (waitForElement(CURRENT_MATCH_DAY_CLASS).shouldBe(Condition.appear).text().equals(MAX_MATCH_DAY)) {
            waitForElement(FIRST_MATCH_DAY_ID).click();
        } else {
            waitForElement(NEXT_MATCH_DAY_CSS).shouldBe(Condition.appear).click();
        }
    }

    public BetSlipForm addRandomOdds(int quantity) {
        BrowserManager.executeScript(SCROLL_SCRIPT, waitForElement(CURRENT_MATCH_DAY_CLASS));
        BrowserManager.exitFrame();
        switchToDefaultIframe();

        List<String> odds = new ArrayList<>();
        ScenarioContext.setContext(Context.ODDS, odds);

        DataManager.getRandomList(getListOfMatchDayGames(), quantity).forEach(element -> {
            SelenideElement randomElement = DataManager.getRandomFromList(getListOfAvailableOdds(element));
            randomElement.shouldBe(interactable).click();
        });

        $$(SELECTED_ODDS_CSS).asDynamicIterable().forEach(element -> odds.add(element.text()));

        return new BetSlipForm();
    }

    private List<SelenideElement> getListOfAvailableOdds(SelenideElement element) {
        return element.$$(ODDS_LIST_CLASS).shouldBe(CollectionCondition.size(8));
    }

    private List<SelenideElement> getListOfMatchDayGames() {
        return $$(GAMES_LIST_CLASS).shouldBe(CollectionCondition.size(8));
    }

    private void switchToDefaultIframe() {
        BrowserManager.switchToFrame(waitForElement(DEFAULT_IFRAME_CSS));
    }

    private void switchToVisualIframe() {
        BrowserManager.switchToFrame(VISUAL_IFRAME_ID);
    }
}
