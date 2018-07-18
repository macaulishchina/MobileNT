package com.sinoyd.environmentNT.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 水平滑动view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyHScrollView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyHScrollView extends HorizontalScrollView {
	ScrollViewObserver mScrollViewObserver = new ScrollViewObserver();

	public MyHScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyHScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		Log.i("pdwy", "MyHScrollView onTouchEvent");
		 
 		  super.onTouchEvent(ev);
		  return false;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		/*
		 * 通知滑动变化
		 */
		if (mScrollViewObserver != null /*&& (l != oldl || t != oldt)*/) {
			mScrollViewObserver.NotifyOnScrollChanged(l, t, oldl, oldt);
		}
//		this.smoothScrollTo(l, t);
		super.onScrollChanged(l, t, oldl, oldt);
	}

	/*
	 * 添加一个通知时间
	 * */
	public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.AddOnScrollChangedListener(listener);
	}

	/*
	 * 删除一个通知时间
	 * */
	public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
		mScrollViewObserver.RemoveOnScrollChangedListener(listener);
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

	/*
	 * �۲���
	 */
	public static class ScrollViewObserver {
		List<OnScrollChangedListener> mList;

		public ScrollViewObserver() {
			super();
			mList = new ArrayList<OnScrollChangedListener>();
		}

		public void AddOnScrollChangedListener(OnScrollChangedListener listener) {
			mList.add(listener);
		}

		public void RemoveOnScrollChangedListener(OnScrollChangedListener listener) {
			mList.remove(listener);
		}

		public void NotifyOnScrollChanged(int l, int t, int oldl, int oldt) {
			if (mList == null || mList.size() == 0) {
				return;
			}
			for (int i = 0; i < mList.size(); i++) {
				if (mList.get(i) != null) {
					mList.get(i).onScrollChanged(l, t, oldl, oldt);
				}
			}
		}
	}
}
