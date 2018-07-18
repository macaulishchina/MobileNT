package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;

/**
 * 必须要有父View Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyGridView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyGridView extends GridView {
	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyGridView(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return ((View) getParent()).onTouchEvent(ev);
	}
}
