package com.sinoyd.environmentNT.data;

import java.util.ArrayList;
import java.util.List;
import com.sinoyd.environmentNT.model.PortInfo;

/***
 * 站点缓存
 * 
 * @author smz
 * 
 */
public class PortCacheUtils {
	private static List<PortInfo> mWaterPortList = new ArrayList<PortInfo>();
	private static List<PortInfo> mAirPortList = new ArrayList<PortInfo>();

	/***
	 * 添加一个水站点
	 * 
	 * @param model
	 */
	public static void addWaterPortInfo(PortInfo model) {
		if (mWaterPortList == null) {
			mWaterPortList = new ArrayList<PortInfo>();
		}
		mWaterPortList.add(model);
	}

	/***
	 * 添加一个气站点
	 * 
	 * @param model
	 */
	public static void addAirPortInfo(PortInfo model) {
		if (mAirPortList == null) {
			mAirPortList = new ArrayList<PortInfo>();
		}
		mAirPortList.add(model);
	}

	/***
	 * 清除水站点缓存
	 */
	public static void clearWaterModel() {
		if (mWaterPortList != null) {
			mWaterPortList.clear();
		}
	}

	/**
	 * 清除气站点缓存
	 */
	public static void clearAirModel() {
		if (mAirPortList != null) {
			mAirPortList.clear();
		}
	}

	/***
	 * 取水站点缓存
	 * 
	 * @return
	 */
	public static List<PortInfo> getWaterPortList() {
		return mWaterPortList;
	}

	public static void setWaterPortList(List<PortInfo> mWaterPortList) {
		PortCacheUtils.mWaterPortList = mWaterPortList;
	}

	/***
	 * 取气站点缓存
	 * 
	 * @return
	 */
	public static List<PortInfo> getAirPortList() {
		return mAirPortList;
	}

	public static void setAirPortList(List<PortInfo> mAirPortList) {
		PortCacheUtils.mAirPortList = mAirPortList;
	}

	/***
	 * 判断水站点缓存是否为空
	 * 
	 * @return
	 */
	public static boolean waterIsEmpty() {
		if (mWaterPortList == null || mWaterPortList.size() < 1) {
			return true;
		}
		return false;
	}

	/***
	 * 判断气站点缓存是否为空
	 * 
	 * @return
	 */
	public static boolean airIsEmpty() {
		if (mAirPortList == null || mAirPortList.size() < 1) {
			return true;
		}
		return false;
	}
}
