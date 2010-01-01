package org.migus.web.content.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.migus.web.content.ContentTestBase;
import org.migus.web.content.rest.ContentServer;
import org.migus.web.content.types.Content;

public abstract class ContentRESTTestBase extends ContentTestBase {

	protected abstract ContentServer getContentServer();

	@Test
	public void testGet() {
		Content content=getContentServer().get(getIdOfTestContent().toString());
		
		assertEquals(content.getTitle(), TITLE);
	}

	@Test
	public void testByAuthor() {
		List<Content> contents=
			getContentServer().getByAuthor(AUTHOR).getContents();

		assertNotNull(contents);
		assertEquals(contents.get(0).getTitle(), TITLE);
	}
	

	@Test
	public void testUpdateText() {
		getContentServer().updateText(getIdOfTestContent().toString(), CONTENT);
	}

	@Test
	public void testUpdateTitle() {
		getContentServer().updateTitle(getIdOfTestContent().toString(), TITLE);
	}
}
