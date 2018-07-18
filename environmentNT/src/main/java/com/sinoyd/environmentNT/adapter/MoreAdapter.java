package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.VersionModel;
import com.sinoyd.environmentNT.util.SystemUtil;

/**
 * 更多界面适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MoreAdapter

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MoreAdapter extends BaseAdapter {
	/** 标题集合 **/
	private String[] mTitles;
	/** 图标集合 **/
	private int[] mIcons;
	private LayoutInflater mLayoutInflater;

	public MoreAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setIconAndTitle(int[] icons, String[] titles) {
		mTitles = titles;
		mIcons = icons;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mTitles == null ? 0 : mTitles.length;
	}

	@Override
	public Object getItem(int position) {
		return mTitles == null ? null : mTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_more_layout, null);
			viewHolder = new ViewHolder();
			viewHolder.titleText = (TextView) convertView.findViewById(R.id.text);
			viewHolder.iconView = (ImageView) convertView.findViewById(R.id.icon);
			viewHolder.update = (ImageView) convertView.findViewById(R.id.update);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.titleText.setText(mTitles[position]);
		viewHolder.iconView.setImageResource(mIcons[position]);
		VersionModel versionModel = new VersionModel();
		if (SystemUtil.versionCode < versionModel.verCode) { // 有新版本
			if (position == 0) {
				viewHolder.update.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}

	private static class ViewHolder {
		public TextView titleText;
		public ImageView iconView, update;
	}
}
