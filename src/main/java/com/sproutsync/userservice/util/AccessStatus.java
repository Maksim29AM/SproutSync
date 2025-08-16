package com.sproutsync.userservice.util;

import java.util.Arrays;

public enum AccessStatus {

    PENDING, APPROVED, REJECTED;

    public static AccessStatus fromString(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid status: " + value + ". Allowed: PENDING, APPROVED, REJECTED"));
    }
}
