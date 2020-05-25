package com.htnova.common.interceptor;

import com.htnova.common.dto.XPage;
import com.htnova.common.dto.XPageImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class XPageMapMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(XPage.class) || parameter.getParameterType().equals(XPageImpl.class);
    }

    @Override
    public XPageImpl<Object> resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String pageSize = webRequest.getParameter("pageSize");
        String pageNum = webRequest.getParameter("pageNum");
        XPageImpl<Object> xPage = new XPageImpl<>();
        if(StringUtils.isNotBlank(pageSize)) {
            xPage.setPageSize(Long.parseLong(pageSize));
        }
        if(StringUtils.isNotBlank(pageNum)) {
            xPage.setPageNum(Long.parseLong(pageNum));
        }
        return xPage;
    }
}
