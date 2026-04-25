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

        String uri = request.getRequestURI();

        // 看帖子、看评论：完全放行！不校验任何东西！
        if (uri.startsWith("/post/list") 
            || uri.startsWith("/post/") 
            || uri.startsWith("/comment/list/")) {
            return true;
        }

        // 只读取 token，不再读取 username！
        String token = request.getHeader("token");

        // 没有 token 直接拦截
        if (token == null || token.isBlank()) {
            response.setStatus(401);
            return false;
        }

        // 用 token 反查用户名
        String username = stringRedisTemplate.opsForValue().get(token);

        // 查不到 = 顶号 / 过期
        if (username == null || username.isBlank()) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"你的账号已在另一处登录，请重新登录\"}");
            response.setStatus(401);
            return false;
        }

        // 把当前登录人放入 request，后面接口直接用
        request.setAttribute("loginUsername", username);

        return true;
    }
}