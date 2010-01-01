package org.migus.web.content.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.migus.web.content.types.Content;
import org.migus.web.content.types.Contents;
import org.migus.web.content.types.NewContent;

@WebService(targetNamespace = "http://migus.org/webservices/content-service",
		name = "content-service")
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public interface ContentService {

	@WebMethod
	@WebResult(name = "id")
	@RequestWrapper(className="org.migus.web.content.soap.Add")
	@ResponseWrapper(className="org.migus.web.content.soap.AddResponse")
	public String add(@WebParam(name = "newContent") NewContent newContent);

	@WebMethod
	@WebResult(name = "content")
	@RequestWrapper(className="org.migus.web.content.soap.GetById")
	@ResponseWrapper(className="org.migus.web.content.soap.GetByIdResponse")
	public Content getById(@WebParam(name = "id") String id) throws
			NotFoundException, InvalidException;

	@RequestWrapper(className="org.migus.web.content.soap.GetByAuthor")
	@ResponseWrapper(className="org.migus.web.content.soap.GetByAuthorResponse")
	@WebMethod
	@WebResult(name = "contents")
	public Contents getByAuthor(@WebParam(name = "author") String author);
	
	@RequestWrapper(className="org.migus.web.content.soap.Update")
	@ResponseWrapper(className="org.migus.web.content.soap.UpdateResponse")
	@WebMethod
	@WebResult(name = "content")
	public Content update(@WebParam(name = "id") String id, 
			@WebParam(name = "title") String title, 
			@WebParam(name = "text") String text) throws
			NotFoundException, InvalidException;
}
