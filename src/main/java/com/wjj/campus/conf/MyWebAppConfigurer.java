package com.wjj.campus.conf;

import com.wjj.campus.interceptor.AdminURLInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: jie
 * Date: 2022/2/4
 * Time: 23:53
 * Description: 应用程序配置类
 *
 * @author jiajie.wan
 */
@Configuration
public class MyWebAppConfigurer implements WebMvcConfigurer {
    @Bean
    public HandlerInterceptor getMyInterceptor() {
        return new AdminURLInterceptor();
    }

    /**
     * 多个拦截器组成一个拦截器链
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求路径为 /admin 开头的请求
        registry.addInterceptor(getMyInterceptor()).excludePathPatterns("/admin/login").addPathPatterns("/admin/*");
    }
}

