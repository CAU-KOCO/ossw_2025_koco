package com.back.introduction.Service;

import com.back.introduction.Dto.UserUpdateRequest;
import com.back.introduction.Entity.User;
import com.back.introduction.Repository.UserRepository;
import com.back.introduction.Util.ApiResponse;
import com.back.introduction.Util.PasswordEncoderUtil;
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
        return ApiResponse.success("Search successful", optionalUser.get());
    }

    /**
     * 更新用户信息（用户名、密码），不允许修改邮箱
     */
    public ApiResponse updateUser(Long userId, UserUpdateRequest request) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ApiResponse.error("用户不存在");
        }

        User user = optionalUser.get();

        // 修改用户名
        user.setUsername(request.getUsername());

        // 如用户输入旧密码，则验证并修改新密码
        if (request.getOldPassword() != null && !request.getOldPassword().isEmpty()) {
            if (!PasswordEncoderUtil.matches(request.getOldPassword(), user.getPassword())) {
                return ApiResponse.error("旧密码不正确，无法修改密码");
            }

            // 设置加密后的新密码
            user.setPassword(PasswordEncoderUtil.encode(request.getNewPassword()));
        }

        userRepository.save(user);
        return ApiResponse.success("User update successful", user);
    }
}
