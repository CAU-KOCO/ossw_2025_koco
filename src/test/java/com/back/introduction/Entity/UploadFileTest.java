package com.back.introduction.Entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UploadFileTest {

    @Test
    void testSettersAndGetters() {
        UploadFile file = new UploadFile();

        Long id = 1L;
        Long userId = 100L;
        String originalName = "自我介绍书.docx";
        String storedName = "uuid123456.docx";
        String filePath = "/upload/path/uuid123456.docx";
        LocalDateTime uploadTime = LocalDateTime.of(2025, 5, 17, 15, 30);

        file.setId(id);
        file.setUserId(userId);
        file.setOriginalFilename(originalName);
        file.setStoredFilename(storedName);
        file.setFilePath(filePath);
        file.setUploadTime(uploadTime);

        assertEquals(id, file.getId());
        assertEquals(userId, file.getUserId());
        assertEquals(originalName, file.getOriginalFilename());
        assertEquals(storedName, file.getStoredFilename());
        assertEquals(filePath, file.getFilePath());
        assertEquals(uploadTime, file.getUploadTime());
    }
}
