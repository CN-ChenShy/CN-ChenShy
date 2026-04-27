package com.example.commentapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register");       // 登录、注册
                //.excludePathPatterns("/user/register")     // 注册
                //.excludePathPatterns("/post/list")         // 帖子列表（游客可看）
                //.excludePathPatterns("/post/*")            // 帖子详情（游客可看）
                //.excludePathPatterns("/comment/list/*");   // 评论列表（游客可看）
    }
}