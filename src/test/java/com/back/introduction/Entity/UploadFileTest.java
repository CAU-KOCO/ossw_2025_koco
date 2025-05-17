package com.back.introduction.Entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UploadFileTest {

    @Test
    public void testUploadFileFields() {
        UploadFile file = new UploadFile();

        file.setId(1L);
        file.setUserId(100L);
        file.setOriginalFilename("resume.pdf");
        file.setStoredFilename("uuid-resume.pdf");
        file.setFilePath("/files/uuid-resume.pdf");
        file.setUploadTime(LocalDateTime.now());
        file.setFavorite(true);
        file.setCategory("求职");

        assertEquals(1L, file.getId());
        assertEquals(100L, file.getUserId());
        assertEquals("resume.pdf", file.getOriginalFilename());
        assertEquals("uuid-resume.pdf", file.getStoredFilename());
        assertEquals("/files/uuid-resume.pdf", file.getFilePath());
        assertTrue(file.isFavorite());
        assertEquals("求职", file.getCategory());
    }
}
