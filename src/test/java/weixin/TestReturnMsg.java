package weixin;

import junit.framework.TestCase;

import com.wb.business.basedata.AppBean;
import com.wb.business.joke.JokeFactory;
import com.wb.business.translate.LangTranslate;
import com.wb.business.wxMsgHandler.ReturnMsg;

/**
 * <p>Title: TestReturnMsg</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月13日
 */
public class TestReturnMsg extends TestCase {
	
//	public void testSentTextMsg(){
//		AppBean._accessToken = "pVqfOWPXf7dUtztcVXW3hHtcT8lyR52iqE_EB40FBIs1942P_halxYI9U99SOWmHr8NuQbwakbX8wXbw-RaXFA";
//		ReturnMsg.sendTextMsg("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "text", "中文:dd");
//	}


//	public void testTranslate(){
//		LangTranslate lt = new LangTranslate();
//		lt.translate("                  \n你好");
//		AppBean._accessToken = "JtUUMCABy-zw3DMTtV7-GzN0QGwgj5SIeH-2rDJJ2jTLrk_2Se4GcjBROgrDXuLp0QMV5ptgnZqz6no8r1fh_Q";
//		ReturnMsg.sendTextMsg("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "text", AppBean.MENU);
//	}
	
	//2014-09-16日测试
	public void testJoke(){
		JokeFactory jf = new JokeFactory();
		jf.getJoke();
		AppBean._accessToken = "Kw4q-Zut4M0Jx3cKbJGLkwYJ-VZQADn5b8bScYqcYl27_YsyV4F4MaQzon1JM0WBKK0c7yS6Z3H-KjH6X6xXRQ";
		jf.provideOneJoke("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "gh_47e2bcb932e0");
	}
}
