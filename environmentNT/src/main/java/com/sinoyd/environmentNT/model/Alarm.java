package com.sinoyd.environmentNT.model;

import com.lidroid.xutils.db.annotation.Table;
import com.sinoyd.environmentNT.json.JSONModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 在线情况 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：Online


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@Table(name = "Alarm")
public class Alarm extends JSONModel {
	public ArrayList<AlarmItem> values = new ArrayList<AlarmItem>();

	@Table(name = "AlarmItem")
	public static class AlarmItem {
		public int id;
		/** 站点名称 **/
		public String PortName;
		/** 报警时间 **/
		public String AlarmTime;
		/** 报警内容 **/
		public String AlarmContent;
		/** 站点Id **/
		public String PortId;
		/** 报警时间长整型 **/
		public long AlarmLongTime;
		/** 状态(0是未处理，1是已处理) **/
		public String AlarmProcess;
        /***处理结果****/
		public String HandleResult;
 
		public AlarmItem() {
		}

		public AlarmItem(JSONObject jsonObject) {
			this.PortName = jsonObject.optString("PortName");
			this.AlarmTime = jsonObject.optString("AlarmTime");
			this.AlarmContent = jsonObject.optString("AlarmContent");
			this.AlarmProcess=jsonObject.optString("AlarmProcess");
			try {
				this.HandleResult=jsonObject.optString("HandleResult");
			}
			catch (Exception e) {
				// TODO: handle exception
				this.HandleResult="";
			}
			
		}
	}

	@Override
	public void parse(JSONObject jb) throws Exception {
		JSONArray array = jb.optJSONArray("Data");
		if (values != null && values.size() > 0) {
			values.clear();
		}
		if (array != null && array.length() > 0) {
			for (int i = 0; i < array.length(); i++) {
				values.add(new AlarmItem(array.optJSONObject(i)));
			}
		}
	}

	@Override
	protected JSONObject putJSON() throws Exception {
		return null;
	}
}
