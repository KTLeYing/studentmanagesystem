package com.mzl.studentmanagesystem.config;

import com.mzl.studentmanagesystem.interceptor.LoginInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName :   SpringmvcConfig
 * @Description: 扩展springmvc功能
 * @Author: 21989
 * @CreateDate: 2020/7/29 14:47
 * @Version: 1.0
 */
@Component
public class SpringmvcConfig implements WebMvcConfigurer {

    /**
     * 拦截除了下面以外是所有路径,联系LoginInterceptor
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/system/login", "/system/checkCode", "/easyui/**", "/h-ui/**", "/upload/**");
    }

    /**
     * 添加拦截器，放行静态资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/classpath:/static/");
    }

    /**
     * 不拦截login.html视图（视图名为login）
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("/login");
    }

}
