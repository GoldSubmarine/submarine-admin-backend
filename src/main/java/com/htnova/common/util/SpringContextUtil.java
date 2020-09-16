package com.htnova.common.util;

import com.htnova.security.entity.AuthUser;
import com.htnova.security.entity.UserDetail;
import com.htnova.security.mapstruct.AuthUserMapStruct;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.Session;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    private static void clearHolder() {
        log.info("清除SpringContextHolder中的ApplicationContext:{}", applicationContext);
        applicationContext = null;
    }

    public static AuthUser getAuthUser() {
        UserDetail userDetail = (UserDetail) Optional
            .ofNullable(SecurityContextHolder.getContext().getAuthentication())
            .map(Authentication::getPrincipal)
            .orElse(null);
        if (Objects.isNull(userDetail)) {
            return null;
        }
        return AuthUserMapStruct.INSTANCE.toAuthUser(userDetail.getUser());
    }

    @SuppressWarnings("unchecked")
    public static AuthUser getAuthUser(String httpSessionId) {
        SessionRepository<? extends Session> sessionRepository = getBean(SessionRepository.class);
        Session session = sessionRepository.findById(httpSessionId);
        if (Objects.isNull(session)) {
            return null;
        }
        SecurityContext securityContext = session.getAttribute(
            HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY
        );
        UserDetail userDetail = (UserDetail) Optional
            .ofNullable(securityContext.getAuthentication())
            .map(Authentication::getPrincipal)
            .orElse(null);
        if (Objects.isNull(userDetail)) {
            return null;
        }
        return AuthUserMapStruct.INSTANCE.toAuthUser(userDetail.getUser());
    }

    public static HttpServletRequest getRequest() {
        return Optional
            .ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
            .map(ServletRequestAttributes::getRequest)
            .orElse(null);
    }

    /** 检查ApplicationContext不为空. */
    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException(
                "applicaitonContext属性未注入, 请在applicationContext.xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder."
            );
        }
    }

    @Override
    public void destroy() {
        SpringContextUtil.clearHolder();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (SpringContextUtil.applicationContext != null) {
            log.warn(
                "SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:{}",
                SpringContextUtil.applicationContext
            );
        }
        SpringContextUtil.applicationContext = applicationContext;
    }
}
