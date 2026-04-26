package com.csu.admin.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        
        // 放行静态资源和登录相关接口
        if (uri.startsWith("/static/") || 
            uri.startsWith("/css/") || 
            uri.startsWith("/js/") || 
            uri.startsWith("/images/") ||
            uri.contains("/login")) {
            return true;
        }
        
        // 检查用户是否登录
        Object user = request.getSession().getAttribute("adminUser");
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }
        
        return true;
    }
}
