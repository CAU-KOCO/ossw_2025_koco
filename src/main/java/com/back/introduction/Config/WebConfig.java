package com.back.introduction.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置全局跨域支持，允许前端跨域访问后端接口。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 匹配所有接口
                .allowedOrigins(
                        "http://localhost:3000",         // 本地开发环境前端地址
                        "https://your-frontend-domain.com" // 正式部署时替换成前端实际地址
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")        // 允许所有请求头
                .allowCredentials(true);    // 允许携带 cookie 或认证信息
    }

    /**
     * 可选：静态资源映射（如果启用了文件上传并提供文件下载）
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:/your/upload/directory/"); // 本地上传文件夹路径
    }
}
