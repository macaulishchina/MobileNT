package com.sinoyd.environmentNT.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sinoyd.environmentNT.json.JSONModel;

/**
 * 实时水质的最新因子浓度等级值 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：RealtimeWaterWQ
 * @创建人：张津明

 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class RealtimeWaterWQ extends JSONModel {
	/** 最新时间 **/
	public String dateTime;
	/** 站点id **/
	public String portId;
	/** 实时水质最新浓度因子 **/
	public List<RealtimeWaterWQDetail> mRealtimeWaterWQDetailList;

	@Override
	public void parse(JSONObject jb) throws Exception {
		// TODO Auto-generated method stub
		JSONArray jsonArray = jb.optJSONArray("PortHourWQ");
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj2 = jsonArray.optJSONObject(i);
				this.dateTime = jsonObj2.optString("DateTime");
				this.portId = jsonObj2.optString("PortId");
				this.mRealtimeWaterWQDetailList = new ArrayList<RealtimeWaterWQDetail>();
				JSONArray array = jsonObj2.optJSONArray("Value");
				if (array != null && array.length() > 0) {
					RealtimeWaterWQDetail model = null;
					for (int j = 0; j < array.length(); j++) {
						model = new RealtimeWaterWQDetail();
						model.parse(array.optJSONObject(j));
						this.mRealtimeWaterWQDetailList.add(model);
					}
				}
			}
			if(jsonArray.length()==0)
			 
					this.mRealtimeWaterWQDetailList.clear();
			 
		}
		
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
