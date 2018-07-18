package com.sinoyd.environmentNT.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.lidroid.xutils.util.LogUtils;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.adapter.WaterWeekReportDataAdapter;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.common.BizCommon;
import com.sinoyd.environmentNT.data.FactorProperty;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
//import com.squareup.leakcanary.watcher.RefWatcher;

/**
 * 监测数据 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：AirMonitorDataActivity

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class WaterWeekReportActivity extends RefreshBaseActivity implements
		OnPageChangeListener, OnCheckedChangeListener {
	/** 请求接口传入参数 **/
	private HashMap<String, String> requestParams;
	/** 当前站点名称 **/
	private TextView mTitleView;
	/** 气监测数据ListView **/
	private ListView mDensityValueList;
	/** 监测数据适配器 **/
	private WaterWeekReportDataAdapter mAdapter;
	/** 监测数据实体 **/
	private List<FactorProperty> mItemList;
	private TextView startTime, endTime;
	private ImageView leftArrow, rightArrow;
	private String startTimeStr, endTimeStr;
	private LinearLayout listLayout, lineChartLayout;
	/** 监测因子名称头文件 **/
	private RelativeLayout mHead;
	private LinearLayout viewChild;
	LoadDialog dialog;
	boolean isSelectDialog = false;
	/*** 因子名称最大列数 ***/
	int MaxColumnCount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waterweekreport);
		// RefWatcher refWatcher =
		// MyApplication.getRefWatcher(this.getActivity());
		// refWatcher.watch(this);
	}

	@Override
	protected void initView() {
		super.initView();
		listLayout = (LinearLayout) findViewById(R.id.listview_layout);
		lineChartLayout = (LinearLayout) findViewById(R.id.chart_layout);
		mHead = (RelativeLayout) findViewById(R.id.table_title);
		viewChild = (LinearLayout) findViewById(R.id.viewChild);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mTitleView = (TextView) findViewById(R.id.title);
		mItemList = new ArrayList<FactorProperty>();
		mDensityValueList = (ListView) findViewById(R.id.densityValueList);
		mDensityValueList
				.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mAdapter = new WaterWeekReportDataAdapter(getActivity(), mItemList, mHead);
		mDensityValueList.setAdapter(mAdapter);
		dialog = new LoadDialog(this.mContext);
	}

	class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
			HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead
					.findViewById(R.id.horizontalScrollView1);
			headSrcrollView.onTouchEvent(arg1);
			return false;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
