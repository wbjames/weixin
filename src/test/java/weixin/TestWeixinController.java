package weixin;

import com.wb.controller.WeixinController;

import junit.framework.TestCase;

public class TestWeixinController extends TestCase {

	public void testParseXml() {
		WeixinController.parseXml("<xml>"
				+ "<ToUserName><![CDATA[toUser]]></ToUserName>"
				+ "<FromUserName><![CDATA[fromUser]]></FromUserName> "
				+ "<CreateTime>1348831860</CreateTime>"
				+ "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[this is a test]]></Content>"
				+ "<MsgId>1234567890123456</MsgId>"
				+ "</xml>");
		//System.out.println(xmlStr);
	}
}