package org.javahub.submarine.common.exception;

import org.javahub.submarine.common.dto.Result;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class GlobalErrorController implements ErrorController {

	private ErrorAttributes errorAttributes;

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
		if(error != null){
			if(error instanceof AuthenticationException){
				return HttpStatus.UNAUTHORIZED;
			}
			if(error instanceof AccessDeniedException){
				return HttpStatus.FORBIDDEN;
			}
			if(error instanceof ServiceException){
				return HttpStatus.BAD_REQUEST;
			}
		}
		return null;
	}

	private HttpStatus servletHttpStatus(HttpServletRequest request) {
		Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
		if(statusCode == null) {
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
	public ResponseEntity<Result> error(HttpServletRequest request) {
		HttpStatus status = this.getStatus(request);
		Result result = getErrorMessage(request, status);
		return new ResponseEntity<>(result, status);
	}

	private Result getErrorMessage(HttpServletRequest request, HttpStatus status){
		Result result = Result.fail();
		Throwable error = this.errorAttributes.getError(new ServletWebRequest(request));
		if(error != null) {
			while(true) {
				if(!(error instanceof ServletException) || error.getCause() == null) {
					BindingResult bindingResult = this.extractBindingResult(error);
					if(bindingResult != null){
						if(bindingResult.hasErrors()) {
							result.setMsg(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
//							result.setMsg("Validation failed for object=\'" + bindingResult.getObjectName() + "\'. Error count: " + bindingResult.getErrorCount());
						} else {
							result.setMsg("No errors");
						}
					}else {
						if(error instanceof AuthenticationException){
							result.setMsg("无效的token,请重新登录");
						}else if(error instanceof AccessDeniedException){
							result.setMsg("未授权的访问");
						}else if(error instanceof ServiceException){
							result.setMsg(error.getMessage());
						}else {
							result.setMsg("请求异常，请稍后重试");
						}
					}
					if(error instanceof ServiceException){
						result.setCode(((ServiceException)error).getCode());
					}
					break;
				}
				error = ((ServletException)error).getCause();
			}
		}
		String message = this.getAttribute(new ServletWebRequest(request), "javax.servlet.error.message");
		if((!StringUtils.isEmpty(message) || StringUtils.isEmpty(result.getMsg())) && !(error instanceof BindingResult)) {
			result.setMsg(StringUtils.isEmpty(message)?"":message);
		}
		return result;
	}


	private BindingResult extractBindingResult(Throwable error) {
		return error instanceof BindingResult?(BindingResult)error:(error instanceof MethodArgumentNotValidException ?((MethodArgumentNotValidException)error).getBindingResult():null);
	}

	@SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T)requestAttributes.getAttribute(name, 0);
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}