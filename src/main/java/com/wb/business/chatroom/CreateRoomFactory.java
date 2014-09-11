package com.wb.business.chatroom;



/**
 * <p>Title: CreateRoomFactory</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月11日
 */
public class CreateRoomFactory {

	private static int serialID = 0;
	
	private CreateRoomFactory(){}
	
	public static synchronized Room getOneRoom(){
		return Room.makeRoom(serialID ++);
	}
	
	
}



