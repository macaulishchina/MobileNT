package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 实时水质24小时浓度值 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：RealtimeWaterWQDetail


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class RealtimeWaterWQDetail extends JSONModel {
	/** 因子名称 **/
	public String factor;
	/** 浓度值等级 **/
	public int WQ;
	/** 浓度值 **/
	public String value;

	@Override
	public void parse(JSONObject jb) throws Exception {
		// TODO Auto-generated method stub
		this.factor = jb.optString("factor");
		this.WQ = jb.optInt("WQ");
		this.value = jb.optString("value");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
