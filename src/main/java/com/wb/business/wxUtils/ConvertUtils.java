package com.wb.business.wxUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConvertUtils {

	/**
	 * 
	* <p>Title: convertArrayToList</p>
	* <p>Description: 将Map中的键值对转换成List<Map<key,value>></p>
	* @param names
	* @param result
	* @return
	 */
	public static List<Map<String, String>> convertArrayToList(String[] names, Map<String, String> result){
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
	public static String makeXml(List<Map<String, String>> list) {
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
}
