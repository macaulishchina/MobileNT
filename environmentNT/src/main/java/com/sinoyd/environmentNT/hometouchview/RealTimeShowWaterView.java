package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iceman.paintdemo.JsonUtil;
import com.iceman.paintdemo.RecordData;
import com.iceman.paintdemo.RecordData.DataItem;
import com.iceman.paintdemo.RecordView;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.FactorDialog;
import com.sinoyd.environmentNT.dialog.FactorDialog.FactorItemSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.model.HoursFactorModel;
import com.sinoyd.environmentNT.model.RealtimeWaterWQ;
import com.sinoyd.environmentNT.model.RealtimeWaterWQDetail;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sinoyd.environmentNT.MyApplication.currentWaterPortInfo;
import static com.sinoyd.environmentNT.MyApplication.showTextToast;

/**
 * 实况界面(单个站点)
 * 
 * @author zz
 * 
 */
public class RealTimeShowWaterView extends LinearLayout implements Handler.Callback, HttpListener, FactorItemSelectListener {
	/******************** 实时水质分析图表 ************************************/
	/** 图表layout **/
	private RelativeLayout wqRecordViewLayout;
	/** 水质等级控件 **/
	private RecordView wqRecordView;
	/** 请求参数 **/
	private HashMap<String, String> requestParams;
	/** 水质等级记录 **/
	private RecordData wqRecordData;
	/** 图表的行列布局设置 **/
	@SuppressWarnings("deprecation")
	private RelativeLayout.LayoutParams chartParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
	private TextView txtRealtime = null;
	/** 图形模式：水质等级柱形模式 **/
	private int chartTypeCOLUMN = RecordView.RECORD_RealTimeWater_COLUMN;
	/** 接口方法地址 **/
	private String requestAction = AppConfig.RequestWaterActionName.GetLatestHourWQ;
	/** 实时水质的最新因子浓度等级值 **/
	private RealtimeWaterWQ mRealtimeWaterWQ;
	/** 柱状图变量 **/
	private static final int REFRESH_CHART_VIEW = 100;
	/** 24小时浓度值变量 **/
	private static final int REFRESH_24HOUR_VIEW = 200;
	/** 最新24小时浓度值 **/
	private RecordData densityRecordData;
	/** 折线图布局 **/
	private RelativeLayout aqiChartLayout;
	/** 画图视图 **/
	private RecordView m24HourRecordView;
	/** 显示实时水质单线 **/
	private int chartType = RecordView.RECORD_RealTimeWater_LINE;
	/** 实时水质24小时浓度值列表 **/
	private List<HoursFactorModel> wqList;
	/** 因子选择控件 **/
	private Button factor;
	/** 选择的因子 **/
	public static String getFactor;
	/** 用于总磷和氨氮的span赋值 **/
	private int span = 50;

	private Context mContext;

	protected Handler mHandler;
	public static boolean isTouchRecordView = false;
	public RealTimeShowWaterView(Context context) {
		super(context);
		mContext = context;
		initView();
	}


	protected void initView() {
		View.inflate(getContext(), R.layout.view_realtimewater, this);
		txtRealtime = (TextView) findViewById(R.id.realtimewater_chart_text);
		wqRecordViewLayout = (RelativeLayout) findViewById(R.id.realtimewater_chartlayout_wq);
		wqRecordViewLayout.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				isTouchRecordView=true;
				return true;
			}
		});
		wqRecordView = (RecordView) findViewById(R.id.realtimewater_recordview_wq);
		wqRecordData = new RecordData();
		wqRecordData.yFields = JsonUtil.getYFiledItem(JsonUtil.getJsonFromAssets(mContext, "YFiled.json"));
		densityRecordData = new RecordData();
		aqiChartLayout = (RelativeLayout) findViewById(R.id.realtimewater_chartlayout_density);
		m24HourRecordView = (RecordView) findViewById(R.id.record_view);
		factor = (Button) findViewById(R.id.realtimewater_sprGene);
		if (MyApplication.factorWaterList != null) {
			getFactor = MyApplication.factorWaterList.get(0);
		}
		factor.setText(getFactor);
		factor.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFactorDialog();
			}
		});
		mHandler = new Handler(this);

