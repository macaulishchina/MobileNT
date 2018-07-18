package com.sinoyd.environmentNT.model;

import org.json.JSONObject;

/**
 * 实况 最新小时AQI Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PortHourAQIInfo


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortHourAQIInfo extends TopHourAQIInfo {
	/** 站点编号 **/
	public String PortId;
	/** 站点名称 **/
	public String PortName;
	/** so2 **/
	public String SO2_IAQI;
	/** no2 **/
	public String NO2_IAQI;
	/** pm10 **/
	public String PM10_IAQI;
	/** co **/
	public String CO_IAQI;
	/** o3 **/
	public String O3_IAQI;
	/** pm2.5 **/
	public String PM25_IAQI;
	/** PrimaryPollutantValue **/
	public String PrimaryPollutantValue; 

	@Override
	protected void parse(JSONObject jb) throws Exception {
		super.parse(jb);
		PortId = jb.getString("PortId");
		PortName = jb.getString("PortName");
		SO2_IAQI = jb.getString("SO2_IAQI");
		NO2_IAQI = jb.getString("NO2_IAQI");
		PM10_IAQI = jb.getString("PM10_IAQI");
		PortName = jb.getString("PortName");
		CO_IAQI = jb.getString("CO_IAQI");
		O3_IAQI = jb.getString("O3_IAQI");
		PM25_IAQI = jb.getString("PM2.5_IAQI");
		PrimaryPollutantValue = jb.getString("PrimaryPollutantValue");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		put("PortId", PortId).put("PortName", PortName).put("SO2_IAQI", SO2_IAQI).put("NO2_IAQI", NO2_IAQI).put("PM10_IAQI", PM10_IAQI).put("CO_IAQI", CO_IAQI).put("O3_IAQI", O3_IAQI).put("PM25_IAQI", PM25_IAQI).put("PrimaryPollutantValue",PrimaryPollutantValue);
		return super.putJSON();
	}
}
