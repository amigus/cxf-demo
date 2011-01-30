package org.migus.web.content.soap;

import java.util.UUID;

import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.WebServiceException;

import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentNotFoundException;
import org.migus.web.data.ContentDao.ContentData;
import org.migus.web.content.ContentBuilder;
import org.migus.web.content.types.Content;
import org.migus.web.content.types.Contents;
import org.migus.web.content.types.NewContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@WebService(endpointInterface="org.migus.web.content.soap.ContentService",
		portName="content-port", serviceName="content-service",
		targetNamespace = "http://migus.org/webservices/content-service")
@Service
public class ContentServiceImpl implements ContentService {
	ContentDao contentDao;
	Logger logger;

	Content convertContentData(ContentData contentData) {
		Content content=null;
		String message="converting contentData to content";
		
		try {
			content=ContentBuilder.fromContentData(contentData).build();
		} catch (DatatypeConfigurationException e) {
			logger.error(message, e);
			throw new WebServiceException("error "+message, e);
		}
		logger.debug("content = "+content);
		return content;
	}

	Content doGetById(String id) throws NotFoundException, InvalidException {
		String message="getting content for id "+id;

		logger.debug(message);
		try {
			ContentData contentData=contentDao.getContent(UUID.fromString(id));
			
			if (contentData == null) {
				logger.error("contentData was null when "+message);
				throw new NotFoundException(id);
			}
			
			logger.debug("contentData = "+contentData);
			return convertContentData(contentData);
		} catch (IllegalArgumentException e) {
			logger.error(message, e);

			Invalid invalid=new Invalid();
			
			invalid.setId(id);
			invalid.setMessage(e.getMessage());
			throw new InvalidException(invalid);
		}
	}
	
	public ContentServiceImpl(ContentDao contentDao, Logger logger) {
		this.contentDao=contentDao;
		this.logger=logger;
	}

	@Autowired
	public ContentServiceImpl(ContentDao contentDao) {
		this(contentDao, LoggerFactory.getLogger(ContentServiceImpl.class));
	}

	public String add(NewContent newContent) {
		String message="adding newContent";
		UUID id;

		logger.debug(message+" with newContent = "+newContent);
		id=contentDao.addContent(newContent.getAuthor(), newContent.getTitle(),
				newContent.getText());
		if (id == null) {
			throw new WebServiceException("new id is null when "+message);
		}
		logger.debug("added newContent as "+id.toString());
		return id.toString();
	}

	public Contents getByAuthor(String author) {
		Contents contents=new Contents();
		String message="getting contents for author "+author;

		logger.debug(message);
		for (ContentData contentData : contentDao.getContentsByAuthor(author)) {
			contents.getContents().add(convertContentData(contentData));
		}
		logger.debug("contents = "+contents);
		return contents;
	}

	public Content getById(String id) throws NotFoundException, InvalidException {
		return doGetById(id);
	}

	public Content update(String id, String title, String text) 
			throws NotFoundException, InvalidException {
		String message="updating title and text of content with id "+id;
		
		logger.debug(message);
		try {
			contentDao.updateContent(UUID.fromString(id), title, text);
		} catch (ContentNotFoundException e) {
			logger.error(message, e);
			throw new NotFoundException(id);
		} catch (IllegalArgumentException e) {
			logger.error(message, e);
			
			Invalid invalid=new Invalid();
			invalid.setId(id);
			invalid.setMessage(e.getMessage());
			throw new InvalidException(invalid);
		}
		logger.debug("updated content with id "+id);
		return doGetById(id);
	}
}
