package com.example.AntiFraudDemo.auth;

import com.example.AntiFraudDemo.exception.BadRequestException;
import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.exception.ResourceNotFoundException;
import com.example.AntiFraudDemo.user.User;
import com.example.AntiFraudDemo.user.UserDTO;
import com.example.AntiFraudDemo.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }
    @Transactional
    public void updateAccess(String username, String operation) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found: " + username));
        if (user.getRole().equals("ADMINISTRATOR")) {
            throw new BadRequestException("cannot perform this operation on an Admin");
        }
        if (operation.equals("UNLOCK") && user.isUnlocked()) {
            throw new ResourceAlreadyExistException(username + " is already unlocked");
        }
        if (operation.equals("LOCK") && !user.isUnlocked()) {
            throw new ResourceAlreadyExistException(username + " is already locked");
        }
        user.setUnlocked(operation.equals("UNLOCK"));
    }

    @Transactional
    public UserDTO updateRole(String username, String role) {
        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Username not found: " + username));

        if (user.getRole().equals(role)) {
            throw new ResourceAlreadyExistException("user already has role: " + role);
        }
        user.setRole(role);
        userService.saveUser(user);
        return new UserDTO(user);

    }
}
