package com.htnova.common.util;

import com.htnova.security.config.JwtConfig;
import com.htnova.security.entity.JwtUser;
import io.jsonwebtoken.Claims;
import lombok.Builder;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by suikelei on 10/12/19.
 */
@Builder
public class JwtToken {

    private Claims claims;

    private JwtConfig jwtConfig;

    private JwtUtil jwtUtil;

    public String getSubject() {
        return claims.getSubject();
    }

    public void refresh(HttpServletResponse response, JwtUser jwtUser) {
        if(shouldRefresh()){
            response.setHeader(jwtConfig.getRefreshHeader(), jwtUtil.create(jwtUser));
        }
    }

    private boolean shouldRefresh() {
        return (claims.getExpiration().getTime() - new Date().getTime()) < jwtConfig.getUpdateExpiration();
    }
}
