package com.back.introduction.Service;

import com.back.introduction.Dto.FeedbackRequest;
import com.back.introduction.Entity.Feedback;
import com.back.introduction.Repository.FeedbackRepository;
import com.back.introduction.Util.ApiResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {

    @InjectMocks
    private FeedbackService feedbackService;

    @Mock
    private FeedbackRepository feedbackRepository;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSubmitFeedback_success() {
        // 1. 构造请求数据
        FeedbackRequest request = new FeedbackRequest();
        request.setUserId(101L);
        request.setEmail("user@test.com");
        request.setContent("系统反馈很有用！");

        // 2. mock save 方法（不需要实际返回值）
        when(feedbackRepository.save(any(Feedback.class)))
                .thenAnswer(invocation -> {
                    Feedback saved = invocation.getArgument(0);
                    saved.setId(1L); // 模拟保存后的主键
                    return saved;
                });

        // 3. 调用服务方法
        ApiResponse response = feedbackService.submitFeedback(request);

        // 4. 验证返回
        assertTrue(response.isSuccess());
        assertEquals("反馈已成功提交", response.getMessage());
        assertNull(response.getData());

        // 5. 验证保存调用
        verify(feedbackRepository, times(1)).save(any(Feedback.class));
    }
}
