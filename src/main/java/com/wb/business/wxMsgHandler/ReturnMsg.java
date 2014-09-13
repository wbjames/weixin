package com.wb.business.wxMsgHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wb.business.basedata.AppBean;
import com.wb.business.wxUtils.ConvertUtils;

/**
 * <p>Title: ReturnMsg</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class ReturnMsg {

	private static Logger log = Logger.getLogger("ReturnMsg");
	/**
	 * 
	* <p>Title: returnTextMsg</p>
	* <p>Description: 返回文本信息</p>
	* @param paraM（required: ToUserName, FromUserName, CreateTime, MsgType, Content）
	* @return
	 */
	public static String returnTextMsg(Map<String, String> paraM){
		List<Map<String, String>> list = ConvertUtils.convertArrayToList(new String[]{
				"ToUserName",
				"FromUserName",
				"CreateTime",
				"MsgType",
				"Content"}, paraM);
		
		String xmlStr = ConvertUtils.makeXml(list);
		
		return xmlStr;
	}
	
	public static Map<String, String> setTextMsgMap(String ToUserName,String FromUserName, 
			Object CreateTime, String MsgType, String Content){
		
		Map<String, String> newMap = new HashMap<String, String>();
		newMap.put("FromUserName", FromUserName);
		newMap.put("ToUserName", ToUserName);
		newMap.put("MsgType", MsgType);
		newMap.put("CreateTime", CreateTime.toString());
		newMap.put("Content", Content);
		return newMap ;
	}
	
	
	public static void sendTextMsg(String touser, String msgtype, String content){
		CloseableHttpClient httpclient = HttpClients.createDefault(); 
    	
    	HttpPost httppost = new HttpPost(AppBean.SENDBASEURL + AppBean._accessToken);
    	
    	JSONObject json = new JSONObject();
    	json.put("touser", touser);
    	json.put("msgtype", msgtype);
    	JSONObject jsonContent = new JSONObject();
    	jsonContent.put("content", content);
    	json.put(msgtype, jsonContent.toString());
    	
    	String jsonString = json.toString();
    	System.out.println(jsonString);
    	
    	StringEntity se = null;
		se = new StringEntity(jsonString, "utf-8");
    	
    	httppost.setEntity(se);
    	httppost.setHeader("Accept", "application/json");
    	httppost.setHeader("Content-type", "application/json");
    	httppost.setHeader("encoding", "utf-8");
    	
    	CloseableHttpResponse response;
		try {
			 response = httpclient.execute(httppost);
			 int statusCode = response.getStatusLine().getStatusCode();  
	         if (statusCode != 200) {  
	        	 httppost.abort();  
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
	         if(object.getInt("errcode") == -1){
	        	 sendTextMsg(touser,msgtype, content); 
	         }else {
	        	 log.info("errcode:" + object.getInt("errcode"));
	        	 log.info("errmsg:" + object.getString("errmsg"));
	         }
	         
		} catch (Exception e) {
			e.printStackTrace();
			log.info("ReturnMsg.sendTextMsg exception#2: " + e.getMessage());
		}
	}
}
