package com.htnova.common.exception;

import com.htnova.common.constant.ResultStatus;
import com.htnova.common.dto.Result;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class GlobalErrorController implements ErrorController {
    private final ErrorAttributes errorAttributes;

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        HttpStatus httpStatus = exceptionHttpStatus(request);
        if (httpStatus != null) return httpStatus;
        return servletHttpStatus(request);
    }

    private HttpStatus exceptionHttpStatus(HttpServletRequest request) {
        Throwable error = this.errorAttributes.getError(new ServletWebRequest(request));
        if (error != null) {
            if (error instanceof AuthenticationException) {
                return HttpStatus.UNAUTHORIZED;
            }
            if (error instanceof AccessDeniedException) {
                return HttpStatus.FORBIDDEN;
            }
            if (error instanceof ServiceException) {
                return HttpStatus.BAD_REQUEST;
            }
        }
        return null;
    }

    private HttpStatus servletHttpStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }

    @RequestMapping
    public ResponseEntity<Result<?>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        Result<?> result = getErrorMessage(request, status);
        return new ResponseEntity<>(result, status);
    }

    private Result<?> getErrorMessage(HttpServletRequest request, HttpStatus status) {
        if (status.value() == 404) return Result.build(ResultStatus.NOT_FOUND);
        Result<?> result = new Result<>();
        Throwable error = this.errorAttributes.getError(new ServletWebRequest(request));
        if (error != null) {
            while (true) {
                if (!(error instanceof ServletException) || error.getCause() == null) {
                    BindingResult bindingResult = this.extractBindingResult(error);
                    if (bindingResult != null) {
                        if (bindingResult.hasErrors()) {
                            result = Result.build(ResultStatus.BIND_ERROR);
                            //							result =
                            // result.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
                        } else {
                            result = Result.build(ResultStatus.NO_ERROR);
                        }
                    } else {
                        if (error instanceof ServiceException) {
                            ServiceException serviceException = (ServiceException) error;
                            result =
                                    Result.build(
                                            serviceException.getCode(),
                                            serviceException.getMessage(),
                                            serviceException.getData());
                        } else {
                            result = Result.build(ResultStatus.SERVER_ERROR);
                        }
                    }
                    if (error instanceof ServiceException) {
                        result.setCode(((ServiceException) error).getCode());
                    }
                    break;
                }
                error = error.getCause();
            }
        }
        String message =
                this.getAttribute(new ServletWebRequest(request), "javax.servlet.error.message");
        if ((!StringUtils.isEmpty(message) || StringUtils.isEmpty(result.getMsg()))
                && !(error instanceof BindingResult)
                && StringUtils.isEmpty(result.getMsg())) {
            result.setMsg(StringUtils.isEmpty(message) ? "" : message);
        }
        return result;
    }

    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        }
        return error instanceof MethodArgumentNotValidException
                ? ((MethodArgumentNotValidException) error).getBindingResult()
                : null;
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
        return (T) requestAttributes.getAttribute(name, 0);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
