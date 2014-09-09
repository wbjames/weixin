package com.wb.business.wxMsgHandler;

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
}
