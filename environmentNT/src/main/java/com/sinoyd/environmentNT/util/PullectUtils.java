package com.sinoyd.environmentNT.util;

import android.graphics.Color;
import com.sinoyd.environmentNT.R;

/**
 * 污染指数 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PullectUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PullectUtils {
	private static final String[] PULLECT_MSG_ARRAY = { "优", "良", "轻度污染", "中度污染", "重度污染", "严重污染" };
	private static final int[] PULLECT_VALUE_BG = { R.drawable.pullect_1_shape, R.drawable.pullect_2_shape, R.drawable.pullect_3_shape, R.drawable.pullect_4_shape, R.drawable.pullect_5_shape, R.drawable.pullect_6_shape };
	private static final int[] PULLECT_GIS_BG = { R.drawable.gis_1, R.drawable.gis_2, R.drawable.gis_3, R.drawable.gis_4, R.drawable.gis_5, R.drawable.gis_6 };
	private static final int[] PULLECT_LU_BG = { R.drawable.pet_1, R.drawable.pet_2, R.drawable.pet_3, R.drawable.pet_4, R.drawable.pet_5, R.drawable.pet_6 };

	public static int ValueByLevel(int pullectValue) {
		if (pullectValue <= 50) {
			return 0;
		}
		if (pullectValue > 50 && pullectValue <= 100) {
			return 1;
		}
		if (pullectValue > 100 && pullectValue <= 150) {
			return 2;
		}
		if (pullectValue > 150 && pullectValue <= 200) {
			return 3;
		}
		if (pullectValue > 200 && pullectValue <= 300) {
			return 4;
		}
		if (pullectValue > 300) {
			return 5;
		}
		return 0;
	}

	/***
	 * 得到污染的指数名称
	 * 
	 * @param pullectValue
	 * @return
	 */
	public static String getPullectMsgByValue(int pullectValue) {
		return PULLECT_MSG_ARRAY[ValueByLevel(pullectValue)];
	}

	/***
	 * 根据污染的指数获取背景图片
	 * 
	 * @param pullectValue
	 * @return
	 */
	public static int getPullectBgByValue(int pullectValue) {
		return PULLECT_VALUE_BG[ValueByLevel(pullectValue)];
	}

	/***
	 * 根据污染的指数获取GIS背景图片
	 * 
	 * @param pullectValue
	 * @return
	 */
	public static int getGISBgByValue(int pullectValue) {
		return PULLECT_GIS_BG[ValueByLevel(pullectValue)];
	}

	/***
	 * 根据污染的指数获取鹿的背景图片
	 * 
	 * @param pullectValue
	 * @return
	 */
	public static int getPetLuBgByValue(int pullectValue) {
		return PULLECT_LU_BG[ValueByLevel(pullectValue)];
	}

	public static int getColor(int pullectValue) {
		int result = Color.rgb(0, 228, 0);
		switch (ValueByLevel(pullectValue)) {
		case 0:
			result = Color.rgb(0, 228, 0);
			break;
		case 1:
			result = Color.rgb(255, 255, 0);
			break;
		case 2:
			result = Color.rgb(255, 126, 0);
			break;
		case 3:
			result = Color.rgb(255, 0, 0);
			break;
		case 4:
			result = Color.rgb(153, 0, 76);
			break;
		case 5:
			result = Color.rgb(126, 0, 35);
			break;
		default:
			break;
		}
		return result;
	}

	public static final int[] WATER_RECT_BGS = { R.drawable.rect_blue, R.drawable.rect_blue, R.drawable.rect_green, R.drawable.rect_yellow, R.drawable.rect_orange, R.drawable.rect_red };

	public static int getWaterBgByLevel(int level) {
		return WATER_RECT_BGS[level];
	}
}
