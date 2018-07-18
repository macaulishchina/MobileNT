package com.sinoyd.environmentNT.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.text.TextUtils;

/**
 * 日期工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：DateUtil


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class DateUtil {
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();
	private static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm";
	/***
	 * 得到格式化的日期
	 * 
	 * @param date 日期数据
	 * @param pattern 格式化样式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (TextUtils.isEmpty(pattern))
			dateFormat.applyPattern(DEFAULT_PATTERN);
		else
			dateFormat.applyPattern(pattern);
		return dateFormat.format(date);
	}
	
	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @param 字符串类型 (格式为：yyyy-MM-dd HH:mm:ss)
	 * @return 时间类型
	 */
	public static Date StringToDate(String dateString) {
		SimpleDateFormat sdf;
		Date date = null;
		if (dateString.contains("-")) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		else {
			sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		try {
			date = sdf.parse(dateString);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/***
	 * 得到当前的时间并格式化
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentTime(String pattern) {
		if (TextUtils.isEmpty(pattern))
			dateFormat.applyPattern(DEFAULT_PATTERN);
		else
			dateFormat.applyPattern(pattern);
		return dateFormat.format(new Date());
	}

	/***
	 * 把String的日期转化为Date对象
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date getDate(String date, String pattern) {
		if (TextUtils.isEmpty(pattern))
			dateFormat.applyPattern(DEFAULT_PATTERN);
		else
			dateFormat.applyPattern(pattern);
		try {
			return dateFormat.parse(date);
		}
		catch (ParseException e) {
			return new Date();
		}
		catch (Exception e) {
			return new Date();
		}
	}

	public static long getTime(String date, String pattern) {
		if (TextUtils.isEmpty(pattern))
			dateFormat.applyPattern(DEFAULT_PATTERN);
		else
			dateFormat.applyPattern(pattern);
		try {
			return dateFormat.parse(date).getTime();
		}
		catch (ParseException e) {
			return 0;
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取两个时间的日期天数，返回-1代表时间错误
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDays(Date startDate, Date endDate) {
		long start = startDate.getTime();
		long end = endDate.getTime();
		if (end < start) {
			return -1;
		}
		else {
			return (int) ((end - start) / 1000 / 60 / 60 / 24);
		}
	}
}
