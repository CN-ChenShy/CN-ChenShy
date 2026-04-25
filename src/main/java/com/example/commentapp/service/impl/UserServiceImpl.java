package com.example.commentapp.service.impl;

import com.example.commentapp.entity.User;
import com.example.commentapp.repository.UserRepository;
import com.example.commentapp.service.UserService;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        String token = UUID.randomUUID().toString().replace("-", "");

        // 每个用户只保留一个最新的 token
        String userKey = "login:" + username;

        // 1. 先获取旧token
        String oldToken = stringRedisTemplate.opsForValue().get(userKey);

        // 2. 删除旧token（让旧登录失效）
        if (oldToken != null) {
            stringRedisTemplate.delete(oldToken);
        }

        // 3. 保存新token到用户key
        stringRedisTemplate.opsForValue().set(userKey, token, 30, TimeUnit.MINUTES);

        // 4. 保存token对应用户名（给拦截器用）
        stringRedisTemplate.opsForValue().set(token, username, 30, TimeUnit.MINUTES);

        return token;
    }
}