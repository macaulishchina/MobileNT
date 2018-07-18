package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;

import java.util.List;
import java.util.Map;

/**
 * 站点选择弹出 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PortExpandableListAdapter


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortExpandableListAdapter extends BaseExpandableListAdapter {
	// 单元类
	class ExpandableListHolder {
		TextView portName;
	}

	// 父单元
	class ExpandableGroupHolder {
		TextView portType;
	}

	private List<Map<String, Object>> groupData;// 组显示
	private List<List<Map<String, Object>>> childData;// 子列表
	private LayoutInflater mGroupInflater; // 用于加载group的布局xml
	private LayoutInflater mChildInflater; // 用于加载listitem的布局xml

	// 自宝义构造
	public PortExpandableListAdapter(Context context, List<Map<String, Object>> groupData, List<List<Map<String, Object>>> childData) {
		this.childData = childData;
		this.groupData = groupData;
		mChildInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mGroupInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 必须实现 得到子数据
	@Override
	public Object getChild(int groupPosition, int j) {
		return childData.get(groupPosition).get(j);
	}

	@Override
	public long getChildId(int groupPosition, int j) {
		return groupPosition;
	}

	@Override
	public int getChildrenCount(int i) {
		return childData.get(i).size();
	}

	@Override
	public Object getGroup(int i) {
		return groupData.get(i);
	}

	@Override
	public int getGroupCount() {
		return groupData.size();
	}

	@Override
	public long getGroupId(int i) {
		return i;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int i, int j) {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean flag, View convertView, ViewGroup viewgroup) {
		ExpandableGroupHolder holder = null; // 清空临时变量holder
		if (convertView == null) { // 判断view（即view是否已构建好）是否为空
			convertView = mGroupInflater.inflate(R.layout.main_tree_group, null);
			holder = new ExpandableGroupHolder();
			holder.portType = (TextView) convertView.findViewById(R.id.main_tree_title_id);
			convertView.setTag(holder);
		}
		else { // 若view不为空，直接从view的tag属性中获得各子视图的引用
			holder = (ExpandableGroupHolder) convertView.getTag();
		}
		String title = (String) this.groupData.get(groupPosition).get("RegionType");
		holder.portType.setText(title);
		notifyDataSetChanged();
//		int[] colors = new int[] { 0xFFC7C7E2 };
		int[] colors = new int[] { 0xFF0882C8 };
		convertView.setBackgroundColor(colors[0]);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup viewgroup) {
		ExpandableListHolder holder = null;
		if (convertView == null) {
			convertView = mChildInflater.inflate(R.layout.main_tree_child, null);
			holder = new ExpandableListHolder();
			holder.portName = (TextView) convertView.findViewById(R.id.mainChildText1);
			convertView.setTag(holder);
		}
		else {// 若行已初始化，直接从tag属性获得子视图的引用
			holder = (ExpandableListHolder) convertView.getTag();
		}
		Map<String, Object> unitData = this.childData.get(groupPosition).get(childPosition);
		holder.portName.setText((String) unitData.get("portName"));
//		int[] colors = new int[] { 0xFFAFDDEB, 0xFFE3F4ED };
		int[] colors = new int[] { 0xFF15a1d9, 0xFF4db7e1 };
		if (childPosition >= 0) {
			int colorPos = childPosition % colors.length;
			convertView.setBackgroundColor(colors[colorPos]);
		}
		return convertView;
	}

	public String getSelectItemPortName(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return (String) childData.get(groupPosition).get(childPosition).get("portName");
	}
	
	public String getSelectItemPortType(int groupPosition) {
		// TODO Auto-generated method stub
		return (String) this.groupData.get(groupPosition).get("RegionType");
	}
}
