package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UploadServiceTest {

    @Mock
    private UploadFileRepository uploadFileRepository;

    @InjectMocks
    private UploadService uploadService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateFavoriteStatus_success() {
        UploadFile file = new UploadFile();
        file.setId(1L);
        file.setFavorite(false);

        when(uploadFileRepository.findById(1L)).thenReturn(Optional.of(file));

        ApiResponse response = uploadService.updateFavoriteStatus(1L, true);

        assertEquals(200, response.getCode());
        assertTrue(((UploadFile) response.getData()).isFavorite());
        verify(uploadFileRepository, times(1)).save(file);
    }

    @Test
    public void testUpdateCategory_success() {
        UploadFile file = new UploadFile();
        file.setId(1L);

        when(uploadFileRepository.findById(1L)).thenReturn(Optional.of(file));

        ApiResponse response = uploadService.updateCategory(1L, "求职");

        assertEquals(200, response.getCode());
        assertEquals("求职", ((UploadFile) response.getData()).getCategory());
        verify(uploadFileRepository, times(1)).save(file);
    }

    @Test
    public void testUpdateFavoriteStatus_fileNotFound() {
        when(uploadFileRepository.findById(2L)).thenReturn(Optional.empty());
        ApiResponse response = uploadService.updateFavoriteStatus(2L, true);
        assertEquals(400, response.getCode());
        assertEquals("文件不存在，无法更新收藏状态", response.getMessage());
    }
}
