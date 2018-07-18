package com.sinoyd.environmentNT.activity;

import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.iceman.paintdemo.JsonUtil;
import com.iceman.paintdemo.RecordData;
import com.iceman.paintdemo.RecordData.DataItem;
import com.iceman.paintdemo.RecordView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.AppConfig.RequestWaterActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.CatAdapter;
import com.sinoyd.environmentNT.dialog.DateSelectDialog;
import com.sinoyd.environmentNT.dialog.DateSelectDialog.DateSelectListener;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.AQIForcastModel;
import com.sinoyd.environmentNT.model.AQIHourForcastModel;
import com.sinoyd.environmentNT.model.DayWQModel;
import com.sinoyd.environmentNT.model.GetPrimaryPollutant;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.DataUtils;
import com.sinoyd.environmentNT.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 趋势 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：TrendActivity
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class TrendActivity extends RefreshBaseActivity implements OnCheckedChangeListener, Callback, DateSelectListener {
    /**
     * 水质等级罗马字符
     **/
    public static String[] LUO_MA_NUMBER = {"Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "劣V"};
    /**
     * 画折线图变量
     **/
    private static final int REFRESH_CHART_VIEW = 100;
    /**
     * 请求接口传递参数
     **/
    private HashMap<String, String> requestParams;
    /**
     * 曲线图、柱状图Radio
     **/
    private RadioGroup aqiRadioGroup, chartRadioGroup;
    /**
     * 画图视图
     **/
    private RecordView mRecordView;
    /**
     * 图表layout
     **/
    private LinearLayout aqiChartLayout;
    /**
     * 当前站点Id
     **/
    private String portId = "-1";
    /**
     * 请求接口
     **/
    private String requestAction = RequestWaterActionName.GetDayWQByDatetime;
    /**
     * 趋势分析列表
     **/
    private List<AQIForcastModel> aqiList;
    /**
     * 空气质量AQI小时列表
     **/
    private List<AQIHourForcastModel> aqiHourList;
    /**
     * 水质日均值的等级情况列表
     **/
    private List<DayWQModel> wqList;
//	/** 画折线图 **/
//	private int chartType = RecordView.RECORD_LINE;
    /**
     * 画AQI折线图 坐标加入提示
     **/
    private int chartType = RecordView.RECORD_AQI_LINE;
    /**
     * 折线图数据
     **/
    private RecordData record;
    /**
     * 图表标题
     **/
    private TextView mchartTitle;
    /**
     * 最近24小时
     **/
    private RadioButton hour24Radio;
    /**
     * 最近7天
     **/
    private RadioButton day7Radio;
    /**
     * 图表的行列布局设置
     **/
    private RelativeLayout.LayoutParams chartParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, android.widget.RelativeLayout.LayoutParams.MATCH_PARENT);
    /**
     * 顶部标题
     **/
    private TextView topExplainView;
    private String startTimeStr, endTimeStr, topN;
    private TextView startTime, endTime;
    /**
     * 时间选择layout
     **/
    private LinearLayout timeSelectLayout;
    /**
     * 图表标题内容显示
     **/
    private String chartTitle = "最近24小时";
    private ImageView imgChange;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    LoadDialog dialog = null;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    CatAdapter adapter;
    private String sendGetPrimaryPollutanttype = "24小时";


    //    private int TIME = 5000;  //时隔   刷新界面
    private LineChart mChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend);
        dialog = new LoadDialog(this.getActivity());
//        handler.postDelayed(runnable, TIME); //每隔1s执行
    }


