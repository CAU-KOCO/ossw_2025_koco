package com.back.introduction.Repository;

import com.back.introduction.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 根据邮箱查找用户，用于登录或注册时验证邮箱是否已存在
    Optional<User> findByEmail(String email);

    // 检查邮箱是否已存在，用于注册时防止重复
    boolean existsByEmail(String email);
}
