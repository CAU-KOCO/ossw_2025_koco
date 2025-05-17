package com.back.introduction.Dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        RegisterRequest request = new RegisterRequest();

        request.setEmail("newuser@example.com");
        request.setPassword("securePass!");
        request.setConfirmPassword("securePass!");
        request.setUsername("newuser");

        assertEquals("newuser@example.com", request.getEmail());
        assertEquals("securePass!", request.getPassword());
        assertEquals("securePass!", request.getConfirmPassword());
        assertEquals("newuser", request.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        String email = "john@example.com";
        String password = "123456";
        String confirmPassword = "123456";
        String username = "johnny";

        RegisterRequest request = new RegisterRequest(email, password, confirmPassword, username);

        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
        assertEquals(confirmPassword, request.getConfirmPassword());
        assertEquals(username, request.getUsername());
    }
}
