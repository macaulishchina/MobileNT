package com.sinoyd.environmentNT.util;

/**
 * 园工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：QuadrantUtil


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class QuadrantUtil {
	/***
	 * 度数转换
	 * 
	 * @param angle
	 * @return
	 */
	public static int getQuadrantScale(double angle) {
		if (angle > 90 && angle < 180) {
			return 1;
		}
		else if (angle > 180 && angle < 270) {
			return 2;
		}
		else if (angle > 270 && angle < 360) {
			return 3;
		}
		else if ((angle > 0 && angle < 90) || (angle - 360 > 0 && angle - 360 < 90)) {
			return 4;
		}
		return 1;
	}
}
