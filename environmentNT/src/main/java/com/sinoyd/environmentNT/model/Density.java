package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 实时浓度实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：Density

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class Density extends JSONModel {
	public String dateTime; // 时间
	public ArrayList<DensityItem> values = new ArrayList<Density.DensityItem>();

	public static class DensityItem {
		public String name; // 名称
		public String type; // 类型
		public String value; // 值
		public String density // 密度
		;

		public DensityItem() {
		}

		public DensityItem(JSONObject jsonObject) {
			this.name = jsonObject.optString("factor");
			this.type = jsonObject.optString("type");
			this.value = jsonObject.optString("value");
		}
	}

	@Override
	public void parse(JSONObject jb) throws Exception {
		JSONObject jsonObject = jb.optJSONArray("HourConcentration").optJSONObject(0);
		this.dateTime = jsonObject.optString("DateTime");
		JSONArray array = jsonObject.optJSONArray("Value");
		if (values != null && values.size() > 0) {
			values.clear();
		}
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				values.add(new DensityItem(array.optJSONObject(i)));
			}
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
