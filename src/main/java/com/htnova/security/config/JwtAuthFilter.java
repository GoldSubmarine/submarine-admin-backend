package com.htnova.security.config;

import com.htnova.common.util.JwtToken;
import com.htnova.common.util.JwtUtil;
import com.htnova.security.entity.JwtUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    private JwtConfig jwtConfig;

    private UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, JwtConfig jwtConfig, @Qualifier("jwtUserDetailService") UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader(jwtConfig.getHeader());
        if (StringUtils.isNotBlank(token) && token.length() >= 7) {
            JwtToken jwtToken = jwtUtil.getJwtToken(token.substring(7));
            if(Objects.nonNull(jwtToken)){
                JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(jwtToken.getSubject());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                jwtToken.refresh(response,jwtUser);
            }
        }
        chain.doFilter(request, response);
    }
}
