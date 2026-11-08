package io.dajzavrsi.dajzavrsi.entity;

import jakarta.persistence.EnumeratedValue;

public enum Role {
    STUDENT("student"),
    FACULTY("faculty");

    @EnumeratedValue
    private final String dbValue;

    Role(String dbValue) {
        this.dbValue = dbValue;
    }
}
