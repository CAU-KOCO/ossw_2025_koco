package com.back.introduction.Service;

import com.back.introduction.Dto.FeedbackRequest;
import com.back.introduction.Entity.Feedback;
import com.back.introduction.Repository.FeedbackRepository;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public ApiResponse submitFeedback(FeedbackRequest request) {
        Feedback feedback = new Feedback();
        feedback.setUserId(request.getUserId());
        feedback.setContent(request.getContent());
        feedback.setEmail(request.getEmail());
        feedback.setSubmittedAt(LocalDateTime.now());

        feedbackRepository.save(feedback);

        return new ApiResponse(true, "反馈已成功提交", null);
    }
}