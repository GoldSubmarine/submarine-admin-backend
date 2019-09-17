package org.javahub.submarine.common.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import io.jsonwebtoken.impl.TextCodec;
import org.javahub.submarine.common.constant.ResultCode;
import org.javahub.submarine.common.exception.ServiceException;
import org.javahub.submarine.modules.security.config.JwtConfig;
import org.javahub.submarine.modules.security.entity.JwtUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Resource
    private UserDetailsService userDetailsService;

    private Clock clock = DefaultClock.INSTANCE;

    @Resource
    private JwtConfig jwtConfig;

    public String create(String username) {
        final Date createdDate = clock.now();
        Date expirationDate = new Date(createdDate.getTime() + jwtConfig.getExpiration());
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(createdDate)
            .signWith(SignatureAlgorithm.HS256, new MySigningKeyResolver().resolveSigningKeyBytes(username))
            .setExpiration(expirationDate)
            .compact();
    }

    public Boolean isExpired(String token) {
        Date expiration = getClaim(token, Claims::getExpiration);
        return expiration.before(clock.now());
    }

    public String refresh(String token) {
        if(isExpired(token)) {
            throw new ServiceException("登录已过期");
        }
        return create(getUsername(token));
    }

    public boolean shouldRefresh(String token) {
        Date expirationDate = getClaim(token, Claims::getExpiration);
        return (expirationDate.getTime() - new Date().getTime()) < jwtConfig.getUpdateExpiration();
    }

    /**
     * 获取用户名
     */
    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    /**
     * 获取body中的值
     */
    private <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        Claims body = getAllClaim(token);
        return claimsResolver.apply(body);
    }

    /**
     * 获取body中所有的值
     */
    private Claims getAllClaim(String token) {
        try {
            return Jwts.parser().setSigningKeyResolver(new MySigningKeyResolver()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new ServiceException(ResultCode.TOLOGIN, "请重新登录");
        }
    }

    /**
     * 用于获取每个用户私有的key
     * 文档位置：https://github.com/jwtk/jjwt#signing-key-resolver
     */
    private class MySigningKeyResolver extends SigningKeyResolverAdapter {
        @Override
        public byte[] resolveSigningKeyBytes(JwsHeader jwsHeader, Claims claims) {
            String username = claims.getSubject();
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
            return TextCodec.BASE64URL.decode(jwtUser.getJwtSecret());
        }

        private byte[] resolveSigningKeyBytes(String username) {
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
            return TextCodec.BASE64URL.decode(jwtUser.getJwtSecret());
        }
    }
}
