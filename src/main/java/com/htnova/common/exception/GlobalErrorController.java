package com.htnova.common.exception;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

@RequestMapping({ "${server.error.path:${error.path:/error}}" })
public class GlobalErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    @RequestMapping
    public ResponseEntity<Result<?>> error(HttpServletRequest request) {
        Throwable error = this.errorAttributes.getError(new ServletWebRequest(request));
        if (error != null) {
            Result<?> result = ExceptionTranslate.translate(error);
            return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
        }
        Result<?> result = translateHttpStatus(request);
        return new ResponseEntity<>(result, HttpStatus.valueOf(result.getStatus()));
    }

    private Result<?> translateHttpStatus(HttpServletRequest request) {
        Result<?> result;
        HttpStatus httpStatus;
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                httpStatus = HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
        if (HttpStatus.NOT_FOUND == httpStatus) {
            result = Result.build(httpStatus, ResultStatus.NOT_FOUND);
        } else {
            result = Result.build(httpStatus, httpStatus.value(), httpStatus.getReasonPhrase(), null);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, 0);
    }

    @Override
    @SuppressWarnings("deprecation")
    public String getErrorPath() {
        return null;
    }
}
