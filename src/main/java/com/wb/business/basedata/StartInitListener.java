package com.wb.business.basedata;

import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.wb.business.chatroom.ChatManager;
import com.wb.business.task.GetAccessTokenTask;
import com.wb.business.wxMsgHandler.CommonMsgHandler;

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
		CommonMsgHandler.cachedThreadPool.shutdown();
		try {
			if(CommonMsgHandler.cachedThreadPool.awaitTermination(10, TimeUnit.SECONDS) == false){
				CommonMsgHandler.cachedThreadPool.shutdownNow();
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
