package com.sinoyd.environmentNT.adapter;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.HealthTipDataInfo;

/**
 * 健康提醒的适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HealthTipAdapter

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HealthTipAdapter extends BaseAdapter {
	/** 健康提醒列表 **/
	private List<HealthTipDataInfo> list;

	public HealthTipAdapter(List<HealthTipDataInfo> list) {
		this.list = list;
	}

	public void addData(HealthTipDataInfo info) {
		if (this.list == null)
			this.list = new ArrayList<HealthTipDataInfo>();
		this.list.add(info);
	}

	@Override
	public int getCount() {
		if (list == null)
			return 0;
		else
			return list.size();
	}

	@Override
	public Object getItem(int position) {
		if (list == null || list.size() < position)
			return null;
		else
			return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItem(position) == null)
			return null;
		ViewHolder holder = null;
		HealthTipDataInfo info = (HealthTipDataInfo) getItem(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(parent.getContext(), R.layout.healthtip_item, null);
			holder.imgTip = (ImageView) convertView.findViewById(R.id.imgTip);
			holder.tvTip = (TextView) convertView.findViewById(R.id.tvTip);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.imgTip.setImageResource(info.resId);
		holder.tvTip.setText(info.tip);
		return convertView;
	}

	public class ViewHolder {
		ImageView imgTip;
		TextView tvTip;
	}
}
