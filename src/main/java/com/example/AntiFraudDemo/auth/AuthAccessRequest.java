package com.example.AntiFraudDemo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthAccessRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "operation is required")
    @Pattern(regexp = "LOCK|UNLOCK")
    private String operation;

    public AuthAccessRequest(String username, String operation) {
        this.username = username;
        this.operation = operation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
