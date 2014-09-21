package weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import junit.framework.TestCase;

import com.wb.business.basedata.AppBean;
import com.wb.business.chatroom.UserInfo;
import com.wb.business.wxMsgHandler.MsgType;
import com.wb.business.wxMsgHandler.ReturnMsg;
import com.wb.business.wxMsgHandler.TextMsgHandler;
import com.wb.business.wxMsgHandler.ImageMsgHandler;

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
	
//	public void testTextMsgHandler(){
//		try {
//			AppBean._accessToken = "pVqfOWPXf7dUtztcVXW3hHtcT8lyR52iqE_EB40FBIs1942P_halxYI9U99SOWmHr8NuQbwakbX8wXbw-RaXFA";
//			
//			
//			
//			Map<String, String> msg1 = new HashMap<String, String>();
//			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "lt");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(100);
//			
//			System.out.println("======================A再发请求聊天，应该是提示信息。。====================");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			System.out.println("======================A退出聊天，应该是退出信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "tc");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(100);
//			
//			System.out.println("======================A再退出聊天，应该是提示信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "tc");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(100);
//			
//			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "lt");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(100);
//			
//			msg1 = new HashMap<String, String>();
//			System.out.println("======================B发退出信息，应该是提示信息。。=================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "B");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "tc");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			System.out.println("======================B请求聊天，应该是先等候信息。。再各自发一条配对成功信息=====================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "B");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "lt");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			System.out.println("======================B发出聊天，应该是A接收信息。。=====================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "B");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "你好，我是B！");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			System.out.println("======================B退出聊天，各发一条聊天退出信息。。=====================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "B");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "tc");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			msg1 = new HashMap<String, String>();
//			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "MsgType");
//			msg1.put("Content", "哈喽，我是A");
//			CommonMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//		} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//		}
//	}
	
	// 2014-09-21 测试图片消息
//		public void testSendImageMsg() throws InterruptedException{
//			AppBean._accessToken = "KFrYNwZYjRzLeDMFoD6qOUGme9zOCs8aGN6J7_I_F5Gba3LudvqXBrNuM8r7MLDDsN5UWOhlcaQdlfm5m97h1g";
//			
//			
//			Map<String, String> msg1 = new HashMap<String, String>();
//			System.out.println("======================A请求聊天，应该是等候信息。。===============================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "text");
//			msg1.put("Content", "lt");
//			msg1.put("MsgId", "123");
//			TextMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			System.out.println("======================B请求聊天，应该是先等候信息。。再各自发一条配对成功信息=====================");
//			msg1.put("ToUserName", "HOST");
//			msg1.put("FromUserName", "o3nDIjlamLDcpeRIXxAYZ8_3fwBg");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "text");
//			msg1.put("Content", "lt");
//			msg1.put("MsgId", "123");
//			TextMsgHandler.textMsgHandler(msg1);
//			Thread.sleep(1000);
//			
//			
//			System.out.println("======================图片测试===============================");
//			msg1.put("ToUserName", "gh_47e2bcb932e0");
//			msg1.put("FromUserName", "A");
//			msg1.put("CreateTime", "1348831860");
//			msg1.put("MsgType", "image");
//			msg1.put("MediaId", "IIroWIg6d_84c7zFc_DcTs5RLw-fvBjr2hb35GGsT79ezts_cZOssWEztTNJ4OUR");
//			msg1.put("MsgId", "123");
//			ImageMsgHandler.imageMsgHandler(msg1);
//		}
	
	public void testSendCommonMsg(){
		AppBean._accessToken = "yRqcoaU0ze61zThoOvCIwIJhlKh_xXv1OQw5PRN8eu19BgOm0iSTTGvbP_0a56XyJorpnUiy5R5gLC6d3cQsDQ";
		
		//ReturnMsg.sendVoiceMsg("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", MsgType.VOICE, "_jKdoBijhl1El09PPlEtlwujq11JvjUlEtHAAoo7-hhg10_-_nAnPTyq_8g2Da2H");
		
		ReturnMsg.sendVideoMsg("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", MsgType.VIDEO, "4tMh4XoABNEx6FxYeFy4dsRFO0lKCK7cVMiCnv8AX6KTZ5rb2JK4TnwzniKtLshUh_4KA5R7TIqyY5iEC52Ysw", "fhhsWOCEGkngDP2JS4Qz1eLAqUXAynqVIsYv6V1jl--hF4dM9Y4PPy4WUQY4EaID", null, null);
	}
	
}
