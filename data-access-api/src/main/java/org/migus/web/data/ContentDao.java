package org.migus.web.data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ContentDao {
	class ContentData {
		private String author, title, text;
		private Date created;
		private UUID id;
		
		ContentData(UUID id) {
			super();
			this.id=id;
			this.created=new Date();
		}
		
		ContentData() {
			this(UUID.randomUUID());
		}
		
		public ContentData(UUID id, String author, String title, String text) {
			this(id);
			this.author = author;
			this.title = title;
			this.text = text;
		}

		public UUID getId() {
			return id;
		}

		public void setId(UUID id) {
			this.id = id;
		}

		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public Date getCreated() {
			return created;
		}

		public void setCreated(Date created) {
			this.created = created;
		}
	}
	
	public UUID addContent(String author, String title, String text);

	public void insertContent(UUID id, String author, String title, String text)
			throws ContentExistsException;

	public List<ContentData> getContentsByAuthor(String author);

	public ContentData getContent(UUID id);
	
	public List<UUID> getIds();

	public void updateContent(UUID id, String title, String text)
			throws ContentNotFoundException;

	public void updateContent(ContentData content)
		throws ContentNotFoundException;

}
