package weixin;

import junit.framework.TestCase;

import com.wb.business.basedata.AppBean;
import com.wb.business.wxMsgHandler.ReturnMsg;

/**
 * <p>Title: TestReturnMsg</p>
 * <p>Description: </p>
 * @author wb_james
 * @date 2014年9月13日
 */
public class TestReturnMsg extends TestCase {
	
	public void testSentTextMsg(){
		AppBean._accessToken = "pVqfOWPXf7dUtztcVXW3hHtcT8lyR52iqE_EB40FBIs1942P_halxYI9U99SOWmHr8NuQbwakbX8wXbw-RaXFA";
		ReturnMsg.sendTextMsg("o3nDIjlamLDcpeRIXxAYZ8_3fwBg", "text", "中文:dd");
	}
}
