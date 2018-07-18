package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 水质站点实体 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：WaterPortInfoModel

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class WaterPortInfoModel extends JSONModel {
	/** 水质站点实体 **/
	public List<WaterPortInfo> PortInfo;

	@Override
	protected void parse(JSONObject jb) {
		try {
			if (PortInfo == null)
				PortInfo = new ArrayList<WaterPortInfo>();
			PortInfo.clear();
			parseArray(PortInfo, jb.getJSONArray("PortInfo"), WaterPortInfo.class);
		}
		catch (Exception e) {
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return putArray("PortInfo", PortInfo);
	}

	public Map<String, List<WaterPortInfo>> getTypePortInfo() {
		Map<String, List<WaterPortInfo>> map = null;
		if (PortInfo != null && PortInfo.size() > 0) {
			map = new HashMap<String, List<WaterPortInfo>>();
			for (WaterPortInfo info : PortInfo) {
				add(info, map);
			}
		}
		return map;
	}

	private void add(WaterPortInfo info, Map<String, List<WaterPortInfo>> map) {
		if (map.containsKey(info.PortType)) {
			map.get(info.PortType).add(info);
		}
		else {
			List<WaterPortInfo> list = new ArrayList<WaterPortInfo>();
			list.add(info);
			map.put(info.PortType, list);
		}
	}
}
