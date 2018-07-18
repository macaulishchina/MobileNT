package com.sinoyd.environmentNT.adapter;

import java.util.ArrayList;
import java.util.List;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.util.PullectUtils;
import com.sinoyd.environmentNT.view.SpringImageView;

/**
 * 位置管理数据适配器
 * 
 * @author zz
 * 
 */
public class PmAdapter extends BaseAdapter {
	public interface PmCallback {
		void addPosition();

		void otherPosition(PortHourAQIInfo info);
	}



	private PmCallback pmCallback;

	public void setPmCallback(PmCallback pmCallback) {
		this.pmCallback = pmCallback;
	}

	public List<PortHourAQIInfo> listData = new ArrayList<PortHourAQIInfo>();

	public void addData(PortHourAQIInfo info) {
		listData.add(info);
		notifyDataSetChanged();
	}

	public void remove(PortHourAQIInfo info) {
		listData.remove(info);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listData.size() + 1;
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
		ViewHolder holder = null;
		if (position != getCount() - 1) {
			convertView = null;
		}
		if (position == getCount() - 1) { // 最后一个站点的view进行特殊的处理
			LinearLayout addView = new LinearLayout(parent.getContext());
			addView.setGravity(Gravity.CENTER);
			SpringImageView view = new SpringImageView(parent.getContext());
			view.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.pm_add));
			addView.addView(view);
			convertView = addView;
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (pmCallback != null)
						pmCallback.addPosition();
				}
			});
		}
		else {
			if (convertView == null) {
				// 加载view并且赋值
				holder = new ViewHolder();
				convertView = View.inflate(parent.getContext(), R.layout.pm_item, null);
				holder.tvAQI = (TextView) convertView.findViewById(R.id.tvAQI);
				holder.tvClass = (TextView) convertView.findViewById(R.id.tvClass);
				holder.tvDateTime = (TextView) convertView.findViewById(R.id.tvDateTime);
				holder.tvPortName = (TextView) convertView.findViewById(R.id.tvPortName);
				holder.imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
				holder.imgItem.setOnLongClickListener(new OnLongClickListener() { // 长按时间
							@Override
							public boolean onLongClick(View v) {
								if (pmCallback != null)
									pmCallback.otherPosition((PortHourAQIInfo) getItem(position));
								return false;
							}
						});
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (holder != null) { // 对view进行数据加载
				holder.tvAQI.setText(((PortHourAQIInfo) getItem(position)).AQI);
				holder.tvClass.setText(((PortHourAQIInfo) getItem(position)).Class);
				holder.tvDateTime.setText(((PortHourAQIInfo) getItem(position)).DateTime);
				holder.tvPortName.setText(((PortHourAQIInfo) getItem(position)).PortName);
				int value = 0;
				try {
					value = Integer.parseInt(((PortHourAQIInfo) getItem(position)).AQI);
				}
				catch (Exception e) {
					value = 0;
				}
				int color = PullectUtils.getColor(value);
				holder.tvAQI.setTextColor(color); // 设置相关的颜色
				holder.tvClass.setTextColor(color);
			}
		}
		return convertView;
	}

	public class ViewHolder {
		public TextView tvAQI, tvClass, tvDateTime, tvPortName;
		public ImageView imgItem;
	}
}
