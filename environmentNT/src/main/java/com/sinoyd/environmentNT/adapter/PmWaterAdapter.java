package com.sinoyd.environmentNT.adapter;

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
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.view.SpringImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 位置管理数据适配器
 * 
 * @author zz
 * 
 */
public class PmWaterAdapter extends BaseAdapter {
	public interface PmWaterCallback {
		void addPosition();

		void otherPosition(PortInfo info);
	}

	private PmWaterCallback pmCallback;

	public void setPmCallback(PmWaterCallback pmCallback) {
		this.pmCallback = pmCallback;
	}

	public List<PortInfo> listData = new ArrayList<PortInfo>();

	public void addData(PortInfo info) {
		listData.add(info);
		notifyDataSetChanged();
	}

	public void remove(PortInfo info) {
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

				holder.tvPortName = (TextView) convertView.findViewById(R.id.tvPortName);
				holder.imgItem = (ImageView) convertView.findViewById(R.id.imgItem);
				holder.imgItem.setOnLongClickListener(new OnLongClickListener() { // 长按时间
							@Override
							public boolean onLongClick(View v) {
								if (pmCallback != null)
									pmCallback.otherPosition((PortInfo) getItem(position));
								return false;
							}
						});
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			if (holder != null) { // 对view进行数据加载

				holder.tvPortName.setText(((PortInfo) getItem(position)).PortName);


			}
		}
		return convertView;
	}

	public class ViewHolder {
		public TextView   tvPortName;
		public ImageView imgItem;



	}
}
