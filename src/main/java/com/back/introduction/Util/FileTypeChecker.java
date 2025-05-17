package com.back.introduction.Util;

import java.util.Arrays;
import java.util.List;

public class FileTypeChecker {

    // 支持的扩展名列表
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "doc", "docx", "hwp");

    public static boolean isAllowed(String fileName) {
        if (fileName == null || !fileName.contains(".")) return false;
        String extension = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}
