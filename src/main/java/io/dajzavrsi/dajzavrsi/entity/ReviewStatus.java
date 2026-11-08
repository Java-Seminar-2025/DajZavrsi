package io.dajzavrsi.dajzavrsi.entity;

import jakarta.persistence.EnumeratedValue;

public enum ReviewStatus {
    APPROVED("approved"),
    DENIED("denied");

    @EnumeratedValue
    private final String dbValue;

    ReviewStatus(String dbValue) {
        this.dbValue = dbValue;
    }
}
