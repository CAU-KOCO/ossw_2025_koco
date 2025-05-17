package com.back.introduction.Controller;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Service.HistoryService;
import com.back.introduction.Util.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUploadHistory_success() throws Exception {
        // 模拟返回的上传记录列表
        UploadFile file = new UploadFile();
        file.setId(1L);
        file.setUserId(100L);
        file.setOriginalFilename("resume.pdf");
        file.setStoredFilename("abc123.pdf");
        file.setUploadTime(LocalDateTime.now());
        file.setFilePath("/fake/path/abc123.pdf");

        List<UploadFile> files = List.of(file);

        // 模拟服务层返回
        when(historyService.getUploadHistory(100L))
                .thenReturn(ApiResponse.success("查询成功", files));

        // 发送 GET 请求
        mockMvc.perform(get("/api/history/uploads/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("查询成功"))
                .andExpect(jsonPath("$.data[0].originalFilename").value("resume.pdf"));
    }
}
