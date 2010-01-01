package org.migus.web.data;

import java.util.UUID;

public class ContentExistsException extends ContentException {

	static final long serialVersionUID = 1L;

	public ContentExistsException(UUID id) {
		super(id);
	}
}
