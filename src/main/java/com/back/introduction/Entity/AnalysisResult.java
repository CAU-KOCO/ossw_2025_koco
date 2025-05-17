package com.back.introduction.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "analysis_results")
public class AnalysisResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long fileId;

    @Column(columnDefinition = "TEXT")
    private String sectionScoresJson;  // 分项打分，格式为 JSON 字符串

    @Column(columnDefinition = "TEXT")
    private String suggestions;        // 对自我介绍书的修改建议

    private int totalScore;            // 总评分

    private LocalDateTime analyzedAt;  // 分析时间

    // 构造函数
    public AnalysisResult() {}

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getSectionScoresJson() {
        return sectionScoresJson;
    }

    public void setSectionScoresJson(String sectionScoresJson) {
        this.sectionScoresJson = sectionScoresJson;
    }

    public String getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(String suggestions) {
        this.suggestions = suggestions;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }
}
