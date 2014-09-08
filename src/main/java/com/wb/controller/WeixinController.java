package com.wb.controller;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Hex;
import org.springframework.jdbc.core.JdbcTemplate;
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

import com.wb.common.Application;
import com.wb.jpa.DataAdpter;

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
	* <p>Description: 登陆验证</p>
	* @return
	 */
	@RequestMapping(value = "/wx", method = RequestMethod.GET)
	@ResponseBody
	public String wxGet(WebRequest request){
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
	* <p>Title: wxPost</p>
	* <p>Description: 普通消息处理</p>
	* @param request
	* @return
	 */
	@RequestMapping(value = "/wx", params = "!event", produces = "application/xml;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String wxPost(WebRequest request, @RequestBody String body){
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
					return textMsgHandler(result);

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
	
	
	
	
	
	
	
	/**
	 * 
	* <p>Title: textMsgHandler</p>
	* <p>Description: 普通文字消息</p>
	* @param result
	* @return
	 */
	private static String textMsgHandler(Map<String, String> result) {
		String insertSql = "insert into wx_receive_msg(tousername, fromusername, createtime,"
				+ "	createtimedate, msgtype, content,msgid)"
				+ "	values(?,?,?,?,?,?,?)";
		DataAdpter da = new DataAdpter();
		JdbcTemplate jt = da.getJdbcTemplate();
		int insCnt = jt.update(insertSql, new Object[]{
				result.get("ToUserName"),
				result.get("FromUserName"),
				Integer.parseInt(result.get("CreateTime")),
				new Date(Long.parseLong(result.get("CreateTime")) * 1000L),
				result.get("MsgType"),
				result.get("Content"),
				Long.parseLong(result.get("MsgId"))
		});
		System.out.println(insCnt);
		
		//将String[]转化为list，生成xml格式
		Map<String, String> newMap = new HashMap<String, String>();
		newMap.putAll(result);
		newMap.put("FromUserName", result.get("ToUserName"));
		newMap.put("ToUserName", result.get("FromUserName"));
		newMap.put("Content", result.get("Content") + "---此条回复来自服务器");
		newMap.put("CreateTime", (new Date().getTime())/1000 + "");
		
		List<Map<String, String>> list = convertArrayToList(new String[]{
				"ToUserName",
				"FromUserName",
				"CreateTime",
				"MsgType",
				"Content"}, newMap);
		
		String xmlStr = makeXml(list);
		
		
		if(xmlStr != null){
			String updateSql = "update wx_receive_msg set isresponsed = 1 where msgid = ?";
			jt.update(updateSql, Long.parseLong(result.get("MsgId")));
			
			return xmlStr;
		}
		
		return null;
	}

	
	private static List<Map<String, String>> convertArrayToList(String[] names, Map<String, String> result){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(String name: names){
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", name);
			map.put("value", result.get(name));
			list.add(map);
		}
		return list;
	}
	
	
	/**
	 * 
	* <p>Title: makeXml</p>
	* <p>Description: 将list转化成xml的String类型</p>
	* @param list
	* @return
	 */
	private static String makeXml(List<Map<String, String>> list) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element xml = doc.createElement("xml");
			
			for(Map<String, String> m : list){
				Element e = doc.createElement(m.get("name"));
				e.appendChild(doc.createCDATASection(m.get("value")));
				xml.appendChild(e);
			}
			
			//讲dom转化成xml格式的String
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer trans = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(xml);
			
			ByteArrayOutputStream  baos = new ByteArrayOutputStream();
			trans.transform(domSource, new StreamResult(baos));
			String xmlStr = baos.toString();
			System.out.println(baos.toString());
			return xmlStr;
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* <p>Title: formatTime</p>
	* <p>Description: 微信创建时间</p>
	* @param createTime
	* @return
	 */
	public static String formatTime(String createTime) {

		// 将微信传入的CreateTime转换成long类型,再乘以1000

		long msgCreateTime = Long.parseLong(createTime) * 1000L;

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(new Date(msgCreateTime));

	}
	
	
}
