package com.back.introduction.Dto;

public class UserUpdateRequest {

    private String username;
    private String email;

    // 默认构造函数（必需用于反序列化）
    public UserUpdateRequest() {
    }

    // 全参构造函数
    public UserUpdateRequest(String username, String email) {
        this.username = username;
        this.email = email;
    }

    // Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
