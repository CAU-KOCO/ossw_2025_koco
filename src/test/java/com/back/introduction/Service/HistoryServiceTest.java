package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryServiceTest {

    @InjectMocks
    private HistoryService historyService;

    @Mock
    private UploadFileRepository uploadFileRepository;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUploadHistory_returnsFileList() {
        // 1. 构造测试数据
        UploadFile file1 = new UploadFile();
        file1.setId(1L);
        file1.setUserId(100L);
        file1.setOriginalFilename("resume1.pdf");
        file1.setStoredFilename("abc123.pdf");
        file1.setUploadTime(LocalDateTime.now());

        UploadFile file2 = new UploadFile();
        file2.setId(2L);
        file2.setUserId(100L);
        file2.setOriginalFilename("resume2.docx");
        file2.setStoredFilename("xyz789.docx");
        file2.setUploadTime(LocalDateTime.now());

        List<UploadFile> fileList = Arrays.asList(file1, file2);

        // 2. mock 数据库返回
        when(uploadFileRepository.findByUserId(100L)).thenReturn(fileList);

        // 3. 执行服务方法
        ApiResponse response = historyService.getUploadHistory(100L);

        // 4. 验证结果
        assertTrue(response.isSuccess());
        assertEquals("查询成功", response.getMessage());
        assertEquals(2, ((List<?>) response.getData()).size());

        verify(uploadFileRepository, times(1)).findByUserId(100L);
    }

    @Test
    void testGetUploadHistory_emptyList() {
        when(uploadFileRepository.findByUserId(200L)).thenReturn(List.of());

        ApiResponse response = historyService.getUploadHistory(200L);

        assertTrue(response.isSuccess());
        assertEquals("查询成功", response.getMessage());
        assertTrue(((List<?>) response.getData()).isEmpty());
    }
}
