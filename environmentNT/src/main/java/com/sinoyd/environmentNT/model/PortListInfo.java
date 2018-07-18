package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 站点信息列表 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PortListInfo

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortListInfo extends JSONModel {
	public List<PortInfo> PortInfo;

	@Override
	public void parse(JSONObject jb) throws JSONException {
		if (PortInfo == null)
			PortInfo = new ArrayList<PortInfo>();
		PortInfo.clear();
		parseArray(PortInfo, jb.getJSONArray("PortInfo"), PortInfo.class);
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return put("PortInfo", PortInfo);
	}
}
