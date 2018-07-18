package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/***
 * 单位时间的指数
 * 
 * @author smz
 * 
 */
public class HourConcentrationItem extends JSONModel {
	public String DateTime; // 日期
	public List<ConcentValueItem> Value; // list对象的值

	@Override
	protected void parse(JSONObject jb) throws Exception {
		jb.getString("DateTime");
		if (Value == null)
			Value = new ArrayList<ConcentValueItem>();
		Value.clear();
		parseArray(Value, jb.getJSONArray("Value"), ConcentValueItem.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		putArray("Value", Value);
		return put("DateTime", DateTime);
	}
}
