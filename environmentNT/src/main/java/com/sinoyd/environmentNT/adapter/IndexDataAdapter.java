package com.sinoyd.environmentNT.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.sinoyd.environmentNT.data.IndexDataInfo;
import com.sinoyd.environmentNT.view.IndexLabelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 实况空气指标
 * 
 * @author zz
 * 
 */
public class IndexDataAdapter extends BaseAdapter {
	private List<IndexDataInfo> list;
   int maxvalue=0;
	@Override
	public int getCount() {
		if (list == null)
			return 0;
		return list.size();
	}

	public IndexDataAdapter(List<IndexDataInfo> list) {
		this.list = list;
		maxvalue=GetMax(list);
	}

	public void addData(IndexDataInfo info) {
		if (list == null)
			list = new ArrayList<IndexDataInfo>();
		list.add(info);
		notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		if (list == null || position > list.size())
			return null;
		return list.get(position);
	}


	public int GetMax(List<IndexDataInfo> list)
	{
		int maxvalue=0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).value>maxvalue)
				maxvalue=list.get(i).value;
		}
		return maxvalue;
	}


	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getItem(position) == null)
			return null;

		IndexDataInfo info = (IndexDataInfo) getItem(position);
		IndexLabelView labelView = null;
		if (convertView == null) {
			labelView = new IndexLabelView(parent.getContext(),((RelativeLayout)parent.getParent()));
			convertView = labelView;
		} else {
			labelView = (IndexLabelView) convertView;
		}
		labelView.setName(info.name);
		maxvalue=GetMax(list);

		labelView.setValue(info.value,maxvalue);

		return convertView;
	}

}
