package com.example.AntiFraudDemo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsernameIgnoreCase() {
        // Arrange
        User testUser = new User("Simon", "simonshen", "password");
        entityManager.persist(testUser);
        entityManager.flush();

        // Act
        User found = userRepository.findByUsernameIgnoreCase("SIMONSHEN").orElse(null);

        // Assert
        assertNotNull(found);
        assertEquals("simonshen", found.getUsername());
    }
}
