package com.pm.utils;

import com.codeborne.selenide.SelenideElement;
import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataManager {
    private static final Logger LOG = LogManager.getRootLogger();

    private DataManager() {
    }

    /**
     * Generates random double number in a given range
     *
     * @param min Minimum number can be generated
     * @param max Maximum number can be generated
     * @return Random number between min and max
     */
    public static double getRandomNumber(double min, double max) {
        double random = (ThreadLocalRandom.current().nextDouble() * (max - min)) + min;
        return roundToTwoDecimalPlaces(random);
    }

    /**
     * Get random elements from the list
     *
     * @param lst List to get elements from
     * @param n   Number of elements to get random. n <= lst.size()
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
        LOG.info("Calculating total odds with odds {}", odds);
        double totalOdds = convertListStringToDouble(odds).stream().reduce(1.0, (a, b) -> a * b);
        ScenarioContext.setContext(Context.TOTAL_ODDS, Math.round(totalOdds * 10000.0) / 10000.0);
        return roundToTwoDecimalPlaces(totalOdds);
    }

    /**
     * Calculates possible payout for the bet
     *
     * @param odds   List of coefficients in the parlay
     * @param betSum Bet sum
     * @return Possible payout
     */
    public static double calculatePossiblePayout(List<String> odds, double betSum) {
        LOG.info("Calculating possible payout according to the coefficients = {} and bet sum = {}",
                odds, betSum);
        return roundToTwoDecimalPlaces((double) ScenarioContext.getContext(Context.TOTAL_ODDS) * betSum);
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

    /**
     * Rounds a number with n decimal places to two decimal places
     *
     * @param number Number to be rounded
     * @return Rounded double number
     */
    private static double roundToTwoDecimalPlaces(double number) {
//        StringBuilder builder = new StringBuilder(String.valueOf(number));
//        int dotIndex = builder.indexOf(".");
//        if (dotIndex != -1 && builder.length() > dotIndex + 3 && builder.charAt(dotIndex + 3) == '5') {
//            builder.insert(dotIndex + 3, "6");
//        }
//        number = Double.parseDouble(builder.toString());
        return Math.round(number * 100.0) / 100.0;
    }

    /**
     * Generate random String line
     *
     * @param length The length of generated String
     * @return Random String
     */
    public static String getRandomString(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    /**
     * Generates random number in String format with given length
     *
     * @param length The length of generated String
     * @return Random number
     */
    public static String getRandomNumber(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    /**
     * Generates random password with given length
     *
     * @param length The length of generated password
     * @return Random number
     */
    public static String generatePassword(int length) {
        return String.format("%1$s%2$s%3$s%4$s",
                getRandomString(length / 2 - 1),
                getRandomNumber(length / 2 - 1),
                getRandomString(1).toUpperCase(),
                RandomStringUtils.random(1, 33, 47, false, false));
    }
}
