
-- 用户表
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       username VARCHAR(100),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 上传文件记录表
CREATE TABLE upload_files (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              user_id BIGINT,
                              original_filename VARCHAR(255),
                              stored_filename VARCHAR(255),
                              file_path TEXT,
                              upload_time TIMESTAMP,
                              is_favorite BOOLEAN DEFAULT FALSE,
                              folder_name VARCHAR(100),
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- AI 分析结果表
CREATE TABLE analysis_results (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  file_id BIGINT,
                                  user_id BIGINT,
                                  score INT,
                                  suggestion TEXT,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  FOREIGN KEY (file_id) REFERENCES upload_files(id) ON DELETE CASCADE,
                                  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 用户反馈记录表
CREATE TABLE feedbacks (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           user_id BIGINT,
                           content TEXT NOT NULL,
                           email VARCHAR(255),
                           submitted_at TIMESTAMP,
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);
