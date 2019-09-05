package org.javahub.submarine.config.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.javahub.submarine.common.util.JwtUtil;
import org.javahub.submarine.modules.security.config.JwtConfig;
import org.javahub.submarine.modules.security.entity.JwtUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private JwtConfig jwtConfig;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //获取 token
        String token = null;
        for (Cookie cookie : request.getCookies()) {
            if(jwtConfig.getCookieKey().equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        if(Objects.nonNull(token)) {
            String username = jwtUtil.getUsername(token);
            JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(username);
            // 校验
            if(!jwtUtil.isExpired(token)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 更新token
            if(SecurityContextHolder.getContext().getAuthentication() != null) {
                if(jwtUtil.shouldRefresh(token)) {
                    String newToken = jwtUtil.refresh(token);
                    Cookie newCookie = new Cookie(jwtConfig.getCookieKey(), newToken);
                    response.addCookie(newCookie);
                }
            }
        }

        chain.doFilter(request, response);
    }

}
