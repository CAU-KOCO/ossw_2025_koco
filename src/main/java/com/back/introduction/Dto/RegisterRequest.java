package com.back.introduction.Dto;

public class RegisterRequest {

    private String email;
    private String password;
    private String confirmPassword;  // 可选：前端可用于验证两次密码一致
    private String username;         // 可选：如果注册时包含用户名

    public RegisterRequest() {
    }

    public RegisterRequest(String email, String password, String confirmPassword, String username) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.username = username;
    }

    // Getter & Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
