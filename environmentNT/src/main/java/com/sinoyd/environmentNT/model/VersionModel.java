package com.sinoyd.environmentNT.model;

import org.json.JSONArray;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 更新版本实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：VersionModel

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class VersionModel extends JSONModel {
	public String verName; // 版本名称
	public int verCode; // 版本号码
	public String verDesc; // 说明
	public String verPath; // 版本下载路径

	@Override
	public void parse(JSONObject jb) throws Exception {
		JSONArray array = jb.optJSONArray("Version");
		JSONObject jsonObject = array.optJSONObject(0);
		this.verName = jsonObject.optString("verName");
		this.verCode = jsonObject.optInt("verCode");
		this.verDesc = jsonObject.optString("verDesc");
		this.verPath = jsonObject.optString("verPath");
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
