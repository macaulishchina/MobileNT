package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 水质站点 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：WaterPortInfo

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class WaterPortInfo extends JSONModel {
	/** 站点id **/
	public String PortId;
	/** 站点名称 **/
	public String PortName;
	/** 纬度 **/
	public String X;
	/** 经度 **/
	public String Y;
	/** 站点类型 **/
	public String PortType;

	@Override
	protected void parse(JSONObject jb) {
		try {
			PortId = jb.getString("PortId");
			PortName = jb.getString("PortName");
			X = jb.getString("X");
			Y = jb.getString("Y");
			PortType = jb.getString("PortType");
		}
		catch (Exception e) {
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("PortId", PortId).put("PortName", PortName).put("X", X).put("Y", Y).put("PortType", PortType);
	}
}
