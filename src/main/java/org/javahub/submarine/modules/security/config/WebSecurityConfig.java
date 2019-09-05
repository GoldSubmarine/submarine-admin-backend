package org.javahub.submarine.modules.security.config;

import org.javahub.submarine.config.Interceptor.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //  允许所有用户访问"/"和"/index.html"
        httpSecurity.csrf().disable()   // 禁用 CSRF
            //因为使用JWT，所以不需要HttpSession
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
//            .antMatchers("/", "/index.html").permitAll()
            .antMatchers("/auth/login", "/auth/info", "/auth/logout").permitAll()
            .antMatchers("/druid/**").permitAll()
            .anyRequest().authenticated()   // 其他地址的访问均需验证权限
            // 防止iframe 造成跨域
            .and().headers().frameOptions().disable();

        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
