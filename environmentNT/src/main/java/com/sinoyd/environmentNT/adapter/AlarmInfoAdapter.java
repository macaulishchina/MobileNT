package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.Alarm.AlarmItem;
import com.sinoyd.environmentNT.view.MyHScrollView;
import com.sinoyd.environmentNT.view.MyHScrollView.OnScrollChangedListener;

/**
 * 在线情况数据适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：OnlineValueAdapter


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class AlarmInfoAdapter extends BaseAdapter {
	private List<AlarmItem> mItemList;
	private LayoutInflater mLayoutInflater;
	/** 判断是否是空数据 **/
	private boolean isEmptyData = false;
	 
	/** 监测因子名称头部 **/
	private RelativeLayout mHead;
	private Context mContext;

	public AlarmInfoAdapter(Context context, List<AlarmItem> list) {
		mContext = context;
		 
		mItemList = list;
	}
	public AlarmInfoAdapter(Context context, List<AlarmItem> list, RelativeLayout head) {
		mContext = context;
		mHead = head;
		mLayoutInflater = LayoutInflater.from(context);
		mItemList = list;
	}

	public void setList(List<AlarmItem> list) {
		mItemList = list;
		notifyDataSetChanged();
	}
	public void setIsEmptyData(boolean isEmptyData) {
		this.isEmptyData = isEmptyData;
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
		TextView name = null, alarmtime = null, alarmcontent = null,handleresult=null;
		if (convertView == null) {
			view = mLayoutInflater.inflate(R.layout.alarm_data_item, null);
		}
		else {
			view = convertView;
		}
		RelativeLayout layout;
		LinearLayout viewChild=new LinearLayout(mContext);
		
		 
		viewChild = (LinearLayout) view.findViewById(R.id.viewChild);
		viewChild.removeAllViews();
		 
		addTv(viewChild, mItemList.get(position).AlarmTime, 200); 
		addTv(viewChild, mItemList.get(position).AlarmContent,500);
		addTv(viewChild, mItemList.get(position).AlarmProcess, 300);
//		name = (TextView) view.findViewById(R.id.name);
//		alarmtime = (TextView) view.findViewById(R.id.alarmtime);
//		alarmcontent = (TextView) view.findViewById(R.id.alarmcontent);
//		handleresult = (TextView) view.findViewById(R.id.handleresult);
//		AlarmItem onalarmItem = mItemList.get(position);
//		name.setText(onalarmItem.PortName);
//		handleresult.setText(onalarmItem.HandleResult);
//		if (!onalarmItem.AlarmTime.equals("")) {
//			alarmtime.setText(onalarmItem.AlarmTime.substring(5));
//		}
//		else {
//			alarmtime.setText(onalarmItem.AlarmTime);
//		}
//		alarmcontent.setText(onalarmItem.AlarmContent);
		MyHScrollView scrollView1 = (MyHScrollView) view.findViewById(R.id.horizontalScrollView1);
		layout = (RelativeLayout) view.findViewById(R.id.density_layout);
		 
		
		
//		if (!isEmptyData) {
//			if (position % 2 != 0) {
//				view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparentblack));
//			}
//			else {
//				view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
//			}
//		}
//		else {
//			view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
//		}
		TextView portName = (TextView) view.findViewById(R.id.portName);
		portName.setText(mItemList.get(position).PortName); // 设置点位
		MyHScrollView headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
		headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));
		if (position % 2 != 0) { // 修改背景样式
			layout.setBackgroundColor(mContext.getResources().getColor(R.color.transparentblack));
		}
		else {
			layout.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
		}
		return view;
	}
	
	/**
	 * 滑动改变 Copyright (c) 2015 江苏远大信息股份有限公司
	 * 
	 * @类型名称：OnScrollChangedListenerImp

	 * @创建日期：2015-1-26
	 * @维护人员：
	 * @维护日期：
	 * @功能摘要：
	 */
	class OnScrollChangedListenerImp implements OnScrollChangedListener {
		MyHScrollView mScrollViewArg;

		public OnScrollChangedListenerImp(MyHScrollView scrollViewar) {
			mScrollViewArg = scrollViewar;
		}

		@Override
		public void onScrollChanged(int l, int t, int oldl, int oldt) {
			mScrollViewArg.smoothScrollTo(l, t);
		}
	};
	
	/***
	 * 添加一个view到列表中
	 * 
	 * @param parent
	 * @param value
	 * @param bz
	 */
	private void addTv(LinearLayout parent, String value, int width) {
		TextView v = (TextView) View.inflate(parent.getContext(), R.layout.tv_item, null);
		v.setGravity(Gravity.CENTER);
		v.setText(value);
		 
			v.setTextColor(Color.WHITE);
		 
		LayoutParams params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		params.leftMargin = params.rightMargin = 10;
		 
		parent.addView(v, params);
	}
}
