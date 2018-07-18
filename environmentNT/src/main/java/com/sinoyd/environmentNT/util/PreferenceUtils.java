package com.sinoyd.environmentNT.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/***
 * Preference工具类
 * 
 * @author smz
 * 
 */
public class PreferenceUtils {
	public static void setData(Context context, String key, String value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getData(Context context, String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getString(key, "");
	}

	public static void saveValue(SharedPreferences sharedPre, String key, String value) {
		SharedPreferences.Editor editor = sharedPre.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static int getInteger(Context context, String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getInt(key, 0);
	}

	public static void saveInteger(Context context, String key, int value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static String getValue(SharedPreferences sharedPre, String key) {
		return sharedPre.getString(key, "");
	}

	public static void saveBoolean(Context context, String key, boolean value) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean getBoolean(Context context, String key) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
		return preferences.getBoolean(key, false);
	}
}
