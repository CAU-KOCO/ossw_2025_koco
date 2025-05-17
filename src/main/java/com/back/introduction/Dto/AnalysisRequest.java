package com.back.introduction.Dto;

public class AnalysisRequest {

    private Long userId;             // 用户 ID（可选）
    private Long fileId;            // 上传的文件 ID（用于后端获取文件内容）
    private String content;         // 可选：直接传入的自我介绍文本内容
    private String analysisType;    // 可选：指定分析模型或分析维度

    public AnalysisRequest() {
    }

    public AnalysisRequest(Long userId, Long fileId, String content, String analysisType) {
        this.userId = userId;
        this.fileId = fileId;
        this.content = content;
        this.analysisType = analysisType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }
}
