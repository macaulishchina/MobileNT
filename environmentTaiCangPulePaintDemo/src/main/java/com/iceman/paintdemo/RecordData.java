package com.iceman.paintdemo;

import java.util.ArrayList;

public class RecordData {
	public float span = 0;
	/** Y轴被划分的单元间隔 */
	public YField[] yFields;
	/** 数据内容 */
	public ArrayList<DataItem[]> dataList = new ArrayList<RecordData.DataItem[]>();
	/** 多条数据时,每条数据的名字 */
	public ArrayList<String> dataListNames = new ArrayList<String>();
	/** 额外的Y轴阶段 */
	public YField extraStage;

	/**
	 * Y轴被划分的单元间隔
	 * 
	 * @author david
	 * 
	 */
	public static class YField {
		/** 底部标识文字 */
		public String bottomState = "";
		/** 底部标识符号 */
		public String bottomChar = "";
		/** 表内说明文字 */
		public String introduce = "";
		/** 阶段跨度 */
		public float span = 0;
	}

	/**
	 * 柱状图的柱形数据源
	 * 
	 * @author david
	 * 
	 */
	public static class DataItem {
		/** 是否显示值 **/
		public boolean hideValue;
		/** 名字 **/
		public String name;
		/** AQI值 **/
//		public int data;
 		public float data;
		/** 浓度值 **/
		public String value;
		/** 所属污染等级 **/
		public int interval;
	}
}
