package org.migus.web.data.ibatis;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import org.migus.web.data.ContentDao;
import org.migus.web.data.ContentExistsException;
import org.migus.web.data.ContentNotFoundException;
import org.migus.web.data.DataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.apache.commons.lang.NullArgumentException;

public class ContentDaoImpl extends SqlMapClientDaoSupport implements
		ContentDao {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	public List<ContentData> getContentsByAuthor(String author) {
		String message = "getting content by author " + author;

		try {
			logger.debug(message);
			return getSqlMapClient().queryForList("getContentByAuthor", author);
		} catch (SQLException e) {
			logger.error(message, e);
			throw new DataAccessException(message, e);
		}
	}

	public UUID addContent(String author, String title, String text) {
		String message = "adding content with author = " + author
				+ "; title = " + title;
		UUID id;

		do {
			id = UUID.randomUUID();
			try {
				logger.debug(message);
				insertContent(id, author, title, text);
			} catch (ContentExistsException e) {
				logger.warn("content already exists for generated id = " + id
						+ " when " + message, e);
				id = null;
			}
		} while (id == null);
		return id;
	}

	public void insertContent(UUID id, String author, String title, String text)
			throws ContentExistsException {
		if (id == null) {
			throw new NullArgumentException("id");
		}

		String message = "inserting content with id = " + id + "; author = "
				+ author + "; title = " + title;

		try {
			ContentData content = new ContentData(id, author, title, text);

			logger.debug(message);
			getSqlMapClient().insert("insertContent", content);
		} catch (SQLException e) {
			if ("23505".equals(e.getSQLState())) {
				logger.error("id already exists when "+message, e);
				throw new ContentExistsException(id);
			} else {
				logger.error(message, e);
				throw new DataAccessException(message, e);
			}
		}
	}

	public ContentData getContent(UUID id) {
		String message = "getting content with id " + id;

		try {
			logger.debug(message);
			return (ContentData) getSqlMapClient().queryForObject(
					"getContentById", id);
		} catch (SQLException e) {
			logger.error(message, e);
			throw new DataAccessException(message, e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<UUID> getIds() {
		try {
			return getSqlMapClient().queryForList("getIds");
		} catch (SQLException e) {
			throw new DataAccessException("getting ids", e);
		}
	}

	public void updateContent(UUID id, String title, String text)
			throws ContentNotFoundException {
		if (id == null) {
			throw new NullArgumentException("id");
		}

		ContentData content = getContent(id);

		if (content == null) {
			throw new ContentNotFoundException(id);
		}

		content.setTitle(title);
		content.setText(text);
		updateContent(content);
	}

	public void updateContent(ContentData content) {
		if (content == null) {
			throw new NullArgumentException("content");
		}

		String message = "updating content with content.getId() = "
				+ content.getId() + "; content.getAuthor() = "
				+ content.getAuthor() + "; content.getTitle() = "
				+ content.getTitle();
		try {
			logger.debug(message);
			getSqlMapClient().update("updateContent", content);
		} catch (SQLException e) {
			logger.error(message, e);
			throw new DataAccessException(message, e);
		}
	}
}
