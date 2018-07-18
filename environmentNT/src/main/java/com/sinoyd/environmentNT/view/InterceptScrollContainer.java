package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * 滑动拦截器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：InterceptScrollContainer


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class InterceptScrollContainer extends LinearLayout {
	public InterceptScrollContainer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public InterceptScrollContainer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		Log.i("pdwy", "ScrollContainer onInterceptTouchEvent");
		return false;
	}
}
