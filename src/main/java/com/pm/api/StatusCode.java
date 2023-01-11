package com.pm.api;

/**
 * Enum for response status code
 */
public enum StatusCode {
    SUCCESS(200),
    CREATED(201),
    BAD_REQUEST(400),
    FORBIDDEN(403);

    private final int value;

    StatusCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