//    Handler handler = new Handler();
//    Runnable runnable = new Runnable() {
//
//        @Override
//        public void run() {
//            // handler自带方法实现定时器
//            try {
//                handler.postDelayed(this, TIME);
//
//                requestServer();
//
//
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//                System.out.println("exception...");
//            }
//        }
//    };


    @Override
    protected void initView() {
        super.initView();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        imgChange = (ImageView) findViewById(R.id.change_system);
        startTimeStr = DataUtils.getWeekDate();
        endTimeStr = DataUtils.getCurrentDate();
        topN = "24";// 第一次进入默认24小时
        record = new RecordData();
        timeSelectLayout = (LinearLayout) findViewById(R.id.date_layout);
        timeSelectLayout.setVisibility(View.GONE);
        findViewById(R.id.flag).setOnClickListener(this);
        startTime = (TextView) findViewById(R.id.startTime);
        startTime.setOnClickListener(this);
        startTime.setText(startTimeStr);
        endTime = (TextView) findViewById(R.id.endTime);
        endTime.setOnClickListener(this);
        endTime.setText(endTimeStr);
        findViewById(R.id.arrowLeft).setOnClickListener(this);
        findViewById(R.id.arrowRight).setOnClickListener(this);
        topExplainView = (TextView) findViewById(R.id.top_explain);
        mchartTitle = (TextView) findViewById(R.id.chart_text);
        hour24Radio = (RadioButton) findViewById(R.id.radio_by_24_hour);
        day7Radio = (RadioButton) findViewById(R.id.radio_by_7_day);
        aqiChartLayout = (LinearLayout) findViewById(R.id.aqi_chart_layout);
        mRecordView = (RecordView) findViewById(R.id.record_view);
        aqiRadioGroup = (RadioGroup) findViewById(R.id.select_aqi_group);
        aqiRadioGroup.setOnCheckedChangeListener(this);
        ((RadioButton) aqiRadioGroup.getChildAt(0)).setChecked(true);
        chartRadioGroup = (RadioGroup) findViewById(R.id.radio_group_chart);
        chartRadioGroup.setOnCheckedChangeListener(this);
        mRecordView.setType(chartType);
        mRecordView.setShowColorVertical(false);
        chartParams.topMargin = 40;
        isClickRefresh = false;
        mChart = (LineChart) findViewById(R.id.chart_zhexiantu);
        if (AppConfig.isWatherSystem()) {
            chartRadioGroup.setVisibility(View.GONE);
            RadioButton lineRadioButton = (RadioButton) findViewById(R.id.radio_line_chart);
            lineRadioButton.setVisibility(View.GONE);
            RadioButton chartRadioButton = (RadioButton) findViewById(R.id.radio_mixed_chart);
            chartRadioButton.setVisibility(View.GONE);
            topN = "7";
            hour24Radio.setText("最近7天");
            day7Radio.setText("最近30天");
            chartTitle = "最近7天";
            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.change_station_icon));
        } else {
            chartTitle = "最近24小时";
            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.more_change_system));
        }
        sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppConfig.isWatherSystem()) {
            if (MyApplication.currentWaterPortInfo != null) {
                mTitleTextView.setText(MyApplication.currentWaterPortInfo.PortName);
                if (!portId.equals(MyApplication.currentWaterPortInfo.PortId)) {
                    portId = MyApplication.currentWaterPortInfo.PortId;
                    requestServer();
                }
            }
        } else {
            if (MyApplication.currentAirPortInfo != null) {
                mTitleTextView.setText(MyApplication.currentAirPortInfo.PortName);
                if (!portId.equals(MyApplication.currentAirPortInfo.PortId)) {
                    portId = MyApplication.currentAirPortInfo.PortId;
                    requestServer();
                }
            }
        }
        sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);

    }

    @Override
    protected void requestServer() {
        super.requestServer();
        dialog.show();
        requestParams = null;
        requestParams = new HashMap<String, String>();
        requestParams.put("portId", portId + "");
        if (AppConfig.isWatherSystem()) {
            requestAction = RequestWaterActionName.GetDayWQByDatetime;
            requestParams.put("startTime", startTimeStr);
            requestParams.put("endTime", endTimeStr);

            if (topN.equals("7")) {
                // 24小时接口
                chartTitle = "最近7天";
                mchartTitle.setText(chartTitle);
                requestAction = RequestWaterActionName.GetTopNDayWQ;
                requestParams.put("topN", topN);
            } else if (topN.equals("30")) {
                // 7天接口
                chartTitle = "最近30天";
                mchartTitle.setText(chartTitle);
                requestAction = RequestWaterActionName.GetTopNDayWQ;
                requestParams.put("topN", topN);
            } else {
                // 自定义接口
                chartTitle = "自定义";
                mchartTitle.setText(chartTitle);
                requestAction = RequestWaterActionName.GetDayWQByDatetime;
                requestParams.put("startDay", startTimeStr);
                requestParams.put("endDay", endTimeStr);
            }


        } else {


            if (topN.equals("24")) {
                // 24小时接口
                chartTitle = "最近24小时";
                mchartTitle.setText(chartTitle);
                requestAction = RequestAirActionName.GetTopNHourAQI;
                requestParams.put("topN", topN);
            } else if (topN.equals("7")) {
                // 7天接口
                chartTitle = "最近7天";
                mchartTitle.setText(chartTitle);
                requestAction = RequestAirActionName.GetTopNDayAQI;
                requestParams.put("topN", topN);
            } else {
                // 自定义接口
                chartTitle = "自定义";
                mchartTitle.setText(chartTitle);
                requestAction = RequestAirActionName.GetDayAQIByDatetime;
                requestParams.put("startDay", startTimeStr);
                requestParams.put("endDay", endTimeStr);
            }
        }


        HttpClient.getJsonWithGetUrl(requestAction, requestParams, this, null);


        sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);

    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        JSONObject jsonObject = resData.getJson();
        if (dialog.isShowing())
            dialog.hide();
        if (aqiList == null) {
            aqiList = new ArrayList<AQIForcastModel>();
        } else {
            aqiList.clear();
        }
        if (aqiHourList == null) {
            aqiHourList = new ArrayList<AQIHourForcastModel>();
        } else {
            aqiHourList.clear();
        }
        if (wqList == null)
            wqList = new ArrayList<DayWQModel>();
        else
            wqList.clear();


        if (RequestWaterActionName.GetDayWQByDatetime.equals(resData.getUri())) {
            try {
                JSONArray array = jsonObject.optJSONArray("DayWQ");
                if (array != null && array.length() > 0) {
                    DayWQModel model = null;
                    for (int i = 0; i < array.length(); i++) {
                        model = new DayWQModel();
                        model.parse(array.optJSONObject(i));
                        wqList.add(model);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            refreshWaterView();


        } else if (RequestWaterActionName.GetTopNDayWQ.equals(resData.getUri())) {
            try {

                JSONArray array = jsonObject.optJSONArray("DayWQ");
                if (array != null && array.length() > 0) {
                    DayWQModel model = null;
                    for (int i = 0; i < array.length(); i++) {
                        model = new DayWQModel();
                        model.parse(array.optJSONObject(i));
                        wqList.add(model);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            refreshWaterView();
        } else {
            JSONArray array = null;
            JSONObject tempObject = null;
            try {
                if (topN.equals("24")) {
                    array = jsonObject.optJSONArray("HourAQI");
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            tempObject = array.optJSONObject(i);
                            aqiHourList.add(new AQIHourForcastModel(tempObject));
                        }
                    }
                    refreshHourView();
                } else {
                    array = jsonObject.optJSONArray("DayAQI");
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            tempObject = array.optJSONObject(i);
                            aqiList.add(new AQIForcastModel(tempObject));
                        }
                    }
                    refreshView();
                }
            } catch (Exception e) {
                showErrorMsg("解析数据失败！");
                e.printStackTrace();
            }
        }
    }


    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        if (dialog.isShowing())
            dialog.hide();
    }

    /**
     * 刷新view的内容
     *
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    private void refreshView() {
        mRecordView.setType(chartType);

        Log.i("scj", aqiList.size() + "");

        if (aqiList != null && aqiList.size() > 0) {
            if (record.dataList == null) {
                record.dataList = new ArrayList<RecordData.DataItem[]>();
            } else {
                record.dataList.clear();
            }
            DataItem[] data = new DataItem[aqiList.size()];
            AQIForcastModel model = null;
            float[] values = new float[aqiList.size()];
            for (int i = 0; i < aqiList.size(); i++) {
                try {
                    values[i] = Float.parseFloat(aqiList.get(i).AQI);
                } catch (Exception e) {
                    // TODO: handle exception
                    values[i] = -1;
                }

            }
            record.yFields = JsonUtil.getCenterYFiledItem(JsonUtil.getMaxValue(values));
            for (int i = 0; i < aqiList.size(); i++) {
                model = aqiList.get(i);
                DataItem item = new DataItem();
                try {
                    item.data = Integer.parseInt(model.AQI);
                } catch (Exception e) {
                    // TODO: handle exception
                    item.data = -1;
                }

                item.name = model.DateTime.substring(model.DateTime.indexOf("-") + 1, model.DateTime.length());
                item.interval = JsonUtil.getInterVal(item.data, record.yFields);
                data[i] = item;
            }
            record.dataList.add(data);
        } else {
            if (record.dataList == null) {
                record.dataList = new ArrayList<RecordData.DataItem[]>();
            } else {
                record.dataList.clear();
            }
        }
        mHandler.sendEmptyMessage(REFRESH_CHART_VIEW);
    }

    /**
     * 刷新24小时view的内容
     *
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    private void refreshHourView() {
        mRecordView.setType(chartType);

        Log.i("scj", aqiHourList.size() + "");

        if (aqiHourList != null && aqiHourList.size() > 0) {
            if (record.dataList == null) {
                record.dataList = new ArrayList<RecordData.DataItem[]>();
            } else {
                record.dataList.clear();
            }
            DataItem[] data = new DataItem[aqiHourList.size()];
            AQIHourForcastModel model = null;
            float[] values = new float[aqiHourList.size()];
            for (int i = 0; i < aqiHourList.size(); i++) {
                try {
                    values[i] = Float.parseFloat(aqiHourList.get(i).AQI);
                } catch (Exception e) {
                    // TODO: handle exception
                    values[i] = -1;
                }

            }
            record.yFields = JsonUtil.getCenterYFiledItem(JsonUtil.getMaxValue(values));
            for (int i = 0; i < aqiHourList.size(); i++) {
                model = aqiHourList.get(i);
                DataItem item = new DataItem();
                try {
                    item.data = Integer.parseInt(model.AQI);

                } catch (Exception e) {
                    // TODO: handle exception
                    item.data = -1;
                }

                item.name = model.DateTime.substring(model.DateTime.indexOf("-") + 1, model.DateTime.length());
                item.interval = JsonUtil.getInterVal(item.data, record.yFields);
                data[i] = item;
            }
            record.dataList.add(data);
        }
        mHandler.sendEmptyMessage(REFRESH_CHART_VIEW);


    }

    /**
     * 刷新水质的内容
     *
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    private void refreshWaterView() {


        mRecordView.setType(chartType);
        record.yFields = JsonUtil.getWaterField();
        if (wqList != null && wqList.size() > 0) {
            if (record.dataList == null) {
                record.dataList = new ArrayList<RecordData.DataItem[]>();
            } else {
                record.dataList.clear();
            }

            Log.i("scj", wqList.size() + "");

            DataItem[] data = new DataItem[wqList.size()];
            DayWQModel model = null;
            int[] values = new int[wqList.size()];
            for (int i = 0; i < wqList.size(); i++) {
                values[i] = wqList.get(i).value;
            }
            for (int i = 0; i < wqList.size(); i++) {
                model = wqList.get(i);
                DataItem item = new DataItem();
                item.data = JsonUtil.getLevelByLuoMaNumber(LUO_MA_NUMBER[Integer.parseInt(model.WQ) - 1]) * 50;
                item.name = model.dateTime.substring(model.dateTime.indexOf("-") + 1, model.dateTime.length());
                item.interval = JsonUtil.getInterVal(item.data, record.yFields);
                data[i] = item;
            }
            record.dataList.add(data);
        } else {
            if (record.dataList == null) {
                record.dataList = new ArrayList<RecordData.DataItem[]>();
            } else {
                record.dataList.clear();
            }
        }
        mHandler.sendEmptyMessage(REFRESH_CHART_VIEW);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REFRESH_CHART_VIEW:
                if (AppConfig.isWatherSystem()) {
                    if (!isClickRefresh) { // 如果是radiobutton切换刷新的话 就需要重新加载数据了view
                        try {
                            mchartTitle.setText(chartTitle);
                            aqiChartLayout.removeView(mRecordView);
                            mRecordView = null;
                            mRecordView = new RecordView(mContext);

                            mRecordView.setType(RecordView.RECORD_COLUMN);
                            mRecordView.setShowColorVertical(true);

                            aqiChartLayout.addView(mRecordView, chartParams);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mRecordView.setYNameRomanValue(true);
                    topExplainView.setText("水质趋势日变化");


                } else {

                    if (!isClickRefresh) { // 如果是radiobutton切换刷新的话 就需要重新加载数据了view
                        try {
                            mchartTitle.setText(chartTitle);
                            aqiChartLayout.removeView(mRecordView);
                            mRecordView = null;
                            mRecordView = new RecordView(mContext);

                            mRecordView.setType(chartType);
//						mRecordView.setIsQuShi(true);
                            mRecordView.setShowColorVertical(true);
                            aqiChartLayout.addView(mRecordView, chartParams);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mRecordView.setYNameRomanValue(false);
                    topExplainView.setText("空气质量指数(AQI)日变化");
                }
                if (record.dataList.size() > 0)
                    mRecordView.setData(record);

                if (chartTitle.equals("最近24小时")) {

                    mRecordView.post(new Runnable() {
                        @Override
                        public void run() {
                            int changdu = mRecordView.getWidth();

                            mRecordView.mDrawOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.mSaveOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.postInvalidate();
                        }
                    });


                }

                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.startTime:
            case R.id.arrowLeft:
//			startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);
//			// 判断开始时间小于一个月前的时间
//			if (DataUtils.StringToDate(startTimeStr).getTime() < DataUtils.StringToDate(DataUtils.getMonthDayDate()).getTime()) {
//				showTextToast("搜索日期不能小于一个月前的时间");
//				return;
//			}
//			startTime.setText(startTimeStr);
//			requestServer();
                startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);


                try {
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 30) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
                        startTime.setText(startTimeStr);


                        requestServer();
                        sendGetPrimaryPollutant("天");
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
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 30) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
                        endTime.setText(endTimeStr);

                        requestServer();
                        sendGetPrimaryPollutant("天");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.flag: // 弹出框选择时间
                endTimeStr = endTime.getText().toString();
                startTimeStr = startTime.getText().toString();
                DateSelectDialog dateSelectDialog = new DateSelectDialog(mContext, R.style.Theme_Dialog_Transparent);
                dateSelectDialog.setStartAndEndDate(startTimeStr, endTimeStr);
                dateSelectDialog.setDateSelectListener(this);
                dateSelectDialog.show();
                break;
        }
    }

    /**
     * 设置日期
     *
     * @param startDate
     * @param endDate
     * @功能描述：TODO
     */
    @Override
    public void selectDate(String startDate, String endDate) {
        // 判断开始时间小于一个月前的时间
        if (DataUtils.StringToDate(startDate).getTime() < DataUtils.StringToDate(DataUtils.getMonthDayDate()).getTime()) {
            showTextToast("搜索日期不能小于一个月前的时间");
            return;
        }
        startTime.setText(startDate);
        endTime.setText(endDate);
        isClickRefresh = false;
        startTimeStr = startDate;
        endTimeStr = endDate;
        requestServer();
        sendGetPrimaryPollutant("天");
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId) {
            case R.id.radio_by_7_day:
                sendGetPrimaryPollutanttype = "7天";
                if (!AppConfig.isWatherSystem()) {
                    chartTitle = "最近7天";
                    topN = "7";
                } else {
                    chartTitle = "最近30天";
                    startTimeStr = DataUtils.getMonthDayDate();
                    endTimeStr = DataUtils.getCurrentDate();
                    topN = "30";
                }
                timeSelectLayout.setVisibility(View.GONE);
                isClickRefresh = false;
                requestServer();

                sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);
                break;
            case R.id.radio_by_24_hour:
                sendGetPrimaryPollutanttype = "24小时";
                if (!AppConfig.isWatherSystem()) {
                    chartTitle = "最近24小时";
                    topN = "24";
                } else {
                    chartTitle = "最近7天";
                    startTimeStr = DataUtils.getWeekDate();
                    endTimeStr = DataUtils.getCurrentDate();
                    topN = "7";
                }
                timeSelectLayout.setVisibility(View.GONE);
                isClickRefresh = false;
                requestServer();

                sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);
                break;
            case R.id.radio_by_custom_day: // 自定义
                sendGetPrimaryPollutanttype = "天";
                chartTitle = "自定义";
                startTime.setText(startTimeStr);
                endTime.setText(endTimeStr);
                startTimeStr = startTime.getText().toString();
                endTimeStr = endTime.getText().toString();
                topN = "0";
                timeSelectLayout.setVisibility(View.VISIBLE);
                isClickRefresh = false;
                requestServer();
                sendGetPrimaryPollutant(sendGetPrimaryPollutanttype);
                break;
            case R.id.radio_line_chart: // 线性图表
                chartType = RecordView.RECORD_LINE;
                if (topExplainView.getText().equals("空气质量指数(AQI)日变化"))
                    chartType = RecordView.RECORD_AQI_LINE;
                mRecordView.setTypeRefresh(chartType);

                if (chartTitle.equals("最近24小时")) {

                    mRecordView.post(new Runnable() {
                        @Override
                        public void run() {
                            int changdu = mRecordView.getWidth();

                            mRecordView.mDrawOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.mSaveOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.postInvalidate();
                        }
                    });


                }
                break;
            case R.id.radio_mixed_chart: // 柱状图表
                chartType = RecordView.RECORD_COLUMN;
                if (topExplainView.getText().equals("空气质量指数(AQI)日变化"))
                    chartType = RecordView.RECORD_AQI_COLUMN;
                mRecordView.setTypeRefresh(chartType);


                if (chartTitle.equals("最近24小时")) {

                    mRecordView.post(new Runnable() {
                        @Override
                        public void run() {
                            int changdu = mRecordView.getWidth();

                            mRecordView.mDrawOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.mSaveOffset = -((int) Math.round(changdu * 1.6));
                            mRecordView.postInvalidate();
                        }
                    });


                }

                break;
        }
    }

    @Override
    public void selectPortinfo(PortInfo portInfo) {
        super.selectPortinfo(portInfo);
        this.portId = portInfo.PortId;
        requestServer();
    }

    @Override
    public void selectPortCallBack() {
        // TODO Auto-generated method stub
    }

    @SuppressWarnings("deprecation")
    @Override
    public void updateFace() {
        super.updateFace();
        if (getView() == null) {
            return;
        }
        if (findViewById(R.id.radio_by_7_day) == null)
            return;
//		findViewById(R.id.radio_by_24_hour).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.aqi_select_btn));
//		findViewById(R.id.radio_by_7_day).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.aqi_select_btn));
//		findViewById(R.id.radio_by_custom_day).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.aqi_select_btn));
//		findViewById(R.id.date_time).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.time));
    }

    GetPrimaryPollutant getPrimaryPollutant = new GetPrimaryPollutant();
    Gson gson = new Gson();

    public void sendGetPrimaryPollutant(String leixing) {
        String url = "";
        if (leixing.equals("24小时")) {
            url = RequestAirActionName.GetPrimaryPollutant + "?portId=" + portId + "&timeType=Hour&startTime=&endTime=";
        }
        if (leixing.equals("天")) {
            url = RequestAirActionName.GetPrimaryPollutant + "?portId=" + portId + "&timeType=Day&startTime=" + startTimeStr + "&endTime=" + endTimeStr;
        }
        if (leixing.equals("7天")) {
            url = RequestAirActionName.GetPrimaryPollutant + "?portId=" + portId + "&timeType=Day&startTime=&endTime=";
        }
        Log.i("scj", "发送请求：" + url);
        HttpUtils utils_Get = new HttpUtils();
        utils_Get.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                Log.i("scj", "发送请求结果：" + responseInfo.result.toString());
                try {
                    getPrimaryPollutant = gson.fromJson(responseInfo.result.toString(), GetPrimaryPollutant.class);
                } catch (Exception e) {
                    showTextToast("getPrimaryPollutan解析失败");
                    return;
                }

                showRecyclerView(getPrimaryPollutant.getData());

            }

            @Override
            public void onFailure(HttpException e, String s) {
                showTextToast("请求失败");
            }
        });

    }

    private void showRecyclerView(List<GetPrimaryPollutant.DataBean> data) {
        Log.i("scj", data.size() + "");
        adapter = new CatAdapter(getActivity(), data);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.scrollToPosition(data.size() - 1);
    }


}