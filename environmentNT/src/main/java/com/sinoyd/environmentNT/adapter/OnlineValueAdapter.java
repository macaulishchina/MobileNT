package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.Online.OnlineItem;

/**
 * 在线情况数据适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：OnlineValueAdapter


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class OnlineValueAdapter extends BaseAdapter {
	private List<OnlineItem> mItemList;
	private LayoutInflater mLayoutInflater;
	private Context mContext;

	public OnlineValueAdapter(Context context, List<OnlineItem> list) {
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context);
		mItemList = list;
	}

	public void setList(List<OnlineItem> list) {
		mItemList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mItemList == null ? 0 : mItemList.size();
	}

	@Override
	public Object getItem(int position) {
		return mItemList == null ? null : mItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		TextView name = null, recordcount = null, recentdatetime = null;
		ImageView status = null;
		if (convertView == null) {
			view = mLayoutInflater.inflate(R.layout.online_item, null);
		}
		else {
			view = convertView;
		}
		name = (TextView) view.findViewById(R.id.name);
		status = (ImageView) view.findViewById(R.id.status);
		recordcount = (TextView) view.findViewById(R.id.recordcount);
		recentdatetime = (TextView) view.findViewById(R.id.recentdatetime);
		OnlineItem onlineItem = mItemList.get(position);
		name.setText(onlineItem.name);
		if (onlineItem.status.equals("True")) {
			status.setBackgroundResource(R.drawable.online);
		}
		else {
			status.setBackgroundResource(R.drawable.offline);
		}
		recordcount.setText(onlineItem.recordcount);
		if (!onlineItem.recentDataTime.contains("--")) {
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append(onlineItem.recentDataTime.replace("/", "-"));
			sBuilder.delete(0, sBuilder.indexOf("-") + 1);
			sBuilder.delete(sBuilder.length() - 3, sBuilder.length());
			recentdatetime.setText(sBuilder);
		}
		else {
			recentdatetime.setText(onlineItem.recentDataTime);
		}
		if (position % 2 != 0) {
			view.setBackgroundResource(R.drawable.list_dark);
		}
		else {
			view.setBackgroundResource(R.drawable.list_qian);
		}
		if (position % 2 != 0) {
			view.setBackgroundColor(mContext.getResources().getColor(R.color.transparentblack));
		}
		else {
			view.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
		}
		return view;
	}
}
