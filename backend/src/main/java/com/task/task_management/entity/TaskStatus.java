package com.task.task_management.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TaskStatus {
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String value;

    @JsonCreator
    public static TaskStatus from(String value) {
        return TaskStatus.valueOf(value.toUpperCase());
    }

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
