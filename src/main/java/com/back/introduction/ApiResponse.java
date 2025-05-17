package com.back.introduction;

public class ApiResponse {
    private int code;
    private String message;
    private Object data;

    // 构造函数
    public ApiResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // 兼容：只包含 code 和 message
    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
        this.data = null;
    }

    // 兼容：使用 boolean 成功标志
    public ApiResponse(boolean success, String message) {
        this.code = success ? 200 : 400;
        this.message = message;
        this.data = null;
    }

    // 静态方法：成功响应
    public static ApiResponse success(String message, Object data) {
        return new ApiResponse(200, message, data);
    }

    // 静态方法：错误响应
    public static ApiResponse error(String message) {
        return new ApiResponse(400, message, null);
    }

    // Getter 和 Setter
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

