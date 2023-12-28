package com.example.AntiFraudDemo.user;

import com.example.AntiFraudDemo.exception.ResourceAlreadyExistException;
import com.example.AntiFraudDemo.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> allUsers = userRepository.findAll();
        ArrayList<UserDTO> allUsersDTO= new ArrayList<>();
        for (User user: allUsers) {
            allUsersDTO.add(new UserDTO(user));
        }
        return allUsersDTO;
    }

    public Boolean hasUser(String username) {
        return getUserByUsername(username).isPresent();
    }

    @Transactional
    public void saveUser(User user) {
        userRepository.save(user);
    }


    @Transactional
    public UserDTO addUser(String name, String username, String password){
        if (hasUser(username)) {
            throw new ResourceAlreadyExistException("user is already present");
        }
        User user = new User(name, username, passwordEncoder.encode(password));
        ArrayList<User> allUsers = (ArrayList<User>) userRepository.findAll();
        if (allUsers.isEmpty()) {
            user.setRole("ADMINISTRATOR");
            user.setUnlocked(true);
        } else {
            user.setRole("MERCHANT");
        }
        saveUser(user);
        return new UserDTO(user);
    }

    @Transactional
    public void deleteUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);
        if (user.isPresent()) {
            userRepository.delete(user.get());
        } else {
            throw new ResourceNotFoundException("Username not found: " + username);
        }
    }
}
