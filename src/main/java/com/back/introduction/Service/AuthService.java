package com.back.introduction.Service;

import com.back.introduction.Dto.LoginRequest;
import com.back.introduction.Dto.RegisterRequest;
import com.back.introduction.Entity.User;
import com.back.introduction.Exception.UserAlreadyExistsException;
import com.back.introduction.Repository.UserRepository;
import com.back.introduction.Util.ApiResponse;
import com.back.introduction.Util.JwtUtil;
import com.back.introduction.Util.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 注册新用户
     */
    public ApiResponse register(RegisterRequest request) {
        // 判断邮箱是否已存在
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("This email address has been registered.");
        }

        // 校验密码是否一致
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ApiResponse.error("The passwords entered twice do not match.");
        }

        // 加密密码
        String encodedPassword = PasswordEncoderUtil.encode(request.getPassword());

        // 创建新用户
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);

        // 保存用户
        userRepository.save(user);

        return ApiResponse.success("Registration successful",true);
    }

    /**
     * 登录逻辑（邮箱 + 密码验证）
     */
    public ApiResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ApiResponse.error("This email address is not registered.");
        }

        User user = optionalUser.get();

        // 验证密码
        if (!PasswordEncoderUtil.matches(request.getPassword(), user.getPassword())) {
            return ApiResponse.error("Password error");
        }

        // 登录成功，生成 JWT token
        String token = jwtUtil.generateToken(user.getEmail());

        return new ApiResponse(true, "Login successful", token);
    }
}
