package com.example.AntiFraudDemo.user;

import com.example.AntiFraudDemo.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO addUser(UserRegistrationRequest request){
        if (userRepository.findByUsernameIgnoreCase(request.username()).isPresent()) {
            throw new ApiRequestException("user is already present", HttpStatus.CONFLICT);
        }
        User user = new User(request.name(), request.username(), passwordEncoder.encode(request.password()));
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        if (allUsers.isEmpty()) {
            user.setRoles("ROLE_ADMINISTRATOR");
        }
        userRepository.save(user);
        return new UserDTO(user);
    }

    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();
        ArrayList<UserDTO> allUsersDTO= new ArrayList<>();
        for (User user: allUsers) {
            allUsersDTO.add(new UserDTO(user));
        }
        return allUsersDTO;
    }

    public Boolean deleteUser(String username) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }
}
