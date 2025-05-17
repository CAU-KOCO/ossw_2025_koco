package com.back.introduction.Entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        User user = new User();

        user.setEmail("test@example.com");
        user.setPassword("secure123");

        assertNull(user.getId()); // id 由数据库生成为 null
        assertEquals("test@example.com", user.getEmail());
        assertEquals("secure123", user.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        String email = "admin@domain.com";
        String password = "admin123";

        User user = new User(email, password);

        assertNull(user.getId()); // id 应为 null
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }
}
