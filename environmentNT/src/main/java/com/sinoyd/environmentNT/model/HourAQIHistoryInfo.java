package com.sinoyd.environmentNT.model;

import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【历史（专业版）】
 * 
 * @author zz
 * 
 */
public class HourAQIHistoryInfo extends JSONModel {
	public List<HourAQIHistoryItem> HourAQI;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		parseArray(HourAQI, jb.getJSONArray("HourAQI"), HourAQIHistoryItem.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("HourAQI", HourAQI);
	}
}
