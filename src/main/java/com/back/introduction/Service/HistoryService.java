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

    public ApiResponse getUploadHistory(Long userId) {
        List<UploadFile> uploadFiles = uploadFileRepository.findByUserId(userId);
        return new ApiResponse(true, "查询成功", uploadFiles);
    }
}
