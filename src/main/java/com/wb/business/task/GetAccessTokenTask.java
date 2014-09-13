package com.wb.business.task;

import java.util.TimerTask;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wb.business.basedata.AppBean;

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
			String token = getAccessToken();
			if (token != null) {
				AppBean._accessToken = token;
			}
			System.out.println(token);
			
			//String s = new MediaUtils().upload(token, "image", new File(getClass().getResource("/").getPath() +"/touxiang.jpg"));
			//System.out.println(s);
			
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
