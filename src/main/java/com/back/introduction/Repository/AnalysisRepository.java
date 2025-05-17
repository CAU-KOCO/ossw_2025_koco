package com.back.introduction.Repository;

import com.back.introduction.Entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisResult, Long> {

    // 查询某用户的所有分析记录
    List<AnalysisResult> findByUserId(Long userId);

    // 查询某文件的分析记录（一个文件一般只有一条分析结果）
    AnalysisResult findByFileId(Long fileId);
}
