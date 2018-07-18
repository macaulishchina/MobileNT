package com.sinoyd.environmentNT.json;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * json工具适用对象模型，所有相关JSON数据对象都要继承该对象 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：JSONModel


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract class JSONModel {
	private JSONObject jsonObject;

	protected void createJSONObj() {
		if (jsonObject == null)
			jsonObject = new JSONObject();
	}

	/**
	 * 对象转化方法
	 * 
	 * @param jb
	 * @throws Exception
	 */
	protected abstract void parse(JSONObject jb) throws Exception;

	protected <T extends JSONModel> void parseArray(List<T> list, JSONArray array, Class<T> clazz) throws JSONException {
		if (list == null)
			list = new ArrayList<T>();
		list.clear();
		for (int i = 0; i < array.length(); i++) {
			list.add(JSON.parse(array.getJSONObject(i).toString(), clazz));
		}
	}

	protected JSONObject put(String name, boolean value) throws Exception {
		createJSONObj();
		jsonObject.put(name, value);
		return jsonObject;
	}

	protected JSONObject put(String name, double value) throws Exception {
		createJSONObj();
		jsonObject.put(name, value);
		return jsonObject;
	}

	protected JSONObject put(String name, int value) throws Exception {
		createJSONObj();
		jsonObject.put(name, value);
		return jsonObject;
	}

	protected JSONObject put(String name, long value) throws Exception {
		createJSONObj();
		jsonObject.put(name, value);
		return jsonObject;
	}

	protected JSONObject put(String name, Object value) throws Exception {
		createJSONObj();
		jsonObject.put(name, value);
		return jsonObject;
	}

	protected <T extends JSONModel> JSONObject putArray(String name, List<T> list) throws Exception {
		createJSONObj();
		JSONArray array = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			array.put(list.get(i).putJSON());
		}
		jsonObject.put(name, array);
		return jsonObject;
	}

	/**
	 * put JSON value
	 * 
	 * @return
	 * @throws Exception
	 */
	protected abstract JSONObject putJSON() throws Exception;

	/**
	 * 获取json字符串
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toJSON() throws Exception {
		return putJSON().toString();
	}
}
