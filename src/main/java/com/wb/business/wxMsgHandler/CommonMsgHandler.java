package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.wxUtils.ConvertUtils;
import com.wb.jpa.DataAdpter;

/**
 * <p>Title: CommonMsgHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月9日
 */
public class CommonMsgHandler {

	

	/**
	 * 
	* <p>Title: textMsgHandler</p>
	* <p>Description: 普通文字消息</p>
	* @param result
	* @return
	 */
	public static String textMsgHandler(Map<String, String> result) {
		String insertSql = "insert into wx_receive_msg(tousername, fromusername, createtime,"
				+ "	createtimedate, msgtype, content,msgid)"
				+ "	values(?,?,?,?,?,?,?)";
		DataAdpter da = new DataAdpter();
		JdbcTemplate jt = da.getJdbcTemplate();
		int insCnt = jt.update(insertSql, new Object[]{
				result.get("ToUserName"),
				result.get("FromUserName"),
				Integer.parseInt(result.get("CreateTime")),
				new Date(Long.parseLong(result.get("CreateTime")) * 1000L),
				result.get("MsgType"),
				result.get("Content"),
				Long.parseLong(result.get("MsgId"))
		});
		System.out.println(insCnt);
		
		//将String[]转化为list，生成xml格式
		Map<String, String> newMap = new HashMap<String, String>();
		newMap.putAll(result);
		newMap.put("FromUserName", result.get("ToUserName"));
		newMap.put("ToUserName", result.get("FromUserName"));
		newMap.put("Content", result.get("Content") + "---此条回复来自服务器");
		newMap.put("CreateTime", (new Date().getTime())/1000 + "");
		
		String xmlStr = ReturnMsg.returnTextMsg(newMap);
		
		
		if(xmlStr != null){
			String updateSql = "update wx_receive_msg set isresponsed = 1 where msgid = ?";
			jt.update(updateSql, Long.parseLong(result.get("MsgId")));
			
			return xmlStr;
		}
		
		return null;
	}

	
	
	
	
	

}
