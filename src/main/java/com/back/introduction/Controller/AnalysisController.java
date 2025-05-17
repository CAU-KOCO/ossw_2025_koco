package com.back.introduction.Controller;

import com.back.introduction.Dto.AnalysisRequest;
import com.back.introduction.Util.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analysis")
public class AnalysisController {

    @PostMapping("/generate")
    public ApiResponse analyzeResume(@RequestBody AnalysisRequest request) {
        return new ApiResponse(200, "Analysis interface reserved", null);
    }
}
