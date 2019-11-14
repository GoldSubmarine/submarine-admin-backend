package com.htnova.common.exception;

import com.htnova.common.constant.ResultStatus;
import lombok.Data;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 */
@Data
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer code;

	private Object data;

	/**
	 * 默认为 ResultStatus.SERVER_ERROR 错误
	 */
	public ServiceException() {
		super(ResultStatus.SERVER_ERROR.getMsg());
		this.code = ResultStatus.SERVER_ERROR.getCode();
	}

	public ServiceException(ResultStatus resultStatus) {
		super(resultStatus.getMsg());
		this.code = resultStatus.getCode();
	}

	/**
	 * @param objects:传入要拼接的字符
	 */
	public ServiceException(ResultStatus resultStatus, Object ...objects) {
		super(resultStatus.getMsg());
		this.code = resultStatus.getCode();
		this.data = objects;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
