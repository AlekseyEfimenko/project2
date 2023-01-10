package com.pm.ui.configuration;

public enum Properties {
    CHROME_OPTIONS_ARGS("chromeoptions.args");

    private final String value;

    Properties(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
