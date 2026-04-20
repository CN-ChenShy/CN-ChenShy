package com.example.commentapp.controller;

import com.example.commentapp.entity.User;
import com.example.commentapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    // 登录
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        return userService.login(user);
    }
}