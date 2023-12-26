package com.example.AntiFraudDemo.user;

import com.example.AntiFraudDemo.exception.ApiRequestException;
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
    public ResponseEntity<UserDTO> register(@RequestBody UserRegistrationRequest request) {
        if (Strings.isBlank(request.name()) || Strings.isBlank(request.username()) || Strings.isBlank(request.password())) {
            throw new ApiRequestException("request is missing required fields", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);

    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String username) {
        if (userService.deleteUser(username)) {
            return new ResponseEntity<>(
                    Map.of("username", username, "status", "Deleted successfully!"),
                    HttpStatus.OK);
        };
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
