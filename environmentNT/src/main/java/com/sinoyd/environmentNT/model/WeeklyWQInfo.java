package com.sinoyd.environmentNT.model;

import org.json.JSONException;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/***
 * 周报wq模型
 * 
 * @author smz
 * 
 */
public class WeeklyWQInfo extends JSONModel {
	public String Class; // 分级说明
	public String Ports; // 站点
	public String Count; // 数量
	public String Percent; // 罗马文字

	@Override
	protected void parse(JSONObject jb) {
		try {
			Class = jb.getString("Class");
			Ports = jb.getString("Ports");
			Count = jb.getString("Count");
			Percent = jb.getString("Percent");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("Class", Class).put("Ports", Ports).put("Count", Count).put("Percent", Percent);
	}
}
