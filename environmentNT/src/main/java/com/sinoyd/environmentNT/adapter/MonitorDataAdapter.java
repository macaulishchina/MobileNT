package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.FactorProperty;
import com.sinoyd.environmentNT.view.MyHScrollView;
import com.sinoyd.environmentNT.view.MyHScrollView.OnScrollChangedListener;

import java.util.List;

/**
 * 监测数据适配器 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MonitorDataAdapter2

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MonitorDataAdapter extends BaseAdapter {
	/** 监测数据列表 **/
	private List<FactorProperty> mItemList;
	private LayoutInflater mLayoutInflater;
	/** 监测因子名称头部 **/
	private RelativeLayout mHead;
	private Context mContext;

	boolean isNewData=false;
 
	int MaxColumnCount=0;
	int MaxColumnRowIndex=0;
	public MonitorDataAdapter(Context context, List<FactorProperty> list, RelativeLayout head) {
		mContext = context;
		mHead = head;
		mLayoutInflater = LayoutInflater.from(context);
		mItemList = list;
	}

	public void setList(List<FactorProperty> list) {
		mItemList = list;
		notifyDataSetChanged();
	}
	
	
	/**
	 * @功能描述 ：判断切换站点时是否重新渲染加载数据，重新渲染时，不从缓存中读取，重新生成表格数据。
	 * @param IsNewdata
	 * @创建者：张津明
	 * @创建日期：2015年12月14日
	 * @维护人员：
	 * @维护日期：
	 */
	public void SetIsNewData(boolean IsNewdata)
	{
		isNewData=IsNewdata;
		
	}

	/**
	 * @功能描述：设置表格最大列数
	 * @param Count
	 * @创建者：张津明
	 * @创建日期：2015年12月14日
	 * @维护人员：
	 * @维护日期：
	 */
	public void SetMaxColumnCount(int  Count)
	{
		MaxColumnCount=Count;
		
	}
	
	
	/**
	 * @功能描述：设置表格最大列数
	 * @param Count
	 * @创建者：张津明
	 * @创建日期：2015年12月14日
	 * @维护人员：
	 * @维护日期：
	 */
	public void SetMaxColumnRowIndex(int  Count)
	{
		MaxColumnRowIndex=Count;
		
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		FactorProperty pro = mItemList.get(position);
		FactorProperty maxproFactorProperty=	mItemList.get(MaxColumnRowIndex);
		
		RelativeLayout layout;
		LinearLayout viewChild=new LinearLayout(mContext);
		try {
			if (convertView == null) { // 初始化view
				view = mLayoutInflater.inflate(R.layout.monitor_data_item2, null);
				viewChild = (LinearLayout) view.findViewById(R.id.viewChild);
				viewChild.removeAllViews();
				if(pro.factorNames.size()<MaxColumnCount)
				{
					 int j=0;
					 for(int i=0;i<maxproFactorProperty.factorNames.size();i++)
					 {
						 j=0;
						 for(;j<pro.factorNames.size();j++)
						 if( pro.factorNames.get(j).equals(maxproFactorProperty.factorNames.get(i)))
						 {
							 addTv(viewChild, pro.factorValues.get(j), pro.isExceeded.get(j));
						    break;
						 }
						 if(j==maxproFactorProperty.factorNames.size())
							 addTv(viewChild, "--", false); 
					 }
				}
				else
				for (int i = 0; i < pro.factorNames.size(); i++) {
					addTv(viewChild, pro.factorValues.get(i), pro.isExceeded.get(i));
				}
//				for (int i = 0; i < pro.factorNames.size(); i++) {
//				addTv(viewChild, pro.factorValues.get(i), pro.isExceeded.get(i));
//			}
//				if(pro.factorNames.size()<MaxColumnCount)
//				{
//					for(int i=pro.factorNames.size();i<MaxColumnCount;i++)
//					addTv(viewChild, "--", false);
//				}
				
				
			}
			else {
				
				view = convertView;
				viewChild = (LinearLayout) view.findViewById(R.id.viewChild);
			    if(isNewData)
			    {
			    	viewChild.removeAllViews();
			    	if(pro.factorNames.size()<MaxColumnCount)
					{
						 int j=0;
						 for(int i=0;i<maxproFactorProperty.factorNames.size();i++)
						 {
							 j=0;
							 for(;j<pro.factorNames.size();j++)
							 if( pro.factorNames.get(j).equals(maxproFactorProperty.factorNames.get(i)))
							 {
								 addTv(viewChild, pro.factorValues.get(j), pro.isExceeded.get(j));
							    break;
							 }
							 if(j==maxproFactorProperty.factorNames.size())
								 addTv(viewChild, "--", false); 
						 }
					}
					else
					for (int i = 0; i < pro.factorNames.size(); i++) {
						addTv(viewChild, pro.factorValues.get(i), pro.isExceeded.get(i));
					}
//					for (int i = 0; i < pro.factorNames.size(); i++) {
//						addTv(viewChild, pro.factorValues.get(i), pro.isExceeded.get(i));
//					}
			    }
//				if(pro.factorNames.size()<MaxColumnCount)
//				{
//					for(int i=pro.factorNames.size();i<MaxColumnCount;i++)
//					addTv(viewChild, "--", false);
//				}
			
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			Log.e("MonitorDataAdapter", e.getMessage());
			StackTraceElement[] stackArray = e.getStackTrace(); 
			 StringBuffer sb = new StringBuffer();  
	        for (int i = 0; i < stackArray.length; i++) {  
	            StackTraceElement element = stackArray[i];  
	            sb.append(element.toString() + "\n");  
	        } 
	        Log.e("MonitorDataAdapter",sb.toString());
		}
 		
		MyHScrollView scrollView1 = (MyHScrollView) view.findViewById(R.id.horizontalScrollView1);
		layout = (RelativeLayout) view.findViewById(R.id.density_layout);
		/**
		 * 查找界面中得元素是否被添加，并且对元素进行赋值
		 */
		for (int i = 0; i < viewChild.getChildCount(); i++) {
			TextView v = null;
			try {
				v = (TextView) viewChild.getChildAt(i);
				v.setText(pro.factorValues.get(i));
				if (!"--".equals(pro.factorValues.get(i)))
					if (pro.isExceeded.get(i))
						v.setTextColor(Color.RED);
					else
						v.setTextColor(Color.WHITE);
				else {
					v.setTextColor(Color.WHITE);
				}
			}
			catch (Exception e) {
				// TODO: handle exception
				Log.d("MonitorDataAdapter", e.getMessage());
			}
		}
		
		
		
		TextView date = (TextView) view.findViewById(R.id.date);
		date.setText(pro.date); // 设置时间
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

	/***
	 * 添加一个view到列表中
	 * 
	 * @param parent
	 * @param value
	 * @param bz
	 */
	private void addTv(LinearLayout parent, String value, Boolean bz) {
		TextView v = (TextView) View.inflate(parent.getContext(), R.layout.tv_item, null);
		v.setGravity(Gravity.CENTER);
		v.setText(value);
		if (!"--".equals(value))
			if (bz)
				v.setTextColor(Color.RED);
			else
				v.setTextColor(Color.WHITE);
		else {
			v.setText("-");
			v.setTextColor(Color.WHITE);
		}
		LayoutParams params = new LayoutParams(220, LayoutParams.MATCH_PARENT);
		params.gravity = Gravity.CENTER;
		params.leftMargin = params.rightMargin = 10;
		parent.addView(v, params);
	}
}
