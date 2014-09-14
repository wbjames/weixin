package com.wb.business.basedata;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

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
		AppBean.cachedThreadPool.shutdown();
		try {
			if(AppBean.cachedThreadPool.awaitTermination(10, TimeUnit.SECONDS) == false){
				AppBean.cachedThreadPool.shutdownNow();
			}
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		System.out.println("CommonMsgHandler.cachedThreadPool is shutdown!");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("==================" + getClass().getName() + "============begin!");
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new GetAccessTokenTask() , 0, AppBean.DURAUTION);
		
		
		
		System.out.println("==================" + getClass().getName() + "============end!");
	}

}
