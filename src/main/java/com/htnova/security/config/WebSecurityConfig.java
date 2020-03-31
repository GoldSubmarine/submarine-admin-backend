package com.htnova.security.config;

import com.alibaba.fastjson.JSON;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private ServerProperties serverProperties;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void config(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().and().userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

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
                    })
                .and()
                    .formLogin()
                    .loginProcessingUrl("/auth/login")
                    .successHandler((request, response, authentication) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.getWriter().write(
                                JSON.toJSONString(Result.build(ResultStatus.LOGIN_SUCCESS, authentication.getName())));
                    })
                .and()
                    .logout()
                    .logoutUrl("/auth/logout")
                .and()
                    .authorizeRequests()
                    .antMatchers("/druid/**").permitAll()
                    .anyRequest()
                .   authenticated()    // 其他地址的访问均需验证权限
                .and()
                    .headers().frameOptions().disable();   // sql监控页面 iframe 配置
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(serverProperties.getError().getPath());
    }
}
