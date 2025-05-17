package com.back.introduction.Controller;

import com.back.introduction.Dto.UserUpdateRequest;
import com.back.introduction.Entity.User;
import com.back.introduction.Service.UserService;
import com.back.introduction.Util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setUsername("TestUser");
    }

    @Test
    public void testGetUserInfo_success() throws Exception {
        Mockito.when(userService.getUserById(1L))
                .thenReturn(ApiResponse.success("查询成功", user));

        mockMvc.perform(get("/api/user/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("test@example.com")))
                .andExpect(content().string(containsString("TestUser")));
    }

    @Test
    public void testUpdateUserInfo_success() throws Exception {
        UserUpdateRequest updateRequest = new UserUpdateRequest("new@example.com", "NewName");

        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(UserUpdateRequest.class)))
                .thenReturn(ApiResponse.success("用户信息更新成功", user));

        mockMvc.perform(put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("用户信息更新成功"))
                .andExpect(jsonPath("$.data.email").value("test@example.com"))
                .andExpect(jsonPath("$.data.username").value("TestUser"));
    }

}
