package com.back.introduction.Controller;

import com.back.introduction.Dto.AnalysisRequest;
import com.back.introduction.Service.AnalysisService;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;

    /**
     * 提交自我介绍书进行分析（调用 NLP 模块）
     * 接收字段：userId, fileId（或 content）, analysisType
     */
    @PostMapping("/generate")
    public ApiResponse analyzeResume(@RequestBody AnalysisRequest request) {
        return analysisService.analyze(request);
    }
}
