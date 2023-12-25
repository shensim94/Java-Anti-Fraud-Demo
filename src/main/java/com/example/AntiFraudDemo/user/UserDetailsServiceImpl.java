package com.example.AntiFraudDemo.user;

import com.example.AntiFraudDemo.exception.ApiRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsernameIgnoreCase(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public ResponseEntity<UserDTO> addUser(UserRegistrationRequest request) throws ApiRequestException {
        if (Strings.isBlank(request.name()) || Strings.isBlank(request.username()) || Strings.isBlank(request.password())) {
            throw new ApiRequestException("request is missing required fields");
        }
        if (userRepository.findByUsernameIgnoreCase(request.username()).isPresent()) {
            throw new ApiRequestException("user is already present");
        }
        User user = new User(request.name(), request.username(), request.password());
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

}
