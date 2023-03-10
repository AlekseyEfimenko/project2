package com.pm.api;

/**
 * Enum for keys for JsonObject
 */
public enum Keys {
    OLD_PASSWORD("oldPassword"),
    NEW_PASSWORD("newPassword"),
    LOGIN("login"),
    PASSWORD("password");

    private final String value;

    Keys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
