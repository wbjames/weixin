package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.Map;

import com.wb.business.basedata.AppBean;

/**
 * <p>Title: EventMsgHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class EventMsgHandler {

	public static String eventMsgHandler(Map<String, String> result) {
		
		Map<String, String> newMap = ReturnMsg.setTextMsgMap(result.get("FromUserName"),
				result.get("ToUserName"), (new Date().getTime()) / 1000,
				MsgType.TEXT, AppBean.MENU);
		return ReturnMsg.returnTextMsg(newMap);
	}
}
