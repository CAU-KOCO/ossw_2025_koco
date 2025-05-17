package com.back.introduction.Dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnalysisRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        AnalysisRequest request = new AnalysisRequest();

        request.setUserId(1L);
        request.setFileId(101L);
        request.setContent("这是我的自我介绍文本");
        request.setAnalysisType("NLP模型A");

        assertEquals(1L, request.getUserId());
        assertEquals(101L, request.getFileId());
        assertEquals("这是我的自我介绍文本", request.getContent());
        assertEquals("NLP模型A", request.getAnalysisType());
    }

    @Test
    void testAllArgsConstructor() {
        Long userId = 2L;
        Long fileId = 202L;
        String content = "这是第二段文本";
        String type = "模型B";

        AnalysisRequest request = new AnalysisRequest(userId, fileId, content, type);

        assertEquals(userId, request.getUserId());
        assertEquals(fileId, request.getFileId());
        assertEquals(content, request.getContent());
        assertEquals(type, request.getAnalysisType());
    }
}
