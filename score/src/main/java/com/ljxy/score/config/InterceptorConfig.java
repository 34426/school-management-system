package com.ljxy.score.config;

import com.ljxy.score.common.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器
 * @author SuSheng
 * @date 2022/3/28 15:25
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login","/doc.html#/**","/v2/**","/webjars/**","/swagger-resources/**","/getcode","/student/upload","/escore/upload");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
