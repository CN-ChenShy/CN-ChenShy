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

        // 放行 查看帖子、查看评论-不登录也能看！
        String uri = request.getRequestURI();
        if (uri.startsWith("/post/list") || uri.startsWith("/post/") || uri.startsWith("/comment/list/")) {
            return true;
        }

        // 只有 发帖、删帖、发评论、删评论 才需要校验登录
        String token = request.getHeader("token");
        String username = request.getHeader("username");

        // 没 token → 拦截
        if (token == null || token.isBlank()) {
            response.setStatus(401);
            return false;
        }

        // 校验 token 是否正确
        String redisToken = stringRedisTemplate.opsForValue().get("login:" + username);
        if (redisToken == null || !redisToken.equals(token)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"你的账号已在另一处登录，请重新登录\"}");
            return false;
        }

        return true;
    }
}