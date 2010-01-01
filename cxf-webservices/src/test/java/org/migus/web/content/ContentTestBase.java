package org.migus.web.content;

import java.util.UUID;

import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class ContentTestBase {
	protected static final String AUTHOR = "Adam";
	protected static final String TITLE = "Title";
	protected static final String CONTENT = "Some content.";

	private Logger logger=LoggerFactory.getLogger(getClass());
	
	protected abstract String[] getContextFiles();
	
	protected ApplicationContext context = new ClassPathXmlApplicationContext(
				getContextFiles());

	protected UUID getIdOfTestContent() {
		ContentDao contentDao=(ContentDao) context.getBean("contentDao");
		UUID id=UUID.randomUUID(); 
		try {
			contentDao.insertContent(id, AUTHOR, TITLE, CONTENT);
		} catch (ContentExistsException e) {
			logger.warn("content for UUID.randomUUID() = { "+
					id+" } already exists");
		}
		return id;
	}
}