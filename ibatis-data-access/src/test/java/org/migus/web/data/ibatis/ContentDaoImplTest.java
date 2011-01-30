package org.migus.web.data.ibatis;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentException;
import org.migus.web.data.ContentExistsException;
import org.migus.web.data.ContentNotFoundException;
import org.migus.web.data.ContentDao.ContentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class ContentDaoImplTest {
	static String AUTHOR="Adam", TITLE="Title", CONTENT="Some content";
	
	private @Autowired ContentDao contentDao;
	
	@Test
	public void testInsertContent() throws ContentExistsException {
		contentDao.insertContent(UUID.randomUUID(), AUTHOR, TITLE, CONTENT);
		contentDao.insertContent(UUID.randomUUID(), AUTHOR, TITLE, CONTENT);
	}

	@Test(expected=ContentExistsException.class)
	public void testDuplicateInsert() throws ContentExistsException {
		UUID id=UUID.randomUUID();
		
		contentDao.insertContent(id, AUTHOR, TITLE, CONTENT);
		contentDao.insertContent(id, AUTHOR, TITLE, CONTENT);
	}

	@Test
	public void testGetContentByAuthor() {
		List<ContentData> content=contentDao.getContentsByAuthor(AUTHOR);
		
		assertNotNull(content);
		assertEquals(content.get(0).getAuthor(), AUTHOR);
	}
	
	@Test
	public void testGetContent() throws ContentExistsException {
		UUID id=UUID.randomUUID();

		contentDao.insertContent(id, AUTHOR, TITLE, CONTENT);

		ContentData content=contentDao.getContent(id);

		assertNotNull(content);
		assertEquals(content.getId(), id);
	}

	@Test
	public void testGetNonexistentContent() {
		UUID id=UUID.randomUUID();

		ContentData content=contentDao.getContent(id);

		assertNull(content);
	}
	
	@Test
	public void testUpdate() throws ContentException {
		UUID id=UUID.randomUUID();
		String updatedContent=UUID.randomUUID().toString();
		
		contentDao.insertContent(id, AUTHOR, TITLE, CONTENT);
		contentDao.updateContent(id, TITLE, updatedContent);
		
		ContentData content=contentDao.getContent(id);

		assertNotNull(content);
		assertEquals(content.getText(), updatedContent);
	}
	
	@Test(expected=ContentNotFoundException.class)
	public void testBadUpdate() throws ContentNotFoundException {
		contentDao.updateContent(UUID.randomUUID(), TITLE, CONTENT);
	}
}
