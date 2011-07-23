package org.migus.web.data;

import java.util.UUID;

public class ContentException extends Exception {

	static final long serialVersionUID = 1L;

	private UUID id;

	protected ContentException() {
		super();
	}

	protected ContentException(UUID id) {
		super();
		this.id = id;
	}

	protected ContentException(Throwable throwable) {
		super(throwable);
	}

	public ContentException(UUID id, Throwable throwable) {
		this(throwable);
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	protected void setId(UUID id) {
		this.id = id;
	}
}
