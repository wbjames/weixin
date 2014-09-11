package com.wb.business.wxUtils;

import java.io.File;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wb.business.basedata.AppBean;

/**
 * <p>Title: MediaUtils</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月10日
 */
public class MediaUtils {

	Logger log = Logger.getLogger(this.getClass().getName());
	
	public String upload(String accessToken, String type, File file) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
		
        HttpPost httppost = new HttpPost(AppBean.UPLOAD_MEDIA_BASE_URL + 
        		"?access_token=" + AppBean._accessToken +
        		"&type=" + type);
        
        HttpEntity httpEntity = MultipartEntityBuilder.create()
//        	    .addBinaryBody("file", file, ContentType.create("image/jpeg"), file.getName())
        		.addBinaryBody("file", file)
        	    .build();
        
        //FileEntity fileentity = new FileEntity(media);
        
        httppost.setEntity(httpEntity);
        
        CloseableHttpResponse response = httpclient.execute(httppost);
		
        int statusCode = response.getStatusLine().getStatusCode();  
        if (statusCode != 200) {  
        	httppost.abort();  
            throw new RuntimeException("HttpClient,error status code :" + statusCode);  
        } 
        
        System.out.println(response.getStatusLine());
        
        HttpEntity entity = response.getEntity(); 
    	String _jsonBody = null;  
        if (entity != null) {  
        	_jsonBody = EntityUtils.toString(entity, "utf-8");  
        }  
        EntityUtils.consume(entity);  
        response.close();  
        
        JSONObject object = JSONObject.fromObject(_jsonBody);
        if(object.getString("media_id") != null){
        	return object.getString("media_id");
        }else {
        	log.info(_jsonBody);
        	return null;
        }
       
	}
}
