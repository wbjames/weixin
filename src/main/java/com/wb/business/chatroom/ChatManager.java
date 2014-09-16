package com.wb.business.chatroom;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>Title: ChatManager</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月11日
 */
@SuppressWarnings({})
public class ChatManager {

	public static int NOROOM = -1;
	
	public static int WAIT = -2;
	
	public static int HASJOIN = -3;
	
	public static int _roomSize = 100;
	
	private static Room currentRoom = CreateRoomFactory.getOneRoom();
	
	//聊天user.id->user
	
	private static Map<String, UserInfo> _userMap = new ConcurrentHashMap<String, UserInfo>();

	//
	private static Map<Integer, Room> _roomMap = new ConcurrentHashMap<Integer, Room>();
	
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
			
			user.roomId = roomId;
			_userMap.put(userId, user);
			_roomMap.put(roomId, currentRoom);
			
			
			currentRoom = CreateRoomFactory.getOneRoom();
			System.out.println("currentRoom id = " + currentRoom.getId());
			return roomId;
		}
		
		
	}
	
	public static Room getRoomById(int roomId){
		return _roomMap.get(roomId);
	}
	
	
	public static UserInfo getUserInfo(String uid){
		return _userMap.get(uid);
	}
	
	public static void removeUserByUserId(String userId){
		_userMap.remove(userId);
	}
	
	public static void removeUserByRoomId(int roomId){
		if(roomId == currentRoom.getId()){
			String[] users = currentRoom.getAllUserId();
			for(String uid: users){
				removeUserByUserId(uid);
			}
		}else {
			Room room = getRoomById(roomId);
			if(room != null) {
				String[] users = room.getAllUserId();
				for(String uid: users){
					removeUserByUserId(uid);
				}
			}else {
				
				System.out.println("#2 clean RoomId is: " + roomId + "============ currentRoomid is " + currentRoom.getId());
			}
		}
    	
	}
	
	public static void removeRoomByRoomId(int roomId){
		if(roomId == currentRoom.getId()){
			currentRoom.cleanRoom();
		}else {
			_roomMap.remove(roomId);
		}
		
	}
	
	public static void cleanByRoomId(int roomId){
		System.out.println("#1 clean RoomId is: " + roomId + "============ currentRoomid is " + currentRoom.getId());
		removeUserByRoomId(roomId);
		removeRoomByRoomId(roomId);
	}
}
