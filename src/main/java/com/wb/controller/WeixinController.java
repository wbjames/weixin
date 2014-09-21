package com.wb.controller;

import java.io.StringReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.wb.business.wxMsgHandler.TextMsgHandler;
import com.wb.business.wxMsgHandler.EventMsgHandler;
import com.wb.business.wxMsgHandler.ImageMsgHandler;
import com.wb.business.wxMsgHandler.VideoMsgHandler;
import com.wb.business.wxMsgHandler.VoiceMsgHandler;
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
	* <p>Title: doGet</p>
	* <p>Description: 登陆验证</p>
	* @return
	 */
	@RequestMapping(value = "/wx", method = RequestMethod.GET)
	@ResponseBody
	public String doGet(WebRequest request){
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
	
	/**
	 * 
	* <p>Title: doPost</p>
	* <p>Description: 消息处理</p>
	* @param request
	* @param body
	* @return
	 */
	@RequestMapping(value = "/wx", produces = "application/xml;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String doPost(WebRequest request, @RequestBody String body){
		System.out.println("＝＝＝＝＝＝＝＝＝＝＝＝＝＝Post");
		System.out.println(body);
		return parseXml(body);
	}
	
	
	
	
	/**
	 * 
	* <p>Title: parseXml</p>
	* <p>Description: 接受消息并处理返回</p>
	* @param xmlData
	* @return
	 */
	public static String parseXml(String xmlData) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			if(nodes != null){
				for(int i=0; i < nodes.getLength(); i++){
					Node node = nodes.item(i);
					result.put(node.getNodeName(), node.getTextContent());
				}
				switch (result.get("MsgType")) {
				case "text":
					return TextMsgHandler.textMsgHandler(result);

				case "image":
					return ImageMsgHandler.imageMsgHandler(result);
					
				case "voice":
					return VoiceMsgHandler.voiceMsgHandler(result);
				
				case "video":
					return VideoMsgHandler.videoMsgHandler(result);
					
				case "event":
					return EventMsgHandler.eventMsgHandler(result);
					
				default:
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
}
