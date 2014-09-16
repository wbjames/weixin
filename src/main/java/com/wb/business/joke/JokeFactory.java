package com.wb.business.joke;

import java.net.URLEncoder;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.basedata.AppBean;
import com.wb.business.wxMsgHandler.MsgType;
import com.wb.business.wxMsgHandler.ReturnMsg;
import com.wb.jpa.DataAdpter;

/**
 * <p>Title: provideJoke</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月16日
 */
public class JokeFactory {

	public String getJoke(){
		try {
			CloseableHttpClient httpclient = HttpClients
					.createDefault();
			HttpGet httpget = new HttpGet(AppBean.JOKE_URL);
			CloseableHttpResponse response = httpclient
					.execute(httpget);
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				httpget.abort();
				throw new RuntimeException(
						"HttpClient,error status code :" + statusCode);
			}
			HttpEntity entity = response.getEntity(); 
			
			String _result = null;  
	        if (entity != null) {  
	        	_result = EntityUtils.toString(entity, "utf-8");  
	        }
	        System.out.println(_result);
	        return _result;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String getDBJoke(){
		try {
			DataAdpter da = new DataAdpter();
			JdbcTemplate jt = da.getJdbcTemplate();
			Map<String, Object> map = jt.queryForMap("select * from lengxiaohua where id = ?", 
					(int)(Math.random()*AppBean.JOKE_TOTAL));
			if(map != null){
				String _result = (String)map.get("content");
				return _result;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void provideOneJoke(String userId, String appId) {
//		AppBean.cachedThreadPool.execute(new Runnable() {
//			
//			@Override
//			public void run() {
				String jokeResult = getDBJoke();
				ReturnMsg.sendTextMsg(userId, MsgType.TEXT, jokeResult);
//			}
//		});
	}
	
	
}
