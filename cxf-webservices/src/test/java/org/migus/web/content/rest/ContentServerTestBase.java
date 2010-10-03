package org.migus.web.content.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.migus.web.content.ContentTestBase;
import org.migus.web.content.rest.ContentServer;
import org.migus.web.content.types.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public abstract class ContentServerTestBase extends ContentTestBase {
	private @Autowired ContentServer contentServer;

	@Test
	public void testGet() {
		Content content=contentServer.get(getIdOfTestContent().toString());
		
		assertEquals(content.getTitle(), TITLE);
	}

	@Test
	public void testByAuthor() {
		List<Content> contents=
			contentServer.getByAuthor(AUTHOR).getContents();

		assertNotNull(contents);
		assertEquals(contents.get(0).getTitle(), TITLE);
	}
	

	@Test
	public void testUpdateText() {
		contentServer.updateText(getIdOfTestContent().toString(), CONTENT);
	}

	@Test
	public void testUpdateTitle() {
		contentServer.updateTitle(getIdOfTestContent().toString(), TITLE);
	}
}
