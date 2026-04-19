package com.example.commentapp;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    // 根据用户名+密码查询（登录用）
    User findByUsernameAndPassword(String username, String password);

    // 判断用户名是否存在（注册用）
    boolean existsByUsername(String username);
}