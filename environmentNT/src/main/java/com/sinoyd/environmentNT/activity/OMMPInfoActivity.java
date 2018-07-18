package com.sinoyd.environmentNT.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.AppConfig.SystemType;
import com.sinoyd.environmentNT.adapter.OMMPInfoAdapter;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.DateSelectDialog;
import com.sinoyd.environmentNT.dialog.DateSelectDialog.DateSelectListener;
import com.sinoyd.environmentNT.model.OMMPInfoModel;
import com.sinoyd.environmentNT.util.DataUtils;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.view.MyHScrollView;
import com.sinoyd.environmentNT.view.VerticalScrollView;

public class OMMPInfoActivity extends RefreshBaseActivity implements
		OnClickListener, DateSelectListener {
	private TextView startTime, endTime, tv_planfinishdate, tv_realfinishdate;
	private ImageView leftArrow, rightArrow;
	private String startTimeStr, endTimeStr;
	/** 请求接口传入参数 **/
	private HashMap<String, String> requestParams;
	private OMMPInfoAdapter adapter;
	private VerticalScrollView rListView;
	private List<OMMPInfoModel> mOMMPInfoList;
	private int Request_Code_GetOMMPInfo = 100;
	boolean tvplanclick = false;
	boolean tvrealclick = false;
	private ImageView imgChange;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ommpinfo);
	}

	@Override
	protected void initView() {
		super.initView();
		imgChange = (ImageView) findViewById(R.id.change_system);
		mTitleTextView = (TextView) findViewById(R.id.title);
		mTitleTextView.setOnClickListener(null);
		startTime = (TextView) findViewById(R.id.startTime);
		startTime.setText(DataUtils.getAddDate2(
				DateUtil.getCurrentTime("yyyy-MM-dd"), -7));
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
		rListView = (VerticalScrollView) findViewById(R.id.refreshlistview);
		tv_planfinishdate = (TextView) findViewById(R.id.tv_planfinishdate);
		tv_realfinishdate = (TextView) findViewById(R.id.tv_realfinishdate);
		final Spinner spinner = (Spinner) findViewById(R.id.sp_status);
		tv_planfinishdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (tvplanclick) {
						String Type = spinner.getSelectedItem().toString();
						if (!Type.equals("全部"))
							mOMMPInfoList = mDB.findAll(Selector
									.from(OMMPInfoModel.class)
									.where("Status", "=", Type)
									.orderBy("PlanfinishDate", false));
						else {
							mOMMPInfoList = mDB.findAll(Selector.from(
									OMMPInfoModel.class).orderBy(
									"PlanfinishDate", false));
						}
						// mOMMPInfoList=
						// mDB.findAll(Selector.from(OMMPInfoModel.class).where("Status",
						// "=", Type).orderBy("PlanfinishDate", false));
						if (mOMMPInfoList != null) {
							adapter.setList(mOMMPInfoList);
							adapter.notifyDataSetChanged();
						}
						tvplanclick = false;
						Drawable drawable= getResources()
								.getDrawable(R.drawable.icon_arrow_top_tv);  
							/// 这一步必须要做,否则不会显示.  
							drawable.setBounds(0, 0, drawable.getMinimumWidth(), 
								drawable.getMinimumHeight());  
							tv_planfinishdate.setCompoundDrawables(null,null,drawable,null);  
					}
					else {
						String Type = spinner.getSelectedItem().toString();
						if (!Type.equals("全部"))
							mOMMPInfoList = mDB.findAll(Selector
									.from(OMMPInfoModel.class)
									.where("Status", "=", Type)
									.orderBy("PlanfinishDate", true));
						else {
							mOMMPInfoList = mDB.findAll(Selector.from(
									OMMPInfoModel.class).orderBy(
									"PlanfinishDate", true));
						}
						// mOMMPInfoList=
						// mDB.findAll(Selector.from(OMMPInfoModel.class).where("Status",
						// "=", Type).orderBy("PlanfinishDate", false));
						if (mOMMPInfoList != null) {
							adapter.setList(mOMMPInfoList);
							adapter.notifyDataSetChanged();
						}
						tvplanclick = true;
						Drawable drawable= getResources()
								.getDrawable(R.drawable.icon_arrow_bottom_tv);  
							/// 这一步必须要做,否则不会显示.  
							drawable.setBounds(0, 0, drawable.getMinimumWidth(), 
								drawable.getMinimumHeight());  
							tv_planfinishdate.setCompoundDrawables(null,null,drawable,null);  
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		tv_realfinishdate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (tvrealclick) {
						String Type = spinner.getSelectedItem().toString();
						if (!Type.equals("全部"))
							mOMMPInfoList = mDB.findAll(Selector
									.from(OMMPInfoModel.class)
									.where("Status", "=", Type)
									.orderBy("RealfinishDate", false));
						else {
							mOMMPInfoList = mDB.findAll(Selector.from(
									OMMPInfoModel.class).orderBy(
									"PlanfinishDate", false));
						}
						// mOMMPInfoList=
						// mDB.findAll(Selector.from(OMMPInfoModel.class).where("Status",
						// "=", Type).orderBy("PlanfinishDate", false));
						if (mOMMPInfoList != null) {
							adapter.setList(mOMMPInfoList);
							adapter.notifyDataSetChanged();
						}
						tvrealclick = false;
						Drawable drawable= getResources()
								.getDrawable(R.drawable.icon_arrow_top_tv);  
							/// 这一步必须要做,否则不会显示.  
							drawable.setBounds(0, 0, drawable.getMinimumWidth(), 
								drawable.getMinimumHeight());  
							tv_realfinishdate.setCompoundDrawables(null,null,drawable,null);  
					}
					else {
						String Type = spinner.getSelectedItem().toString();
						if (!Type.equals("全部"))
							mOMMPInfoList = mDB.findAll(Selector
									.from(OMMPInfoModel.class)
									.where("Status", "=", Type)
									.orderBy("RealfinishDate", true));
						else {
							mOMMPInfoList = mDB.findAll(Selector.from(
									OMMPInfoModel.class).orderBy(
									"PlanfinishDate", true));
						}
						// mOMMPInfoList=
						// mDB.findAll(Selector.from(OMMPInfoModel.class).where("Status",
						// "=", Type).orderBy("PlanfinishDate", false));
						if (mOMMPInfoList != null) {
							adapter.setList(mOMMPInfoList);
							adapter.notifyDataSetChanged();
						}
						tvrealclick = true;
						Drawable drawable= getResources()
								.getDrawable(R.drawable.icon_arrow_bottom_tv);  
							/// 这一步必须要做,否则不会显示.  
							drawable.setBounds(0, 0, drawable.getMinimumWidth(), 
								drawable.getMinimumHeight());  
							tv_realfinishdate.setCompoundDrawables(null,null,drawable,null);  
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		final MyHScrollView headview = (MyHScrollView) findViewById(R.id.headscrollview);
		rListView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				headview.onTouchEvent(event);
				return false;
			}
		});
		List<String> list = new ArrayList<String>();
		list.add("全部");
		list.add("已完成");
		list.add("未完成");
		ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(
				this.getActivity(), R.layout.simple_spinner_item, list);
		spinneradapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinneradapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				// if(drawable.equals(spinner.))
				// spinner.setBackgroundResource(R.drawable.icon_arrow_bottom);
				// else
				//
				// spinner.setBackgroundResource(R.drawable.icon_arrow_top);
				String Type = spinner.getSelectedItem().toString();
				try {
					if (!Type.equals("全部"))
						mOMMPInfoList = mDB
								.findAll(Selector.from(OMMPInfoModel.class)
										.where("Status", "=", Type));
					else {
						mOMMPInfoList = mDB.findAll(Selector
								.from(OMMPInfoModel.class));
					}
					if (mOMMPInfoList != null) {
						adapter.setList(mOMMPInfoList);
						adapter.notifyDataSetChanged();
					}
					else {
						mOMMPInfoList = new ArrayList<OMMPInfoModel>();
					}
				}
				catch (Exception e) {
					// TODO: handle exception
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		mOMMPInfoList = new ArrayList<OMMPInfoModel>();
		adapter = new OMMPInfoAdapter(mContext, mOMMPInfoList, headview);
		rListView.setAdapter(adapter);
		//loadRequestServer();
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
			// loadNativeData();
		}
		// loadNativeData();
	}

	/**
	 * HTTP数据请求
	 * 
	 * @param serverUrl 请求地址
	 * @param params 请求参数
	 * @param method 请求调用方法
	 * @param obj
	 * @param requestCode 请求标识代码
	 * 
	 * @throws Exception
	 */
	public void sendHttpRequest(String serverUrl, RequestParams params,
			HttpRequest.HttpMethod method, JSONObject obj, int requestCode)
			throws Exception {
		final int code = requestCode;
		if (obj != null) {
			StringEntity entity = new StringEntity(obj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json;charset=UTF-8"));
			params.setBodyEntity(entity);
		}
		mHttp.send(method, serverUrl, params, new RequestCallBack<String>() {
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				// super.onStart();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				if (mRefreshButton != null) {
					mRefreshButton.stop();
				}
				Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				requestSuccess(responseInfo.result, code);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (AppConfig.isWatherSystem()) {
			if (MyApplication.currentWaterPortInfo != null) {
				mTitleTextView
						.setText(MyApplication.currentWaterPortInfo.PortName);
				if (!portId.equals(MyApplication.currentWaterPortInfo.PortId)) {
					portId = MyApplication.currentWaterPortInfo.PortId;
					requestServer();
				}
				else {
					requestServer();
				}
			}
		}
		else {
			if (MyApplication.currentAirPortInfo != null) {
				mTitleTextView
						.setText(MyApplication.currentAirPortInfo.PortName);
				if (!portId.equals(MyApplication.currentAirPortInfo.PortId)) {
					portId = MyApplication.currentAirPortInfo.PortId;
					requestServer();
				}
				else {
					requestServer();
				}
			}
		}
	}

	@Override
	protected void requestServer() {
		super.requestServer();
		// HttpClient.getJsonWithGetUrl("http://222.92.77.251:8083/V02WebServiceForOutSZ/WebServiceForOutData.asmx/GetTaskInfoByDateTime",
		// requestParams, this, null);
		if (AppConfig.isWatherSystem()) {
			 
			imgChange.setImageDrawable(getResources().getDrawable(R.drawable.change_station_icon));
		}
		else {
			 
			imgChange.setImageDrawable(getResources().getDrawable(R.drawable.more_change_system));
		}
		RequestParams params = new RequestParams();
		params.addBodyParameter("strStartDate", startTime.getText().toString());
		params.addBodyParameter("strEndDate", endTime.getText().toString());
		if (AppConfig.systemType == SystemType.WatherType)
			params.addBodyParameter("sysType", "water");
		else if (AppConfig.systemType == SystemType.AirType)
			params.addBodyParameter("sysType", "air");
		if (!portId.equals("0"))
			params.addBodyParameter("pointIds", portId);
		else
			params.addBodyParameter("pointIds", "");
		try {
			sendHttpRequest(
					"http://222.92.77.251:8083/V02WebServiceForOutSZ/WebServiceForOutData.asmx/GetTaskInfoByDateTime",
					params, HttpRequest.HttpMethod.POST, null,
					Request_Code_GetOMMPInfo);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void requestSuccess(String responseInfo, int Code) {
		if (mRefreshButton != null) {
			mRefreshButton.stop();
		}
		try {
			if (mOMMPInfoList != null)
				mOMMPInfoList.clear();
			JSONArray jsonArray = new JSONArray(responseInfo);
			String ReadlEnddate = "";
			for (int i = 0; i < jsonArray.length(); i++) {
				OMMPInfoModel model = new OMMPInfoModel();
				model.OperateUser = jsonArray.optJSONObject(i).optString(
						"OperateUserName");
				model.PlanfinishDate = jsonArray.optJSONObject(i).optString(
						"EndDatePlan");
				ReadlEnddate = jsonArray.optJSONObject(i).optString(
						"EndDateActual");
				model.RealfinishDate = ReadlEnddate.equals("") ? "--"
						: ReadlEnddate;
				model.StationName = jsonArray.optJSONObject(i).optString(
						"MonitoringPointName");
				model.Status = jsonArray.optJSONObject(i).optString(
						"statustext");
				model.TaskName = jsonArray.optJSONObject(i).optString(
						"TaskName");
				mOMMPInfoList.add(model);
			}
			if (mOMMPInfoList != null) {
				adapter.notifyDataSetChanged();
				mDB.deleteAll(OMMPInfoModel.class);
				mDB.saveAll(mOMMPInfoList);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			mOMMPInfoList.clear();
			adapter.notifyDataSetChanged();
			Toast.makeText(getActivity(), "查询数据错误", Toast.LENGTH_SHORT).show();
		}
	}

	public void loadNativeData() {
		// try {
		// WhereBuilder wb = WhereBuilder.b();
		// wb.and("AlarmLongTime", ">=",
		// DateUtil.StringToDate(startTime.getText().toString() +
		// " 00:00:00").getTime());
		// wb.and("AlarmLongTime", "<=",
		// DateUtil.StringToDate(endTime.getText().toString() +
		// " 00:00:00").getTime());
		// List<OMMPInfoModel> mOMMPInfoModelList =
		// AbstractActivity.mDB.findAll(Selector.from(OMMPInfoModel.class).where(wb));
		// if (mOMMPInfoModelList != null && mOMMPInfoModelList.size() > 0) {
		// adapter.setList(mOMMPInfoModelList);
		// // adapter.setIsEmptyData(false);
		// }
		// else {
		// loadNoData();
		// }
		// }
		// catch (Exception e) {
		// // TODO: handle exception
		// }
		for (int i = 0; i < 30; i++) {
			OMMPInfoModel model = new OMMPInfoModel();
			model.OperateUser = "测试人员";
			model.PlanfinishDate = "2016-01-05";
			model.RealfinishDate = "2016-10-05";
			model.StationName = "南门";
			model.Status = "已完成";
			model.TaskName = "主要任务";
			mOMMPInfoList.add(model);
		}
		adapter.setList(mOMMPInfoList);
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
		// mAlarm.values.clear();
		// AlarmItem mAlarmItem = new AlarmItem();
		// mAlarmItem.PortName = "";
		// mAlarmItem.AlarmTime = "";
		// mAlarmItem.AlarmContent = "";
		// mAlarmItem.AlarmProcess = "";
		// mAlarm.values.add(mAlarmItem);
		// adapter.setList(mAlarm.values);
		// adapter.setIsEmptyData(true);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.startTime:
		case R.id.arrowLeft:
			startTimeStr = DataUtils.getAddDate2(
					startTime.getText().toString(), -1);
			startTime.setText(startTimeStr);
			requestServer();
			break;
		case R.id.endTime:
		case R.id.arrowRight:
			endTimeStr = DataUtils.getAddDate2(endTime.getText().toString(), 1);
			endTime.setText(endTimeStr);
			requestServer();
			break;
		case R.id.flag: // 弹出框选择时间
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
		requestServer();
	}
}
