package com.back.introduction.Controller;

import com.back.introduction.Service.UploadService;
import com.back.introduction.Util.ApiResponse;
import com.back.introduction.Util.FileTypeChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传自我介绍书
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse uploadResume(@RequestParam("file") MultipartFile file,
                                    @RequestParam("userId") Long userId) {
        // 1. 空文件检测（上传失败，文件不能为空）
        if (file.isEmpty()) {
            return ApiResponse.error("Upload failed, file cannot be empty");
        }

        // 2. 检查文件类型是否为 pdf、doc、docx、hwp（仅支持上传 pdf、word 或 hwp 格式的文件）
        String fileName = file.getOriginalFilename();
        if (!FileTypeChecker.isAllowed(fileName)) {
            return ApiResponse.error("Only supports uploading files in PDF, Word, or HP formats.");
        }

        // 3. 执行文件保存及数据库记录
        try {
            return uploadService.saveFile(file, userId);
        } catch (Exception e) {
            return ApiResponse.error("File upload failed: " + e.getMessage());
            //文件上传失败
        }
    }

    /**
     * 更新收藏状态
     */
    @PatchMapping("/{fileId}/favorite")
    public ApiResponse updateFavoriteStatus(@PathVariable Long fileId,
                                            @RequestParam("favorite") boolean favorite) {
        return uploadService.updateFavoriteStatus(fileId, favorite);
    }

    /**
     * 更新分类标签
     */
    @PatchMapping("/{fileId}/category")
    public ApiResponse updateCategory(@PathVariable Long fileId,
                                      @RequestParam("category") String category) {
        return uploadService.updateCategory(fileId, category);
    }
}
