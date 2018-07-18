package com.sinoyd.environmentNT.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.DateSelectDialog;
import com.sinoyd.environmentNT.dialog.DateSelectDialog.DateSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.model.YouLiangDays;
import com.sinoyd.environmentNT.model.YouLiangMonths;
import com.sinoyd.environmentNT.util.DataUtils;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.view.PieView;
import com.sinoyd.environmentNT.view.StackBarChartView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xclcharts.chart.PieData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class YouLiangConditionActivity extends RefreshBaseActivity implements Callback, DateSelectListener {
    /**
     * 画饼图变量
     **/
    private static final int REFRESH_PIE_VIEW = 100;
    /**
     * 画柱状图变量
     **/
    private static final int REFRESH_StackBar_VIEW = 101;
    @SuppressWarnings("deprecation")
    private LinearLayout.LayoutParams chartParams = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, android.widget.LinearLayout.LayoutParams.FILL_PARENT);
    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams = null;
    /**
     * 优良天数列表
     **/
    private List<YouLiangDays> mYouLiangDaysList = null;
    /**
     * 优良月份列表
     **/
    private List<YouLiangMonths> mYouLiangMonthsList = null;
    /**
     * 优良天数的饼图数据源
     **/
    private LinkedList<PieData> mChartDataList = new LinkedList<PieData>();
    /**
     * 饼图视图
     **/
    private PieView mBudgetView = null;
    /**
     * 饼图视图布局
     **/
    private LinearLayout linPieView = null;
    /**
     * 柱状图视图布局
     **/
    private LinearLayout linStackBarView = null;
    /**
     * 柱状图自定义视图
     **/
    private StackBarChartView mStackBarChartView = null;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private TextView txtdaycount = null;
    private TextView txtdayyouliang = null;
    private TextView txtdayyou = null;
    private TextView txtdayliang = null;
    private TextView txtdayqingdu = null;
    private TextView txtdayzhongdu = null;
    private TextView txtdayzhongdu2 = null;
    private TextView txtdayyanzhong = null;
    private String startTimeStr, endTimeStr;
    private TextView startTime, endTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youliangcondition);
    }

    @Override
    protected void initView() {
        super.initView();
        endTimeStr = DataUtils.getCurrentDate();
        startTimeStr = DataUtils.getAddDate(endTimeStr, -30);
        startTime = (TextView) findViewById(R.id.startTime);
        endTime = (TextView) findViewById(R.id.endTime);
        startTime.setText(startTimeStr);
        endTime.setText(endTimeStr);
        findViewById(R.id.arrowLeft).setOnClickListener(this);
        findViewById(R.id.arrowRight).setOnClickListener(this);
        findViewById(R.id.flag).setOnClickListener(this);
        startTime.setOnClickListener(this);
        endTime.setOnClickListener(this);
        mBudgetView = (PieView) findViewById(R.id.youliangcondition_budget);
        linPieView = (LinearLayout) findViewById(R.id.youliangcondition_lin_pieview);
        mStackBarChartView = (StackBarChartView) findViewById(R.id.youliangcondition_stackbar);
        linStackBarView = (LinearLayout) findViewById(R.id.youliangcondition_lin_stackbar);
        txtdaycount = (TextView) findViewById(R.id.youliangcondition_txt_daycounts);
        txtdayyouliang = (TextView) findViewById(R.id.youliangcondition_txt_dayyouliang);
        txtdayyou = (TextView) findViewById(R.id.youliangcondition_txt_dayyou);
        txtdayliang = (TextView) findViewById(R.id.youliangcondition_txt_dayliang);
        txtdayqingdu = (TextView) findViewById(R.id.youliangcondition_txt_dayqingdu);
        txtdayzhongdu = (TextView) findViewById(R.id.youliangcondition_txt_dayzhongdu);
        txtdayzhongdu2 = (TextView) findViewById(R.id.youliangcondition_txt_dayzhongdu2);
        txtdayyanzhong = (TextView) findViewById(R.id.youliangcondition_txt_dayyanzhong);
        requestServer();
    }

    @Override
    public void selectDate(String startDate, String endDate) {
        // TODO Auto-generated method stub
        startTime.setText(startDate);
        endTime.setText(endDate);
        isClickRefresh = false;
        startTimeStr = startDate;
        endTimeStr = endDate;
        requestServer();
    }

    @Override
    public void selectPortinfo(PortInfo portInfo) {
        super.selectPortinfo(portInfo);
    }

    @Override
    protected void requestServer() {
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        requestParams.put("portId", portId);
        requestParams.put("startTime", startTimeStr);
        requestParams.put("endTime", endTimeStr);
        HttpClient.getJsonWithGetUrl(RequestAirActionName.GetAirClassDayStruct, requestParams, this, null);
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        requestParams.put("portId", portId);
        requestParams.put("topN", "12");
        HttpClient.getJsonWithGetUrl(RequestAirActionName.GetAirClassMonthStruct, requestParams, this, null);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.startTime:
            case R.id.arrowLeft:
//			startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);
//			startTime.setText(startTimeStr);
//			requestServer();
                startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);


                try {
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 31) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
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
//			startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);
                try {
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 30) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
                        endTime.setText(endTimeStr);

                        requestServer();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.flag: // 弹出框选择时间
                endTimeStr = endTime.getText().toString();
                startTimeStr = startTime.getText().toString();
