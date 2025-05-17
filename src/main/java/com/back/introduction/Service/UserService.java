package com.back.introduction.Service;

import com.back.introduction.Dto.UserUpdateRequest;
import com.back.introduction.Entity.User;
import com.back.introduction.Repository.UserRepository;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 获取用户信息
     */
    public ApiResponse getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ApiResponse.error("未找到对应的用户");
        }
        return ApiResponse.success("查询成功", optionalUser.get());
    }

    /**
     * 更新用户信息（用户名、邮箱）
     */
    public ApiResponse updateUser(Long userId, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ApiResponse.error("用户不存在");
        }

        User user = optionalUser.get();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        userRepository.save(user);
        return ApiResponse.success("用户信息更新成功", user);
    }
}
