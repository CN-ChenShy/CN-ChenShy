package com.example.commentapp.service.impl;

import com.example.commentapp.entity.User;
import com.example.commentapp.repository.UserRepository;
import com.example.commentapp.service.UserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String register(User user) {
        // 禁止注册admin
        if ("admin".equals(user.getUsername())) {
            return "此用户不可注册";
        }

        // 用户名已存在
        if (userRepository.existsByUsername(user.getUsername())) {
            return "用户名已存在";
        }

        userRepository.save(user);
        return "注册成功";
    }

    @Override
    public String login(User user) {
        User loginUser = userRepository.findByUsernameAndPassword(
            user.getUsername(), 
            user.getPassword()
        );

        if (loginUser == null) {
            return "登录失败";
        }

        String username = user.getUsername();
        String newToken = UUID.randomUUID().toString().replace("-", "");

        // 删除该用户之前的所有旧 token
        Set<String> oldTokens = stringRedisTemplate.keys("*" + username);
        if (oldTokens != null && !oldTokens.isEmpty()) {
            stringRedisTemplate.delete(oldTokens);
        }

        // 保存新 token
        stringRedisTemplate.opsForValue().set(
            newToken,       
            username,       
            30, 
            TimeUnit.MINUTES
        );

        return newToken;
    }
}