package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public ApiResponse saveFile(MultipartFile file, Long userId) throws IOException {
        // 1. 获取原始文件名和扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);

        // 2. 生成唯一文件名
        String newFilename = UUID.randomUUID() + "." + extension;

        // 3. 构造文件路径
        File destFile = new File(uploadDir + File.separator + newFilename);

        // 确保目录存在
        destFile.getParentFile().mkdirs();
        file.transferTo(destFile);

        // 4. 保存上传记录到数据库
        UploadFile uploadFile = new UploadFile();
        uploadFile.setUserId(userId);
        uploadFile.setOriginalFilename(originalFilename);
        uploadFile.setStoredFilename(newFilename);
        uploadFile.setUploadTime(LocalDateTime.now());
        uploadFile.setFilePath(destFile.getAbsolutePath());

        uploadFileRepository.save(uploadFile);

        // 5. 返回成功响应
        return ApiResponse.success("文件上传成功", uploadFile);
    }

    public ApiResponse updateFavoriteStatus(Long fileId, boolean favorite) {
        UploadFile uploadFile = uploadFileRepository.findById(fileId).orElse(null);
        if (uploadFile == null) {
            return ApiResponse.error("文件不存在，无法更新收藏状态");
        }

        uploadFile.setFavorite(favorite);
        uploadFileRepository.save(uploadFile);
        return ApiResponse.success("收藏状态更新成功", uploadFile);
    }

    public ApiResponse updateCategory(Long fileId, String category) {
        UploadFile uploadFile = uploadFileRepository.findById(fileId).orElse(null);
        if (uploadFile == null) {
            return ApiResponse.error("文件不存在，无法更新分类");
        }

        uploadFile.setCategory(category);
        uploadFileRepository.save(uploadFile);
        return ApiResponse.success("分类更新成功", uploadFile);
    }

}
