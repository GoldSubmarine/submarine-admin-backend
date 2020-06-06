package com.htnova.common.interceptor;

import com.htnova.common.exception.GlobalErrorController;
import com.htnova.common.exception.RestErrorAttributes;
import java.util.List;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** 拦截器 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //        registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new XPageMapMethodArgumentResolver());
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new RestErrorAttributes();
    }

    @Bean
    public GlobalErrorController getError(ErrorAttributes errorAttributes) {
        return new GlobalErrorController(errorAttributes);
    }
}
