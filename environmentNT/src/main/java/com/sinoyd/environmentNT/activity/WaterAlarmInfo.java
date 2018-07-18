package com.sinoyd.environmentNT.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.adapter.AlarmInfoAdapter;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.DateSelectDialog;
import com.sinoyd.environmentNT.dialog.DateSelectDialog.DateSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.Alarm;
import com.sinoyd.environmentNT.model.Alarm.AlarmItem;
import com.sinoyd.environmentNT.util.DataUtils;
import com.sinoyd.environmentNT.util.DateUtil;

public class WaterAlarmInfo extends RefreshBaseActivity implements OnClickListener, DateSelectListener {
//	private TextView startTime, endTime;
//	private ImageView leftArrow, rightArrow;
//	private String startTimeStr, endTimeStr;
//	/** 请求接口传入参数 **/
//	private HashMap<String, String> requestParams;
//	private AlarmInfoAdapter adapter;
//	private ListView rListView;
//	private Alarm mAlarm;
//	private ImageView imgChange;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_alarminfo);
//	}
//
//	@Override
//	protected void initView() {
//		super.initView();
//		imgChange = (ImageView) findViewById(R.id.change_system);
//		mTitleTextView = (TextView) findViewById(R.id.title);
//		mTitleTextView.setOnClickListener(null);
//		startTime = (TextView) findViewById(R.id.startTime);
//		startTime.setText(DataUtils.getAddDate2(DateUtil.getCurrentTime("yyyy-MM-dd"), -1));
//		startTimeStr = startTime.getText().toString();
//		endTime = (TextView) findViewById(R.id.endTime);
//		endTime.setText(DateUtil.getCurrentTime("yyyy-MM-dd"));
//		endTimeStr = endTime.getText().toString();
//		leftArrow = (ImageView) findViewById(R.id.arrowLeft);
//		leftArrow.setOnClickListener(this);
//		rightArrow = (ImageView) findViewById(R.id.arrowRight);
//		rightArrow.setOnClickListener(this);
//		startTime.setOnClickListener(this);
//		endTime.setOnClickListener(this);
//		findViewById(R.id.flag).setOnClickListener(this);
//		rListView = (ListView) findViewById(R.id.refreshlistview);
//		mAlarm = new Alarm();
//		adapter = new AlarmInfoAdapter(mContext, mAlarm.values);
//		rListView.setAdapter(adapter);
//		mTitleTextView.setText("报警信息");
//		loadRequestServer();
//	}
//
//	/**
//	 * 
//	 * @功能描述：获取数据
//	 * @创建者：刘敏
//	 * @创建日期：2015-4-21
//	 * @维护人员：
//	 * @维护日期：
//	 */
//	public void loadRequestServer() {
//		if (isHasNetWork()) {
//			requestServer();
//		}
//		else {
//			loadNativeData();
//		}
//	}
//
//	@Override
//	public void onResume() {
//		super.onResume();
//	}
//
//	@Override
//	protected void requestServer() {
//		super.requestServer();
//		if (requestParams == null) {
//			requestParams = new HashMap<String, String>();
//		}
////		requestParams.put("startTime", startTimeStr);
////		requestParams.put("endTime", endTimeStr);
//		requestParams.put("startTime", startTime.getText().toString() + " 00:00");
//		requestParams.put("endTime", endTime.getText().toString() + " 23:59");
//		requestParams.put("sysType", "water");
//		HttpClient.getJsonWithGetUrl(AppConfig.RequestWaterActionName.GetWaterAlarmInfo, requestParams, this, null);
//		if (AppConfig.isWatherSystem()) {
//			 
//			imgChange.setImageDrawable(getResources().getDrawable(R.drawable.change_station_icon));
//		}
//		else {
//			 
//			imgChange.setImageDrawable(getResources().getDrawable(R.drawable.more_change_system));
//		}
//		
//		//		requestParams.put("loginId", "test");
////		requestParams.put("pwd", "test");
////		HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLoginInfo, requestParams, this, null);
//	}
//
//	@Override
//	public void requestSuccess(HttpResponse resData) {
//		super.requestSuccess(resData);
//		try {
//			 JSONObject jsonObject =
//			 resData.getJson().optJSONObject("AlarmInfo");
////			JSONObject jsonObject = new JSONObject(AppConfig.RequestWaterActionName.GetWaterAlarmInfo).optJSONObject("AlarmInfo");
//			String isSuccess = jsonObject.optString("IsSuccess");
//			if (isSuccess.equals("true")) {
//				mAlarm.parse(jsonObject);
//				if (mAlarm.values.size() == 0) {
//					loadNoData();
//				}
//				else {
//					adapter.setList(mAlarm.values);
//					adapter.setIsEmptyData(false);
//				}
//			}
//			else {
//				loadNoData();
//			}
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			String mString="";
//		}
//	}
//
//	@Override
//	public void requestFailed(HttpResponse resData) {
//		super.requestFailed(resData);
//		loadNativeData();
//	}
//
//	public void loadNativeData() {
//		try {
//			WhereBuilder wb = WhereBuilder.b();
//			wb.and("AlarmLongTime", ">=", DateUtil.StringToDate(startTime.getText().toString() + " 00:00:00").getTime());
//			wb.and("AlarmLongTime", "<=", DateUtil.StringToDate(endTime.getText().toString() + " 00:00:00").getTime());
//			List<Alarm.AlarmItem> mAlarmItemList = MyApplication.mDB.findAll(Selector.from(Alarm.AlarmItem.class).where(wb));
//			if (mAlarmItemList != null && mAlarmItemList.size() > 0) {
//				adapter.setList(mAlarmItemList);
//				adapter.setIsEmptyData(false);
//			}
//			else {
//				loadNoData();
//			}
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
//
//	/**
//	 * 
//	 * @功能描述：加载空数据,方便用户下拉刷新
//	 * @创建者：刘敏
//	 * @创建日期：2015-4-17
//	 * @维护人员：
//	 * @维护日期：
//	 */
//	public void loadNoData() {
//		mAlarm.values.clear();
//		AlarmItem mAlarmItem = new AlarmItem();
//		mAlarmItem.PortName = "";
//		mAlarmItem.AlarmTime = "";
//		mAlarmItem.AlarmContent = "";
//		mAlarmItem.AlarmProcess = "";
//		mAlarm.values.add(mAlarmItem);
//		adapter.setList(mAlarm.values);
//		adapter.setIsEmptyData(true);
//	}
//
//	@Override
//	public void onClick(View v) {
//		super.onClick(v);
//		switch (v.getId()) {
//		case R.id.startTime:
//		case R.id.arrowLeft:
//			startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);
//			startTime.setText(startTimeStr);
//			requestServer();
//			break;
//		case R.id.endTime:
//		case R.id.arrowRight:
//			endTimeStr = DataUtils.getAddDate2(endTime.getText().toString(), 1);
//			endTime.setText(endTimeStr);
//			requestServer();
//			break;
//		case R.id.flag: // 弹出框选择时间
//			DateSelectDialog dateSelectDialog = new DateSelectDialog(getActivity(), R.style.Theme_Dialog_Transparent);
//			dateSelectDialog.setStartAndEndDate(startTimeStr, endTimeStr);
//			dateSelectDialog.setDateSelectListener(this);
//			dateSelectDialog.show();
//			break;
//		}
//	}
//
//	@Override
//	public void selectDate(String startDate, String endDate) {
//		startTime.setText(startDate);
//		endTime.setText(endDate);
//		startTimeStr = startDate;
//		endTimeStr = endDate;
//		requestServer();
//	}
//
//	@Override
//	public void selectPortCallBack() {
////		portId = portInfo.PortId;
//	}
	private TextView startTime, endTime;
	private ImageView leftArrow, rightArrow;
	private String startTimeStr, endTimeStr;
	/** 请求接口传入参数 **/
	private HashMap<String, String> requestParams;
	private AlarmInfoAdapter adapter;
	private ListView rListView;
	private Alarm mAlarm;
	/** 监测因子名称头文件 **/
	private RelativeLayout mHead;
	private LinearLayout viewChild;
	/** 监测数据实体 **/
	private List<AlarmItem> mItemList;
	/** 气监测数据ListView **/
	private ListView mDensityValueList;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarminfo);
	}

	@Override
	protected void initView() {
		super.initView();
		mHead = (RelativeLayout) findViewById(R.id.table_title);
		viewChild = (LinearLayout) findViewById(R.id.viewChild);
		mHead.setFocusable(true);
		mHead.setClickable(true);
		mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		mTitleTextView = (TextView) findViewById(R.id.title);
		mTitleTextView.setOnClickListener(null);
		startTime = (TextView) findViewById(R.id.startTime);
		startTime.setText(DataUtils.getAddDate2(
				DateUtil.getCurrentTime("yyyy-MM-dd"), -1));
		startTimeStr = startTime.getText().toString();
		endTime = (TextView) findViewById(R.id.endTime);
		endTime.setText(DateUtil.getCurrentTime("yyyy-MM-dd"));
		endTimeStr = endTime.getText().toString();
		leftArrow = (ImageView) findViewById(R.id.arrowLeft);
		leftArrow.setOnClickListener(this);
		rightArrow = (ImageView) findViewById(R.id.arrowRight);
		rightArrow.setOnClickListener(this);
		startTime.setOnClickListener(this);
		endTime.setOnClickListener(this);
		findViewById(R.id.flag).setOnClickListener(this);
		// rListView = (ListView) findViewById(R.id.refreshlistview);
		mAlarm = new Alarm();
		 
		mDensityValueList = (ListView) findViewById(R.id.densityValueList);
		mDensityValueList.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
		//adapter = new AlarmInfoAdapter(getActivity(), new ArrayList<AlarmItem>(), mHead);
//		mDensityValueList.setAdapter(adapter);
		// rListView.setAdapter(adapter);
		mTitleTextView.setText("报警信息");
		findViewById(R.id.change_station_icon).setOnClickListener(null);
		loadRequestServer();
	}

	/**
	 * 
	 * @功能描述：获取数据
	 * @创建者：刘敏
	 * @创建日期：2015-4-21
	 * @维护人员：
	 * @维护日期：
	 */
	public void loadRequestServer() {
		if (isHasNetWork()) {
			requestServer();
		}
		else {
			loadNativeData();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void requestServer() {
		super.requestServer();
		if (requestParams == null) {
			requestParams = new HashMap<String, String>();
		}
		// requestParams.put("startTime", startTimeStr);
		// requestParams.put("endTime", endTimeStr);
		requestParams.put("startTime", startTime.getText().toString()
				+ " 00:00");
		requestParams.put("endTime", endTime.getText().toString() + " 23:59");
		requestParams.put("sysType", "water");
		HttpClient.getJsonWithGetUrl(
				AppConfig.RequestAirActionName.GetAlarmInfo, requestParams,
				this, null);
		// requestParams.put("loginId", "test");
		// requestParams.put("pwd", "test");
		// HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLoginInfo,
		// requestParams, this, null);
	}

	@Override
	public void requestSuccess(HttpResponse resData) {
		super.requestSuccess(resData);
		try {
			JSONObject jsonObject = resData.getJson()
					.optJSONObject("AlarmInfo");
			// JSONObject jsonObject = new
			// JSONObject(AppConfig.RequestWaterActionName.GetWaterAlarmInfo).optJSONObject("AlarmInfo");
			String isSuccess = jsonObject.optString("IsSuccess");
			if (isSuccess.equals("true")) {
				mAlarm.parse(jsonObject);
				if (mAlarm.values.size() == 0) {
					loadNoData();
				}
				else {
					if (mAlarm.values.size() > 0)
						addHead(mAlarm.values.get(0));
					adapter = new AlarmInfoAdapter(getActivity(),
							mAlarm.values, mHead);
					mDensityValueList.setAdapter(adapter);
					adapter.setList(mAlarm.values);
					// adapter.setIsEmptyData(false);
				}
			}
			else {
				loadNoData();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			String mString = "";
		}
	}

	@Override
	public void requestFailed(HttpResponse resData) {
		super.requestFailed(resData);
		loadNativeData();
	}

	public void loadNativeData() {
		try {
			WhereBuilder wb = WhereBuilder.b();
			wb.and("AlarmLongTime",
					">=",
					DateUtil.StringToDate(
							startTime.getText().toString() + " 00:00:00")
							.getTime());
			wb.and("AlarmLongTime",
					"<=",
					DateUtil.StringToDate(
							endTime.getText().toString() + " 00:00:00")
							.getTime());
			List<Alarm.AlarmItem> mAlarmItemList = MyApplication.mDB
					.findAll(Selector.from(Alarm.AlarmItem.class).where(wb));
			if (mAlarmItemList != null && mAlarmItemList.size() > 0) {
				adapter.setList(mAlarmItemList);
				adapter.setIsEmptyData(false);
			}
			else {
				loadNoData();
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 
	 * @功能描述：加载空数据,方便用户下拉刷新
	 * @创建者：刘敏
	 * @创建日期：2015-4-17
	 * @维护人员：
	 * @维护日期：
	 */
	public void loadNoData() {
		mAlarm.values.clear();
		AlarmItem mAlarmItem = new AlarmItem();
		mAlarmItem.PortName = "";
		mAlarmItem.AlarmTime = "";
		mAlarmItem.AlarmContent = "";
		mAlarmItem.AlarmProcess = "";
		mAlarmItem.HandleResult = "";
		mAlarm.values.add(mAlarmItem);
		adapter.setList(mAlarm.values);
		adapter.setIsEmptyData(true);
	}

	/***
	 * 添加一个head
	 * 
	 * @param FactorProperty
	 */
	private void addHead(AlarmItem alarmItem) {
		viewChild.removeAllViews();
		 
		addTv(viewChild, "报警时间",200);
		addTv(viewChild, "报警内容",500);
		addTv(viewChild, "处理结果",300);
	}

	/***
	 * 添加一个view
	 * 
	 * @param parent
	 * @param name
	 */
	private void addTv(LinearLayout parent, String name,int width) {
		TextView v = (TextView) View.inflate(parent.getContext(),
				R.layout.tv_item, null);
		v.setText(name);
		v.setTextColor(Color.WHITE);
		LayoutParams params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
		params.height = findViewById(R.id.table_title).getHeight();
		params.leftMargin = params.rightMargin = 10;
		params.gravity = Gravity.CENTER;
		 
		parent.addView(v, params);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.startTime:
		case R.id.arrowLeft:
//			startTimeStr = DataUtils.getAddDate2(
//					startTime.getText().toString(), -1);
//			startTime.setText(startTimeStr);
//			requestServer();
			startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);


			try {
				if(DateUtil.getDays(dateFormat.parse(startTimeStr),dateFormat.parse(endTimeStr))>31)
				{
					MyApplication.showTextToast("日期范围不能超过1个月!");
					return;
				}
				else
				{
					startTime.setText(startTimeStr);


					requestServer();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case R.id.endTime:
		case R.id.arrowRight:
//			endTimeStr = DataUtils.getAddDate2(endTime.getText().toString(), 1);
//			endTime.setText(endTimeStr);
//			requestServer();


			endTimeStr = DataUtils.getAddDate2(endTime.getText().toString(), 1);

			try {
				if(DateUtil.getDays(dateFormat.parse(startTimeStr),dateFormat.parse(endTimeStr))>31)
				{
					MyApplication.showTextToast("日期范围不能超过1个月!");
					return;
				}
				else
				{
					startTime.setText(startTimeStr);


					requestServer();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case R.id.flag: // 弹出框选择时间
			endTimeStr = endTime.getText().toString();
			startTimeStr = startTime.getText().toString();
			DateSelectDialog dateSelectDialog = new DateSelectDialog(
					getActivity(), R.style.Theme_Dialog_Transparent);
			dateSelectDialog.setStartAndEndDate(startTimeStr, endTimeStr);
			dateSelectDialog.setDateSelectListener(this);
			dateSelectDialog.show();
			break;
		}
	}

	@Override
	public void selectDate(String startDate, String endDate) {
		startTime.setText(startDate);
		endTime.setText(endDate);
		startTimeStr = startDate;
		endTimeStr = endDate;
		requestServer();
	}

	@Override
	public void selectPortCallBack() {
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
}
