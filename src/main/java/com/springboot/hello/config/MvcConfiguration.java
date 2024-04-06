package com.springboot.hello.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/images/**")
//                .addResourceLocations("classpath:/static/images/"); // 이미지 경로

        // 추가 경로 설정
        registry.addResourceHandler("/api/v1/images/**")
                .addResourceLocations("classpath:/static/images/");
    }
}
