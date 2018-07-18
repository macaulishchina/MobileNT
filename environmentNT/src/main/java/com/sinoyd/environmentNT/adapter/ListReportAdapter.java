package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.WeeklyWQInfo;

/***
 * 站点列表适配器
 * 
 * @author smz
 * 
 */
public class ListReportAdapter extends BaseAdapter {
	private List<WeeklyWQInfo> list;

	public ListReportAdapter() {
	}

	public ListReportAdapter(List<WeeklyWQInfo> list) {
		this.list = list;
	}

	@Override
	public int getCount() {
		return (list == null || list.size() == 0) ? 4 : list.size();
	}

	@Override
	public Object getItem(int position) {
		return (list == null || list.size() == 0) ? null : list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(), R.layout.list_report_item, null);
			holder = new ViewHolder();
			holder.tvShuizhi = (TextView) convertView.findViewById(R.id.tvShuizhi);
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
			holder.tvPercent = (TextView) convertView.findViewById(R.id.tvPercent);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvName.setText(getItem(position) == null ? "--" : ((WeeklyWQInfo) getItem(position)).Ports);
		holder.tvShuizhi.setText(getItem(position) == null ? "--" : ((WeeklyWQInfo) getItem(position)).Class);
		holder.tvNum.setText(getItem(position) == null ? "--" : ((WeeklyWQInfo) getItem(position)).Count);
		holder.tvPercent.setText(getItem(position) == null ? "--" : ((WeeklyWQInfo) getItem(position)).Percent);
		return convertView;
	}

	public class ViewHolder {
		public TextView tvShuizhi, tvName, tvNum, tvPercent;
	}
}
