package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义view pager Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyViewPager

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyViewPager extends ViewPager {
	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (RealTimeShowView.isTouchRecordView) {
			return false;
		}
		if (RealTimeShowWaterView.isTouchRecordView) {
			return false;
		}
		return super.onInterceptTouchEvent(arg0);
	}
}
