package org.migus.web.content.soap;

import org.migus.web.content.soap.ContentService;

public class ContentServiceImplTest  extends ContentSOAPTestBase {
	static final String [] contexts = { "/dataSourceContext.xml", 
		"/applicationContext.xml" };
	

	protected ContentService getContentService() {
		return (ContentService) context.getBean("contentServiceClient");
	}
	
	protected String [] getContextFiles() {
		return contexts;
	}
}
