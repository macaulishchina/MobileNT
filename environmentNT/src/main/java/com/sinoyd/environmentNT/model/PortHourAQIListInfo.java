package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 实况最新小时AQI 列表信息
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * @类型名称：PortHourAQIListInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortHourAQIListInfo extends JSONModel {
	public List<PortHourAQIInfo> PortHourAQI;

	@Override
	public void parse(JSONObject jb) throws Exception {
		if (PortHourAQI == null)
			PortHourAQI = new ArrayList<PortHourAQIInfo>();
		PortHourAQI.clear();
		parseArray(PortHourAQI, jb.getJSONArray("PortHourAQI"), PortHourAQIInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		if (PortHourAQI == null)
			return put("PortHourAQI", new JSONArray());
		else {
			return put("PortHourAQI", PortHourAQI);
		}
	}
}
