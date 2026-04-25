package com.example.commentapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        // 看帖子、看评论：完全放行！不校验任何东西！
        if (uri.startsWith("/post/list") 
            || uri.startsWith("/post/") 
            || uri.startsWith("/comment/list/")) {
            return true;
        }

        // 下面的代码 只有 发帖/删帖/发评论/删评论 才会执行
        String token = request.getHeader("token");
        String encodedUsername = request.getHeader("username");

        // 没有 token 直接拦截
        if (token == null || token.isBlank()) {
            response.setStatus(401);
            return false;
        }

        // 没有用户名 直接拦截（修复空指针）
        if (encodedUsername == null || encodedUsername.isBlank()) {
            response.setStatus(401);
            return false;
        }

        // 解码中文username
        String username = URLDecoder.decode(encodedUsername, StandardCharsets.UTF_8.name());

        // 校验 token
        String redisToken = stringRedisTemplate.opsForValue().get("login:" + username);
        if (redisToken == null || !redisToken.equals(token)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"你的账号已在另一处登录，请重新登录\"}");
            return false;
        }

        return true;
    }
}