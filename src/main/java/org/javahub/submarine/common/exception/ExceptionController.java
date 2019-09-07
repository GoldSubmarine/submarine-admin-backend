package org.javahub.submarine.common.exception;

import org.javahub.submarine.config.Interceptor.JwtAuthFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController {

    /**
     * 直接throw Filter 传过来的异常，让 ControllerAdvice 处理
     */
    @RequestMapping("/filterExceptionHandler")
    public void filterExceptionHandler(HttpServletRequest request) throws Throwable {
        Exception e = (Exception) request.getAttribute(JwtAuthFilter.class.getSimpleName());
        if (e != null) {
            throw e;
        }
    }
}
