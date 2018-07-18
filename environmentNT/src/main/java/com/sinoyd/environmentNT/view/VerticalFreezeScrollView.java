package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * 水平滑动view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyHScrollView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class VerticalFreezeScrollView extends ListView {
	 
	OnScrollChangedListener scrollChangedListener;
	
	public VerticalFreezeScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public VerticalFreezeScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public VerticalFreezeScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void SetScrollListener( OnScrollChangedListener scrollListener)
	{
		scrollChangedListener=scrollListener;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		  
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		/*
		 * 通知滑动变化
		 */
//		this.scrollTo(l, t);
//		scrollChangedListener.onScrollChanged(l, t, oldl, oldt);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	 
	/*
	 *监听器
	 */
	public static interface OnScrollChangedListener {
		/***
		 * 滑动改变事件
		 * 
		 * @param l
		 * @param t
		 * @param oldl
		 * @param oldt
		 */
		public void onScrollChanged(int l, int t, int oldl, int oldt);
	}

	 
}
