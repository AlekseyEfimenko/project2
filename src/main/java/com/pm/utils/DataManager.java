package com.pm.utils;

import com.codeborne.selenide.SelenideElement;
import com.pm.temp.Context;
import com.pm.temp.ScenarioContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
     * Gets text from the element and puts it to the List
     *
     * @param elements The element to get text from
     * @return The list of texts in all elements
     */
    public static List<String> getListOfTextsMobile(List<WebElement> elements) {
        List<String> odds = new ArrayList<>();
        elements.forEach(element -> odds.add(element.getText()));
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

        /* Because of mistakes in multiplying, sometimes the result is incorrect, which is why the next rounding
        of incorrect results will lead to an error. For example, 2.05 * 2.90 always will be 5.944999999999999 but
        not 5.945 as expected. And after rounding to two decimal places we will receive 5.94 but not 5.95.
        In asserting total odds the test fails. So we add 0.0000001 to solve this error.
         */
        totalOdds += .0000001;

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

    /**
     * Gets last n elements from the list
     *
     * @param number Number of last elements to get
     * @return List? contains last n elements
     */
    public static <T> List<T> getLastElements(List<T> lst, int number) {
        return lst.subList(lst.size() - number, lst.size());
    }

    /**
     * Gets the element background colour and converts it in RGB format
     *
     * @param point The coordinates of the element on the screen
     * @return The String of colour in RGB format
     */
    public static String getElementColor(Point point) {
        LOG.info("Getting the color of the element");
        File screenshot = DriverManager.takeScreenshot();
        int xCoordinate = point.getX() + 10;
        int yCoordinate = point.getY() + 10;
        int color = 0;

        try {
            BufferedImage image = ImageIO.read(screenshot);
            color = image.getRGB(xCoordinate, yCoordinate);
        } catch (IOException ex) {
            LOG.error(ex.getMessage());
        }

        int red = (color >> 16) & 0xFF;
        int green = (color >> 8) & 0xFF;
        int blue = (color) & 0xFF;
        String space = ", ";

        return "rgb(" + red + space + green + space + blue + ")";
    }
}
