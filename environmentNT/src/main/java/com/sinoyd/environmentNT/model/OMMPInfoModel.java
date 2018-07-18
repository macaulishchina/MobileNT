package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.lidroid.xutils.db.annotation.Table;
import com.sinoyd.environmentNT.json.JSONModel;

 
/**
 * Copyright (c) 2016 江苏远大信息股份有限公司
 * @类型名称：OMMPInfoModel
 * @创建人：
 * @创建日期：2016年2月25日
 * @维护人员：
 * @维护日期：
 * @功能摘要： 
 */
@Table(name = "OMMPInfoModel")
public class OMMPInfoModel extends JSONModel {
	 
	public  int id;//唯一标识
	public String StationName; //测点
	public String TaskName; //任务名称
	public String Status; // 状态
	public String PlanfinishDate; // 计划完成时间
	public String RealfinishDate; // 实际完成时间
	public String OperateUser;//执行人
 
	public OMMPInfoModel() {
		super();
		 
	}
	
	public OMMPInfoModel(JSONObject jsonObject) {
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
		this.StationName = jb.optString("StationName");
		this.TaskName = jb.optString("TaskName");
		this.Status = jb.optString("Status");
		this.PlanfinishDate = jb.optString("RealfinishDate");
		this.OperateUser = jb.optString("OperateUser");
		this.RealfinishDate = jb.optString("RealfinishDate");
	 
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
