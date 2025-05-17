package com.back.introduction.Controller;

import com.back.introduction.Service.UploadService;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UploadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadService uploadService;

    @Test
    void testUploadResume_success() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.pdf",
                "application/pdf",
                "dummy content".getBytes()
        );

        when(uploadService.saveFile(any(), eq(1L)))
                .thenReturn(ApiResponse.success("上传成功", null));

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("上传成功"));
    }

    @Test
    void testUploadResume_emptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "resume.pdf",
                "application/pdf",
                new byte[0] // 空文件内容
        );

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("上传失败，文件不能为空"));
    }

    @Test
    void testUploadResume_invalidFileType() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "malware.exe",
                "application/octet-stream",
                "danger".getBytes()
        );

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("仅支持上传 pdf、word 或 hwp 格式的文件"));
    }
}
