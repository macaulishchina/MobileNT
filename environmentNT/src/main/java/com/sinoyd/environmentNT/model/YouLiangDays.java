package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 优良天数实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：YouLiangDays
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class YouLiangDays extends JSONModel {
	/** 类别名称：you、liang、qingdu、zhongdu、zhongdu2、yanzhong **/
	public String ClassName;
	/** 天数 **/
	public Integer DayCount;
	/** 百分比 **/
	public Double Percentage;

	@Override
	public void parse(JSONObject jb) throws Exception {
		// TODO Auto-generated method stub
		ClassName = jb.getString("Class");
		DayCount = jb.getInt("Value");
		Percentage = jb.getDouble("Percentage");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return put("Class", ClassName).put("Value", DayCount).put("Percentage", Percentage);
	}
}
