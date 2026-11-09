package io.dajzavrsi.dajzavrsi.entity;

import jakarta.persistence.EnumeratedValue;

public enum AssignmentStatus {
    IN_PROGRESS("in_progress"),
    PASSED("passed"),
    FAILED("failed");

    @EnumeratedValue
    private final String dbValue;

    AssignmentStatus(String dbValue) {
        this.dbValue = dbValue;
    }
}
