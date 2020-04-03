package com.htnova.security.config;

import com.htnova.common.constant.AppConst;
import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import com.htnova.common.util.response.ResponseHandler;
import com.htnova.security.entity.UserDetail;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //  启用方法级别的权限认证
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityConfig securityConfig;

    @Resource
    private ServerProperties serverProperties;

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    // 认证和授权的逻辑自定义，需要注意的是：不能在httpSecurity里直接配置 authenticationProvider
    // 这样只会配置出一个 AuthenticationProvider，parent 是 DaoAuthenticationProvider 的 authenticationProvider
    // 如果我们的AuthenticationProvider认证失败，则结果由 DaoAuthenticationProvider决定，不符合我们的预期
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(getAuthenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(serverProperties.getError().getPath());
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()   // 禁用 CSRF
                .requestCache().requestCache(new HttpSessionRequestCache())
                .and()
                // ExceptionTranslationFilter 异常处理使用
                .exceptionHandling()
                // 未登录的请求，请求失败处理
                .authenticationEntryPoint(getAuthenticationEntryPoint())
                // 未授权的请求，请求失败处理
                .accessDeniedHandler(getAccessDeniedHandler())
                .and()
                .formLogin()
                .loginProcessingUrl(securityConfig.getLoginProcessingUrl())
                // 登录成功后的处理
                .successHandler(getAuthenticationSuccessHandler())
                // 登录失败后的处理
                .failureHandler(getAuthenticationFailureHandler())
                .and()
                // 退出处理
                .logout()
                .permitAll()
                .logoutUrl(securityConfig.getLogoutUrl())
                // 退出默认302到登录页
                .logoutSuccessUrl(securityConfig.getLoginPage())
                // ajax请求退出后，返回 LOGOUT_SUCCESS
                .defaultLogoutSuccessHandlerFor(
                        (request, response, authentication) ->
                                ResponseHandler.create(Result.build(ResultStatus.LOGOUT_SUCCESS)).handle(request,response),
                        getAjaxHttpRequestMatcher())
                .and()
                .authorizeRequests()
                .antMatchers(AppConst.APP_LOGIN_URL, "/file/download/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .anyRequest()
                .   authenticated()    // 其他地址的访问均需验证权限
                .and()
                .headers().frameOptions().disable();   // sql监控页面 iframe 配置
    }

    private RequestMatcher getAjaxHttpRequestMatcher() {
        return new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return daoAuthenticationProvider;
    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler(){
        return new AuthenticationSuccessHandler(){
            private RequestCache requestCache = new HttpSessionRequestCache();
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                SavedRequest savedRequest = this.requestCache.getRequest(request, response);
                // 登录成功后跳转的URL，由后端决定。优先级如下：
                // 1. 上次失败的 ajax 请求的 Referer
                // 2. 上次失败的 请求的 URL
                // 3. 登录成功URL
                String redirectUrl = Optional.ofNullable(savedRequest)
                        .flatMap(sq -> sq.getHeaderNames().contains("X-Requested-With") ?
                                sq.getHeaderValues("Referer").stream().findFirst() :
                                Optional.ofNullable(sq.getRedirectUrl())).orElse(securityConfig.getLoginSuccessPage());
                Map<String,String> result = new HashMap<>();
                result.put("redirectUrl",redirectUrl);
                result.put("userName",((UserDetail)authentication.getPrincipal()).getUsername());
                ResponseHandler responseHandler = ResponseHandler.builder()
                        // ajax请求 返回 LOGIN_SUCCESS，并返回 redirectUrl
                        .handlerFor(ResponseHandler.create(Result.build(ResultStatus.LOGIN_SUCCESS, result)), getAjaxHttpRequestMatcher())
                        // 默认 直接302 到 redirectUrl
                        .defaultHandler((request1, response1) -> redirectStrategy.sendRedirect(request1, response1, redirectUrl)).build();
                responseHandler.handle(request,response);
            }
        };
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler(){
        return new AuthenticationFailureHandler(){
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                Result<?> result = getResult(e);
                ResponseHandler responseHandler = ResponseHandler.builder()
                        // ajax请求 返回 LOGIN_SUCCESS，并返回 redirectUrl
                        .handlerFor(ResponseHandler.create(result), getAjaxHttpRequestMatcher())
                        // 默认 直接302 到 登录页
                        .defaultHandler((request1, response1) -> redirectStrategy.sendRedirect(request1, response1, securityConfig.getLoginPage()+"?error="+result.getCode())).build();
                responseHandler.handle(request,response);
            }

            private Result<?> getResult(AuthenticationException e) {
                Result<?> result = Result.build(ResultStatus.LOGIN_ERROR);
                if(e instanceof AccountStatusException){
                    result = Result.build(ResultStatus.ACCOUNT_FREEZE);
                }else if(e instanceof UsernameNotFoundException || e instanceof BadCredentialsException){
                    result = Result.build(ResultStatus.USERNAME_PASSWORD_ERROR);
                }
                return result;
            }
        };
    }

    private AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new AuthenticationEntryPoint() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                ResponseHandler responseHandler = ResponseHandler.builder()
                        // ajax请求 返回 FORBIDDEN
                        .handlerFor(ResponseHandler.create(HttpStatus.UNAUTHORIZED, Result.build(ResultStatus.UNAUTHORIZED)), getAjaxHttpRequestMatcher())
                        // 默认 直接302 到 登录页面
                        .defaultHandler((request1, response1) -> redirectStrategy.sendRedirect(request1, response1, securityConfig.getLoginPage()+"?error="+ResultStatus.UNAUTHORIZED.getCode()))
                        .build();
                responseHandler.handle(request,response);
            }
        };
    }

    private AccessDeniedHandler getAccessDeniedHandler() {
        return new AccessDeniedHandler() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                ResponseHandler responseHandler = ResponseHandler.builder()
                        // ajax请求 返回 FORBIDDEN
                        .handlerFor(ResponseHandler.create(HttpStatus.FORBIDDEN, Result.build(ResultStatus.FORBIDDEN)), getAjaxHttpRequestMatcher())
                        // 默认 直接302 到 错误页面
                        .defaultHandler((request1, response1) -> redirectStrategy.sendRedirect(request1, response1, securityConfig.getErrorPage()+"?error="+ResultStatus.FORBIDDEN.getCode()))
                        .build();
                responseHandler.handle(request,response);
            }
        };
    }
}
