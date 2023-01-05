package com.pm.api;

/**
 * Enum for API request endpoints
 */
public enum EndPoints {
    REGISTRATION("/api/v3/registration/byform"),
    UPDATE_PASSWORD("/api/user/updatepassword");
    private final String value;

    EndPoints(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
