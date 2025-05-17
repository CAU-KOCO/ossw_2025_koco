package com.back.introduction.Service;

import com.back.introduction.Entity.UploadFile;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    /**
     * 查询用户上传历史记录
     */
    public ApiResponse getUploadHistory(Long userId) {
        List<UploadFile> uploadFiles = uploadFileRepository.findByUserId(userId);
        return new ApiResponse(true, "查询成功", uploadFiles);
    }

    /**
     * 删除单条上传记录（通过文件 ID）
     */
    public ApiResponse deleteUploadRecord(Long fileId) {
        if (!uploadFileRepository.existsById(fileId)) {
            return ApiResponse.error("该上传记录不存在");
        }

        uploadFileRepository.deleteById(fileId);
        return ApiResponse.success("上传记录删除成功", null);
    }
}
