package com.back.introduction.Repository;

import com.back.introduction.Entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // 查询某个用户提交的所有反馈（可选）
    List<Feedback> findByUserId(Long userId);

    // 可根据需要添加更多自定义查询方法
}
