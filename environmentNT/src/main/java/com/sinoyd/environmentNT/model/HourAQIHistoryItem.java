package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

public class HourAQIHistoryItem extends JSONModel {
	public String DateTime; // 日期
	public String AQI; // aqi值
	public String PrimaryPollutant; // 污染源
	public String RGBValue; // 颜色值
	public String Class; // 污染说明
	public String Grade; // 级别
	public String HealthEffect; // 说明
	public String AQI1; // 说明

	@Override
	protected void parse(JSONObject jb) throws Exception {
		DateTime = jb.getString("DateTime");
		AQI = jb.getString("AQI");
		PrimaryPollutant = jb.getString("PrimaryPollutant");
		RGBValue = jb.getString("RGBValue");
		Class = jb.getString("Class");
		Grade = jb.getString("Grade");
		HealthEffect = jb.getString("HealthEffect");
		AQI1 = jb.getString("AQI1");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("DateTime", DateTime).put("AQI", AQI).put("PrimaryPollutant", PrimaryPollutant).put("RGBValue", RGBValue).put("Class", Class).put("Grade", Grade).put("HealthEffect", HealthEffect).put("AQI1", AQI1);
	}
}
