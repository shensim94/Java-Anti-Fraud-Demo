package com.example.AntiFraudDemo.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AuthRoleRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "role is required")
    @Pattern(regexp = "MERCHANT|SUPPORT")
    private String role;

    public AuthRoleRequest() {
    }

    public AuthRoleRequest(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() { return username; }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
