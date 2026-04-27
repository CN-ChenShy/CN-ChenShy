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
        String method = request.getMethod();

        // 只对真正的"读取"操作放行，管理操作（如发帖、删帖）需要验证
        if ("GET".equalsIgnoreCase(method) && (
            uri.startsWith("/post/list") 
            || isPostDetailUri(stripContextPath(uri))  // 精确匹配帖子详情页
            || uri.startsWith("/comment/list/")
        )) {
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

        // 单点登录核心：校验是否为最新 token
        String latestToken = stringRedisTemplate.opsForValue().get("login:" + username);
        if (latestToken == null || !token.equals(latestToken)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"你的账号已在另一处登录，请重新登录\"}");
            response.setStatus(401);
            return false;
        }

        // 把当前登录人放入 request，后面接口直接用
        request.setAttribute("loginUsername", username);

        return true;
    }
    
    private String stripContextPath(String uri) {
        // 移除上下文路径，例如 /api/post/1 -> /post/1
        if (uri.startsWith("/api")) {
            return uri.substring("/api".length());
        }
        return uri;
    }
    
    private boolean isPostDetailUri(String uri) {
        // 检查是否为 /post/{id} 格式的URI，其中id是纯数字
        if (!uri.startsWith("/post/")) {
            return false;
        }
        
        String[] parts = uri.split("/");
        if (parts.length != 3) {  // /post/{id} 分割后应该是3部分
            return false;
        }
        
        try {
            Integer.parseInt(parts[2]);  // 尝试解析ID为整数
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}