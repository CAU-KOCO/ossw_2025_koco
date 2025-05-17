package com.back.introduction.Repository;

import com.back.introduction.Entity.UploadFile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UploadFileRepositoryTest {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Test
    @DisplayName("应根据 userId 查询上传记录")
    void testFindByUserId() {
        // given
        UploadFile file1 = new UploadFile();
        file1.setUserId(1L);
        file1.setOriginalFilename("origin1.docx");
        file1.setStoredFilename("uuid1.docx");
        file1.setFilePath("/path/uuid1.docx");
        file1.setUploadTime(LocalDateTime.now());

        UploadFile file2 = new UploadFile();
        file2.setUserId(1L);
        file2.setOriginalFilename("origin2.pdf");
        file2.setStoredFilename("uuid2.pdf");
        file2.setFilePath("/path/uuid2.pdf");
        file2.setUploadTime(LocalDateTime.now());

        UploadFile file3 = new UploadFile();
        file3.setUserId(2L); // 另一用户
        file3.setOriginalFilename("origin3.hwp");
        file3.setStoredFilename("uuid3.hwp");
        file3.setFilePath("/path/uuid3.hwp");
        file3.setUploadTime(LocalDateTime.now());

        uploadFileRepository.saveAll(List.of(file1, file2, file3));

        // when
        List<UploadFile> result = uploadFileRepository.findByUserId(1L);

        // then
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(f -> f.getUserId().equals(1L)));
    }

    @Test
    @DisplayName("应根据 storedFilename 查询唯一上传记录")
    void testFindByStoredFilename() {
        UploadFile file = new UploadFile();
        file.setUserId(3L);
        file.setOriginalFilename("cv.pdf");
        file.setStoredFilename("stored_abc123.pdf");
        file.setFilePath("/uploads/stored_abc123.pdf");
        file.setUploadTime(LocalDateTime.now());

        uploadFileRepository.save(file);

        UploadFile found = uploadFileRepository.findByStoredFilename("stored_abc123.pdf");

        assertNotNull(found);
        assertEquals("cv.pdf", found.getOriginalFilename());
        assertEquals("stored_abc123.pdf", found.getStoredFilename());
    }

    @Test
    @DisplayName("应成功保存并生成 ID")
    void testSaveFileGeneratesId() {
        UploadFile file = new UploadFile();
        file.setUserId(4L);
        file.setOriginalFilename("intro.docx");
        file.setStoredFilename("file_456.docx");
        file.setFilePath("/files/file_456.docx");
        file.setUploadTime(LocalDateTime.now());

        UploadFile saved = uploadFileRepository.save(file);

        assertNotNull(saved.getId());
        assertEquals("intro.docx", saved.getOriginalFilename());
    }
}
