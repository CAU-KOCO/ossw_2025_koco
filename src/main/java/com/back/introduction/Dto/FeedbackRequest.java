package com.back.introduction.Dto;

public class FeedbackRequest {
    private Long userId;
    private String content;
    private String email;
    public FeedbackRequest() {
    }
    public FeedbackRequest(Long userId, String content, String email) {
        this.userId = userId;
        this.content = content;
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
