package com.wb.business.wxMsgHandler;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.basedata.AppBean;
import com.wb.jpa.DataAdpter;

/**
 * <p>Title: EventMsgHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class EventMsgHandler {

	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();  
	
	public static String eventMsgHandler(Map<String, String> result) {
		
		saveUserInfoToDB(result.get("FromUserName"), result.get("ToUserName"));
		
		Map<String, String> newMap = ReturnMsg.setTextMsgMap(result.get("FromUserName"),
				result.get("ToUserName"), (new Date().getTime()) / 1000,
				MsgType.TEXT, AppBean.MENU);
		return ReturnMsg.returnTextMsg(newMap);
	}

	public static void saveUserInfoToDB(String uid, String appid) {
		
		cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				
				try {
					CloseableHttpClient httpclient = HttpClients.createDefault(); 
					
					HttpGet httpget = new HttpGet(AppBean.USERINFOURL + AppBean._accessToken
									+ "&openid=" + uid 
									+ "&lang=" + AppBean.APP_LANG
									);
					
					CloseableHttpResponse response = httpclient.execute(httpget);
					int statusCode = response.getStatusLine().getStatusCode();  
					if (statusCode != 200) {  
					    httpget.abort();  
					    throw new RuntimeException("HttpClient,error status code :" + statusCode);  
					}
					
					HttpEntity entity = response.getEntity(); 
			    	String _jsonBody = null;  
			        if (entity != null) {  
			        	_jsonBody = EntityUtils.toString(entity, "utf-8");  
			        }  
			        EntityUtils.consume(entity);  
			        response.close();  
			        

			        JSONObject object = JSONObject.fromObject(_jsonBody);
			        if(object != null && object.getString("subscribe") != null && object.getInt("subscribe") == 1){
			        	String userid = object.getString("openid");
			        	String nickname = object.getString("nickname");
			        	byte sex = (byte)object.getInt("sex");
			        	String city = object.getString("city");
			        	String country = object.getString("country");
			        	String province = object.getString("province");
			        	String language = object.getString("language");
			        	String headimgurl = object.getString("headimgurl");
			        	int subscribe_time = object.getInt("subscribe_time");
			        	Date subscribe_time_date = new Date(subscribe_time * 1000L);
			        	String unionid = object.containsKey("unionid") ? object.getString("unionid") : null;
			        	
			        	DataAdpter da = new DataAdpter();
						JdbcTemplate jt = da.getJdbcTemplate();
						
						
		        		String updSql = "update wx_userinfo set subscribe_time = ?,"
		        				+ "	subscribe_time_date = ? "
		        				+ "	where appid = ?"
		        				+ "	and userid = ?";
		        		// 默认更新
		        		int updCnt = jt.update(updSql, new Object[]{
		        				subscribe_time, 
		        				subscribe_time_date,
		        				appid,
								userid
		        		});
		        		// 更新数量为0的话表示没有，需要插入！
		        		if(updCnt == 0){
		        			String insSql = "insert into wx_userinfo values("
			        				+ "	?, ?, ?, ?, ?, ?, ?,"
			        				+ "	?, ?, ?, ?, ?)";
			        		jt.update(insSql, new Object[]{
			        				appid, userid, nickname, sex, city, country, province,
			        				language, headimgurl, subscribe_time, subscribe_time_date, unionid
			        		});
		        		}
			        		

			        	
			        	
			        	
			        }else {
			        	System.out.println(object);
			        }
					
					
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        
				
			}
		});
		
	}
	
	
}
