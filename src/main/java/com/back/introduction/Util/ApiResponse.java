package com.back.introduction.Util;

public class ApiResponse {
    private boolean success;
    private int code;
    private String message;
    private Object data;

    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = success ? 200 : 400; // 默认给出 code
    }

    // 带 code 的构造函数
    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = (code >= 200 && code < 300);
    }

    // 静态方法：成功响应
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(true, message, data);
    }

    // 静态方法：失败响应
    public static ApiResponse error(String message) {
        return new ApiResponse(false, message, null);
    }

    // Getter & Setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
