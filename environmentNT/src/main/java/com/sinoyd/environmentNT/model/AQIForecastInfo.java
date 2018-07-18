package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【预报】获取空气质量预报
 * 
 * @author zz
 * 
 */
public class AQIForecastInfo extends JSONModel {
	public List<AQIForcecastItem> AQIForcast;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		if (AQIForcast == null)
			AQIForcast = new ArrayList<AQIForcecastItem>();
		AQIForcast.clear();
		parseArray(AQIForcast, jb.getJSONArray("AQIForcast"), AQIForcecastItem.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("AQIForcast", AQIForcast);
	}
}
