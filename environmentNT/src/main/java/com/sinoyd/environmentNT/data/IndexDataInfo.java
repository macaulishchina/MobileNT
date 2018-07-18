package com.sinoyd.environmentNT.data;

/**
 * 基本数据模型 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：IndexDataInfo

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class IndexDataInfo {
	public IndexDataInfo() {
	}

	public IndexDataInfo(CharSequence sName, int sValue) {
		name = sName;
		value = sValue;
	}

	public CharSequence name; // 名称
	public int value; // 值
}
