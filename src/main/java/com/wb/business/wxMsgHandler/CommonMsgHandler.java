package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.basedata.AppBean;
import com.wb.business.chatroom.ChatManager;
import com.wb.business.chatroom.Room;
import com.wb.business.chatroom.UserInfo;
import com.wb.business.joke.JokeFactory;
import com.wb.business.translate.LangTranslate;
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
		String _content = result.get("Content");
		String _fromId = result.get("FromUserName");
		String _toId = result.get("ToUserName");
		String _msgType = MsgType.TEXT;
		Long _msgId = Long.parseLong(result.get("MsgId"));
		
		insertTextMsgToDB(result);
		
		_content = _content==null?"":_content.toLowerCase();
		
		Map<String, String> newMap = null;
		
		String xmlStr = null;
		switch (_content) {
			case "lt":{ //请求聊天
				UserInfo ui = new UserInfo();
				ui.userId = _fromId;
				ui.fromId = _toId;
				ltHandler(ui);
				
				break;
			}
			case "tc":{ //退出聊天
				UserInfo ui = new UserInfo();
				ui.userId = _fromId;
				ui.fromId = _toId;
				tcHandler(ui);
				
				break;
			}
			case "xh":{
				new JokeFactory().provideOneJoke(_fromId, _toId);
				break;
			}
			default:{
				if(_content.startsWith("fy") || _content.startsWith("翻译")){
//					String fyResult = new LangTranslate().translate(_content.substring(3));
//					newMap = ReturnMsg.setTextMsgMap(_fromId,
//							_toId, (new Date().getTime()) / 1000,
//							_msgType, fyResult);
//					
//					//将String[]转化为list，生成xml格式
//					xmlStr = ReturnMsg.returnTextMsg(newMap);	
					//LangTranslate().translate
					
					new LangTranslate().sendResult(_fromId, _toId, _content.substring(2));
					break;
				}
				
				
				UserInfo ui = ChatManager.getUserInfo(_fromId);
				if( ui != null){
					sendOthersMsg(ui, _content);
					
					break;
				}else {
					newMap = ReturnMsg.setTextMsgMap(_fromId,
							_toId, (new Date().getTime()) / 1000,
							_msgType, AppBean.MENU);
					
					//将String[]转化为list，生成xml格式
					xmlStr = ReturnMsg.returnTextMsg(newMap);
					break;
				}
			}
				
		}
		
		updateResponseToDB(_msgId);
		
		return xmlStr;
	}

	
	
	
	




	private static void sendOthersMsg(UserInfo ui, String content) {

		System.out.println(ui.userId);
		Room room = ChatManager.getRoomById(ui.roomId);
		if(room != null){
			String[] users = room.getAllUserId();
			for (String uid : users) {

				if (uid.equals(ui.userId))
					continue;

				ReturnMsg.sendTextMsg(uid, MsgType.TEXT, content);
			}
		}
	}








	public static void ltHandler(UserInfo fromui){

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println(fromui.userId);
		int roomStatus = ChatManager.arrangeRoom(fromui);
		if (roomStatus >= 0) {
			String ltContent = "有朋友向你打了声招呼，回复消息和Ta聊天吧。\n" + 
						"退出聊天请回复【TC】";
			Room room = ChatManager.getRoomById(roomStatus);
			String[] users = room.getAllUserId();
			System.out.println("room users: " + users.length);
			for (String uid : users) {
				
				ReturnMsg.sendTextMsg(uid, MsgType.TEXT, ltContent);
				
			}

		} else if(roomStatus == ChatManager.HASJOIN){
			String ltContent = "你已加入聊天室，退出请回复【TC】";
			
			ReturnMsg.sendTextMsg(fromui.userId, MsgType.TEXT, ltContent);
		} else if(roomStatus == ChatManager.WAIT){
			String ltContent = "正在努力为你配对，请稍等……";
			
			ReturnMsg.sendTextMsg(fromui.userId, MsgType.TEXT, ltContent);
			
		} 
		//System.out.println(roomStatus);
			
		
	}
	
	
	private static void tcHandler(UserInfo fromui) {
		if(ChatManager.getUserInfo(fromui.userId) == null){
			String content = "你尚未进入聊天室，找人聊天请回复【LT】";
			ReturnMsg.sendTextMsg(fromui.userId, MsgType.TEXT, content);

		}else {
			String content = "你已退出聊天，祝你天天开心～～";
			ReturnMsg.sendTextMsg(fromui.userId, MsgType.TEXT, content);
			
			String otherContent = "对方已退出聊天,你已自动退出。";
			sendOthersMsg(fromui, otherContent);
			ChatManager.cleanByRoomId(fromui.roomId);
		}
		
	}
	
	
	public static void insertTextMsgToDB(Map<String, String> result){
		AppBean.cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
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
				System.out.println("insertTextMsgToDB count = " + insCnt);
			}
		});
	}
	
	public static void updateResponseToDB(Long msgId){
		AppBean.cachedThreadPool.execute(new Runnable() {

			@Override
			public void run() {

				DataAdpter da = new DataAdpter();
				JdbcTemplate jt = da.getJdbcTemplate();
				String updateSql = "update wx_receive_msg set isresponsed = 1 where msgid = ?";
				int updCnt = jt.update(updateSql, msgId);
				System.out.println("updateResponseToDB count = " +updCnt);
			}
		});
	}

}
