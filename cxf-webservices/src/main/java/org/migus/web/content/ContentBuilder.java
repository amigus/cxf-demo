package org.migus.web.content;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.migus.web.content.types.Content;
import org.migus.web.data.ContentDao.ContentData;

public class ContentBuilder {
	private GregorianCalendar created;
	private String author, title, text;
	private UUID id;
	
	public static ContentBuilder fromContentData(ContentData contentData) {
		return new ContentBuilder(contentData.getId())
		.withCreated(contentData.getCreated())
		.withAuthor(contentData.getAuthor())
		.withTitle(contentData.getTitle())
		.withText(contentData.getText());
	}
	
	public ContentBuilder(UUID id) {
		this.id=id;
	}
	
	public ContentBuilder withAuthor(String author) {
		this.author=author;
		return this;
	}
	
	public ContentBuilder withTitle(String title) {
		this.title=title;
		return this;
	}
	
	public ContentBuilder withText(String text) {
		this.text=text;
		return this;
	}
	
	public ContentBuilder withCreated(Date created) {
		GregorianCalendar cal=new GregorianCalendar();

		cal.setTime(created);
		this.created=cal;
		return this;
	}
	
	public Content build() throws DatatypeConfigurationException {
		Content content=new Content();

		content.setId(id.toString());
		content.setCreated(
				DatatypeFactory.newInstance().newXMLGregorianCalendar(created));
		content.setAuthor(author);
		content.setTitle(title);
		content.setText(text);
		return content;
	}
}
