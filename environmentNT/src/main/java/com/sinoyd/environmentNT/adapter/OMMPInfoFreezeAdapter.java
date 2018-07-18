package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;

/**
 * 在线情况数据适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：OnlineValueAdapter


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class OMMPInfoFreezeAdapter extends BaseAdapter {
	private List<String> mOMMPInfoList;
	private LayoutInflater mLayoutInflater;
	/** 判断是否是空数据 **/
	private boolean isEmptyData = false;

	public OMMPInfoFreezeAdapter(Context context, List<String> list) {
		mLayoutInflater = LayoutInflater.from(context);
		mOMMPInfoList = list;
	}

//	public void setIsEmptyData(boolean isEmptyData) {
//		this.isEmptyData = isEmptyData;
//		notifyDataSetChanged();
//	}

	public void setList(List<String> list) {
		this.mOMMPInfoList = list;
	}

	@Override
	public int getCount() {
		return mOMMPInfoList == null ? 0 : mOMMPInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		return mOMMPInfoList == null ? null : mOMMPInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		TextView stationname = null;
		if (convertView == null) {
			view = mLayoutInflater.inflate(R.layout.item_vscrollview, null);
		}
		else {
			view = convertView;
		}
		stationname = (TextView) view.findViewById(R.id.tv_headname);
		 
		 
		  mOMMPInfoList.get(position);
		stationname.setText(mOMMPInfoList.get(position));
		 
		 
		if (!isEmptyData) {
			if (position % 2 != 0) {
				view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparentblack));
			}
			else {
				view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
			}
		}
		else {
			view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
		}
		return view;
	}
}
