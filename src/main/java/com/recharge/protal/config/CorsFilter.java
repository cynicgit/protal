package com.recharge.protal.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 *  跨域过滤器
 *
 */
//@Component
public class CorsFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        System.out.println("请求Origin为："+request.getMethod());
        String method = request.getMethod();
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "token, Content-Type, Menu-Url");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
            System.out.println("*********************************跨域过滤器被使用**************************");
        if(!"OPTIONS".equals(method)){
            chain.doFilter(req, res);
        }
    }
    public void init(FilterConfig filterConfig) {}
    public void destroy()
    {
    }
}
