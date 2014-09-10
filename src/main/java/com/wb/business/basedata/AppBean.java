package com.wb.business.basedata;

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
	
	public static final int DURAUTION = 3000 * 1000;
	
	public static final String TOKENBASEURL = "https://api.weixin.qq.com/cgi-bin/token";
	
	public static final String UPLOAD_MEDIA_BASE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
	
	public static final String GET_MEDIA_BASE_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
}
