package com.wb.business.wxUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	
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
