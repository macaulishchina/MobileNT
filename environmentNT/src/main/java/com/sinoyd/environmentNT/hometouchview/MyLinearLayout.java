package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 自定义LinearLayer Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyLinearLayout


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyLinearLayout extends View {
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return ((View) getParent()).onTouchEvent(ev);
	}

	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyLinearLayout(Context context) {
		super(context);
	}
}
