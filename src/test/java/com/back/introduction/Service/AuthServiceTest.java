package com.back.introduction.Service;

import com.back.introduction.Dto.LoginRequest;
import com.back.introduction.Dto.RegisterRequest;
import com.back.introduction.Entity.User;
import com.back.introduction.Exception.UserAlreadyExistsException;
import com.back.introduction.Repository.UserRepository;
import com.back.introduction.Util.ApiResponse;
import com.back.introduction.Util.JwtUtil;
import com.back.introduction.Util.PasswordEncoderUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close(); // ✅ 手动关闭
    }


    @Test
    void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("123456");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);

        ApiResponse response = authService.register(request);

        assertTrue(response.isSuccess());
        assertEquals("注册成功", response.getMessage());
    }

    @Test
    void testRegister_EmailExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("123456");

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.register(request);
        });
    }

    @Test
    void testRegister_PasswordMismatch() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("654321");

        ApiResponse response = authService.register(request);

        assertFalse(response.isSuccess());
        assertEquals("两次输入的密码不一致", response.getMessage());
    }

    @Test
    void testLogin_Success() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword(PasswordEncoderUtil.encode("123456"));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));
        when(jwtUtil.generateToken("test@example.com")).thenReturn("mockToken");

        ApiResponse response = authService.login(request);

        assertTrue(response.isSuccess());
        assertEquals("登录成功", response.getMessage());
        assertEquals("mockToken", response.getData());
    }

    @Test
    void testLogin_EmailNotRegistered() {
        LoginRequest request = new LoginRequest();
        request.setEmail("notfound@example.com");
        request.setPassword("123456");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        ApiResponse response = authService.login(request);

        assertFalse(response.isSuccess());
        assertEquals("该邮箱未注册", response.getMessage());
    }

    @Test
    void testLogin_WrongPassword() {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        User mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword(PasswordEncoderUtil.encode("123456"));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockUser));

        ApiResponse response = authService.login(request);

        assertFalse(response.isSuccess());
        assertEquals("密码错误", response.getMessage());
    }
}
