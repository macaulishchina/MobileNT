package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【历史】最近24小时AQI
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * @类型名称：TopNHourAQIInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class TopNHourAQIInfo extends JSONModel {
	public List<TopHourAQIInfo> HourAQI;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		if (HourAQI == null)
			HourAQI = new ArrayList<TopHourAQIInfo>();
		HourAQI.clear();
		parseArray(HourAQI, jb.getJSONArray("HourAQI"), TopHourAQIInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("HourAQI", HourAQI);
	}
}
