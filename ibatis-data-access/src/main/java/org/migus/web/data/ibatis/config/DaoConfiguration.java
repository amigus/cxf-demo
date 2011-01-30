package org.migus.web.data.ibatis.config;

import org.migus.web.data.ContentDao;
import org.migus.web.data.ibatis.ContentDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.ibatis.sqlmap.client.SqlMapClient;

@Configuration
@ImportResource("classpath:org/migus/web/data/ibatis/applicationContext.xml")
public class DaoConfiguration {
	private @Autowired SqlMapClient sqlMapClient;

	public @Bean ContentDao contentDao() {
		ContentDaoImpl contentDao=new ContentDaoImpl();
		
		contentDao.setSqlMapClient(sqlMapClient);
		return contentDao;
	}
}
