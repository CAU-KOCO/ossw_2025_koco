package com.back.introduction.Entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FeedbackTest {

    @Test
    void testConstructorAndGetters() {
        Long userId = 10L;
        String content = "这是测试反馈";
        String email = "test@example.com";
        LocalDateTime submittedAt = LocalDateTime.now();

        Feedback feedback = new Feedback(userId, content, email, submittedAt);

        assertEquals(userId, feedback.getUserId());
        assertEquals(content, feedback.getContent());
        assertEquals(email, feedback.getEmail());
        assertEquals(submittedAt, feedback.getSubmittedAt());
    }

    @Test
    void testSettersAndGetters() {
        Feedback feedback = new Feedback();

        Long id = 5L;
        Long userId = 15L;
        String content = "反馈内容测试";
        String email = "hello@feedback.com";
        LocalDateTime submittedAt = LocalDateTime.of(2025, 5, 17, 12, 30);

        feedback.setId(id);
        feedback.setUserId(userId);
        feedback.setContent(content);
        feedback.setEmail(email);
        feedback.setSubmittedAt(submittedAt);

        assertEquals(id, feedback.getId());
        assertEquals(userId, feedback.getUserId());
        assertEquals(content, feedback.getContent());
        assertEquals(email, feedback.getEmail());
        assertEquals(submittedAt, feedback.getSubmittedAt());
    }
}
