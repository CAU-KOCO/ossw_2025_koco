package com.back.introduction.Controller;

import com.back.introduction.Service.HistoryService;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    // 查询当前用户上传过的文件列表（模拟从 token 中解析 userId）
    @GetMapping("/uploads/{userId}")
    public ApiResponse getUploadHistory(@PathVariable Long userId) {
        return historyService.getUploadHistory(userId);
    }
}
