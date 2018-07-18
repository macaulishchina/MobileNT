package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 单位小时空气指数 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：TopHourAQIInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class TopHourAQIInfo extends JSONModel {
	/** 日期 **/
	public String DateTime;
	/** aqi **/
	public String AQI;
	/** 污染指数 **/
	public String PrimaryPollutant;
	/** 颜色值 **/
	public String RGBValue;
	/** 污染说明 **/
	public String Class;
	/** 级别 **/
	public String Grade;
	/** 说明 **/
	public String HealthEffect;
	/** 步长 **/
	public String TakeStep;

	@Override
	protected void parse(JSONObject jb) throws Exception {
		DateTime = jb.getString("DateTime");
		AQI = jb.getString("AQI");
		PrimaryPollutant = jb.getString("PrimaryPollutant");
		Class = jb.getString("Class");
		Grade = jb.getString("Grade");
		RGBValue = jb.getString("RGBValue");
		HealthEffect = jb.getString("HealthEffect");
		TakeStep = jb.getString("TakeStep");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("DateTime", DateTime).put("AQI", AQI).put("PrimaryPollutant", PrimaryPollutant).put("Class", Class).put("Grade", Grade).put("RGBValue", RGBValue).put("HealthEffect", HealthEffect).put("TakeStep", TakeStep);
	}
}
