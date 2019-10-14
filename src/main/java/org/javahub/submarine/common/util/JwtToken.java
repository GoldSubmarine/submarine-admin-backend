package org.javahub.submarine.common.util;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import org.javahub.submarine.modules.security.config.JwtConfig;
import org.javahub.submarine.modules.security.entity.JwtUser;

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
