package org.migus.web.content.soap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.migus.web.content.ContentTestBase;
import org.migus.web.content.soap.ContentService;
import org.migus.web.content.types.Content;
import org.migus.web.content.types.NewContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration
public class ContentServiceTest extends ContentTestBase {
	private @Autowired ContentService contentService;

	@Test
	public void testAdd() {
		NewContent newContent=new NewContent();

		newContent.setAuthor(AUTHOR);
		newContent.setTitle(TITLE);
		newContent.setText(CONTENT);

		String id=contentService.add(newContent);

		assertNotNull(id);
	}
	
	@Test
	public void testGetById() throws NotFoundException, InvalidException {
		Content content = contentService.getById(getIdOfTestContent().toString());

		assertEquals(content.getTitle(), TITLE);
	}

	@Test
	public void testGetByAuthor() {
		List<Content> contents=
			contentService.getByAuthor(AUTHOR).getContents();

		assertNotNull(contents);
		assertEquals(contents.get(0).getTitle(), TITLE);
	}
	

	@Test
	public void testUpdate() throws NotFoundException, InvalidException {
		contentService.update(getIdOfTestContent().toString(), TITLE, CONTENT);
	}
}
