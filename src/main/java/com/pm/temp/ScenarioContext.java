package com.pm.temp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final Logger LOGGER = LogManager.getRootLogger();
    private static final Map<String, Object> scenario = new HashMap<>();

    private ScenarioContext() {
    }

    /**
     * Adds new pairs key/value to the Map
     *
     * @param key   Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public static void setContext(Context key, Object value) {
        scenario.put(key.toString(), value);
    }

    /**
     * Gets the test value by the specific key
     *
     * @param key The key associated with the value
     * @return Test value
     */
    public static Object getContext(Context key) {
        Object context = null;
        try {
            context = scenario.get(key.toString());
        } catch (NullPointerException ex) {
            LOGGER.error("There is no match with the given key");
        }
        return context;
    }
}
