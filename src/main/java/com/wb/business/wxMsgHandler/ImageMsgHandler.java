package com.wb.business.wxMsgHandler;

import java.util.Map;

import com.wb.business.basedata.AppBean;
import com.wb.business.chatroom.ChatManager;
import com.wb.business.chatroom.Room;
import com.wb.business.chatroom.UserInfo;

/**
 * <p>Title: ImageMsgHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月21日
 */
public class ImageMsgHandler {

	public static String imageMsgHandler(Map<String, String> result) {
		String _fromId = result.get("FromUserName");

		String _msgType = MsgType.IMAGE;

		String _mediaId = result.get("MediaId");
		
		String _msgId = result.get("MsgId");
		
		DBHandler.insertImageMsgToDB(result);
		
		UserInfo ui = ChatManager.getUserInfo(_fromId);
		if( ui != null){
			//向同个房间的人发信息
			Room room = ChatManager.getRoomById(ui.roomId);
			if(room != null){
				String[] users = room.getAllUserId();
				for (String uid : users) {

					if (uid.equals(ui.userId))
						continue;

					ReturnMsg.sendImageMsg(uid, _msgType, _mediaId);
				}
			}
		}else {
			ReturnMsg.sendTextMsg(_fromId, MsgType.TEXT, AppBean.MENU);	
		}
		
		
		DBHandler.updateResponseToDB(_msgId);
		
		
		
		
		return null;
	}
	

}
