package org.qql.vigour.web.config;

import java.sql.SQLException;

import org.h2.tools.Server;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("rawtypes")
@Slf4j
public class DatabaseApplicationContext implements ApplicationContextInitializer {
	
	@Override
	public void initialize(ConfigurableApplicationContext arg0) {
		
			log.info("内部数据库初始化开始.......");
			try {
				String[] args={"-ifExists","-tcp","-webAllowOthers","-tcpAllowOthers","-tcpPort","9092"};
				new Server().runTool(args);//-webAllowOthers -webPort 8082 -tcpAllowOthers -tcpPort 8083
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	log.info("内部数据库初始化结束.");
	}

}
