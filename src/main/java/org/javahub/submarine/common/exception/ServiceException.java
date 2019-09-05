/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package org.javahub.submarine.common.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
@Data
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 业务代码code
	 */
	private Integer code;

	/**
	 * http协议的status
	 */
	private HttpStatus httpStatus;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super(msg);
	}

	public ServiceException(int resultCode, String msg) {
		super(msg);
		this.code = resultCode;
	}

	public ServiceException(HttpStatus httpStatus, String msg) {
		super(msg);
		this.httpStatus = httpStatus;
	}

	public ServiceException(HttpStatus httpStatus, int resultCode, String msg) {
		super(msg);
		this.httpStatus = httpStatus;
		this.code = resultCode;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
