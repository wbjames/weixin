package com.wb.controller;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.wb.common.Application;

/**
 * 
* <p>Title: WeixinController</p>
* <p>Description: </p>
* @author wb_james
* @date 2014年9月7日
 */
@Controller
public class WeixinController {

	/**
	 * 验证消息的有效性
	* <p>Title: wx</p>
	* <p>Description: </p>
	* @return
	 */
	@RequestMapping(value = "/wx", method = RequestMethod.GET)
	@ResponseBody
	public String wx(WebRequest request, Model mode){
		System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝GET");
		String signature = request.getParameter("signature");  
        String timestamp = request.getParameter("timestamp");  
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
		
        List<String> list = new ArrayList<String>(3);
        list.add(Application.token);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);// 排序
        
        try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			String tmpStr = list.get(0) + list.get(1) + list.get(2);
			char[] newByte = Hex.encodeHex(md.digest(tmpStr.getBytes()));
			String newStr = new String(newByte);
			System.out.println("=============tmpStr: " + tmpStr);
			System.out.println("=============newStr: " + newStr );
			if(newStr.equals(signature)){
				return echostr;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return null;
	}
}
