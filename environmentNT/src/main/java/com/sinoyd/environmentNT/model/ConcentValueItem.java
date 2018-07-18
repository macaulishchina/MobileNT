package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

public class ConcentValueItem extends JSONModel {
	// "指标" : "PM2.5",
	// "均值类型" : "24小时",
	// "浓度" : "218μg/m3"
	/** 指标 **/
	public String kpi;
	/** 均值类型 **/
	public String avgType;
	/** 浓度 **/
	public String ci;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		kpi = jb.getString("指标");
		avgType = jb.getString("均值类型");
		ci = jb.getString("浓度");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("指标", kpi).put("均值类型", avgType).put("浓度", ci);
	}
}
