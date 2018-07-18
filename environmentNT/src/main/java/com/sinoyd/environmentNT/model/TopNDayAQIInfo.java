package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【历史】最近30天AQI
 * 
 * @author zz
 * 
 */
public class TopNDayAQIInfo extends JSONModel {
	public List<TopDayAQIInfo> DayAQI;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		if (DayAQI == null)
			DayAQI = new ArrayList<TopDayAQIInfo>();
		DayAQI.clear();
		parseArray(DayAQI, jb.getJSONArray("DayAQI"), TopDayAQIInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("DayAQI", DayAQI);
	}
}
