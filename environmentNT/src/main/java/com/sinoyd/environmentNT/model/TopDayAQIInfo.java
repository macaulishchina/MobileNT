package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 【历史】最近30天AQI:其中一项
 * 
 * @author zz
 * 
 */
public class TopDayAQIInfo extends JSONModel {
	public String DateTime; // 日期
	public String AQI; // aqi 值
	public String PrimaryPollutant; // 污染指数
	public String RGBValue; // 颜色值
	public String Class; // 污染说明
	public String Grade; // 级别
	public String HealthEffect; // 说明
	public String TakeStep; // 说明

	@Override
	protected void parse(JSONObject jb) throws Exception {
		DateTime = jb.getString("DateTime");
		AQI = jb.getString("AQI");
		PrimaryPollutant = jb.getString("PrimaryPollutant");
		RGBValue = jb.getString("RGBValue");
		Class = jb.getString("Class");
		Grade = jb.getString("Grade");
		HealthEffect = jb.getString("HealthEffect");
		TakeStep = jb.getString("TakeStep");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("DateTime", DateTime).put("AQI", AQI).put("PrimaryPollutant", PrimaryPollutant).put("RGBValue", RGBValue).put("Class", Class).put("Grade", Grade).put("HealthEffect", HealthEffect).put("TakeStep", TakeStep);
	}
}
