package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HistoryServiceTest {

    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private HistoryService historyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUploadHistory_returnsList() {
        UploadFile f1 = new UploadFile();
        UploadFile f2 = new UploadFile();
        when(uploadFileRepository.findByUserId(1L)).thenReturn(List.of(f1, f2));

        ApiResponse response = historyService.getUploadHistory(1L);
        assertEquals(200, response.getCode());
        assertTrue(response.getData() instanceof List);
    }

    @Test
    public void testDeleteUploadRecord_success() {
        when(uploadFileRepository.existsById(10L)).thenReturn(true);

        ApiResponse response = historyService.deleteUploadRecord(10L);

        assertEquals(200, response.getCode());
        assertEquals("上传记录删除成功", response.getMessage());
        verify(uploadFileRepository, times(1)).deleteById(10L);
    }

    @Test
    public void testDeleteUploadRecord_notFound() {
        when(uploadFileRepository.existsById(99L)).thenReturn(false);

        ApiResponse response = historyService.deleteUploadRecord(99L);

        assertEquals(400, response.getCode());
        assertEquals("该上传记录不存在", response.getMessage());
    }
}
