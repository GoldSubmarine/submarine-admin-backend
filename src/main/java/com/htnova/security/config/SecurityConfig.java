package com.htnova.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security")
@Data
public class SecurityConfig {
    private String loginPage;
    private String loginProcessingUrl;
    private String loginSuccessPage;
    private String logoutUrl;
    private String errorPage;
}
