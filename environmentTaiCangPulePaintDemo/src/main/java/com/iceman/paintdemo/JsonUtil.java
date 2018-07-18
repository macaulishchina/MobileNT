package com.iceman.paintdemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.iceman.paintdemo.RecordData.DataItem;
import com.iceman.paintdemo.RecordData.YField;

public class JsonUtil {
	/** 水质等级罗马字符 **/
	public static String[] LUO_MA_NUMBER = { "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "劣V" };
	/** 水质等级数字字符 **/
	public static String[] A_LA_BO_NUMBER = { "1", "2", "3", "4", "5", "6" };
	/** 空气质量AQI等级名称 **/
	private static final String[] AIR_LEVEL = { "优", "良", "轻度污染", "中度污染",
			"重度污染", "严重污染" };

	/** 解析单条数据 */
	public static RecordData getRecordItem(String str) {
		try {
			RecordData record = new RecordData();
			JSONObject baseObj = new JSONObject(str);
			JSONArray stageArray = baseObj.getJSONArray("stages");
			YField[] fields = new YField[stageArray.length()];
			JSONObject stageObj;
			for (int i = 0; i < stageArray.length(); i++) {
				stageObj = stageArray.getJSONObject(i);
				YField field = new YField();
				field.span = stageObj.getInt("span");
				field.bottomState = stageObj.getString("bottomstate");
				field.bottomChar = stageObj.getString("bottomchar");
				field.introduce = stageObj.getString("introduce");
				fields[i] = field;
			}
			record.yFields = fields;
			if (baseObj.has("extrastage")) {
				JSONObject extraObj = baseObj.getJSONObject("extrastage");
				YField field = new YField();
				field.bottomState = extraObj.getString("bottomstate");
				field.bottomChar = extraObj.getString("bottomchar");
				field.introduce = extraObj.getString("introduce");
				record.extraStage = field;
			}
			JSONArray dataListArray = baseObj.getJSONArray("data");
			DataItem[] data = new DataItem[dataListArray.length()];
			JSONObject dataObj;
			boolean hidePoint = false;
			int count = dataListArray.length();
			for (int i = 0; i < count; i++) {
				dataObj = dataListArray.getJSONObject(i);
				DataItem item = new DataItem();
				item.data = dataObj.getInt("data");
				item.name = dataObj.getString("yname");
				item.interval = getInterVal(item.data, record.yFields);
				if (hidePoint) {
					if (i == 1) {
						DataItem temp = data[i - 1];
						temp.data = item.data;
						data[i - 1] = temp;
					} else {
						DataItem leftTemp = data[i - 2];
						DataItem temp = data[i - 1];
						temp.data = leftTemp.data + (item.data - leftTemp.data)
								/ 2;
						data[i - 1] = temp;
					}
				}
				if (item.data == RecordView.HIDE_POINT) {
					hidePoint = true;
					item.hideValue = true;
					if (i == count - 1) {
						item.data = data[i - 1].data;
					}
				} else {
					hidePoint = false;
				}
				data[i] = item;

			}
			record.dataList.add(data);
			return record;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 解析多条数据 */
	public static RecordData getMultiRecordItem(String str) {
		try {
			RecordData record = new RecordData();
			JSONObject baseObj = new JSONObject(str);
			JSONArray stageArray = baseObj.getJSONArray("stages");
			YField[] fields = new YField[stageArray.length()];
			JSONObject stageObj;
			for (int i = 0; i < stageArray.length(); i++) {
				stageObj = stageArray.getJSONObject(i);
				YField field = new YField();
				field.span = stageObj.getInt("span");
				field.bottomState = stageObj.getString("bottomstate");
				field.bottomChar = stageObj.getString("bottomchar");
				field.introduce = stageObj.getString("introduce");
				fields[i] = field;
			}
			record.yFields = fields;

			JSONArray dataListArray = baseObj.getJSONArray("datalist");
			for (int i = 0; i < dataListArray.length(); i++) {
				JSONObject itemObj = dataListArray.getJSONObject(i);
				String listName = itemObj.getString("name");
				record.dataListNames.add(listName);
				JSONArray dataArray = itemObj.getJSONArray("data");
				DataItem[] data = new DataItem[dataArray.length()];
				JSONObject dataObj;
				for (int j = 0; j < dataArray.length(); j++) {
					dataObj = dataArray.getJSONObject(j);
					DataItem item = new DataItem();
					item.data = dataObj.getInt("data");
					item.name = dataObj.getString("yname");
					item.interval = getInterVal(item.data, record.yFields);
					data[j] = item;
				}
				record.dataList.add(data);
			}
			return record;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 计算所属的Y区间 */
	public static int getInterVal(float data, YField[] yFields) {
		int height = 0;
		for (int i = 0; i < yFields.length; i++) {
			height += yFields[i].span;
			if (data <= height) {
				return i;
			}
		}
		return 0;
	}

	public static String getJsonFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context
					.getResources().getAssets().open(fileName), "utf-8");
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			StringBuilder result = new StringBuilder();
			while ((line = bufReader.readLine()) != null) {
				result.append(line);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static YField[] getYFiledItem(String str) {
		try {
			JSONObject baseObj = new JSONObject(str);
			JSONArray stageArray = baseObj.getJSONArray("stages");
			YField[] fields = new YField[stageArray.length()];
			JSONObject stageObj;
			for (int i = 0; i < stageArray.length(); i++) {
				stageObj = stageArray.getJSONObject(i);
				YField field = new YField();
				field.span = stageObj.getInt("span");
				field.bottomState = stageObj.getString("bottomstate");
				field.bottomChar = stageObj.getString("bottomchar");
				field.introduce = stageObj.getString("introduce");
				fields[i] = field;
			}
			return fields;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static YField[] getCenterYFiledItem(float maxY) {
		float yValue = maxY * 2;
		Log.e("max", yValue + "");
		if (yValue < 50.00)
			yValue = 50f;
		int count=0;
//		if (yValue < 200) {
//			count = (int) Math.ceil(yValue / 50);
//		} else {
//			count = 4;
//		}
//		if (yValue >= 200) {
//			count++;
//		}
//		if (yValue >= 300) {
//			count++;
//		}
		if (yValue < 200f) {
			count = (int) Math.ceil(yValue / 50);
		} else if(yValue >= 200f&&yValue<=300f){
			count = 4;
		}
		else if(yValue >300f&&yValue<=500f){
			count = 5;
		}
		else if(yValue >500f){
			count=6;
		}
		YField[] fields = new YField[count];
		YField field = null;
		for (int i = 0; i < count; i++) {
			field = new YField();
//			if (i <= 4) {
//			if (i <= 3) {
//				field.span = 50;
//			} else {
//				if (count == 4) {
//					field.span = 100;
//				} else {
//					field.span = yValue - 300;
//					field.span = field.span - field.span % 50;
//				}
//			}
			
			if (i <= 3) {
				field.span = 50;
			} else {
				if (i == 4) {
					field.span = 100;
				} else if (i > 4){
					field.span = (int)yValue - 300;
					field.span = field.span - field.span % 50;
				}
			}
			
			field.bottomState = AIR_LEVEL[i];
			field.bottomChar = LUO_MA_NUMBER[i];
			fields[i] = field;
		}
		return fields;
	}

	public static YField[] getWaterField(int span) {
		YField[] fields = new YField[LUO_MA_NUMBER.length];
		YField field = null;
		for (int i = 0; i < LUO_MA_NUMBER.length; i++) {
			field = new YField();
			field.span = span;
			field.bottomChar = LUO_MA_NUMBER[i];
			fields[i] = field;
		}
		return fields;
	}

	
	public static YField[] getWaterField(Float span) {
//		YField[] fields = new YField[LUO_MA_NUMBER.length];
		YField[] fields = new YField[4];
		YField field = null;
//		for (int i = 0; i < LUO_MA_NUMBER.length; i++) {
			for (int i = 0; i < 4; i++) {
			field = new YField();
			field.span = span;
			field.bottomChar = LUO_MA_NUMBER[i];
			fields[i] = field;
		}
		return fields;
	}
	
	public static float getMaxValue(DataItem[] datas) {
		float max = 0;
		if (datas != null && datas.length >= 1) {
			float[] values = new float[datas.length];
			for (int i = 0; i < datas.length; i++) {
				values[i] = datas[i].data;
			}
			Arrays.sort(values);
			max =   values[values.length - 1];
		}
		Log.e("Max", max + "");
		return max;
	}

	public static float getMaxValue(float[] values) {
		float[] valuescopy=new float[values.length];
		for(int i=0;i<values.length;i++)
			valuescopy[i]=Math.abs(values[i]);
		Arrays.sort(values);
		Arrays.sort(valuescopy);
		
		float max = values[values.length - 1];
		float maxcopy=valuescopy[valuescopy.length - 1];
		if(max!=maxcopy)
			max=-maxcopy;
		return max;
	}

	public static YField[] getWaterField() {
		YField[] fields = new YField[LUO_MA_NUMBER.length];
		YField field = null;
		for (int i = 0; i < LUO_MA_NUMBER.length; i++) {
			field = new YField();
			field.span = 50;
			field.bottomChar = LUO_MA_NUMBER[i];
			fields[i] = field;
		}
		return fields;
	}

	/***
	 * 根据阿拉伯数字得到等级
	 * 
	 * @param number
	 * @return
	 */
	public static int getLevelByALaBoNumber(String number) {
		int index = 0;
		for (int i = 0; i < LUO_MA_NUMBER.length; i++) {
			if (A_LA_BO_NUMBER[i].equals(number)) {
				index = i;
				break;
			}
			if (number.startsWith("劣")) {
				index = LUO_MA_NUMBER.length - 1;
				break;
			}
		}
		return index;
	}

	/***
	 * 根据罗马数字得到等级
	 * 
	 * @param number
	 * @return
	 */
	public static int getLevelByLuoMaNumber(String number) {
		int index = 0;
		for (int i = 0; i < LUO_MA_NUMBER.length; i++) {
			if (LUO_MA_NUMBER[i].equals(number)) {
				index = i;
				break;
			}
			if (number.startsWith("劣")) {
				index = LUO_MA_NUMBER.length - 1;
				break;
			}
		}
		return index;
	}

	/***
	 * 根据罗马数字得到级别
	 * 
	 * @param number
	 * @return
	 */
	public static int getLevelyNumber(int number) {
		int index = 0;
		if (number <= 50 && number >= 0) {
			index = 1;
		} else if (number <= 50 && number >= 100) {
			index = 2;
		} else if (number < 100 && number >= 150) {
			index = 3;
		} else if (number < 150 && number >= 200) {
			index = 4;
		} else if (number < 200 && number >= 250) {
			index = 5;
		}
		return index;
	}

	public static String getLuoMaByLevel(int number) {
		if (number >= LUO_MA_NUMBER.length) {
			return "";
		}
		return LUO_MA_NUMBER[number];
	}

	public static YField[] getWaterCenterYFiledItem(float maxY, int count) {
		float yValue = maxY * 2;
		YField[] fields = new YField[count];
		YField field = null;
		for (int i = 0; i < count; i++) {
			field = new YField();
			field.span = yValue / count;
			field.bottomState = yValue / count + "";
			field.bottomChar = yValue / count * (i + 1) + "";
			fields[i] = field;
		}
		return fields;
	}

	public static DataItem[] formatDataItem(DataItem[] _dateItems) {
		boolean hidePoint = false;
		int count = _dateItems.length;
		for (int i = 0; i < count; i++) {
			DataItem item = _dateItems[i];
			if (hidePoint) {
				if (i == 1) {
					DataItem temp = _dateItems[i - 1];
					temp.data = item.data;
					_dateItems[i - 1] = temp;
				} else {
					DataItem leftTemp = _dateItems[i - 2];
					DataItem temp = _dateItems[i - 1];
					temp.data = leftTemp.data + (item.data - leftTemp.data) / 2;
					_dateItems[i - 1] = temp;
				}
			}
			if (item.data == RecordView.HIDE_POINT) {
				hidePoint = true;
				item.hideValue = true;
				if (i == count - 1) {
					item.data = _dateItems[i - 1].data;
				}
			} else {
				hidePoint = false;
			}
			_dateItems[i] = item;
		}
		return _dateItems;
	}
	
	
	  /** 
     * 使用java正则表达式去掉多余的.与0 
     * @param s 
     * @return  
     */  
    public static String subZeroAndDot(String s){  
        if(s.indexOf(".") > 0){  
            s = s.replaceAll("0+?$", "");//去掉多余的0  
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return s;  
    }  
}
