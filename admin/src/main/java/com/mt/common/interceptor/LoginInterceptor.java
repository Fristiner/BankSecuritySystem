package com.mt.common.interceptor;


import com.mt.common.biz.user.UserContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    // 重写拦截器，前置操作
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (UserContext.getAccount() == null) {
            response.setStatus(401);
            return false;
        }
        return true;
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
