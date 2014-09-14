package com.wb.business.task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.basedata.AppBean;
import com.wb.jpa.DataAdpter;

/**
 * <p>Title: GetAccessTokenTask</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月10日
 */
public class GetAccessTokenTask extends TimerTask{

	@Override
	public void run() {
		try {
			String token = null;
			token = getAccessToken();
			
			DataAdpter da = new DataAdpter();
			JdbcTemplate jt = da.getJdbcTemplate();
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			try {
				jt.update("update wx_data set data_value = ?, description = ?"
						+ "	where data_key = ?", new Object[]{
						token,
						df.format(new Date()),
						"ACCESS_TOKEN"
					});
			} catch (Exception e) {
				// TODO: 通知管理员
				e.printStackTrace();
			}
//			Map<String, Object> kvMap = jt.queryForMap("select * from wx_data where key = ?", "ACCESS_TOKEN");
//			if(kvMap == null){
//				token = getAccessToken();
//				System.out.println(token);
//				
//				jt.update("insert into wx_data values(default, 'ACCESS_TOKEN', ?, ?);", new Object[]{
//					token,
//					df.format(new Date())
//				});
//			}else {
//				token = (String)kvMap.get("ACCESS_TOKEN");
//			}
			
			
			if (token != null) {
				AppBean._accessToken = token;
				
			}
			
			//String s = new MediaUtils().upload(token, "image", new File(getClass().getResource("/").getPath() +"/touxiang.jpg"));
			//System.out.println(s);
			//Thread.sleep(2000);
			//EventMsgHandler.saveUserInfoToDB("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "你好");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "fuck you!");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "爱あなたの一生");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "привет");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "Enquêtes sur la satisfaction des utilisateurs à la traduction YouDao!");
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "당기 프로그램 주요 내용");
//			
//			Thread.sleep(2000);
//			new LangTranslate().sendResult("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0", "我就是想测试一下到底灵不灵光");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    public String getAccessToken() throws Exception {
    	Logger log = Logger.getLogger(this.getClass().getName());
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault(); 
    	
    	HttpGet httpget = new HttpGet(AppBean.TOKENBASEURL + "?grant_type=client_credential&appid="
                + AppBean.APPID + "&secret=" + AppBean.APPSECRET);

    	CloseableHttpResponse response = httpclient.execute(httpget);
    	 int statusCode = response.getStatusLine().getStatusCode();  
         if (statusCode != 200) {  
             httpget.abort();  
             throw new RuntimeException("HttpClient,error status code :" + statusCode);  
         }  
         
    	System.out.println(response.getStatusLine());
    	System.out.println(response.getEntity());
    	
    	HttpEntity entity = response.getEntity(); 
    	String _jsonBody = null;  
        if (entity != null) {  
        	_jsonBody = EntityUtils.toString(entity, "utf-8");  
        }  
        EntityUtils.consume(entity);  
        response.close();  
        

        JSONObject object = JSONObject.fromObject(_jsonBody);
        if(object.getString("access_token") != null){
        	return object.getString("access_token");
        }else {
        	log.info(_jsonBody);
        	return null;
        }
    }
}
