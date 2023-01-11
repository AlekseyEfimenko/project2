package com.pm.mobile.configuration;

public enum AppiumCapabilities {
    ENABLE_MULTI_WINDOWS("enableMultiWindows"),
    ALLOW_INVISIBLE_ELEMENTS("allowInvisibleElements"),
    SETTINGS("settings");

    private final String value;

    AppiumCapabilities(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
