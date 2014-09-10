package com.wb.business.basedata;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wb.business.task.GetAccessTokenTask;

/**
 * 
* <p>Title: GetAccessTokenListener</p>
* <p>Description: </p>
* @author wb_james
* @date 2014年9月10日
 */
public class StartInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// do nothing

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("==================" + getClass().getName() + "============begin!");
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new GetAccessTokenTask() , 0, AppBean.DURAUTION);
		
		
		
		System.out.println("==================" + getClass().getName() + "============end!");
	}

}
