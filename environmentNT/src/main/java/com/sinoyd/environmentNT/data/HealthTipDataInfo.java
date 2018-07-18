package com.sinoyd.environmentNT.data;

/**
 * 健康提醒实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HealthTipDataInfo

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HealthTipDataInfo {
	/** 数据id **/
	public int resId;
	/** 健康提醒的文字 **/
	public String tip;

	public HealthTipDataInfo() {
	}

	public HealthTipDataInfo(int resId, String tip) {
		this.resId = resId;
		this.tip = tip;
	}
}
