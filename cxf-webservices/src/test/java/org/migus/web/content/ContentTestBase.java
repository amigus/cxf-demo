package org.migus.web.content;

import java.util.UUID;

import org.junit.runner.RunWith;
import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration({
	"classpath:/org/migus/web/content/applicationContext.xml",
	"classpath:/org/migus/web/content/ContentTestBase-context.xml",
	"classpath:/org/migus/web/data/ibatis/applicationContext.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class ContentTestBase {
	protected static final String AUTHOR = "Adam";
	protected static final String TITLE = "Title";
	protected static final String CONTENT = "Some content.";

	private Logger logger=LoggerFactory.getLogger(getClass());
	private @Autowired ContentDao contentDao;
	
	protected UUID getIdOfTestContent() {
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