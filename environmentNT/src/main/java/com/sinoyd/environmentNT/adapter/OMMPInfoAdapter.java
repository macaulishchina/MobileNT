package com.sinoyd.environmentNT.adapter;

import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.OMMPInfoModel;
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
public class OMMPInfoAdapter extends BaseAdapter {
	private List<OMMPInfoModel> mOMMPInfoList;
	private LayoutInflater mLayoutInflater;
	/** 判断是否是空数据 **/
	private boolean isEmptyData = false;

	MyHScrollView headscrollview;
    Context mcontext;
	public OMMPInfoAdapter(Context context, List<OMMPInfoModel> list,MyHScrollView head) {
		mLayoutInflater = LayoutInflater.from(context);
		mOMMPInfoList = list;
		headscrollview=head;
		mcontext=context;
	}

//	public void setIsEmptyData(boolean isEmptyData) {
//		this.isEmptyData = isEmptyData;
//		notifyDataSetChanged();
//	}

	public void setList(List<OMMPInfoModel> list) {
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

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		 
		if (convertView == null) {
			view = mLayoutInflater.inflate(R.layout.item_ommp_listview, null);
		}
		else {
			view = convertView;
		}
		final TextView stationname = (TextView) view.findViewById(R.id.tv_stationname);
		final TextView TaskName = (TextView) view.findViewById(R.id.tv_taskname);
		TextView Status = (TextView) view.findViewById(R.id.tv_status);
		TextView tv_planfinishdate = (TextView) view.findViewById(R.id.tv_planfinishdate);
		TextView tv_realfinishdate = (TextView) view.findViewById(R.id.tv_realfinishdate);
		TextView tv_operateuser = (TextView) view.findViewById(R.id.tv_operateuser);
		final OMMPInfoModel onalarmItem = mOMMPInfoList.get(position);
		stationname.setText(onalarmItem.StationName);
		TaskName.setText(onalarmItem.TaskName);
		Status.setText(onalarmItem.Status);
		TaskName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(mcontext,onalarmItem.TaskName, Toast.LENGTH_SHORT).show(); 
			}
		});
//		TaskName.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				return true;
//			}
//		});
		if(onalarmItem.Status.equals("已完成"))
		{
			Drawable drawable= view.getResources().getDrawable(R.drawable.tv_finish_shape);
			Status.setBackground(drawable);
			
			
		}
		else
		{
			Drawable drawable= view.getResources().getDrawable(R.drawable.tv_unfinish_shape);
			Status.setBackground(drawable);
			
			
		}
			
		tv_planfinishdate.setText(onalarmItem.PlanfinishDate);
		tv_realfinishdate.setText(onalarmItem.RealfinishDate);
		tv_operateuser.setText(onalarmItem.OperateUser);
		final MyHScrollView scrollView1 = (MyHScrollView) view.findViewById(R.id.hscrollview1);
		 
		headscrollview.AddOnScrollChangedListener(new OnScrollChangedListener() {
			@Override
			public void onScrollChanged(int l, int t, int oldl, int oldt) {
				scrollView1.smoothScrollTo(l, t);
			}
		});;
		 
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
