package org.migus.web.content.soap;

import javax.xml.ws.WebFault;

@WebFault(faultBean="org.migus.web.content.soap.NotFound", 
		name = "notFound",
		targetNamespace = "http://migus.org/webservices/content-service/fault")
public class NotFoundException extends Exception {

	static final long serialVersionUID = 1L;

	NotFound notFound;

	public NotFoundException(String id) {
		this.notFound=new NotFound();
		this.notFound.setId(id);
	}
	
	public NotFoundException(String message, NotFound notFound) {
		super(message);
		this.notFound = notFound;
	}

	public NotFoundException(String message, NotFound notFound, 
			Throwable cause) {
		super(message, cause);
		this.notFound = notFound;
	}

	public NotFound getFaultInfo() {
		return this.notFound;
	}
}
