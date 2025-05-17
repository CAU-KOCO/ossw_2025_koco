package com.back.introduction.Dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        LoginRequest request = new LoginRequest();

        request.setEmail("test@example.com");
        request.setPassword("securePass123");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("securePass123", request.getPassword());
    }

    @Test
    void testAllArgsConstructor() {
        String email = "user@domain.com";
        String password = "mypassword";

        LoginRequest request = new LoginRequest(email, password);

        assertEquals(email, request.getEmail());
        assertEquals(password, request.getPassword());
    }
}
