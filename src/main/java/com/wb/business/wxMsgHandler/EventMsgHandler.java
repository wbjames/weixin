package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: EventMsgHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class EventMsgHandler {

	public static String eventMsgHandler(Map<String, String> result) {
		Map<String, String> newMap = new HashMap<String, String>();
		newMap.put("MsgType", "text");
		newMap.put("FromUserName", result.get("ToUserName"));
		newMap.put("ToUserName", result.get("FromUserName"));
		newMap.put("Content", result.get("Event") + "---此条回复来自服务器");
		newMap.put("CreateTime", (new Date().getTime())/1000 + "");
		
		
		return ReturnMsg.returnTextMsg(newMap);
	}
}
