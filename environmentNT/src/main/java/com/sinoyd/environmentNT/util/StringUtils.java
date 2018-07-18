package com.sinoyd.environmentNT.util;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.SuperscriptSpan;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;

/**
 * 文字样式的工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：StringUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class StringUtils {
	private static int MAX_SIZE = 30;
	private static int MIN_SIZE = 25;

	public static CharSequence getPM10() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("PM10");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getPM25() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("PM2.5");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getNO2() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("NO2");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getSO2() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("SO2");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getO3() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("O3");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getCO() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("CO");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	
	public static CharSequence getNOy() {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString("NOy");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	
	public static CharSequence getNatural(String string) {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString(string);
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	 
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 6, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	
	public static CharSequence getCO2(String string) {
		MAX_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.max_size);
		MIN_SIZE = MyApplication.mContext.getResources().getInteger(R.integer.min_size);
		SpannableString ss = new SpannableString(string);
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		 
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	
	public static CharSequence getPM10ForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("PM10");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getPM25ForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("PM2.5");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getNO2ForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("NO2");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getSO2ForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("SO2");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getO3ForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("O3");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}

	public static CharSequence getCOForDialog() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("CO");
		ss.setSpan(new AbsoluteSizeSpan(MAX_SIZE), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	
	public static CharSequence getUnit() {
		MAX_SIZE = 65;
		MIN_SIZE = 35;
		SpannableString ss = new SpannableString("μg/m3");
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 2, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new AbsoluteSizeSpan(MIN_SIZE), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ss.setSpan(new SuperscriptSpan(), 4, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ss;
	}
	




}
