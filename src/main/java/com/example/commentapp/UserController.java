package com.example.commentapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // 注册：用户名 + 密码(没设置别的任何验证)
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        //禁止注册admin账号
        if ("admin".equals(user.getUsername())) {
            return "此用户不可注册";
        }

        if (userRepository.existsByUsername(user.getUsername())) {
            return "用户名已存在";
        }
        userRepository.save(user);
        return "注册成功";
    }

    // 登录
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loginUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            return "登录失败";
        }
        return "登录成功";
    }
}