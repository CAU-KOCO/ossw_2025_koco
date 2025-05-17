-- 用户表
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    email VARCHAR(255) NOT NULL UNIQUE,
                                    password VARCHAR(255) NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 自我介绍文件上传记录
CREATE TABLE IF NOT EXISTS upload_file (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT,
                                           filename VARCHAR(255),
                                           filetype VARCHAR(20),
                                           uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 反馈信息
CREATE TABLE IF NOT EXISTS feedback (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        user_id BIGINT,
                                        content TEXT,
                                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
