package com.sinoyd.environmentNT;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences统一管理 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PreferencesManager


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PreferencesManager {
	public SharedPreferences shared;
	private static PreferencesManager configManager = new PreferencesManager();
	public Context context;

	private PreferencesManager() {
	}

	public static PreferencesManager getInstance() {
		return configManager;
	}

	public void setContext(Context context) {
		if (this.context == null) {
			this.context = context.getApplicationContext();
		}
		shared = context.getSharedPreferences("app_config", 0);
	}

	/**
	 * 保存SharedPreferences key-value
	 */
	public void save(String key, String value) {
		Editor editor = shared.edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * 清除SharedPreferences key-value
	 */
	public void popup(String key, String value) {
		Editor editor = shared.edit();
		editor.putString("key", "");
		editor.commit();
	}
}
