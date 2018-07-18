package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/***
 * 周报item
 * 
 * @author smz
 * 
 */
public class WeeklyReachedModel extends JSONModel {
	public String Item; // 名称
	public String Value; // 值

	@Override
	protected void parse(JSONObject jb) {
		try {
			Item = jb.getString("Item");
			Value = jb.getString("Value");
		}
		catch (Exception e) {
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("Item", Item).put("Value", Value);
	}
}
