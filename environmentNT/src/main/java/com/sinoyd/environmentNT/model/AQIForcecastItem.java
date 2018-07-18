package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

public class AQIForcecastItem extends JSONModel {
	// {
	// "DateTime" : "2013-12-12 09:00",
	// "TimeSpan" : "8:00-20:00",
	// "Class" : "重度污染",
	// "PrimaryPollutant" : "PM2.5",
	// "AQI" : "280-300",
	// "DiffusionCondition" : "12日白天，我市受低压系统影响，风力较小，湿度较大，污染物扩散条件不利。",
	// "HealthEffect" : "空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响",
	// "TakeStep" : "极少数异常敏感人群应减少户外活动"
	// }
	public String DateTime; // 日期
	public String TimeSpan; // 时间段
	public String Class; // 污染情况
	public String PrimaryPollutant; // 污染数据
	public String AQI; // aqi值
	public String DiffusionCondition; // 说明
	public String HealthEffect; // 介绍
	public String TakeStep; // 说明

	@Override
	protected void parse(JSONObject jb) throws Exception {
		DateTime = jb.getString("DateTime");
		TimeSpan = jb.getString("TimeSpan");
		Class = jb.getString("Class");
		PrimaryPollutant = jb.getString("PrimaryPollutant");
		AQI = jb.getString("AQI");
		DiffusionCondition = jb.getString("DiffusionCondition");
		HealthEffect = jb.getString("HealthEffect");
		TakeStep = jb.getString("TakeStep");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("DateTime", DateTime).put("TimeSpan", TimeSpan).put("Class", Class).put("PrimaryPollutant", PrimaryPollutant).put("AQI", AQI).put("DiffusionCondition", DiffusionCondition).put("HealthEffect", HealthEffect).put("TakeStep", TakeStep);
	}
}
