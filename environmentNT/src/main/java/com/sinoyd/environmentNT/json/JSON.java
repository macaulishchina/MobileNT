package com.sinoyd.environmentNT.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON工具类 <br/>
 * PortListInfo listInfo = new PortListInfo();<br />
 * List<PortInfo> portInfo = new ArrayList<PortInfo>(); <br />
 * for (int i = 0; i < 4; i++) { <br />
 * &nbsp&nbsp&nbsp&nbsp PortInfo info = new PortInfo(); <br />
 * &nbsp&nbsp&nbsp&nbsp info.PortId = "" + i; <br />
 * &nbsp&nbsp&nbsp&nbsp info.PortName = "port" + i;<br />
 * &nbsp&nbsp&nbsp&nbsp info.X = "" + (12.3 * (i + 1)); <br />
 * &nbsp&nbsp&nbsp&nbsp info.Y = "465.32";<br />
 * &nbsp&nbsp&nbsp&nbsp portInfo.add(info); <br />
 * }<br />
 * listInfo.PortInfo = portInfo; <br />
 * String json = JSON.toJSON(listInfo); <br />
 * System.out.println("json:" + json); <br />
 * PortListInfo jsonObj = JSON.parse(json, PortListInfo.class);<br />
 * System.out.println("obj:"+jsonObj.PortInfo);
 * 
 * @author zz
 * 
 */
public final class JSON {
	/**
	 * json字符串转对象
	 * 
	 * @param json
	 * @param clazz
	 * @return
	 */
	public static <T extends JSONModel> T parse(String json, Class<T> clazz) {
		if (json == null || "".equals(json.trim()))
			return null;
		T t = null;
		try {
			t = clazz.newInstance();
			t.parse(new JSONObject(json));
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 对象转json字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String toJSON(JSONModel obj) {
		if (obj == null)
			return "{}";
		try {
			return obj.toJSON();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "{}";
		}
	}
}
