package com.back.introduction.Controller;

import com.back.introduction.Service.HistoryService;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HistoryService historyService;

    @BeforeEach
    public void setup() {
        Mockito.reset(historyService);
    }

    @Test
    public void testGetUploadHistory_returns200() throws Exception {
        Mockito.when(historyService.getUploadHistory(eq(1L)))
                .thenReturn(ApiResponse.success("查询成功", null));

        mockMvc.perform(get("/api/history/uploads/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteUploadRecord_returns200() throws Exception {
        Mockito.when(historyService.deleteUploadRecord(eq(2L)))
                .thenReturn(ApiResponse.success("删除成功", null));

        mockMvc.perform(delete("/api/history/uploads/2"))
                .andExpect(status().isOk());
    }
}
