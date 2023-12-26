package com.example.AntiFraudDemo.user;

import com.example.AntiFraudDemo.exception.ApiRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<UserDTO> addUser(UserRegistrationRequest request) throws ApiRequestException {
        if (Strings.isBlank(request.name()) || Strings.isBlank(request.username()) || Strings.isBlank(request.password())) {
            throw new ApiRequestException("request is missing required fields", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.findByUsernameIgnoreCase(request.username()).isPresent()) {
            throw new ApiRequestException("user is already present", HttpStatus.CONFLICT);
        }
        User user = new User(request.name(), request.username(), passwordEncoder.encode(request.password()));
        userRepository.save(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();
        ArrayList<UserDTO> allUsersDTO= new ArrayList<>();
        for (User user: allUsers) {
            allUsersDTO.add(new UserDTO(user));
        }
        return new ResponseEntity<>(allUsersDTO, HttpStatus.OK);
    }

    public ResponseEntity<Map<String, String>> deleteUser(String username) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return new ResponseEntity<>(
                    Map.of("username", user.get().getUsername(), "status", "Deleted successfully!"),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
