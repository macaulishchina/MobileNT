package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 实时水质24小时浓度值 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HoursFactorModel


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HoursFactorModel extends JSONModel {
	public String dateTime;
	public String value;

	@Override
	public void parse(JSONObject jb) throws Exception {
		this.dateTime = jb.optString("DateTime");
		this.value = jb.optString("value");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
