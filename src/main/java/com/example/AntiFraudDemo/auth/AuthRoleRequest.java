package com.example.AntiFraudDemo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRoleRequest {

    @NotBlank(message = "username is required")
    private final String username;

    @NotBlank(message = "role is required")
    @Pattern(regexp = "MERCHANT|SUPPORT")
    private final String role;

    public AuthRoleRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }

    public String getRole() {
        return role;
    }

}
