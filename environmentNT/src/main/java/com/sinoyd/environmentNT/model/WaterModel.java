package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 水质模型 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：WaterModel


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class WaterModel extends JSONModel {
	/** 优 **/
	public String ClaName;
	/** 高锰酸盐指数,氨氮,总磷 **/
	public String MainPollute;
	/** 28 **/
	public String PortId;
	/** 庙泾河二水厂 **/
	public String PortName;
	/** 环保局自动站 **/
	public String PortType;
	/** Ⅱ **/
	public String WQ;
	/** 120.9180 **/
	public String X;
	/** 31.4000 **/
	public String Y;

	@Override
	public void parse(JSONObject jb) throws Exception {
		this.ClaName = jb.optString("Class");
		this.MainPollute = jb.optString("MainPollute");
		this.PortId = jb.optString("PortId");
		this.PortName = jb.optString("PortName");
		this.PortType = jb.optString("PortType");
		this.WQ = jb.optString("WQ");
		this.X = jb.optString("X");
		this.Y = jb.optString("Y");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
