package org.migus.web.content.soap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.migus.web.content.ContentTestBase;
import org.migus.web.content.soap.Add;
import org.migus.web.content.soap.GetByAuthor;
import org.migus.web.content.soap.GetById;
import org.migus.web.content.types.Content;
import org.migus.web.content.types.NewContent;
import org.migus.web.content.soap.fromwsdl.ContentService;
import org.migus.web.content.soap.fromwsdl.InvalidFault;
import org.migus.web.content.soap.fromwsdl.NotFoundFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration
public class UnwrappedContentServiceTest extends ContentTestBase {
	private @Autowired ContentService contentService;
	
	@Test
	public void testAdd() {
		Add add=new Add();
		NewContent content=new NewContent();

		content.setAuthor(AUTHOR);
		content.setTitle(TITLE);
		content.setText(CONTENT);

		add.setNewContent(content);
		contentService.add(add);
	}
	
	@Test
	public void testGetContentByAuthor() {
		GetByAuthor request = new GetByAuthor();
		
		request.setAuthor(AUTHOR);
		List<Content> contents=
			contentService.getByAuthor(request).getContents().getContents();
		
		assertNotNull(contents);
		assertEquals(contents.get(0).getTitle(), TITLE);
	}

	@Test
	public void testGetContentById() throws NotFoundFault, InvalidFault {
		Add add=new Add();
		NewContent newContent=new NewContent();

		newContent.setAuthor(AUTHOR);
		newContent.setTitle(TITLE);
		newContent.setText(CONTENT);

		add.setNewContent(newContent);

		String id=contentService.add(add).getId();
		GetById request = new GetById();
		
		request.setId(id);
		
		Content content=contentService.getById(request).getContent();
		
		assertNotNull(content);
		assertEquals(content.getTitle(), TITLE);
	}
}
