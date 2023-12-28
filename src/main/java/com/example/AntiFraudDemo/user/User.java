package com.example.AntiFraudDemo.user;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class User {

    @Valid


    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "username/email is required")
    private String username;

    @NotBlank(message = "password is required")
    private String password;

    private String roles;

    public User() {}

    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() { return roles; }

    public void setRoles(String roles) { this.roles = roles; }

}
