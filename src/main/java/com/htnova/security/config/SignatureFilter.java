package com.htnova.security.config;

import com.htnova.signature.security.cachedrequest.CachedBodyHttpServletRequest;
import com.htnova.signature.utils.SignatureUtil;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
// TODO: 1/9/20 指定urlPatterns，只有设备接口才需要经过这个filter
// 注意，需要配置@ServletComponentScan，这个filter才能启动
@WebFilter(filterName = "SignatureFilter", urlPatterns = "/test/*")
public class SignatureFilter extends OncePerRequestFilter {

    // 请求必须传参有三个：s (签名) t （时间戳） d（设备ID）
    private static final String SIGN_PARAM = "s";
    private static final String TIMESTAMP_PARAM = "t";
    private static final String DEVICE_PARAM = "d";

    private static final long TIME_THRESHOLD_IN_SECONDS = 15;

    private PathMatcher ignoreUrlMatcher = new AntPathMatcher();

    // TODO: 1/8/20 增加URL，某些上传的URL可以排除，不校验
    private List<String> ignoreURLs = new ArrayList<String>();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(httpServletRequest);
        String deviceId = cachedBodyHttpServletRequest.getParameter(DEVICE_PARAM);
        String timestamp = cachedBodyHttpServletRequest.getParameter(TIMESTAMP_PARAM);
        String signature = cachedBodyHttpServletRequest.getParameter(SIGN_PARAM);

        if(StringUtils.isEmpty(deviceId) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(signature)){
            // TODO: 1/9/20 全局异常处理
            throw new IllegalArgumentException("参数不全");
        }
        if(Math.abs(Long.parseLong(timestamp) - System.currentTimeMillis()) > TIME_THRESHOLD_IN_SECONDS * 1000){
            // TODO: 1/9/20 全局异常处理
            throw new IllegalArgumentException("时间戳不正确");
        }
        // TODO: 1/8/20 get secretKey by deviceId
        String secretKey = "112233";
        boolean result = SignatureUtil.getInstance(secretKey, Collections.singleton(SIGN_PARAM))
                .verifySignature(cachedBodyHttpServletRequest, signature);
        if(!result){
            // TODO: 1/9/20 全局异常处理
            throw new SecurityException("签名错误");
        }
        filterChain.doFilter(cachedBodyHttpServletRequest, httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestPath = getRequestPath(request);
        for(String url : ignoreURLs){
            if(ignoreUrlMatcher.match(url,requestPath)){
                return true;
            }
        }
        return false;
    }

    private String getRequestPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }
}