//		requestServer();
	}
	class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			request24HourData();
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}










	protected void requestServer() {

		if (requestParams == null) {
			requestParams = new HashMap<String, String>();
		}
		requestAction = AppConfig.RequestWaterActionName.GetLatestHourWQ;
		requestParams.put("portId", currentWaterPortInfo.PortId + "");
		HttpClient.getJsonWithGetUrl(requestAction, requestParams, this, null);
	}

	/**
	 * 刷新24小时浓度值
	 *
	 * @功能描述：TODO
	 * @创建者：刘敏
	 * @创建日期：2015-1-26
	 * @维护人员：
	 * @维护日期：
	 */
	public void request24HourData() {
		requestAction = AppConfig.RequestWaterActionName.Get24HoursFactorDataWater;
		if (requestParams == null) {
			requestParams = new HashMap<String, String>();
		}
		requestParams.put("portId", currentWaterPortInfo.PortId + "");
		requestParams.put("factorName", getFactor);
		HttpClient.getJsonWithGetUrl(requestAction, requestParams, this, null);
	}

//	@Override
//	public void onResume() {
//		super.onResume();
//		if (AppConfig.isWatherSystem()) {
//			if (currentWaterPortInfo != null) {
//				mTitleTextView.setText(currentWaterPortInfo.PortName);
//				if (!portId.equals(currentWaterPortInfo.PortId)) {
//					portId = currentWaterPortInfo.PortId;
//					requestServer();
//				}
//			}
//		}
//	}


	public void UpdateLatestHourWQ(HttpResponse resData)
	{
		if(resData!=null&&resData.getJson()!=null) {

		if (mRealtimeWaterWQ == null) {
			mRealtimeWaterWQ = new RealtimeWaterWQ();
		}
		try {
			JSONObject jsonObject = resData.getJson();
			mRealtimeWaterWQ.parse(jsonObject);
			refreshWQView();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		}
	}

	public void UpdateHoursFactorDataWater(HttpResponse resData)
	{
		if(resData!=null&&resData.getJson()!=null) {
			if (wqList == null)
				wqList = new ArrayList<HoursFactorModel>();
			else
				wqList.clear();

			try {
				JSONObject jsonObject = resData.getJson();
				JSONArray array = jsonObject.optJSONArray("HoursFactorData");
				if (array != null && array.length() > 0) {
					HoursFactorModel model = null;
					for (int i = 0; i < array.length(); i++) {
						model = new HoursFactorModel();
						model.parse(array.optJSONObject(i));
						wqList.add(model);
					}
				}
				refresh24HourDenisty();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void requestSuccess(HttpResponse resData) {

		if (AppConfig.RequestWaterActionName.GetLatestHourWQ.equals(resData.getUri())) {
			JSONObject jsonObject = resData.getJson();
			if (mRealtimeWaterWQ == null) {
				mRealtimeWaterWQ = new RealtimeWaterWQ();
			}
			try {
				mRealtimeWaterWQ.parse(jsonObject);
				refreshWQView();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (AppConfig.RequestWaterActionName.Get24HoursFactorDataWater.equals(resData.getUri())) {
			if (wqList == null)
				wqList = new ArrayList<HoursFactorModel>();
			else
				wqList.clear();
			JSONObject jsonObject = resData.getJson();
			try {
				JSONArray array = jsonObject.optJSONArray("HoursFactorData");
				if (array != null && array.length() > 0) {
					HoursFactorModel model = null;
					for (int i = 0; i < array.length(); i++) {
						model = new HoursFactorModel();
						model.parse(array.optJSONObject(i));
						wqList.add(model);
					}
				}
				refresh24HourDenisty();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void requestFailed(HttpResponse resData) {

	}

	/**
	 * 刷新水质等级图
	 */
	private void refreshWQView() {
		if (mRealtimeWaterWQ != null && mRealtimeWaterWQ.mRealtimeWaterWQDetailList.size() > 0) {
			if (wqRecordData.dataList == null) {
				wqRecordData.dataList = new ArrayList<RecordData.DataItem[]>();
			}
			else {
				wqRecordData.dataList.clear();
			}
			DataItem[] data = new DataItem[mRealtimeWaterWQ.mRealtimeWaterWQDetailList.size()];
			RealtimeWaterWQDetail model = null;
			int[] values = new int[mRealtimeWaterWQ.mRealtimeWaterWQDetailList.size()];
			for (int i = 0; i < mRealtimeWaterWQ.mRealtimeWaterWQDetailList.size(); i++) {
				try {
					values[i] = mRealtimeWaterWQ.mRealtimeWaterWQDetailList.get(i).WQ;
				}
				catch (Exception e) {
					// TODO: handle exception
					values[i] = -1;
				}

			}
			wqRecordData.yFields = JsonUtil.getWaterField();
			for (int i = 0; i < mRealtimeWaterWQ.mRealtimeWaterWQDetailList.size(); i++) {
				model = mRealtimeWaterWQ.mRealtimeWaterWQDetailList.get(i);
				DataItem item = new DataItem();
				item.name = model.factor;
				if (model.WQ == 1) {
					item.data = 50;
				}
				if (model.WQ == 2) {
					item.data = 100;
				}
				if (model.WQ == 3) {
					item.data = 150;
				}
				if (model.WQ == 4) {
					item.data = 200;
				}
				if (model.WQ == 5) {
					item.data = 250;
				}
				if (model.WQ == 6) {
					item.data = 300;
				}
				item.interval = JsonUtil.getInterVal(item.data, wqRecordData.yFields);
				item.value = model.value;
				data[i] = item;
			}
			wqRecordData.dataList.add(data);
		}
		else {
			wqRecordData.dataList.clear();
			showTextToast("暂无数据，请稍后刷新!");
		}
		mHandler.sendEmptyMessage(REFRESH_CHART_VIEW);
	}

//	/**
//	 * 刷新24小时浓度值
//	 */
//	public void refresh24HourDenisty() {
//		if (wqList != null && wqList.size() > 0) {
//			if (densityRecordData.dataList == null) {
//				densityRecordData.dataList = new ArrayList<RecordData.DataItem[]>();
//			}
//			else {
//				densityRecordData.dataList.clear();
//			}
//			DataItem[] data = new DataItem[wqList.size()];
//			HoursFactorModel model = null;
//			int[] values = new int[wqList.size()];
//			for (int i = 0; i < wqList.size(); i++) {
//				try {
//					values[i] = (int) (Double.parseDouble(wqList.get(i).value));
//					if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("电导率")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value));
//					}
//					if (getFactor.equals("水温")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 10);
//					}
//					if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//
//				}
//				catch (Exception e) {
//					// TODO: handle exception
//					values[i]=-1;
//				}
//
//			}
//			densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//
//			if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("电导率") || getFactor.equals("水温")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			span = JsonUtil.getMaxValue(values) * 2 / 6;
//
//			for (int i = 0; i < wqList.size(); i++) {
//				model = wqList.get(i);
//				DataItem item = new DataItem();
//				try {
//
//					item.data = (int) (Double.parseDouble(wqList.get(i).value));
//					if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("电导率")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value));
//					}
//					if (getFactor.equals("水温")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 10);
//					}
//					if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//				}
//				catch (Exception e) {
//					// TODO: handle exception
//					item.data=-1;
//				}
//
//				item.name = model.dateTime.substring(11, 16);
//				item.value = model.value;
//				data[i] = item;
//			}
//			densityRecordData.dataList.add(data);
//		}
//		else {
//			if (densityRecordData.dataList == null) {
//				densityRecordData.dataList = new ArrayList<RecordData.DataItem[]>();
//			}
//			else {
//				densityRecordData.dataList.clear();
//			}
//		}
//		mHandler.sendEmptyMessage(REFRESH_24HOUR_VIEW);
//	}




	/**
	 * 刷新24小时浓度值
	 */
	public void refresh24HourDenisty() {
		if (wqList != null && wqList.size() > 0) {
			if (densityRecordData.dataList == null) {
				densityRecordData.dataList = new ArrayList<RecordData.DataItem[]>();
			}
			else {
				densityRecordData.dataList.clear();
			}
			DataItem[] data = new DataItem[wqList.size()];
			HoursFactorModel model = null;
			float[] values = new float[wqList.size()];
			for (int i = 0; i < wqList.size(); i++) {
				try {
					values[i] = (float) (Double.parseDouble(wqList.get(i).value));
//					if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("电导率")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value));
//					}
//					if (getFactor.equals("水温")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 10);
//					}
//					if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//						values[i] = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}

				}
				catch (Exception e) {
					// TODO: handle exception
					values[i]=-1;
				}

			}
// 			densityRecordData.yFields = JsonUtil.getWaterField((int) (JsonUtil.getMaxValue(values)*2/6));
			densityRecordData.yFields= JsonUtil.getWaterField(
					JsonUtil.getMaxValue(values)/4>=4?(int)((JsonUtil.getMaxValue(values))/4)*4:(JsonUtil
							.getMaxValue(values))/4f);
			// 			int sectionCount = 6;

//			if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("电导率") || getFactor.equals("水温")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//				densityRecordData.yFields = JsonUtil.getWaterField(JsonUtil.getMaxValue(values) * 2 / 6);
//			}
//			span = JsonUtil.getMaxValue(values) * 2 / 6;

			for (int i = 0; i < wqList.size(); i++) {
				model = wqList.get(i);
				DataItem item = new DataItem();
				try {

					item.data = (float) (Double.parseDouble(wqList.get(i).value));
//					if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("电导率")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value));
//					}
//					if (getFactor.equals("水温")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 10);
//					}
//					if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
//					if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//						item.data = (int) (Double.parseDouble(wqList.get(i).value) * 100);
//					}
				}
				catch (Exception e) {
					// TODO: handle exception
					item.data=-1;
				}

				item.name = model.dateTime.substring(11, 16);
				item.value = model.value;
				data[i] = item;
			}
			densityRecordData.dataList.add(data);
		}
		else {
			if (densityRecordData.dataList == null) {
				densityRecordData.dataList = new ArrayList<RecordData.DataItem[]>();
			}
			else {
				densityRecordData.dataList.clear();
			}
			showTextToast("暂无数据，请稍后刷新!");
		}
		mHandler.sendEmptyMessage(REFRESH_24HOUR_VIEW);
	}

	protected void sendMessage(int what) {
		if (mHandler.hasMessages(what)) {
			mHandler.removeMessages(what);
		}
		mHandler.sendEmptyMessage(what);
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
			case REFRESH_CHART_VIEW:
				try {
					txtRealtime.setText(mRealtimeWaterWQ.dateTime);
					wqRecordViewLayout.removeView(wqRecordView);
					wqRecordView = null;
					wqRecordView = new RecordView(mContext);
					wqRecordView.setOnTouchListener(new OnTouchListener() {
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
							isTouchRecordView = true;
							return false;
						}
					});
					wqRecordView.setType(chartTypeCOLUMN);
					wqRecordView.setShowColorVertical(true);
					wqRecordView.setColumnNumber(getResources().getInteger(R.integer.column_x_width));
					RelativeLayout.LayoutParams chartLayoutParams = new RelativeLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//				chartLayoutParams.= Gravity.TOP | Gravity.CENTER_HORIZONTAL;
					wqRecordViewLayout.addView(wqRecordView, chartLayoutParams);

				}
				catch (Exception e) {
					e.printStackTrace();
				}
				wqRecordView.setYNameRomanValue(true);
				wqRecordView.setData(wqRecordData);
				request24HourData();
				break;
			case REFRESH_24HOUR_VIEW:
				try {
					aqiChartLayout.removeView(m24HourRecordView);
					if (densityRecordData.dataList.size() != 0) {
						m24HourRecordView = null;
						m24HourRecordView = new RecordView(mContext);
//					if (getFactor.equals("pH值") || getFactor.equals("溶解氧") || getFactor.equals("总氮") || getFactor.equals("总有机碳") || getFactor.equals("叶绿素a")) {
//						m24HourRecordView.setSectionValue(span / 3);
//					}
//					else if (getFactor.equals("电导率")) {
//						m24HourRecordView.setSectionValue(span / 3);
//					}
//					else	if (getFactor.equals("水温")) {
//						m24HourRecordView.setSectionValue(span / 3);
//					}
//					else if (getFactor.equals("浑浊度") || getFactor.equals("蓝绿藻")) {
//						m24HourRecordView.setSectionValue(span / 3);
//					}
//					else if (getFactor.equals("氨氮") || getFactor.equals("总磷")) {
//						m24HourRecordView.setSectionValue(span / 3);
//					}
//					else if (getFactor.equals("藻密度")) {
//						m24HourRecordView.setSectionValue(span);
//					}
//
//						if (span / 3 != 0)
//							m24HourRecordView.setSectionValue(span / 3);
//						else if(span!=0){
//							m24HourRecordView.setSectionValue(span);
//						}
						m24HourRecordView.setSectionValue(6);
						m24HourRecordView.setFactorName(getFactor);
						m24HourRecordView.setType(chartType);
//					m24HourRecordView.setShowColorVertical(true);
						m24HourRecordView.setLineWidth(getResources().getInteger(R.integer.chart_y_linewidth));

						aqiChartLayout.addView(m24HourRecordView, chartParams);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				m24HourRecordView.setShowColorVertical(false);
				m24HourRecordView.setData(densityRecordData);
				break;
		}
		return true;
	}



	/**
	 * 弹出选择因子列表
	 *
	 * @功能描述：TODO
	 * @创建者：刘敏
	 * @创建日期：2015-1-26
	 * @维护人员：
	 * @维护日期：
	 */
	public void showFactorDialog() {
		FactorDialog dialog = new FactorDialog(mContext, R.style.dialog, MyApplication.factorWaterList);
		dialog.setFactorItemSelectListener(this);
		dialog.show();
	}

	/***
	 * 设置当前的站点信息
	 */
	@Override
	public void selectFactorinfo(String selectFactorInfo) {
		getFactor = selectFactorInfo;
		if (factor != null) {
			factor.setText(getFactor);
		}
		request24HourData();
	}
}
