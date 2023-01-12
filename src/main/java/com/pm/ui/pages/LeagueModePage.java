package com.pm.ui.pages;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.interactable;
import static com.codeborne.selenide.Condition.appear;
import static com.pm.temp.Context.ODDS;
import static com.pm.temp.ScenarioContext.setContext;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import com.pm.utils.BrowserManager;
import com.pm.utils.DataManager;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeagueModePage extends Form {
    private static final By TITLE_PAGE_XPATH = By
            .xpath("//*[@id = 'virtual-sports']//*[text() = 'Virtual Football League Mode']");
    private static final By CURRENT_MATCH_DAY_CLASS = By.className("selectedMatchday");
    private static final By NEXT_MATCH_DAY_CSS = By.cssSelector(".selectedMatchday + div");
    private static final By FIRST_MATCH_DAY_ID = By.id("md31");
    private static final By ODDS_LIST_CLASS = By.className("sr-oddsButton");
    private static final By GAMES_LIST_CLASS = By.className("sr-matchRow");
    private static final By SELECTED_ODDS_CSS = By.cssSelector(".sr-selected");
    private static final By DEFAULT_IFRAME_CSS = By.cssSelector("#virtual-sports iframe");
    private static final String VISUAL_IFRAME_ID = "vsm-ea-iframe";
    private static final String MAX_MATCH_DAY = "30";
    private static final int MATCHES_PER_DAY = 8;
    private static final String SCROLL_SCRIPT = "arguments[0].scrollIntoView();";

    public LeagueModePage() {
        super(TITLE_PAGE_XPATH, "Virtual football league mode page");
    }

    public void selectNextMatchDay() {
        switchToDefaultIframe();
        switchToVisualIframe();

        if (Objects.requireNonNull(waitForElement(CURRENT_MATCH_DAY_CLASS).getAttribute("class")).contains("currentMatchday")) {
            if (waitForElement(CURRENT_MATCH_DAY_CLASS).shouldBe(appear).text().equals(MAX_MATCH_DAY)) {
                waitForElement(FIRST_MATCH_DAY_ID).click();
            } else {
                waitForElement(NEXT_MATCH_DAY_CSS).shouldBe(appear).click();
            }
        }
    }

    public BetSlipForm addRandomOdds(int quantity) {
        BrowserManager.executeScript(SCROLL_SCRIPT, waitForElement(CURRENT_MATCH_DAY_CLASS));
        BrowserManager.exitFrame();
        switchToDefaultIframe();

        List<String> odds = new ArrayList<>();
        setContext(ODDS, odds);

        DataManager.getRandomList(getListOfMatchDayGames(), quantity).forEach(element -> {
            SelenideElement randomElement = DataManager.getRandomFromList(getListOfAvailableOdds(element));
            randomElement.shouldBe(interactable).click();
        });

        $$(SELECTED_ODDS_CSS).asDynamicIterable().forEach(element -> odds.add(element.text()));

        return new BetSlipForm();
    }

    private List<SelenideElement> getListOfAvailableOdds(SelenideElement element) {
        return element.$$(ODDS_LIST_CLASS).shouldBe(CollectionCondition.size(MATCHES_PER_DAY));
    }

    private List<SelenideElement> getListOfMatchDayGames() {
        return $$(GAMES_LIST_CLASS).shouldBe(CollectionCondition.size(MATCHES_PER_DAY));
    }

    private void switchToDefaultIframe() {
        BrowserManager.switchToFrame(waitForElement(DEFAULT_IFRAME_CSS));
    }

    private void switchToVisualIframe() {
        BrowserManager.switchToFrame(VISUAL_IFRAME_ID);
    }
}
