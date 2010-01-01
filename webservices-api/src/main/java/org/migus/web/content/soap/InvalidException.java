package org.migus.web.content.soap;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.migus.web.content.soap.Invalid", 
		name = "invalid",
		targetNamespace = "http://migus.org/webservices/content-service/fault")
public class InvalidException extends Exception {

	static final long serialVersionUID = 1L;

	Invalid invalid;

	public InvalidException(Invalid invalid) {
		this.invalid=invalid;
	}
	
	public InvalidException(String message, Invalid invalid) {
		super(message);
		this.invalid = invalid;
	}

	public InvalidException(String message, Invalid invalid, 
			Throwable cause) {
		super(message, cause);
		this.invalid = invalid;
	}

	public Invalid getFaultInfo() {
		return this.invalid;
	}
}
