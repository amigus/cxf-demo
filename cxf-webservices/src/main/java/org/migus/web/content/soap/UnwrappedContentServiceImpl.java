package org.migus.web.content.soap;

import java.util.List;
import java.util.UUID;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.WebServiceException;

import org.migus.web.content.ContentBuilder;
import org.migus.web.content.soap.Add;
import org.migus.web.content.soap.AddResponse;
import org.migus.web.content.soap.GetByAuthor;
import org.migus.web.content.soap.GetByAuthorResponse;
import org.migus.web.content.soap.GetById;
import org.migus.web.content.soap.GetByIdResponse;
import org.migus.web.content.soap.fromwsdl.ContentService;
import org.migus.web.content.soap.fromwsdl.InvalidFault;
import org.migus.web.content.soap.fromwsdl.NotFoundFault;
import org.migus.web.content.types.Content;
import org.migus.web.content.types.Contents;
import org.migus.web.content.types.NewContent;
import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentDao.ContentData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(endpointInterface="org.migus.web.content.soap.fromwsdl.ContentService")
public class UnwrappedContentServiceImpl implements ContentService {
	ContentDao contentDao;
	Logger logger=LoggerFactory.getLogger(getClass());

	Content convertContentData(ContentData contentData) {
		Content content=null;
		String message="converting contentData to content";
		
		try {
			content=ContentBuilder.fromContentData(contentData).build();
		} catch (DatatypeConfigurationException e) {
			logger.error(message, e);
			throw new WebServiceException("error when "+message);
		}
		logger.debug("content = "+content);
		return content;
	}

	public UnwrappedContentServiceImpl(ContentDao contentDao) {
		this.contentDao=contentDao;
	}
	
	public AddResponse add(Add body) {
		AddResponse response=new AddResponse();
		NewContent newContent=body.getNewContent();
		String message="adding newContent";
		UUID id;

		logger.debug(message+" with newContent = "+newContent);
		id=contentDao.addContent(newContent.getAuthor(), newContent.getTitle(),
				newContent.getText());
		if (id == null) {
			String error="new id is null when "+message;
			throw new WebServiceException(error);
		}
		logger.debug("added new content with id "+id.toString());
		response.setId(id.toString());
		return response;
	}

	public GetByAuthorResponse getByAuthor(GetByAuthor body) {
		GetByAuthorResponse response=new GetByAuthorResponse();
		Contents contents=new Contents();
		List<Content> contentList=contents.getContents();
		String author=body.getAuthor();
		String message="getting contents for author "+author;

		logger.debug(message);
		for (ContentData contentData : contentDao.getContentsByAuthor(author)) {
			contentList.add(convertContentData(contentData));
		}
		response.setContents(contents);
		return response;
	}

	public GetByIdResponse getById(GetById body) throws NotFoundFault,
			InvalidFault {
		GetByIdResponse response = new GetByIdResponse();

		try {
			UUID id=UUID.fromString(body.getId());
			ContentData contentData=contentDao.getContent(id);
			String message="getting content for id "+id;

			logger.info(message);
			if (contentData == null) {
				String error="contentData was null when"+message;
				
				logger.debug(error);
				NotFound faultInfo=new NotFound();
				
				faultInfo.setId(id.toString());
				
				NotFoundFault notFoundFault=new NotFoundFault(error, faultInfo);
				
				throw notFoundFault;
			}
			response.setContent(convertContentData(contentData));
			return response;
		} catch (IllegalArgumentException e) {
			Invalid faultInfo=new Invalid();
			
			faultInfo.setId(body.getId());
			
			InvalidFault invalidFault=new InvalidFault(e.getMessage(), faultInfo);
			
			throw invalidFault;
		}
	}
}
