package com.example.AntiFraudDemo.user;

import org.apache.logging.log4j.util.Strings;
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
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<UserViewModel>> getUsers() {
        Iterable<User> allUsers = users.findAll();
        ArrayList<UserViewModel> allUsersView = new ArrayList<>();
        for (User user: allUsers) {
            allUsersView.add(new UserViewModel(user));
        }
        return new ResponseEntity<>(allUsersView, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserViewModel> register(@RequestBody RegistrationRequest request) {
        if (Strings.isBlank(request.name()) || Strings.isBlank(request.username()) || Strings.isBlank(request.password())) {
            System.out.println("Bad request here");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (users.findByUsernameIgnoreCase(request.username()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        System.out.println("Request is okay");
        User user = new User();
        user.setName(request.name());
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        users.save(user);
        return new ResponseEntity<>(new UserViewModel(user), HttpStatus.CREATED);
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

    record RegistrationRequest(String name, String username, String password) { }
}
