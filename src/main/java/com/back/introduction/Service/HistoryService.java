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
        return new ApiResponse(true, "Search successful", uploadFiles);
    }

    /**
     * 删除单条上传记录（通过文件 ID）
     */
    public ApiResponse deleteUploadRecord(Long fileId) {
        if (!uploadFileRepository.existsById(fileId)) {
            return ApiResponse.error("This upload record does not exist");
        }

        uploadFileRepository.deleteById(fileId);
        return ApiResponse.success("Upload record deleted successfully", null);
    }
}
