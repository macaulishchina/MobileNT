package com.sinoyd.environmentNT.database;

import com.lidroid.xutils.db.annotation.Table;

/**
 * 站点信息 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PositionDBModel

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@Table(name = "position")
public class PositionDBModel extends DBBaseModel {


	/** 站点id **/
	private String portId;
	/** 站点编号 **/
	private String portName;
	/** 纬度 **/
	private String x;
	private String y; // 经度
	private String SO2_IAQI; // so2
	private String NO2_IAQI; // no2
	private String PM10_IAQI; // pm10
	private String CO_IAQI; // co
	private String O3_IAQI; // o3
	private String PM25_IAQI; // pm2.5
	private String DateTime;// 时间
	private String AQI; // aqi指数
	private String PrimaryPollutant; // 提示
	private String RGBValue; // 颜色代码
	private String pClass; // 分级信息
	private String Grade; // 分级
	private String HealthEffect; // 说明
	private String TakeStep; // 说明

	public String getDateTime() {
		return DateTime;
	}

	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}

	public String getAQI() {
		return AQI;
	}

	public void setAQI(String aQI) {
		AQI = aQI;
	}

	public String getPrimaryPollutant() {
		return PrimaryPollutant;
	}

	public void setPrimaryPollutant(String primaryPollutant) {
		PrimaryPollutant = primaryPollutant;
	}

	public String getRGBValue() {
		return RGBValue;
	}

	public void setRGBValue(String rGBValue) {
		RGBValue = rGBValue;
	}

	public String getpClass() {
		return pClass;
	}

	public void setpClass(String pClass) {
		this.pClass = pClass;
	}

	public String getGrade() {
		return Grade;
	}

	public void setGrade(String grade) {
		Grade = grade;
	}

	public String getHealthEffect() {
		return HealthEffect;
	}

	public void setHealthEffect(String healthEffect) {
		HealthEffect = healthEffect;
	}

	public String getTakeStep() {
		return TakeStep;
	}

	public void setTakeStep(String takeStep) {
		TakeStep = takeStep;
	}

	public String getSO2_IAQI() {
		return SO2_IAQI;
	}

	public void setSO2_IAQI(String sO2_IAQI) {
		SO2_IAQI = sO2_IAQI;
	}

	public String getNO2_IAQI() {
		return NO2_IAQI;
	}

	public void setNO2_IAQI(String nO2_IAQI) {
		NO2_IAQI = nO2_IAQI;
	}

	public String getPM10_IAQI() {
		return PM10_IAQI;
	}

	public void setPM10_IAQI(String pM10_IAQI) {
		PM10_IAQI = pM10_IAQI;
	}

	public String getCO_IAQI() {
		return CO_IAQI;
	}

	public void setCO_IAQI(String cO_IAQI) {
		CO_IAQI = cO_IAQI;
	}

	public String getO3_IAQI() {
		return O3_IAQI;
	}

	public void setO3_IAQI(String o3_IAQI) {
		O3_IAQI = o3_IAQI;
	}

	public String getPM25_IAQI() {
		return PM25_IAQI;
	}

	public void setPM25_IAQI(String pM25_IAQI) {
		PM25_IAQI = pM25_IAQI;
	}

	public PositionDBModel() {
	}

	public PositionDBModel(String portName, String portId, String x, String y) {
		this.portName = portName;
		this.portId = portId;
		this.x = x;
		this.y = y;
	}

	public String getPortId() {
		return portId;
	}

	public String getPortName() {
		return portName;
	}

	public String getX() {
		return x;
	}

	public String getY() {
		return y;
	}

	public void setPortId(String portId) {
		this.portId = portId;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public void setX(String x) {
		this.x = x;
	}

	public void setY(String y) {
		this.y = y;
	}
}
