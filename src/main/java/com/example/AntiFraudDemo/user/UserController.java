package com.example.AntiFraudDemo.user;

import jakarta.validation.Valid;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<Iterable<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody User user) {
        UserDTO userDTO = userService.addUser(user.getName(), user.getUsername(), user.getPassword());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        // throws ResourceNotFoundException in the service layer
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(
                Map.of("username", username, "status", "Deleted successfully!"),
                HttpStatus.OK);
    }
}
