package io.dajzavrsi.dajzavrsi.entity;

import jakarta.persistence.EnumeratedValue;

public enum SubmissionStatus {
    PENDING_VALIDATION("pending_validation"),
    PENDING_REVIEW("pending_review"),
    APPROVED("approved"),
    DENIED("denied");

    @EnumeratedValue
    private final String dbValue;

    SubmissionStatus(String dbValue) {
        this.dbValue = dbValue;
    }
}
