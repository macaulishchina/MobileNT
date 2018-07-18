package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * 自定义ListView Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyListView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyListView extends ListView {
	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return ((View) getParent()).onTouchEvent(ev);
	}
}
