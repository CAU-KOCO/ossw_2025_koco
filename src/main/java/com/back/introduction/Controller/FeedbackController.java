package com.back.introduction.Controller;

import com.back.introduction.Dto.FeedbackRequest;
import com.back.introduction.Service.FeedbackService;
import com.back.introduction.Util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ApiResponse submitFeedback(@RequestBody FeedbackRequest request) {
        return feedbackService.submitFeedback(request);
    }
}

