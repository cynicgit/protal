package com.recharge.protal.config;

import com.recharge.protal.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//@Configuration
public class LoginInterceptorConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/")
                .excludePathPatterns("/error/**")
                .excludePathPatterns("/register/**")
                .excludePathPatterns("/imageCode/**");

        super.addInterceptors(registry);
    }


    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

}
