package com.wb.business.chatroom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ChatManager</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月11日
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ChatManager {

	public static int WAIT = -2;
	public static int HASJOIN = 1;
	public static int NOROOM = -1;
	
	public static int _roomSize = 100;
	
	private static Room currentRoom = CreateRoomFactory.getOneRoom();
	
	//聊天user.id->user
	
	private static Map<String, UserInfo> _userMap = new HashMap<String, UserInfo>();

	//
	private static Map<Integer, Room> rooms = new HashMap<Integer, Room>();
	
	public static int arrangeRoom(UserInfo user){
		String userId = user.userId;
		if(_userMap.containsKey(userId)){
			return ChatManager.HASJOIN;
		}else if(currentRoom.isEmpty()) {
			
			//currentRoom = CreateRoomFactory.getOneRoom();
			currentRoom.joinOne(userId);
			user.roomId = currentRoom.getId();
			_userMap.put(userId, user);

			return ChatManager.WAIT;
		}else {

			int roomId = currentRoom.getId();
			currentRoom.joinOne(userId);
			rooms.put(roomId, currentRoom);
			
			
			currentRoom = CreateRoomFactory.getOneRoom();;
			return roomId;
		}
		
		
	}
	
	public static Room getRoomById(int roomId){
		return rooms.get(roomId);
	}
	
}
