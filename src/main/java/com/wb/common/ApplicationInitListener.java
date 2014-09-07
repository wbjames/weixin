package com.wb.common;

import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * <p>Title: ApplicationInitListener</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月7日
 */
public class ApplicationInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();
		String appPath = servletContext.getInitParameter("appConfigPath");
		
		if(appPath == null || appPath.equals("")){
			appPath = "appConfig.properties";
		}
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(appPath);
		if(is == null ){
			throw new RuntimeException("系统未包含属性配置文件");
		}
		
		Properties properties = new Properties();
		try {
			properties.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Application.APP_NAME = properties.getProperty("app_name");
		Application.defaultDBName = properties.getProperty("db_name");
		Application.defaultDBKey = properties.getProperty("db_key");
		Application.token = properties.getProperty("token");
	}

}
