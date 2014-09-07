package com.wb.jpa;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

import com.wb.common.Application;
import com.wb.common.SpringBeanFactory;

/**
 * @author wb_james
 *
 */
public class DBConnectBeanFactory {

	private static String defaultKey = "default";
	
	public static DBConnectBean getDBConnectBean(String dbname, String dbkey){
		if(dbname == null || dbname.trim().length() == 0 ){
			return getDBConnectBean();
		}
		if(dbkey == null || dbkey.trim().length() == 0) {dbkey = defaultKey;}
		
		TransactionTemplate transactionTemplate = (TransactionTemplate) SpringBeanFactory.getBean(dbname + "_" + dbkey + "_transactionTemplate");
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringBeanFactory.getBean(dbname + "_" + dbkey + "_jdbcTemplate");
		DBConnectBean dbConnectBean = new DBConnectBean();
		dbConnectBean.setTransactionTemplate(transactionTemplate);
		dbConnectBean.setJdbcTemplate(jdbcTemplate);
		dbConnectBean.setDbName(dbname);
		dbConnectBean.setDkKey(dbkey);
		
		return dbConnectBean;
	}
	
	/**
	 * 1、若Application已经有定义了默认数据库 defaultDBName,则使用。
	 * 2、否则使用transactionTemplate和jdbcTemplate
	 * @return
	 */
	public static DBConnectBean getDBConnectBean(){
		if (Application.defaultDBName != null && Application.defaultDBName.trim().length() > 0){
			return getDBConnectBean(Application.defaultDBName, Application.defaultDBKey);
		}
		
		TransactionTemplate transactionTemplate = null;
		JdbcTemplate jdbcTemplate = null;
		
		try {
			transactionTemplate = (TransactionTemplate)SpringBeanFactory.getBean("transactionTemplate");
			jdbcTemplate = (JdbcTemplate)SpringBeanFactory.getBean("jdbcTemplate");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		DBConnectBean dbConnectBean = new DBConnectBean();
		dbConnectBean.setTransactionTemplate(transactionTemplate);
		dbConnectBean.setJdbcTemplate(jdbcTemplate);
		dbConnectBean.setDkKey(defaultKey);
		
		return dbConnectBean;
	}
}
