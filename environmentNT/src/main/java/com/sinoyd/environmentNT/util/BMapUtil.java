package com.sinoyd.environmentNT.util;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * 百度地图工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：BMapUtil


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@SuppressLint("UseSparseArrays")
public class BMapUtil {
	private static HashMap<Integer, SoftReference<Drawable>> imageCache;
	static {
		imageCache = new HashMap<Integer, SoftReference<Drawable>>();
	}

	@SuppressWarnings("deprecation")
	public static Drawable getDrawableFromCache(Context context, int resId) {
		Drawable d = null;
		if (imageCache.get(resId) != null) {
			SoftReference<Drawable> softReference = imageCache.get(resId);
			d = softReference.get();
		}
		else {
			d = new BitmapDrawable(readBitMap(context, resId));
			imageCache.put(resId, new SoftReference<Drawable>(d));
		}
		return d;
	}

	private static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	public static Drawable getDrawableFormView(View view) {
		@SuppressWarnings("deprecation")
		Drawable drawable = new BitmapDrawable(getBitmapFromView(view));
		return drawable;
	}

	public static void removeCache() {
		imageCache.clear();
	}
}
