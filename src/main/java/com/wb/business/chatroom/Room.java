package com.wb.business.chatroom;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>Title: OneRoom</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月11日
 */
public class Room {

	private Set<String> userSet = new HashSet<String>();
	
	private int id;
	
	private Room(int id) {
		this.id = id;
	}
	
	public static Room makeRoom(int id){
		return new Room(id);
	}
	
	public int getId(){
		return id;
	}
	
	public int getUsesCount(){
		return userSet.size();
	}
	
	public boolean isEmpty(){
		return userSet.size() == 0;
	}
	
	public boolean joinOne(String userId) {
		if(userSet.contains(userId)){
			return false;
		}else {
			return userSet.add(userId);
		}
	}
	
	public boolean leaveOne(String userId){
		if(!userSet.contains(userId)){
			return false;
		}else {
			return userSet.remove(userId);
		}
	}
	
	public void cleanRoom(){
		userSet.clear();
	}
	
	public String[] getAllUserId(){
		String[] userIdArr =  (String[])userSet.toArray(new String[userSet.size()]);
		return userIdArr;
	}
}
