package com.back.introduction.Controller;

import com.back.introduction.Dto.LoginRequest;
import com.back.introduction.Dto.RegisterRequest;
import com.back.introduction.Service.AuthService;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // 注册
    @PostMapping("/register")
    public ApiResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    // 登录
    @PostMapping("/login")
    public ApiResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
