package com.back.introduction.Controller;

import com.back.introduction.Service.UploadService;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UploadController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UploadService uploadService;

    @BeforeEach
    public void setup() {
        Mockito.reset(uploadService);
    }

    @Test
    public void testUpdateFavoriteStatus_returns200() throws Exception {
        Mockito.when(uploadService.updateFavoriteStatus(eq(1L), eq(true)))
                .thenReturn(ApiResponse.success("收藏状态更新成功", null));

        mockMvc.perform(patch("/api/upload/1/favorite")
                        .param("favorite", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCategory_returns200() throws Exception {
        Mockito.when(uploadService.updateCategory(eq(1L), eq("留学")))
                .thenReturn(ApiResponse.success("分类更新成功", null));

        mockMvc.perform(patch("/api/upload/1/category")
                        .param("category", "留学")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
