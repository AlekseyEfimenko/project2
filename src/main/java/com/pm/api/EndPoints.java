package com.pm.api;

/**
 * Enum for API request endpoints
 */
public enum EndPoints {
    ;
    private final String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
