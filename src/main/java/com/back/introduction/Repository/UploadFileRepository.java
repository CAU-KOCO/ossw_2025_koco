package com.back.introduction.Repository;

import com.back.introduction.Entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {

    // 根据用户ID查询该用户上传的所有文件
    List<UploadFile> findByUserId(Long userId);

    // 根据存储的文件名查询上传记录（可选）
    UploadFile findByStoredFilename(String storedFilename);
}
