package weixin;

import junit.framework.TestCase;

import com.wb.business.chatroom.UserInfo;
import com.wb.business.wxMsgHandler.CommonMsgHandler;

public class TestWeixinController extends TestCase {

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
	
	
	public void testLtHandler(){
		UserInfo ui = new UserInfo();
		ui.userId = "123";
		CommonMsgHandler.ltHandler(ui);
	}
}
