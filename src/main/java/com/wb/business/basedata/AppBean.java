package com.wb.business.basedata;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>Title: AppBean</p>
 * <p>Description: 微信的全局信息</p>
 * @author wb_james
 * @date 2014年9月10日
 */
public class AppBean {

	public static String _accessToken;
	
	public static final String APPID = "wxa7e30892e187b43c";
	
	public static final String APPSECRET = "49687b388836e1eba277d8528e92bd39";
	
	public static final String APP_LANG = "zh_CN";
	
	public static final int DURAUTION = 3000 * 1000;
	
	public static final String TOKENBASEURL = "https://api.weixin.qq.com/cgi-bin/token";
	
	public static final String UPLOAD_MEDIA_BASE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
	
	public static final String GET_MEDIA_BASE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
	
	public static final String USERINFOURL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=";
	
	public static final String SENDBASEURL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	
	//有道翻译API
	//http://fanyi.youdao.com/openapi.do?keyfrom=<keyfrom>&key=<key>&type=data&doctype=<doctype>&version=1.1&q=要翻译的文本
	public static final String LANG_TRANS_YD_URL = "http://fanyi.youdao.com/openapi.do?"
													+ "keyfrom=womenaixuexi"
													+ "&key=218016019"
													+ "&type=data"
													+ "&doctype=json"
													+ "&version=1.1"
													+ "&q=";
	
	
	
	
	public static final String MENU = "回复指令	功能说明\n"
			+ "LT	找人聊天	如(LT)\n\n"
			+ "FY	翻译，如(FY学习)\n\n"
//			+ "ZH#	英译中，如(ZH#love)\n+"
			+ "指令代码不区分大小写，如(fygood morning!）。"
			;
	
	public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
	
	
}
