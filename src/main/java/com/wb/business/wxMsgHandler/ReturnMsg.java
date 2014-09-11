package com.wb.business.wxMsgHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wb.business.wxUtils.ConvertUtils;

/**
 * <p>Title: ReturnMsg</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class ReturnMsg {

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
}
