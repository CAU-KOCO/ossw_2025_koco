package com.back.introduction.Repository;

import com.back.introduction.Entity.Feedback;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    @DisplayName("应能保存反馈并通过 userId 查询")
    void testSaveAndFindByUserId() {
        // 保存两条反馈，分别来自 userId=1 和 userId=2
        Feedback f1 = new Feedback(1L, "这是一条反馈内容 A", "a@test.com", LocalDateTime.now());
        Feedback f2 = new Feedback(1L, "这是一条反馈内容 B", "b@test.com", LocalDateTime.now());
        Feedback f3 = new Feedback(2L, "用户2的反馈内容", "c@test.com", LocalDateTime.now());

        feedbackRepository.save(f1);
        feedbackRepository.save(f2);
        feedbackRepository.save(f3);

        // 查询 userId = 1 的反馈
        List<Feedback> results = feedbackRepository.findByUserId(1L);

        // 返回两条反馈
        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(f -> f.getUserId().equals(1L)));
    }

    @Test
    @DisplayName("应能通过 save() 成功存储反馈并获取 ID")
    void testSaveFeedbackAndIdGenerated() {
        Feedback feedback = new Feedback(99L, "测试自动生成主键", "test@user.com", LocalDateTime.now());
        Feedback saved = feedbackRepository.save(feedback);

        assertNotNull(saved.getId());
        assertEquals("test@user.com", saved.getEmail());
    }
}
