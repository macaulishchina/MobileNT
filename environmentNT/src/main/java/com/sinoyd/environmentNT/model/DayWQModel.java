package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.iceman.paintdemo.JsonUtil;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 水质日均值的等级情况
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * @类型名称：DayWQModel

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class DayWQModel extends JSONModel {
	/** 日期 **/
	public String dateTime;
	/** 等级 **/
	public String WQ;
	/** 等级值 **/
	public int value;
	/** 等级类型 **/
	public String Class;
	/** 水质因子名称 **/
	public String MainPollute;

	@Override
	public void parse(JSONObject jb) throws Exception {
		this.dateTime = jb.optString("dateTime");
		this.WQ = jb.optString("WQ");
		this.Class = jb.optString("Class");
		this.MainPollute = jb.optString("MainPollute");
		this.value = JsonUtil.getLevelByLuoMaNumber(this.WQ) * 50;
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
