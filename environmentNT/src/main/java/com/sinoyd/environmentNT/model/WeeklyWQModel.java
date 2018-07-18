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
public class WeeklyWQModel extends JSONModel {
	public List<WeeklyWQInfo> WeeklyWQ;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		if (WeeklyWQ == null)
			WeeklyWQ = new ArrayList<WeeklyWQInfo>();
		WeeklyWQ.clear();
		parseArray(WeeklyWQ, jb.getJSONArray("WeeklyWQ"), WeeklyWQInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("WeeklyWQ", WeeklyWQ);
	}
}
