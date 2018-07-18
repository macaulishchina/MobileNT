package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.sinoyd.environmentNT.view.OnlineView;

/**
 * 在线情况界面的适配器
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * @类型名称：OnlinePagerAdapter

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class OnlinePagerAdapter extends PagerAdapter {
	private List<OnlineView> mListViews;

	public OnlinePagerAdapter() {
	}

	/***
	 * 设置数据源
	 * 
	 * @param mListViews
	 */
	public void setListViews(List<OnlineView> mListViews) {
		this.mListViews = mListViews;
		notifyDataSetChanged();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		try {
			container.removeView(mListViews.get(position));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		try {
			container.addView(mListViews.get(position), 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return mListViews.get(position);
	}

	@Override
	public int getCount() {
		return mListViews == null ? 0 : mListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
}