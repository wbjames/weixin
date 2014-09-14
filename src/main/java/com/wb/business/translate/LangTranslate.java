package com.wb.business.translate;

import java.net.URLEncoder;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.wb.business.basedata.AppBean;
import com.wb.business.wxMsgHandler.MsgType;
import com.wb.business.wxMsgHandler.ReturnMsg;

/**
 * <p>Title: LangTranslate</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月13日
 */
public class LangTranslate {

	public String translate(String content){
	
			try {
				CloseableHttpClient httpclient = HttpClients
						.createDefault();
				HttpGet httpget = new HttpGet(AppBean.LANG_TRANS_YD_URL
						+ URLEncoder.encode(content, "utf-8"));
				CloseableHttpResponse response = httpclient
						.execute(httpget);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					httpget.abort();
					throw new RuntimeException(
							"HttpClient,error status code :" + statusCode);
				}
				HttpEntity entity = response.getEntity(); 
		    	String _jsonBody = null;  
		        if (entity != null) {  
		        	_jsonBody = EntityUtils.toString(entity, "utf-8");  
		        }  
		        EntityUtils.consume(entity);  
		        response.close();  
				
		        System.out.println(_jsonBody);
				
		        String result = null;
		        
				JSONObject jsonMain = JSONObject.fromObject(_jsonBody);
				
				if(jsonMain.containsKey("errorCode")){
					switch (jsonMain.getInt("errorCode")) {
					case 20:
						result = "要翻译的文本过长";
						break;
					case 30:
						result = "无法进行有效的翻译";
						break;
					case 40:
						result = "不支持的语言类型";
						break;
					case 50:
						result = "无效的key";
						//TODO 通知管理员
						break;
					case 60:
						result = "无词典结果，仅在获取词典结果生效";
						break;
					default:
						if(jsonMain.containsKey("query")) {
							result = jsonMain.getString("query") + "\n";
						}
						if(jsonMain.containsKey("basic")){
							JSONObject jsonBasic = jsonMain.getJSONObject("basic");
							if(jsonBasic.containsKey("us-phonetic")){
								result += "美[" + jsonBasic.getString("us-phonetic") + "]	";
							}
							if(jsonBasic.containsKey("uk-phonetic")){
								result += "英[" + jsonBasic.getString("uk-phonetic") + "]\n";
							}
							if(!jsonBasic.containsKey("us-phonetic") && !jsonBasic.containsKey("uk-phonetic") 
									&& jsonBasic.containsKey("phonetic"))
							{
								result += "[" + jsonBasic.getString("phonetic") +"]\n";
							}
							JSONArray jsonExplains = jsonBasic.getJSONArray("explains");
							for(int i=0; i < jsonExplains.size(); i ++){
								result += jsonExplains.getString(i) + "\n";
							}
							result += "\n";
						}
						if(jsonMain.containsKey("translation")){
							JSONArray jsonArray = jsonMain.getJSONArray("translation");
							for(int i=0; i <jsonArray.size(); i++){
								result += jsonArray.getString(i) + "\n";
							}
							result += "\n";
						}
						if(jsonMain.containsKey("web")){
							JSONArray jsonWeb = jsonMain.getJSONArray("web");
							for(int i=0; i < jsonWeb.size(); i++){
								JSONObject jsonWebObject = jsonWeb.getJSONObject(i);
								result += jsonWebObject.getString("key") + ":";
								JSONArray jsonWebValue = jsonWebObject.getJSONArray("value");
								for(int j=0; j < jsonWebValue.size(); j ++){
									result += "(" + j + ")" + jsonWebValue.getString(j) + "; ";
								}
								result += "\n";
							}
						}
						
						//System.out.println(result);
						break;
					}
				}
				//System.out.println(result.lastIndexOf('\n'));
				if(result != null ){
				    // \r\n for Windows; \n for Unix; \r for Mac
					result = result.replaceAll("(?:\r\n|\n|\r)*$", ""); 
				}
				System.out.println(result);
				return result;
				

			} catch (Exception e) {
				e.printStackTrace();
			}

		return null;
	}
	
	public void sendResult(Map<String, String> result){
		
		String _userid = result.get("FromUserName");
		String _appid = result.get("ToUserName");
		String _content = result.get("Content");
		
		_content = _content.substring(2);
		sendResult(_userid, _appid, _content);
		
	}

	public void sendResult(String userId, String appId, String content) {
		
		AppBean.cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String transResult = translate(content);
				ReturnMsg.sendTextMsg(userId, MsgType.TEXT, transResult);
			}
		});
	}
}
