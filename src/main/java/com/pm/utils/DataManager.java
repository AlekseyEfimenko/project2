package com.pm.utils;

import com.codeborne.selenide.SelenideElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class DataManager {
    private static final Logger LOG = LogManager.getRootLogger();

    private DataManager() {}

    /**
     * Get random elements from the list
     * @param lst List to get elements from
     * @param n Number of elements to get random. n <= lst.size()
     * @param <T> The type of objects in the list
     * @return List of random elements selected from lst
     */
    public static <T> List<T> getRandomList(List<T> lst, int n) {
        int size = Math.min(lst.size(), n);
        List<T> copy = new LinkedList<>(lst);
        Collections.shuffle(copy);
        return copy.subList(0, size);
    }

    /**
     * Gets random element from the given list
     *
     * @param elements The list to get random element
     * @param <T>      The type of elements in the list
     * @return Random element
     */
    public static <T> T getRandomFromList(List<T> elements) {
        return elements.get(new Random().nextInt(elements.size()));
    }

    /**
     * Parses text and returns number of bets in the bet slip
     *
     * @param text Text to be parsed
     * @return Number of added bets to the bet slip
     */
    public static int getNumOfBets(String text) {
        return Integer.parseInt(text.substring(text.indexOf("(") + 1, text.indexOf(")")));
    }

    /**
     * Gets text from the element and puts it to the List
     *
     * @param elements The element to get text from
     * @return The list of texts in all elements
     */
    public static List<String> getListOfTexts(List<SelenideElement> elements) {
        List<String> odds = new ArrayList<>();
        elements.forEach(element -> odds.add(element.text()));
        return odds;
    }

    /**
     * Calculates total coefficient of the parlay mode
     *
     * @param odds List of coefficients in the parlay
     * @return Total coefficient
     */
    public static double calculateTotalOdds(List<String> odds) {
        LOG.info("Calculating total odds");
        return convertListStringToDouble(odds).stream().reduce(1.0, (a, b) -> a * b);
    }

    /**
     * Calculates possible payout for the bet
     *
     * @param coefficient Bet coefficient
     * @param betSum      Bet sum
     * @return Possible payout
     */
    public static double calculatePossiblePayout(double coefficient, double betSum) {
        LOG.info("Calculating possible payout according to the coefficient = {} and bet sum = {}",
                coefficient, betSum);
        return coefficient * betSum;
    }

    /**
     * Parses 'String' value from the List<String> to 'double' value and puts it to the List<double>
     *
     * @param listString List<String> to parse
     * @return List<Double>
     */
    private static List<Double> convertListStringToDouble(List<String> listString) {
        List<Double> listDouble = new ArrayList<>();
        listString.forEach(str -> listDouble.add(Double.parseDouble(str)));
        return listDouble;
    }
}
