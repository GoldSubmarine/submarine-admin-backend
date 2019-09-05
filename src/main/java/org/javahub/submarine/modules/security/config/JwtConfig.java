package org.javahub.submarine.modules.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {

    /**
     * cookie的key
     */
    @Value("${jwt.cookieKey}")
    private String cookieKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.updateExpiration}")
    private Long updateExpiration;

}
