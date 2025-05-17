package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UploadServiceTest {

    @InjectMocks
    private UploadService uploadService;

    @Mock
    private UploadFileRepository uploadFileRepository;

    private AutoCloseable mocks;

    private static final String TEST_UPLOAD_DIR = "test-uploads";

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        // 设置 uploadDir 字段值
        ReflectionTestUtils.setField(uploadService, "uploadDir", TEST_UPLOAD_DIR);

        // 确保目录存在（真实测试时可以注释掉）
        new File(TEST_UPLOAD_DIR).mkdirs();
    }

    @Test
    void testSaveFile_success() throws IOException {
        // 1. 构造虚拟上传文件（.pdf 格式）
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",                      // 表单字段名
                "resume.pdf",                // 原始文件名
                "application/pdf",          // Content-Type
                "dummy content".getBytes()   // 文件内容
        );

        // 2. 模拟保存操作
        when(uploadFileRepository.save(any(UploadFile.class))).thenAnswer(invocation -> {
            UploadFile file = invocation.getArgument(0);
            file.setId(1L); // 模拟数据库返回主键
            return file;
        });

        // 3. 调用上传逻辑
        ApiResponse response = uploadService.saveFile(mockFile, 42L);

        // 4. 验证返回结果
        assertTrue(response.isSuccess());
        assertEquals("文件上传成功", response.getMessage());

        UploadFile savedFile = (UploadFile) response.getData();
        assertEquals("resume.pdf", savedFile.getOriginalFilename());
        assertEquals(42L, savedFile.getUserId());
        assertNotNull(savedFile.getStoredFilename());
        assertTrue(savedFile.getStoredFilename().endsWith(".pdf"));

        // 5. 验证数据库调用了一次
        verify(uploadFileRepository, times(1)).save(any(UploadFile.class));
    }
}
