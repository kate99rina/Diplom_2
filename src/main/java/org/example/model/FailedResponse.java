package org.example.model;

import lombok.Getter;

@Getter
public class FailedResponse {
    private boolean success;
    private String message;

    public FailedResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
