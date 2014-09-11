package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.bag.TreeBag;
import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.chatroom.ChatManager;
import com.wb.business.chatroom.UserInfo;
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
		
		String _content = result.get("Content");
		_content = _content==null?"":_content.toLowerCase();
		
		Map<String, String> newMap = null;
		
		switch (_content) {
			case "lt":
				newMap = ReturnMsg.setTextMsgMap(result.get("FromUserName"),
						result.get("ToUserName"), (new Date().getTime()) / 1000,
						result.get("MsgType"), "正在努力为你配对，请稍等……");
	
				UserInfo ui = new UserInfo();
				ui.userId = result.get("FromUserName");
				ltHandler(ui);
	
				break;
			default:
				newMap = ReturnMsg.setTextMsgMap(result.get("FromUserName"),
						result.get("ToUserName"), (new Date().getTime()) / 1000,
						result.get("MsgType"), "找人聊天请回复【lt】");
				break;
		}
			
		//将String[]转化为list，生成xml格式
		
		
		String xmlStr = ReturnMsg.returnTextMsg(newMap);
		
		
		if(xmlStr != null){
			String updateSql = "update wx_receive_msg set isresponsed = 1 where msgid = ?";
			jt.update(updateSql, Long.parseLong(result.get("MsgId")));
			
			return xmlStr;
		}
		
		return null;
	}

	
	
	
	public static void ltHandler(UserInfo ui){
		Thread t = new Thread(new Runnable(){
		    @Override
		    public void run() {
		    	System.out.println(111);
		    	int roomStatus = ChatManager.arrangeRoom(ui);
		    	System.out.println(roomStatus);
		    }
		});
		t.start();
	}
	

}
