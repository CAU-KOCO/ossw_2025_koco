package com.back.introduction.Service;

import com.back.introduction.Dto.AnalysisRequest;
import com.back.introduction.Entity.AnalysisResult;
import com.back.introduction.Repository.AnalysisRepository;
import com.back.introduction.Repository.UploadFileRepository;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalysisService {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public ApiResponse analyze(AnalysisRequest request) {
        String content = request.getContent();

        // 如果前端未直接传入内容，通过 fileId 获取文件路径后读取
        if (content == null || content.trim().isEmpty()) {
            if (request.getFileId() == null) {
                return ApiResponse.error("Missing analytical content or file ID");
            }

            String filePath = uploadFileRepository.findById(request.getFileId())
                    .map(f -> f.getFilePath())
                    .orElse(null);

            if (filePath == null) {
                return ApiResponse.error("No corresponding file path found");
            }

            try {
                content = java.nio.file.Files.readString(java.nio.file.Path.of(filePath));
            } catch (Exception e) {
                return ApiResponse.error("Failed to read file：" + e.getMessage());
            }
        }

        // 模拟 NLP 分析结果（真实环境应调用 NLP 同学提供的 API）
        String sectionScoresJson = "{ \"自我介绍\": 80, \"经验经历\": 85, \"结尾总结\": 75 }";
        String suggestions = "请将经验部分描述得更具体；语言表达需更有逻辑性。";
        int totalScore = 80;

        // 构造分析结果实体
        AnalysisResult result = new AnalysisResult();
        result.setUserId(request.getUserId());
        result.setFileId(request.getFileId());
        result.setSectionScoresJson(sectionScoresJson);
        result.setSuggestions(suggestions);
        result.setTotalScore(totalScore);
        result.setAnalyzedAt(LocalDateTime.now());

        // 保存分析结果
        analysisRepository.save(result);

        return ApiResponse.success("Analysis complete", result);
    }
}
