package org.javahub.submarine.common.exception;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestErrorAttributes implements ErrorAttributes, HandlerExceptionResolver {
    private static final String ERROR_ATTRIBUTE = RestErrorAttributes.class.getName() + ".ERROR";

    public RestErrorAttributes() {
    }

    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        this.storeErrorAttributes(request, ex);
        return null;
    }

    private void storeErrorAttributes(HttpServletRequest request, Exception ex) {
        request.setAttribute(ERROR_ATTRIBUTE, ex);
    }

    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        return null;
    }

    public Throwable getError(WebRequest webRequest) {
        Throwable exception = (Throwable)this.getAttribute(webRequest, ERROR_ATTRIBUTE);
        if(exception == null) {
            exception = (Throwable)this.getAttribute(webRequest, "javax.servlet.error.exception");
        }
        return exception;
    }

    @SuppressWarnings("unchecked")
    private   <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T)requestAttributes.getAttribute(name, 0);
    }
}