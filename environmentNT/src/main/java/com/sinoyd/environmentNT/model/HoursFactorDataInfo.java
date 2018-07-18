package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【实况】获取首要污染物最新24小时浓度值 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HoursFactorDataInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HoursFactorDataInfo extends JSONModel {
	public List<DateInfo> HoursFactorData;

	@Override
	public void parse(JSONObject jb) throws Exception {
		if (HoursFactorData == null)
			HoursFactorData = new ArrayList<DateInfo>();
		HoursFactorData.clear();
		parseArray(HoursFactorData, jb.getJSONArray("HoursFactorData"), DateInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("HoursFactorData", HoursFactorData);
	}
}
