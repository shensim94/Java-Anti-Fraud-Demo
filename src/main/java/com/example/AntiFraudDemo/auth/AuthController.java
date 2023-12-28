package com.example.AntiFraudDemo.auth;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PutMapping("/access")
    public ResponseEntity<Object> changeAccess(@Valid @RequestBody AuthAccessRequest request) {

        authService.updateAccess(request.getUsername(), request.getOperation());
        String operation = request.getOperation().toLowerCase() + "ed";
        return new ResponseEntity<>(
                Map.of("status", "User " + request.getUsername() + " " + operation + "!"),
                HttpStatus.OK);
    }
    @PutMapping("/role")
    public ResponseEntity<Object> changeRole(@Valid @RequestBody AuthRoleRequest request) {

        return new ResponseEntity<>(
                authService.updateRole(request.getUsername(), request.getRole()),
                HttpStatus.OK);
    }
}
