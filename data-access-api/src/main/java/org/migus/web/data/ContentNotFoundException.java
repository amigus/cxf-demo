package org.migus.web.data;

import java.util.UUID;

public class ContentNotFoundException extends ContentException {

	static final long serialVersionUID = 1L;

	public ContentNotFoundException(UUID id) {
		super(id);
	}
}
