package org.javahub.submarine.common.exception;

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
	public ResponseEntity<ErrorResponse> error(HttpServletRequest request) {
		HttpStatus status = this.getStatus(request);
		ErrorResponse errorResponse = getErrorMessage(request, status);
		return new ResponseEntity<>(errorResponse, status);
	}

	private ErrorResponse getErrorMessage(HttpServletRequest request, HttpStatus status){
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setExtCode("");
		Throwable error = this.errorAttributes.getError(new ServletWebRequest(request));
		if(error != null) {
			while(true) {
				if(!(error instanceof ServletException) || error.getCause() == null) {
					BindingResult result = this.extractBindingResult(error);
					if(result != null){
						if(result.hasErrors()) {
							// TODO: 10/12/19 这里把 message填充成 校验的错误信息
							errorResponse.setMessage("Validation failed for object=\'" + result.getObjectName() + "\'. Error count: " + result.getErrorCount());
						} else {
							errorResponse.setMessage("No errors");
						}
					}else {
						if(error instanceof AuthenticationException){
							errorResponse.setMessage("无效的token,请重新登录");
						}else if(error instanceof AccessDeniedException){
							errorResponse.setMessage("未授权的访问");
						}else if(error instanceof ServiceException){
							errorResponse.setMessage(error.getMessage());
						}else {
							errorResponse.setMessage("请求异常，请稍后重试");
						}
					}
					if(error instanceof ServiceException){
						errorResponse.setExtCode(((ServiceException)error).getExtCode());
					}
					break;
				}
				error = ((ServletException)error).getCause();
			}
		}
		String message = this.getAttribute(new ServletWebRequest(request), "javax.servlet.error.message");
		if((!StringUtils.isEmpty(message) || StringUtils.isEmpty(errorResponse.getMessage())) && !(error instanceof BindingResult)) {
			errorResponse.setMessage(StringUtils.isEmpty(message)?"":message);
		}
		return errorResponse;
	}


	private BindingResult extractBindingResult(Throwable error) {
		return error instanceof BindingResult?(BindingResult)error:(error instanceof MethodArgumentNotValidException ?((MethodArgumentNotValidException)error).getBindingResult():null);
	}

	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T)requestAttributes.getAttribute(name, 0);
	}

	@Override
	public String getErrorPath() {
		return null;
	}

}