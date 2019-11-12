package com.htnova.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    /**
     * 前端传递过来的header
     */
    @Value("${jwt.header}")
    private String header;

    /**
     * 刷新token的header
     */
    @Value("${jwt.refreshHeader}")
    private String refreshHeader;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.updateExpiration}")
    private Long updateExpiration;

}
