package com.sinoyd.environmentNT.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 广播管理类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：BracaseUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class BracaseUtils {
	/** 增加站点广播标识符 **/
	public static final String ADD_ACTION = "add.action";
	/** 移除站点广播标识符 **/
	public static final String REMOVE_ACTION = "remove.action";

	/** 增加站点广播标识符 **/
	public static final String ADD_WATER_ACTION = "add.water.action";
	/** 移除站点广播标识符 **/
	public static final String REMOVE_WATER_ACTION = "remove.water.action";
	/**
	 * 通知更新站点
	 * 
	 * @param intent
	 */
	protected static void NotifyUpdate(Intent intent) {
	}

	/**
	 * 发送广播
	 * 
	 * @param mContext 上下文
	 * @param intent
	 */
	public static void startUpdate(Context mContext, Intent intent) {
		mContext.sendOrderedBroadcast(intent,null);
	}

	/**
	 * 注册广播(添加站点、移除站点)
	 * 
	 * @param mContext 上下文
	 * @param receiver 广播接收器
	 */
	public static void register(Context mContext, BroadcastReceiver receiver,int Priority) {
		IntentFilter filter = new IntentFilter(ADD_ACTION);
		filter.addAction(REMOVE_ACTION);
		filter.setPriority(Priority);
		mContext.registerReceiver(receiver, filter);
	}


	/**
	 * 注册广播(添加站点、移除站点)
	 *
	 * @param mContext 上下文
	 * @param receiver 广播接收器
	 */
	public static void registerWater(Context mContext, BroadcastReceiver receiver,int Priority) {
		IntentFilter filter = new IntentFilter(ADD_WATER_ACTION);
		filter.addAction(REMOVE_WATER_ACTION);
		filter.setPriority(Priority);
		mContext.registerReceiver(receiver, filter);
	}
	/***
	 * 取消注册广播
	 * 
	 * @param mContext
	 * @param receiver
	 */
	public static void unregister(Context mContext, BroadcastReceiver receiver) {
		mContext.unregisterReceiver(receiver);
	}
}
