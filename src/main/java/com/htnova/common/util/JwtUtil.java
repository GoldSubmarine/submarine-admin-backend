package com.htnova.common.util;

import com.htnova.security.config.JwtConfig;
import com.htnova.security.entity.AuthUser;
import com.htnova.system.manage.entity.User;
import com.htnova.system.manage.service.UserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Resource
    private UserService userService;

    private Clock clock = DefaultClock.INSTANCE;

    @Resource
    private JwtConfig jwtConfig;

    public String create(AuthUser authUser) {
        final Date createdDate = clock.now();
        Date expirationDate = new Date(createdDate.getTime() + jwtConfig.getExpiration());
        return Jwts.builder()
            .setSubject(authUser.getUsername())
            .setIssuedAt(createdDate)
            .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64URL.decode(authUser.getJwtSecret()))
            .setExpiration(expirationDate)
            .compact();
    }

    public JwtToken getJwtToken(String token) {
        try {
            return JwtToken
                    .builder()
                    .claims(
                            Jwts
                                    .parser()
                                    .setSigningKeyResolver(new SigningKeyResolverAdapter(){
                                        @Override
                                        public byte[] resolveSigningKeyBytes(JwsHeader jwsHeader, Claims claims) {
                                            String username = claims.getSubject();
                                            User user = userService.getByUsername(username);
                                            return TextCodec.BASE64URL.decode(user.getJwtSecret());
                                        }
                                        @Override
                                        public byte[] resolveSigningKeyBytes(JwsHeader header, String payload) {
                                            User user = userService.getByUsername(payload);
                                            return TextCodec.BASE64URL.decode(user.getJwtSecret());
                                        }})
                                    .parseClaimsJws(token)
                                    .getBody()
                    )
                    .jwtConfig(jwtConfig)
                    .jwtUtil(this)
                    .build();
        } catch (Exception e) {
            log.debug("parse token {} error",token,e);
            return null;
        }
    }
}
