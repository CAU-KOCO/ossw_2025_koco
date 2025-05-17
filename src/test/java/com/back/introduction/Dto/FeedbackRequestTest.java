package com.back.introduction.Dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        FeedbackRequest request = new FeedbackRequest();

        request.setUserId(1L);
        request.setContent("这是一个测试反馈内容");
        request.setEmail("test@example.com");

        assertEquals(1L, request.getUserId());
        assertEquals("这是一个测试反馈内容", request.getContent());
        assertEquals("test@example.com", request.getEmail());
    }

    @Test
    void testAllArgsConstructor() {
        Long userId = 2L;
        String content = "反馈内容内容";
        String email = "user@domain.com";

        FeedbackRequest request = new FeedbackRequest(userId, content, email);

        assertEquals(userId, request.getUserId());
        assertEquals(content, request.getContent());
        assertEquals(email, request.getEmail());
    }
}
