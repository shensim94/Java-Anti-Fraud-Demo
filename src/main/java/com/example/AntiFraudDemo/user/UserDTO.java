package com.example.AntiFraudDemo.user;

public class UserDTO{
    private Long id;

    private String name;

    private String username;

    private String roles;

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.roles = user.getRole();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getRoles() { return roles; }
}
