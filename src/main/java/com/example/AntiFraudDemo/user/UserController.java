package com.example.AntiFraudDemo.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserRepository users;

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<UserDTO>> getUsers() {
        Iterable<User> allUsers = users.findAll();
        ArrayList<UserDTO> allUsersView = new ArrayList<>();
        for (User user: allUsers) {
            allUsersView.add(new UserDTO(user));
        }
        return new ResponseEntity<>(allUsersView, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationRequest request) {

        return userDetailsService.addUser(request);

    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        System.out.println(username);
        Optional<User> user = users.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            users.delete(user.get());
            return new ResponseEntity<>(Map.of("username", user.get().getUsername(), "status", "Deleted successfully!"), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
