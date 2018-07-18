package com.sinoyd.environmentNT.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.sinoyd.environmentNT.R;

/**
 * 记录及设置终端与用户的绑定情况 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：SystemUtil


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class SystemUtil {
	/** 得到分辨率高度 */
	public static int heightPs = -1;
	/** 得到分辨率宽度 */
	public static int widthPs = -1;
	/** 得到屏幕密度 */
	public static int densityDpi = -1;
	/** 得到X轴密度 */
	public static float Xdpi = -1;
	/** 得到Y轴密度 */
	public static float Ydpi = -1;
	public static int versionCode;
	public static String versionName;
	/**
	 * 数据库的版本号
	 * 
	 * 使用SystemVersionCode的前3位作为数据库版本号的标志 每次数据库需要升级时 使前3位的值加1，后3位的值也要加1
	 * 如：100000--》101001
	 * 
	 * 后面3位在每发布一个新版本的时候都要加1（数据库不需要升级时则前3位不加1） 如：100000--》100001
	 * */
	public static int dbVersionCode;

	/***
	 * 得到手机的屏幕基本信息
	 * 
	 * @param context
	 */
	public static void getScreen(Activity context) {
		DisplayMetrics metrics = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		heightPs = metrics.heightPixels;
		widthPs = metrics.widthPixels;
		densityDpi = metrics.densityDpi;
		Xdpi = metrics.xdpi;
		Ydpi = metrics.ydpi;
		getVersion(context);
		Log.i("手机分辨率", "分辨率：" + widthPs + "X" + heightPs + "    屏幕密度：" + densityDpi + "    宽高密度：" + Xdpi + "X" + Ydpi);
	}

	/***
	 * 获取客户端版本
	 * 
	 * @param context
	 * @return
	 */
	public static void getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
			versionName = info.versionName;
			versionCode = info.versionCode;
		}
		catch (Exception e) {
			Log.e("version", "获取版本失败");
			e.printStackTrace();
		}
	}

	/***
	 * 获取手机model
	 */
	public static String getPhoneMode() {
		return getDeviceName();
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		String andvoidVersion = Build.VERSION.RELEASE;
		if (model.startsWith(manufacturer)) {
			return capitalize(model) + " @ " + andvoidVersion;
		}
		else {
			return capitalize(manufacturer) + " " + model + " @ " + andvoidVersion;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		}
		else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	/**
	 * 把密度dip单位转化为像数px单位
	 * 
	 * @param context
	 * @param dip
	 * @return
	 */
	public static int dipToPx(Context context, int dip) {
 		float scale = context.getResources().getDisplayMetrics().density;
 		return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
		 
		 
	 
	}

	/***
	 * 把像数px转化为密度dip单位
	 * 
	 * @param context
	 * @param px
	 * @return
	 */
	public static int pxToDip(Context context, int px) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (px * scale + 0.5f * (px >= 0 ? 1 : -1));
	}

	/**
	 * 是否已经创建了快捷方式
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasShortcut(Context context) {
		boolean isInstallShortcur = false;
		final String AUTHORITY = "com.android.launcher.settings";
		final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/favorites?notify=true");
		Cursor c = context.getContentResolver().query(CONTENT_URI, new String[] { "title", "iconResource" }, "title=?", new String[] { context.getString(R.string.app_name).trim() }, null);
		if (null != c && c.getCount() > 0) {
			isInstallShortcur = true;
		}
		return isInstallShortcur;
	}

	/**
	 * 检测某个apk是否已经安装
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean checkApkExist(Context context, String packageName) throws NameNotFoundException {
		if (packageName == null || "".equals(packageName))
			return false;
		return true;
	}

	/**
	 * 开启别的apk
	 * 
	 * @param context
	 * @param packageName
	 */
	public static void startOtherApk(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return;
		}
		try {
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			context.startActivity(intent);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
