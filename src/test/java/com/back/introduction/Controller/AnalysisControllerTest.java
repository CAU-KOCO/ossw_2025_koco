package com.back.introduction.Controller;

import com.back.introduction.Dto.AnalysisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnalysisController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAnalyzeResume_returnsReservedMessage() throws Exception {
        // 1. 构造请求对象（可以空字段，因为只是占位接口）
        AnalysisRequest dummyRequest = new AnalysisRequest();
        // 你可以设置 request 字段，如果 AnalysisRequest 有必填字段的话

        // 2. 执行 POST 请求
        mockMvc.perform(post("/api/analysis/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Analysis interface reserved"))
                .andExpect(jsonPath("$.data").doesNotExist());
    }
}
