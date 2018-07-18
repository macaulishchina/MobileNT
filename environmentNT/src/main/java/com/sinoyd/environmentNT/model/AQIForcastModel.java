package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 趋势分析信息
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * @类型名称：AQIForcastModel

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class AQIForcastModel extends JSONModel {
	public String DateTime; // 时间
	public String TimeSpan; // 时间节点
	public String Class; // 暂时没用
	public String PrimaryPollutant; // 值
	public String AQI; // aqi值
	public String DiffusionCondition;
	public String HealthEffect; // 适合
	public String TakeStep; // 预报信息

	public AQIForcastModel(JSONObject jsonObject) {
		super();
		try {
			parse(jsonObject);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void parse(JSONObject jb) throws Exception {
		this.DateTime = jb.optString("DateTime");
		this.TimeSpan = jb.optString("TimeSpan");
		this.Class = jb.optString("Class");
		this.PrimaryPollutant = jb.optString("PrimaryPollutant");
		this.AQI = jb.optString("AQI");
		this.DiffusionCondition = jb.optString("DiffusionCondition");
		this.HealthEffect = jb.optString("HealthEffect");
		this.TakeStep = jb.optString("TakeStep");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
