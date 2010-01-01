package org.migus.web.content.rest;

import org.migus.web.content.rest.ContentServer;

public class ContentImplXMLTest  extends ContentRESTTestBase {
	static final String [] contexts = { "/dataSourceContext.xml", 
		"/applicationContext-jaxrs.xml" };

	protected ContentServer getContentServer() {
		return  (ContentServer) context.getBean("contentServerXMLClient");
	}

	protected String [] getContextFiles() {
		return contexts;
	}
}
