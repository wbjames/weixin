package com.wb.jpa;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 
* <p>Title: DataAdpter</p>
* <p>Description: 数据库操作适配器</p>
* @author wb_james
* @date 2014年9月7日
 */
//@SuppressWarnings("rawtypes")
public class DataAdpter {
	private DBConnectBean dbConnectBean;

	public DataAdpter() {
		dbConnectBean = DBConnectBeanFactory.getDBConnectBean();
	}

	public void setConnect(String dbname, String dbkey) {
		dbConnectBean = DBConnectBeanFactory.getDBConnectBean(dbname, dbkey);
	}
	
	public void setConnect(String dbname){
		setConnect(dbname, null);
	}

	public JdbcTemplate getJdbcTemplate(){
		return dbConnectBean.getJdbcTemplate();
	}

	
	
}
