package com.example.commentapp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String username = request.getHeader("username");

        if (token == null || username == null || token.isBlank() || username.isBlank()) {
            response.setStatus(401);
            return false;
        }

        String redisToken = stringRedisTemplate.opsForValue().get("login:" + username);

        if (redisToken == null || !redisToken.equals(token)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"你的账号已在另一处登录\"}");
            return false;
        }

        return true;
    }
}