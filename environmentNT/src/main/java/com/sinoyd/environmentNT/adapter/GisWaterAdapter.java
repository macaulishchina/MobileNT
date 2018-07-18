package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.iceman.paintdemo.JsonUtil;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.WaterModel;
import com.sinoyd.environmentNT.util.PullectUtils;

/***
 * 水质的gis适配器
 * 
 * @author smz
 * 
 */
public class GisWaterAdapter extends BaseAdapter {
	private List<WaterModel> mList;
	private LayoutInflater mLayoutInflater;

	public GisWaterAdapter(Context context) {
		mLayoutInflater = LayoutInflater.from(context);
	}

	public void setList(List<WaterModel> list) {
		this.mList = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList == null ? null : mList.size();
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_gis_station, null);
			viewHolder = new ViewHolder();
			viewHolder.areaNameText = (TextView) convertView.findViewById(R.id.area_name);
			viewHolder.pullectValueText = (TextView) convertView.findViewById(R.id.area_pullect_value);
			viewHolder.pullectMsgText = (TextView) convertView.findViewById(R.id.area_pullect_msg);
			viewHolder.pulleactLayout = convertView.findViewById(R.id.area_pullect_layout);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		WaterModel model = mList.get(position);
		viewHolder.areaNameText.setText(model.PortName);
		viewHolder.pullectValueText.setText(model.WQ + "");
		viewHolder.pullectMsgText.setBackgroundResource(PullectUtils.getWaterBgByLevel(JsonUtil.getLevelByLuoMaNumber(model.WQ)));
		viewHolder.pullectMsgText.setText(model.ClaName);
		if (AppConfig.isWatherSystem()) {
			viewHolder.pulleactLayout.setVisibility(View.GONE);
		}
		else {
			viewHolder.pulleactLayout.setVisibility(View.VISIBLE);
		}
		/*if (position % 2 != 0) {
			convertView.setBackgroundResource(R.drawable.list_item_space_bg);
		} else {
			convertView.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
		}*/
		return convertView;
	}

	private static class ViewHolder {
		public TextView areaNameText, pullectValueText, pullectMsgText;
		public View pulleactLayout;
	}
}
