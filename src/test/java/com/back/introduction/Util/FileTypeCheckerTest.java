package com.back.introduction.Util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileTypeCheckerTest {

    @Test
    public void testValidPdfFile() {
        assertTrue(FileTypeChecker.isAllowed("resume.pdf"));
    }

    @Test
    public void testValidTxtFile() {
        assertTrue(FileTypeChecker.isAllowed("notes.txt"));
    }

    @Test
    public void testInvalidDocFile() {
        assertFalse(FileTypeChecker.isAllowed("document.doc"));
    }

    @Test
    public void testInvalidHwpFile() {
        assertFalse(FileTypeChecker.isAllowed("file.hwp"));
    }

    @Test
    public void testInvalidExtensionlessFile() {
        assertFalse(FileTypeChecker.isAllowed("no_extension"));
    }

    @Test
    public void testNullFilename() {
        assertFalse(FileTypeChecker.isAllowed(null));
    }

    @Test
    public void testUpperCaseExtension() {
        assertTrue(FileTypeChecker.isAllowed("report.PDF"));
        assertTrue(FileTypeChecker.isAllowed("text.TXT"));
    }

    @Test
    public void testHiddenFileLikeUnix() {
        assertFalse(FileTypeChecker.isAllowed(".bashrc"));
    }
}
