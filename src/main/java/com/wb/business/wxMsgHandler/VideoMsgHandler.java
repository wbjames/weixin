package com.wb.business.wxMsgHandler;

import java.util.Map;

import com.wb.business.basedata.AppBean;
import com.wb.business.chatroom.ChatManager;
import com.wb.business.chatroom.Room;
import com.wb.business.chatroom.UserInfo;

public class VideoMsgHandler {

	public static String videoMsgHandler(Map<String, String> result) {
		String _fromId = result.get("FromUserName");

		String _msgType = MsgType.VIDEO;

		String _mediaId = result.get("MediaId");
		
		String _thumbMediaId = result.get("ThumbMediaId");
		
		String _msgId = result.get("MsgId");
		
		DBHandler.insertVideoMsgToDB(result);
		
		UserInfo ui = ChatManager.getUserInfo(_fromId);
		if( ui != null){
			//向同个房间的人发信息
			Room room = ChatManager.getRoomById(ui.roomId);
			if(room != null){
				String[] users = room.getAllUserId();
				for (String uid : users) {

					if (uid.equals(ui.userId))
						continue;

					ReturnMsg.sendVideoMsg(uid, _msgType, _mediaId, _thumbMediaId, null, null);
				}
			}
		}else {
			ReturnMsg.sendTextMsg(_fromId, MsgType.TEXT, AppBean.MENU);	
		}
		
		
		DBHandler.updateResponseToDB(_msgId);
		
		
		
		
		return null;
	}
}