//
//                Activity activity = YouLiangConditionActivity.this;
//
//                while (activity.getParent() != null) {
//                    activity = activity.getParent();
//                }
                try {

                    DateSelectDialog dateSelectDialog = new DateSelectDialog(mContext, R.style.Theme_Dialog_Transparent);
                    dateSelectDialog.setStartAndEndDate(startTimeStr, endTimeStr);
                    dateSelectDialog.setDateSelectListener(this);
                    dateSelectDialog.show();


                } catch (Exception e) {
                    Log.e("AlertDialog  Exception:", e.getMessage());
                }


                break;
        }
    }

    @Override
    public void requestSuccess(HttpResponse resData) {


        if (mRefreshButton != null) {
            mRefreshButton.stop();
        }
        if (resData.getUri().equals(RequestAirActionName.GetAirClassDayStruct)) {
            txtdayyou.setText("0");
            txtdayliang.setText("0");
            txtdayqingdu.setText("0");
            txtdayzhongdu.setText("0");
            txtdayzhongdu2.setText("0");
            txtdayyanzhong.setText("0");
            JSONObject mJSONObject = resData.getJson();
            JSONArray mJSONArray = mJSONObject.optJSONArray("ClassInfo");
            if (mJSONArray != null && mJSONArray.length() > 0) {
                if (mYouLiangDaysList == null) {
                    mYouLiangDaysList = new ArrayList<YouLiangDays>();
                } else {
                    mYouLiangDaysList.clear();
                }
                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject jsonObj2 = mJSONArray.optJSONObject(i);
                    YouLiangDays obj = new YouLiangDays();
                    try {
                        obj.parse(jsonObj2);
                        mYouLiangDaysList.add(obj);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                if (mYouLiangDaysList != null)
                    mYouLiangDaysList.clear();
            }
            if (mYouLiangDaysList != null)
                RefreshPie();
        }
        if (resData.getUri().equals(RequestAirActionName.GetAirClassMonthStruct)) {
            JSONObject mJSONObject = resData.getJson();
            JSONArray mJSONArray = mJSONObject.optJSONArray("ClassInfo");
            if (mJSONArray != null && mJSONArray.length() > 0) {
                if (mYouLiangMonthsList == null) {
                    mYouLiangMonthsList = new ArrayList<YouLiangMonths>();
                } else {
                    mYouLiangMonthsList.clear();
                }
                for (int i = 0; i < mJSONArray.length(); i++) {
                    JSONObject jsonObj2 = mJSONArray.optJSONObject(i);
                    YouLiangMonths obj = new YouLiangMonths();
                    try {
                        obj.parse(jsonObj2);
                        mYouLiangMonthsList.add(obj);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } else {
                if (mYouLiangMonthsList != null)
                    mYouLiangMonthsList.clear();
            }
            if (mYouLiangMonthsList != null)
                RefreshStackBar();
        }
    }

    private void RefreshStackBar() {
        mHandler.sendEmptyMessage(REFRESH_StackBar_VIEW);
    }

    /**
     * 刷新饼图
     */
    private void RefreshPie() {
        if (mChartDataList == null) {
            mChartDataList = new LinkedList<PieData>();
        } else {
            mChartDataList.clear();
        }
        for (int i = 0; i < mYouLiangDaysList.size(); i++) {
            if (mYouLiangDaysList.get(i).DayCount != 0) {
                if (mYouLiangDaysList.get(i).ClassName.equals("优")) {
                    mChartDataList.add(new PieData("0", "优-(" + mYouLiangDaysList.get(i).Percentage + ")%", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(1, 228, 1)));
                }
                if (mYouLiangDaysList.get(i).ClassName.equals("良")) {
                    mChartDataList.add(new PieData("1", "良-(" + mYouLiangDaysList.get(i).Percentage + ")%", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(255, 255, 1)));
                }
                if (mYouLiangDaysList.get(i).ClassName.equals("轻度污染")) {
                    mChartDataList.add(new PieData("2", "轻度污染(" + mYouLiangDaysList.get(i).Percentage + "%)", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(254, 165, 0)));
                }
                if (mYouLiangDaysList.get(i).ClassName.equals("中度污染")) {
                    mChartDataList.add(new PieData("3", "中度污染(" + mYouLiangDaysList.get(i).Percentage + "%)", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(251, 0, 17)));
                }
                if (mYouLiangDaysList.get(i).ClassName.equals("重度污染")) {
                    mChartDataList.add(new PieData("4", "重度污染(" + mYouLiangDaysList.get(i).Percentage + "%)", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(155, 1, 73)));
                }
                if (mYouLiangDaysList.get(i).ClassName.equals("严重污染")) {
                    mChartDataList.add(new PieData("4", "严重污染(" + mYouLiangDaysList.get(i).Percentage + "%)", mYouLiangDaysList.get(i).Percentage, (int) Color.rgb(155, 1, 73)));
                }
            }
        }
        Integer youliangday = 0;

        for (int i = 0; i < mYouLiangDaysList.size(); i++) {
            if (mYouLiangDaysList.get(i).ClassName.equals("优")) {
                txtdayyou.setText(mYouLiangDaysList.get(i).DayCount.toString());
                youliangday = youliangday + mYouLiangDaysList.get(i).DayCount;
            }
            if (mYouLiangDaysList.get(i).ClassName.equals("良")) {
                txtdayliang.setText(mYouLiangDaysList.get(i).DayCount.toString());
                youliangday = youliangday + mYouLiangDaysList.get(i).DayCount;
            }
            if (mYouLiangDaysList.get(i).ClassName.equals("轻度污染")) {
                txtdayqingdu.setText(mYouLiangDaysList.get(i).DayCount.toString());
            }
            if (mYouLiangDaysList.get(i).ClassName.equals("中度污染")) {
                txtdayzhongdu.setText(mYouLiangDaysList.get(i).DayCount.toString());
            }
            if (mYouLiangDaysList.get(i).ClassName.equals("重度污染")) {
                txtdayzhongdu2.setText(mYouLiangDaysList.get(i).DayCount.toString());
            }
            if (mYouLiangDaysList.get(i).ClassName.equals("严重污染")) {
                txtdayyanzhong.setText(mYouLiangDaysList.get(i).DayCount.toString());
            }
        }
        txtdayyouliang.setText(youliangday.toString());
        Integer daycount = DateUtil.getDays(DateUtil.getDate(startTimeStr, "yyyy-MM-dd"), DateUtil.getDate(endTimeStr, "yyyy-MM-dd")) + 1;
        if (daycount != -1) {
            txtdaycount.setText(daycount.toString());
        } else {
            txtdaycount.setText(daycount.toString());
        }
        mHandler.sendEmptyMessage(REFRESH_PIE_VIEW);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case REFRESH_PIE_VIEW:
                linPieView.removeView(mBudgetView);
                mBudgetView = null;
                mBudgetView = new PieView(mContext);
                mBudgetView.setData(mChartDataList);
                linPieView.addView(mBudgetView, chartParams);
                break;
            case REFRESH_StackBar_VIEW:
                linStackBarView.removeView(mStackBarChartView);
                mStackBarChartView = null;
                mStackBarChartView = new StackBarChartView(mContext);
                mStackBarChartView.setChartDataSet(mYouLiangMonthsList);
                linStackBarView.addView(mStackBarChartView, chartParams);
                break;
        }
        return super.handleMessage(msg);
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
    }

    @Override
    public void selectPortCallBack() {
        // TODO Auto-generated method stub
        requestServer();
    }
}
