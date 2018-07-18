package com.sinoyd.environmentNT.data;

import java.util.List;

/**
 * 监测数据实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：FactorProperty

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class FactorProperty {
	/** 监测日期 **/
	public String date;
	/** 因子名称 **/
	public List<String> factorNames;
	/** 数据值 **/
	public List<String> factorValues;
	/** 是否超标 **/
	public List<Boolean> isExceeded;
	/** danwei **/
	public List<String> danwei;
}
