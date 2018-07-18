package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 首要污染物小时浓度值 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：DateInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class DateInfo extends JSONModel {
	/** 时间（小时） **/
	public String DateTime;
	/** 值 **/
	public String value; // 值

	@Override
	protected void parse(JSONObject jb) throws Exception {
		DateTime = jb.getString("DateTime");
		value = jb.getString("value");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("value", value).put("DateTime", DateTime);
	}
}