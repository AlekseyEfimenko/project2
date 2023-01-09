package com.pm.mobile.configuration;

public enum Direction {
    UP("up"),
    DOWN("down"),
    RIGHT("right"),
    LEFT("left");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
