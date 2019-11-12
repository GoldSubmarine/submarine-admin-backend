package com.htnova.common.util;

import com.htnova.modules.security.config.JwtConfig;
import com.htnova.modules.security.entity.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    @Resource
    private UserDetailsService userDetailsService;

    private Clock clock = DefaultClock.INSTANCE;

    @Resource
    private JwtConfig jwtConfig;

    public String create(JwtUser jwtUser) {
        final Date createdDate = clock.now();
        Date expirationDate = new Date(createdDate.getTime() + jwtConfig.getExpiration());
        return Jwts.builder()
            .setSubject(jwtUser.getUsername())
            .setIssuedAt(createdDate)
            .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64URL.decode(jwtUser.getJwtSecret()))
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
                                            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
                                            return TextCodec.BASE64URL.decode(jwtUser.getJwtSecret());
                                        }
                                        @Override
                                        public byte[] resolveSigningKeyBytes(JwsHeader header, String payload) {
                                            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(payload);
                                            return TextCodec.BASE64URL.decode(jwtUser.getJwtSecret());
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
