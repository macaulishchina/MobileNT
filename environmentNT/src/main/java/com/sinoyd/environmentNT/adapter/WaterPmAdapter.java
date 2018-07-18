package com.sinoyd.environmentNT.adapter;

import java.util.ArrayList;
import java.util.List;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.WaterPortInfo;

/**
 * 位置管理数据适配器
 * 
 * @author zz
 * 
 */
public class WaterPmAdapter extends BaseAdapter {
	public WaterPmAdapter(List<WaterPortInfo> listData) {
		this.listData = listData;
	}

	public interface WaterPmCallback {
		void onClick(WaterPortInfo info);
	}

	private WaterPmCallback pmCallback;

	public void setPmCallback(WaterPmCallback pmCallback) {
		this.pmCallback = pmCallback;
	}

	public List<WaterPortInfo> listData = new ArrayList<WaterPortInfo>();

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < listData.size())
			return listData.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		WaterPortInfo item = (WaterPortInfo) getItem(position);
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.water_pm_item, null);
			holder = new ViewHolder();
			holder.tvPortName = (TextView) convertView.findViewById(R.id.tvPortName);
			holder.imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
			convertView.setTag(holder);
			holder.imgItem.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (pmCallback != null)
						pmCallback.onClick((WaterPortInfo) getItem(position));
				}
			});
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvPortName.setText(item.PortName);
		return convertView;
	}

	public class ViewHolder {
		public TextView tvAQI, tvClass, tvDateTime, tvPortName;
		public ImageView imgItem;
	}
}
