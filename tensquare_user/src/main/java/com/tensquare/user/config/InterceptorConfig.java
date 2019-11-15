package com.tensquare.user.config;

import com.tensquare.user.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
  @Resource
  private JwtInterceptor jwtInterceptor;

  @Override
  protected void addInterceptors(InterceptorRegistry registry){
    registry.addInterceptor(jwtInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/**/login/**");
  }

}
