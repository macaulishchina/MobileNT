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
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.FactorProperty;
import com.sinoyd.environmentNT.view.MyHScrollView;
import com.sinoyd.environmentNT.view.MyHScrollView.OnScrollChangedListener;

/**
 * 查看浓度数据适配器
 * 
 * @author Jason
 * 
 */
public class MonitorDataAdapter2 extends BaseAdapter {
	private List<FactorProperty> mItemList;
	private LayoutInflater mLayoutInflater;
	private Context context;
	private RelativeLayout mHead;

	public MonitorDataAdapter2(Context context, List<FactorProperty> list, RelativeLayout head) {
		this.context = context;
		mHead = head;
		mLayoutInflater = LayoutInflater.from(context);
		mItemList = list;
	}

	public void setList(List<FactorProperty> list) {
		mItemList = list;
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

	/***
	 * 滑动改变
	 * 
	 * @author smz
	 * 
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
		// HashMap<String, Double> factorMap;
		/*
		 * if (AppConfig.isWatherSystem()) { factorMap =
		 * MyApplication.factorWaterMap; } else { factorMap =
		 * MyApplication.factorMap; } if (factorMap == null) factorMap = new
		 * HashMap<String, Double>();
		 */
		FactorProperty pro = mItemList.get(position);
		RelativeLayout layout;
		LinearLayout viewChild;
		if (convertView == null) { // 初始化view
			view = mLayoutInflater.inflate(R.layout.monitor_data_item2, null);
			viewChild = (LinearLayout) view.findViewById(R.id.viewChild);
			viewChild.removeAllViews();
			for (int i = 0; i < pro.factorNames.size(); i++) {
				/*
				 * double bz = factorMap.get(pro.factorNames.get(i)) == null ?
				 * 0.0 : factorMap.get(pro.factorNames.get(i));
				 */
				addTv(viewChild, pro.factorValues.get(i), pro.isExceeded.get(i));
			}
		}
		else {
			view = convertView;
			viewChild = (LinearLayout) view.findViewById(R.id.viewChild);
		}
		MyHScrollView scrollView1 = (MyHScrollView) view.findViewById(R.id.horizontalScrollView1);
		layout = (RelativeLayout) view.findViewById(R.id.density_layout);
		/***
		 * 查找界面中得元素是否被添加，并且对元素进行赋值
		 */
		for (int i = 0; i < viewChild.getChildCount(); i++) {
			TextView v = (TextView) viewChild.getChildAt(i);
			v.setText(pro.factorValues.get(i));
			if (!"--".equals(pro.factorValues.get(i)))
				if (pro.isExceeded.get(i))
					v.setTextColor(Color.RED);
				else
					v.setTextColor(Color.BLACK);
			else {
				v.setTextColor(Color.BLACK);
			}
		}
		TextView date = (TextView) view.findViewById(R.id.date);
		date.setText(pro.date); // 设置时间
		MyHScrollView headSrcrollView = (MyHScrollView) mHead.findViewById(R.id.horizontalScrollView1);
		headSrcrollView.AddOnScrollChangedListener(new OnScrollChangedListenerImp(scrollView1));
		// if(position!=0 && position!=1){
		if (position % 2 != 0) { // 修改背景样式
			layout.setBackgroundResource(R.drawable.list_item_space_bg);
			// layout.setAlpha(0)
		}
		else {
			layout.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.transparent));
		}
		// }
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
				v.setTextColor(Color.BLACK);
		else {
			v.setText("-");
			v.setTextColor(Color.BLACK);
		}
		LayoutParams params = new LayoutParams(120, LayoutParams.MATCH_PARENT);
		// params.width = 120;
		params.gravity = Gravity.CENTER;
		params.leftMargin = params.rightMargin = 10;
		parent.addView(v, params);
	}
}
