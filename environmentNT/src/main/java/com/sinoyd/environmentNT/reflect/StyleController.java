package com.sinoyd.environmentNT.reflect;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;

/**
 * 样式工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：StyleController


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class StyleController {
	private static final int[] attrs = { R.attr.page_bg, R.attr.more_item_bg, R.attr.aqi_select_btn, R.attr.line, R.attr.dialog_bg, R.attr.dialog_btn, R.attr.first_pullect_value_bg, R.attr.city_info_line, R.attr.health_prompt_bg, R.attr.aqi_chart_bg, R.attr.gis_header_bg, R.attr.time, };
	private int[] resourceIds = new int[attrs.length];
	private static StyleController controller;

	private StyleController() {
		super();
	}

	/***
	 * 单例
	 * 
	 * @return
	 */
	private static StyleController getInstance() {
		if (null == controller) {
			controller = new StyleController();
		}
		return controller;
	}

	/***
	 * 设置主题样式
	 * 
	 * @param context
	 * @param themeId
	 */
	private void setTheme(Context context, int themeId) {
		TypedArray typedArray = context.obtainStyledAttributes(themeId, attrs);
		for (int i = 0; i < resourceIds.length; i++) {
			resourceIds[i] = typedArray.getResourceId(i, -1);
		}
		typedArray.recycle();
	}

	/***
	 * 获取一个id
	 * 
	 * @param attrId
	 * @return
	 */
	private int getResourceIdByAttrId(int attrId) {
		int index = 0;
		for (int i = 0; i < attrs.length; i++) {
			if (attrId == attrs[i]) {
				index = i;
				break;
			}
		}
		return resourceIds[index];
	}

	/***
	 * 根据styleid获取数据来源
	 * 
	 * @param attrId
	 * @return
	 */
	public static int getResIdByAttrId(int attrId) {
		return StyleController.getInstance().getResourceIdByAttrId(attrId);
	}

	/***
	 * 根据图片样式图片资源
	 * 
	 * @param attrId
	 * @return
	 */
	public static Drawable getDrawableByAttrId(int attrId) {
		int id = StyleController.getInstance().getResourceIdByAttrId(attrId);
		return MyApplication.mContext.getResources().getDrawable(id);
	}

	/***
	 * 更新view的样式
	 * 
	 * @param view
	 * @param attrId
	 */
	public static void updateViewBgAttrId(View view, int attrId) {
		int resId = getResIdByAttrId(attrId);
		updateViewBg(view, resId);
	}

	/***
	 * 更新view的背景
	 * 
	 * @param view
	 * @param resId
	 */
	public static void updateViewBg(View view, int resId) {
		// Drawable drawable = view.getBackground();
		view.setBackgroundResource(resId);
		// if(drawable != null){
		// drawable.setCallback(null);
		// if(drawable instanceof BitmapDrawable){
		// BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		// Bitmap bitmap = bitmapDrawable.getBitmap();
		// if(!bitmap.isRecycled()){
		// bitmap.recycle();
		// bitmap = null;
		// }
		// bitmapDrawable = null;
		// }
		// }
		System.gc();
	}

	public static void freeViewBg(View view) {
		updateViewBg(view, 0);
	}

	public static void setThemeStyle(Context context, int themeId) {
		StyleController.getInstance().setTheme(context, themeId);
	}
}
