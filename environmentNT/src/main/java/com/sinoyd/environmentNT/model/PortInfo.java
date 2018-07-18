package com.sinoyd.environmentNT.model;

import com.lidroid.xutils.db.annotation.Table;
import com.sinoyd.environmentNT.json.JSONModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 站点信息 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PortInfo

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@Table(name = "PortInfo")
public class PortInfo extends JSONModel {
	protected int id;
	/** 站点id **/
	public String PortId;
	/** 站点名称 **/
	public String PortName;
	/** 纬度 **/
	public String X;
	/** 经度 **/
	public String Y; // 经度
	/** 是否为水质站点 **/
	public boolean isWaterPort;
	/** 国控非国控类型 **/
	public String PortType;
	/** 地区类型 **/
	public String RegionType;
	/** 排序类型 **/
	public String orderNumber;

	public String Level;//等级
	
	public String IsOnline;//是否在线









	
	public PortInfo() {
		super();
	}

	public PortInfo(int id, String portId, String portName, String x, String y, boolean isWaterPort, String portType, String regionType, String OrderNumber,String level,String isOnline) {
		super();
		this.id = id;
		PortId = portId;
		PortName = portName;
		X = x;
		Y = y;
		PortType = portType;
		RegionType = regionType;
		orderNumber = OrderNumber;
		Level= level;
		IsOnline=isOnline;
//		this.isWaterPort = isWaterPort;
	}

	@Override
	public void parse(JSONObject jb) {
		try {
			PortId = jb.getString("PortId");
			PortName = jb.getString("PortName");
			X = jb.getString("X");
			Y = jb.getString("Y");
			PortType = jb.getString("PortType");
			RegionType = jb.getString("RegionType");
			orderNumber = jb.getString("orderNumber");
//			Level=jb.getString("Level");
//			IsOnline=jb.getString("IsOnline");
//			isWaterPort=jb.getBoolean("isWaterPort");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("PortId", PortId).put("PortName", PortName).put("X", X).put("Y", Y).put("PortType", PortType).put("RegionType", RegionType).put("orderNumber", orderNumber);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPortId() {
		return PortId;
	}

	public void setPortId(String portId) {
		PortId = portId;
	}

	public String getPortName() {
		return PortName;
	}

	public void setPortName(String portName) {
		PortName = portName;
	}

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public boolean isWaterPort() {
		return isWaterPort;
	}

	public void setWaterPort(boolean isWaterPort) {
		this.isWaterPort = isWaterPort;
	}

	public String getPortType() {
		return PortType;
	}

	public void setPortType(String portType) {
		PortType = portType;
	}

	public String getRegionType() {
		return RegionType;
	}

	public void setRegionType(String regionType) {
		RegionType = regionType;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
}
