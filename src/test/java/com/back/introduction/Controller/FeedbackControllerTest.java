package com.back.introduction.Controller;

import com.back.introduction.Dto.FeedbackRequest;
import com.back.introduction.Service.FeedbackService;
import com.back.introduction.Util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
@AutoConfigureMockMvc(addFilters = false)
public class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FeedbackService feedbackService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSubmitFeedback_success() throws Exception {
        // 构造请求体
        FeedbackRequest request = new FeedbackRequest();
        request.setUserId(1L);
        request.setEmail("user@example.com");
        request.setContent("这款软件体验非常棒！");

        // 模拟服务层返回值
        when(feedbackService.submitFeedback(any(FeedbackRequest.class)))
                .thenReturn(ApiResponse.success("Feedback submitted successfully", null));

        // 执行 POST 请求并断言
        mockMvc.perform(post("/api/feedback/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Feedback submitted successfully"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
