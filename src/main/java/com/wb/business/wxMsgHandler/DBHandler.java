package com.wb.business.wxMsgHandler;

import java.util.Date;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.wb.business.basedata.AppBean;
import com.wb.jpa.DataAdpter;

/**
 * <p>Title: DBHandler</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月21日
 */
public class DBHandler {

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
	
	
	public static void insertImageMsgToDB(Map<String, String> result){
		AppBean.cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String insertSql = "insert into wx_receive_msg(tousername, fromusername, createtime,"
						+ "	createtimedate, msgtype, picurl, mediaid, msgid)"
						+ "	values(?,?,?,?,?,?,?,?)";
				DataAdpter da = new DataAdpter();
				JdbcTemplate jt = da.getJdbcTemplate();
				int insCnt = jt.update(insertSql, new Object[]{
						result.get("ToUserName"),
						result.get("FromUserName"),
						Integer.parseInt(result.get("CreateTime")),
						new Date(Long.parseLong(result.get("CreateTime")) * 1000L),
						result.get("MsgType"),
						result.get("PicUrl"),
						result.get("MediaId"),
						Long.parseLong(result.get("MsgId"))
				});
				System.out.println("insertTextMsgToDB count = " + insCnt);
			}
		});
	}
	
	
	public static void insertVoiceMsgToDB(Map<String, String> result){
		AppBean.cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String insertSql = "insert into wx_receive_msg(tousername, fromusername, createtime,"
						+ "	createtimedate, msgtype, mediaid, format, msgid)"
						+ "	values(?,?,?,?,?,?,?,?)";
				DataAdpter da = new DataAdpter();
				JdbcTemplate jt = da.getJdbcTemplate();
				int insCnt = jt.update(insertSql, new Object[]{
						result.get("ToUserName"),
						result.get("FromUserName"),
						Integer.parseInt(result.get("CreateTime")),
						new Date(Long.parseLong(result.get("CreateTime")) * 1000L),
						result.get("MsgType"),
						result.get("MediaId"),
						result.get("Format"),
						Long.parseLong(result.get("MsgId"))
				});
				System.out.println("insertTextMsgToDB count = " + insCnt);
			}
		});
	}
	
	public static void insertVideoMsgToDB(Map<String, String> result){
		AppBean.cachedThreadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				String insertSql = "insert into wx_receive_msg(tousername, fromusername, createtime,"
						+ "	createtimedate, msgtype, mediaid, thumbmediaid, msgid)"
						+ "	values(?,?,?,?,?,?,?,?)";
				DataAdpter da = new DataAdpter();
				JdbcTemplate jt = da.getJdbcTemplate();
				int insCnt = jt.update(insertSql, new Object[]{
						result.get("ToUserName"),
						result.get("FromUserName"),
						Integer.parseInt(result.get("CreateTime")),
						new Date(Long.parseLong(result.get("CreateTime")) * 1000L),
						result.get("MsgType"),
						result.get("MediaId"),
						result.get("ThumbMediaId"),
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
	
	
	public static void updateResponseToDB(String msgId){
		Long _msgId = Long.parseLong(msgId);
		updateResponseToDB(_msgId);
	}
}
