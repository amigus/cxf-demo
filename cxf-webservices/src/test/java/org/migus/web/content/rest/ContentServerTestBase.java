package org.migus.web.content.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assert.fail;
import static javax.ws.rs.core.Response.Status.CONFLICT;

import java.util.List;

import javax.ws.rs.WebApplicationException;

import org.junit.Test;
import org.migus.web.content.ContentTestBase;
import org.migus.web.content.rest.ContentServer;
import org.migus.web.content.types.Content;
import org.migus.web.content.types.NewContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public abstract class ContentServerTestBase extends ContentTestBase {
	private @Autowired ContentServer contentServer;

	@Test
	public void testAdd() {
		NewContent newContent=new NewContent();

		newContent.setAuthor(AUTHOR);
		newContent.setText(CONTENT);
		newContent.setTitle(TITLE);

		Content content=null;
		try {
			content=contentServer.add(newContent);
			if (content != null) {
				assertEquals(content.getText(), newContent.getText());
			}
		}
		catch(WebApplicationException e) {
			fail("did not expect to get "+e);
		}

		assertNotNull(content);

		String id=content.getId();

		try {
			content=contentServer.add(id, newContent);
			fail("expected WebApplicationException with status "+CONFLICT);
		}
		catch(WebApplicationException e) {
			assumeNotNull(e);
			assertEquals(CONFLICT.getStatusCode(), e.getResponse().getStatus());
		}
	}

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
