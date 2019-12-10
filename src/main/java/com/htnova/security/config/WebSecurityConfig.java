package com.htnova.security.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthFilter jwtAuthFilter;

    @Resource
    private ServerProperties serverProperties;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        httpSecurity
                .csrf().disable()   // 禁用 CSRF
                .exceptionHandling() // 异常直接抛出，交给SpringBoot处理
                    .authenticationEntryPoint((httpServletRequest, httpServletResponse, e) -> {
                        if (e != null) throw e;
                    })
                    .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                        if (e != null) throw e;
                    }).and()
                //因为使用JWT，所以不需要HttpSession
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                    .antMatchers("/auth/login", "/auth/info", "/auth/logout").permitAll()
                    .antMatchers("/druid/**").permitAll()
                    .anyRequest().authenticated();   // 其他地址的访问均需验证权限
        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(serverProperties.getError().getPath());
    }
}
