package com.back.introduction.Repository;

import com.back.introduction.Entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("应能保存用户并自动生成主键 ID")
    void testSaveUser() {
        User user = new User("testuser@example.com", "password123");
        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("testuser@example.com", saved.getEmail());
    }

    @Test
    @DisplayName("应能根据邮箱查找用户")
    void testFindByEmail() {
        User user = new User("login@test.com", "mypassword");
        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("login@test.com");

        assertTrue(found.isPresent());
        assertEquals("mypassword", found.get().getPassword());
    }

    @Test
    @DisplayName("应正确判断邮箱是否已存在")
    void testExistsByEmail() {
        User user = new User("exist@test.com", "abc123");
        userRepository.save(user);

        assertTrue(userRepository.existsByEmail("exist@test.com"));
        assertFalse(userRepository.existsByEmail("notfound@test.com"));
    }
}
