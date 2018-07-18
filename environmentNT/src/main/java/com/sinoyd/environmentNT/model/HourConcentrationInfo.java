package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【浓度】最新小时浓度值
 * 
 * @author zz
 * 
 */
public class HourConcentrationInfo extends JSONModel {
	public List<HourConcentrationItem> HourConcentration;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		if (HourConcentration == null)
			HourConcentration = new ArrayList<HourConcentrationItem>();
		HourConcentration.clear();
		parseArray(HourConcentration, jb.getJSONArray("HourConcentration"), HourConcentrationItem.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("HourConcentration", HourConcentration);
	}
}
