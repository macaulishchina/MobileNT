package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 自定义RelativeLayout Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyRelativeLayout


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyRelativeLayout extends RelativeLayout {
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return ((View) getParent()).onTouchEvent(ev);
	}

	public MyRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyRelativeLayout(Context context) {
		super(context);
	}
}
