package com.back.introduction.Util;

import java.util.Set;

public class FileTypeChecker {

    // 允许的文件扩展名
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "txt");

    /**
     * 检查文件名是否为允许的文件类型（pdf、txt）
     * @param filename 原始文件名
     * @return 是否允许上传
     */
    public static boolean isAllowed(String filename) {
        if (filename == null || !filename.contains(".")) {
            return false;
        }
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(ext);
    }
}
