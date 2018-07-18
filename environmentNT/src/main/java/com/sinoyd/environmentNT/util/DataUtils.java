package com.sinoyd.environmentNT.util;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：DataUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@SuppressLint("SimpleDateFormat")
public class DataUtils {
	/**
	 * 计算前一天与后一天的日期
	 * 
	 * @param dateStr
	 * @param addDay
	 * @return
	 */
	public static String getAddDate2(String dateStr, int addDay) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		GregorianCalendar gc = new GregorianCalendar();
		if (dateStr != null) {
			try {
				Date date = dateFormat.parse(dateStr);
				// gc.add(1,-1)表示年份减一.
				// gc.add(2,-1)表示月份减一.
				// gc.add(3.-1)表示周减一.
				// gc.add(5,-1)表示天减一.
				gc.setTime(date);
				gc.add(Calendar.DATE, addDay);
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Date date = gc.getTime();
		return dateFormat.format(date);
	}

	/***
	 * 得到指定日期后的日期
	 * 
	 * @param dateStr 为空表示为当前
	 * @param addDay 要增加的日期
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getAddDate(String dateStr, int addDay) {
		String str = dateStr;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date myDate = null;
		try {
			myDate = formatter.parse(str);
		}
		catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(myDate);
		c.add(Calendar.DATE, addDay);
		myDate = c.getTime();
		return formatter.format(myDate);
	}

	/***
	 * 得到今天
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		String temp_str = "";
		Date dt = new Date();
		// 最后的aa表示“上午”或“下午” HH表示24小时制 如果换成hh表示12小时制
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	/***
	 * 得到24小时前的时间
	 * 
	 * @return
	 */
	public static String get24FormerDate() {
		return getAddDate(getCurrentDate(), -1);
	}

	/***
	 * 得到一周前的今天
	 * 
	 * @return
	 */
	public static String getWeekDate() {
		return getAddDate(getCurrentDate(), -7);
	}

	/***
	 * 得到一个月前的今天
	 * 
	 * @return
	 */
	public static String getMonthDayDate() {
		return getAddDate(getCurrentDate(), -30);
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
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(dateString);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
