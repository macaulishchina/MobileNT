package com.sinoyd.environmentNT.model;

import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 优良月份实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：YouLiangMonths
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class YouLiangMonths extends JSONModel {
	public String Month;
	public Integer Class1;
	public Integer Class2;
	public Integer Class3;
	public Integer Class4;
	public Integer Class5;
	public Integer Class6;

	@Override
	public void parse(JSONObject jb) throws Exception {
		// TODO Auto-generated method stub
		Month = jb.getString("Month");
		Class1 = jb.getInt("Class1");
		Class2 = jb.getInt("Class2");
		Class3 = jb.getInt("Class3");
		Class4 = jb.getInt("Class4");
		Class5 = jb.getInt("Class5");
		Class6 = jb.getInt("Class6");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return put("Month", Month).put("Class1", Class1).put("Class2", Class2).put("Class3", Class3).put("Class4", Class4).put("Class5", Class5).put("Class6", Class6);
	}
}
