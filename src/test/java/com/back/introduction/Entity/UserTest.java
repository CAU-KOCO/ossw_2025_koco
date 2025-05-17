package com.back.introduction.Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();

        user.setEmail("test@example.com");
        user.setPassword("secure123");
        user.setUsername("TestUser");

        assertNull(user.getId()); // id 应由数据库自动生成
        assertEquals("test@example.com", user.getEmail());
        assertEquals("secure123", user.getPassword());
        assertEquals("TestUser", user.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        String email = "admin@domain.com";
        String password = "admin123";

        User user = new User(email, password);

        assertNull(user.getId());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());

        // 默认 username 为 null（仅在 setter 中赋值）
        assertNull(user.getUsername());
    }

    @Test
    void testUsernameUpdate() {
        User user = new User();
        user.setUsername("NewUsername");
        assertEquals("NewUsername", user.getUsername());
    }

    @Test
    void testPasswordChange() {
        User user = new User("user@sample.com", "oldPass");
        user.setPassword("newPass");
        assertEquals("newPass", user.getPassword());
    }
}
