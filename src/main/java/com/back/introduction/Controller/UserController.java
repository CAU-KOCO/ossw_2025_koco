package com.back.introduction.Controller;

import com.back.introduction.Dto.UserUpdateRequest;
import com.back.introduction.Service.UserService;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息（用于“个人信息”页面）
     */
    @GetMapping("/{userId}")
    public ApiResponse getUserInfo(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    /**
     * 修改用户信息（邮箱、用户名）
     */
    @PutMapping("/{userId}")
    public ApiResponse updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }
}
