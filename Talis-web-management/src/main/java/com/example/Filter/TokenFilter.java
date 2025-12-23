package com.example.Filter;

import com.example.Utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1. 获取请求路径
        String path = request.getRequestURI();

        //2. 判断请求路径是否为登录接口
        if (path.contains("/login")){
            log.info("登录接口--放行");
            filterChain.doFilter(request, response);
            return;
        }
        //3. 获取token
        String token = request.getHeader("token");

        //4. 判断token是否存在
        if (token == null || token.isEmpty()){
            log.info("token不存在--拦截");
            response.setStatus(401);
            return;
        }
        //5. 验证token
        try {
            JwtUtil.parseJwt(token);
        } catch (Exception e) {
            log.info("token验证失败--拦截");
            response.setStatus(401);
            return;
        }

        //6. 放行
        log.info("放行");
        filterChain.doFilter(request, response);
    }
}
