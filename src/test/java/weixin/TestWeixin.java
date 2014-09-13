package weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import junit.framework.TestCase;

import com.wb.business.basedata.AppBean;
import com.wb.business.chatroom.UserInfo;
import com.wb.business.wxMsgHandler.CommonMsgHandler;

public class TestWeixin extends TestCase {

//	public void testParseXml() {
//		WeixinController.parseXml("<xml>"
//				+ "<ToUserName><![CDATA[toUser]]></ToUserName>"
//				+ "<FromUserName><![CDATA[fromUser]]></FromUserName> "
//				+ "<CreateTime>1348831860</CreateTime>"
//				+ "<MsgType><![CDATA[text]]></MsgType>"
//				+ "<Content><![CDATA[this is a test]]></Content>"
//				+ "<MsgId>1234567890123456</MsgId>"
//				+ "</xml>");
//		//System.out.println(xmlStr);
//	}
	
	
//	public void testLtHandler(){
//		UserInfo ui = new UserInfo();
//		ui.userId = "123";
//		CommonMsgHandler.ltHandler(ui);
//	}
	
	public void testTextMsgHandler(){
		try {
			AppBean._accessToken = "pVqfOWPXf7dUtztcVXW3hHtcT8lyR52iqE_EB40FBIs1942P_halxYI9U99SOWmHr8NuQbwakbX8wXbw-RaXFA";
			
			
			
			Map<String, String> msg1 = new HashMap<String, String>();
			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "A");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "lt");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(100);
			
			System.out.println("======================A再发请求聊天，应该是提示信息。。====================");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
			System.out.println("======================A退出聊天，应该是退出信息。。===============================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "A");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "tc");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(100);
			
			System.out.println("======================A再退出聊天，应该是提示信息。。===============================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "A");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "tc");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(100);
			
			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "A");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "lt");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(100);
			
			msg1 = new HashMap<String, String>();
			System.out.println("======================B发退出信息，应该是提示信息。。=================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "B");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "tc");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
			System.out.println("======================B请求聊天，应该是先等候信息。。再各自发一条配对成功信息=====================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "B");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "lt");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
			System.out.println("======================B发出聊天，应该是A接收信息。。=====================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "B");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "你好，我是B！");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
			System.out.println("======================B退出聊天，各发一条聊天退出信息。。=====================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "B");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "tc");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
			msg1 = new HashMap<String, String>();
			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
			msg1.put("ToUserName", "HOST");
			msg1.put("FromUserName", "A");
			msg1.put("CreateTime", "1348831860");
			msg1.put("MsgType", "MsgType");
			msg1.put("Content", "哈喽，我是A");
			CommonMsgHandler.textMsgHandler(msg1);
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
}