//		if (MyApplication.currentAirPortInfo != null) {
//			// mTitleTextView.setText(MyApplication.currentAirPortInfo.PortName);
//			portId = MyApplication.currentAirPortInfo.PortId;
//		}
		if (MyApplication.currentWaterPortInfo != null && !MyApplication.currentWaterPortInfo.PortId.equals(portId)) {
			mTitleTextView.setText(MyApplication.currentWaterPortInfo.PortName);
			portId = MyApplication.currentWaterPortInfo.PortId;
			requestServer();
		}
		 
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAdapter = null;
		// RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
		// refWatcher.watch(this);
	}

	 

	/***
	 * 加载数据
	 */
	 

	@Override
	protected void requestServer() {
		super.requestServer();
		if (requestParams == null) {
			requestParams = new HashMap<String, String>();
		}
		LogUtils.d("=========================== portId: " + portId);
		// if(MyApplication.UserGuid!=null)
		// requestParams.put("userUid", MyApplication.UserGuid);
		// else
		// requestParams.put("userUid", "");
		if (portId != null)
			requestParams.put("pointId", portId);
		else
			requestParams.put("pointId", "");
		HttpClient.getJsonWithGetUrl(
				RequestAirActionName.GetWaterWeekReportData, requestParams,
				this, null);
	}

	// @Override
	// public void selectPortinfo(PortInfo portInfo) {
	// super.selectPortinfo(portInfo);
	//
	// }
	@Override
	public void requestSuccess(HttpResponse resData) {
		super.requestSuccess(resData);
		mItemList.clear();
		JSONObject jsonObj = resData.getJson();
		JSONArray jsonArray = jsonObj.optJSONArray("WeekReport");
		if (jsonArray != null && jsonArray.length() > 0) {
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObj2 = jsonArray.optJSONObject(i);
				if (jsonObj2 != null) {
					String dateTime = jsonObj2.optString("DateTime");
					
					FactorProperty prop = new FactorProperty();
					if(i==jsonArray.length()-1)
					{
						try {
							prop.date = dateTime.split("~")[0]+"~\n"+dateTime.split("~")[1];
						}
						catch (Exception e) {
							// TODO: handle exception
							prop.date = dateTime;
						}
					
					}
					else
				    prop.date = dateTime;
					prop.factorNames = new ArrayList<String>();
					prop.factorValues = new ArrayList<String>();
					prop.isExceeded = new ArrayList<Boolean>();
					JSONArray array = jsonObj2.optJSONArray("Data");
					if (array != null) {
						for (int j = 0; j < array.length(); j++) {
							prop.factorValues.add(array.optJSONObject(j)
									.optString("value"));
							prop.factorNames.add(array.optJSONObject(j)
									.optString("factor"));
							prop.isExceeded.add(false);
						}
					}
					mItemList.add(prop);
				}
			}
			// if (mItemList.size() > 0)
			// addHead(mItemList.get(0));
			if (mItemList.size() != 0) {
				MaxColumnCount = BizCommon.GetMaxFactor(mItemList);
				if (mItemList.size() > 0)
					addHead(mItemList.get(MaxColumnCount));
				// mAdapter.notifyDataSetChanged();
				// mAdapter = new MonitorDataAdapter(getActivity(), mItemList,
				// mHead);
				// mDensityValueList.setAdapter(mAdapter);
				if (dialog.isShowing())
					dialog.hide();
				if (mAdapter == null) {
					mAdapter = new WaterWeekReportDataAdapter(getActivity(), mItemList,
							mHead);
					/*** 设置最大因子列数 ****/
					mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames
							.size());
					mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
					if (isSelectDialog) {
						mAdapter.SetIsNewData(true);
						isSelectDialog = false;
					}
					mDensityValueList.setAdapter(mAdapter);
				}
				else {
					/*** 设置最大因子列数 ****/
					mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames
							.size());
					mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
					if (isSelectDialog) {
						mAdapter.SetIsNewData(true);
						isSelectDialog = false;
					}
					mAdapter.notifyDataSetChanged();
				}
			}
			else {
				dialog.hide();
				mItemList.clear();
				mRefreshButton.stop();
				mAdapter = new WaterWeekReportDataAdapter(getActivity(), mItemList, mHead);
				mDensityValueList.setAdapter(mAdapter);
			}
		}
	}
	
	@Override
	public void requestFailed(HttpResponse resData) {
		super.requestFailed(resData);
		dialog.hide();
		mItemList.clear();
		mRefreshButton.stop();
		mAdapter = new WaterWeekReportDataAdapter(getActivity(), mItemList, mHead);
		mDensityValueList.setAdapter(mAdapter);
	}
	/***
	 * 添加一个head
	 * 
	 * @param FactorProperty
	 */
	private void addHead(FactorProperty FactorProperty) {
		viewChild.removeAllViews();
		for (int i = 0; i < FactorProperty.factorNames.size(); i++) {
			addTv(viewChild, FactorProperty.factorNames.get(i));
		}
	}

	/***
	 * 添加一个view
	 * 
	 * @param parent
	 * @param name
	 */
	private void addTv(LinearLayout parent, String name) {
		TextView v = (TextView) View.inflate(parent.getContext(),
				R.layout.tv_item, null);
		v.setText(name);
		v.setTextColor(Color.WHITE);
		LayoutParams params = new LayoutParams(200, LayoutParams.MATCH_PARENT);
		params.height = findViewById(R.id.table_title).getHeight();
		params.leftMargin = params.rightMargin = 10;
		params.gravity = Gravity.CENTER;
		parent.addView(v, params);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	 
	/***
	 * 选中变化
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_listview:
			listLayout.setVisibility(View.VISIBLE);
			lineChartLayout.setVisibility(View.GONE);
			break;
		case R.id.radio_line_chart:
			listLayout.setVisibility(View.GONE);
			lineChartLayout.setVisibility(View.VISIBLE);
			break;
		}
	}

	@Override
	public void selectPortCallBack() {
		isSelectDialog = true;
		dialog.show();
		requestServer();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void updateFace() {
		super.updateFace();
		if (getView() == null) {
			return;
		}
//		findViewById(R.id.date_time).setBackgroundDrawable(
//				StyleController.getDrawableByAttrId(R.attr.time));
	}

	@Override
	public void onPageSelected(int arg0) {
	}
}
