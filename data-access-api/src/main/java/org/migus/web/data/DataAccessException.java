package org.migus.web.data;

import org.apache.commons.lang.exception.NestableRuntimeException;

public class DataAccessException extends NestableRuntimeException {

	static final long serialVersionUID = 1L;

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}

}
