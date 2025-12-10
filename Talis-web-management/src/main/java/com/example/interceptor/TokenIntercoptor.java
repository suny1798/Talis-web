package com.example.interceptor;

import com.example.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class TokenIntercoptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        1. 获取请求路径
//        String path = request.getRequestURI();
//        2. 判断请求路径是否为登录接口
//        if (path.contains("/login")){
//            log.info("登录接口--放行");
//            return true;
//        }
        //3. 获取token
        String token = request.getHeader("token");

        //4. 判断token是否存在
        if (token == null || token.isEmpty()){
            log.info("token不存在--拦截");
            response.setStatus(401);
            return false;
        }
        //5. 验证token
        try {
            JwtUtil.parseJwt(token);
        } catch (Exception e) {
            log.info("token验证失败--拦截");
            response.setStatus(401);
            return false;
        }
        //6. 放行
        log.info("拦截请求---preHandle");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("拦截请求---postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("拦截请求---afterCompletion");
    }
}
