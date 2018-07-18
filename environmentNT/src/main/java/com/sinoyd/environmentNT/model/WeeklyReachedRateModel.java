package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/***
 * 周报模型
 * 
 * @author smz
 * 
 */
public class WeeklyReachedRateModel extends JSONModel {
	public List<WeeklyReachedModel> WeeklyReachedRate;

	@Override
	protected void parse(JSONObject jb) {
		try {
			if (WeeklyReachedRate == null) {
				WeeklyReachedRate = new ArrayList<WeeklyReachedModel>();
			}
			WeeklyReachedRate.clear();
			parseArray(WeeklyReachedRate, jb.getJSONArray("WeeklyReachedRate"), WeeklyReachedModel.class);
		}
		catch (Exception e) {
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("WeeklyReachedRate", WeeklyReachedRate);
	}
}
