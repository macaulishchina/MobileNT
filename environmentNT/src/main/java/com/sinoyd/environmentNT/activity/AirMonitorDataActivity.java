package com.sinoyd.environmentNT.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.util.LogUtils;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.Entity.JsonobjHistoryData;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.MonitorDataAdapter;
import com.sinoyd.environmentNT.common.BizCommon;
import com.sinoyd.environmentNT.data.FactorProperty;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.dialog.DateSelectDialog;
import com.sinoyd.environmentNT.dialog.DateSelectDialog.DateSelectListener;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.reflect.StyleController;
import com.sinoyd.environmentNT.util.DataUtils;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class AirMonitorDataActivity extends RefreshBaseActivity implements OnPageChangeListener, OnClickListener, DateSelectListener, OnCheckedChangeListener {


    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;
    /**
     * 当前站点名称
     **/
    private TextView mTitleView;
    /**
     * 气监测数据ListView
     **/
    private ListView mDensityValueList;
    /**
     * 监测数据适配器
     **/
    private MonitorDataAdapter mAdapter;
    /**
     * 监测数据实体
     **/
    private List<FactorProperty> mItemList;
    private TextView startTime, endTime;
    private ImageView leftArrow, rightArrow;
    private String startTimeStr, endTimeStr;
    private LinearLayout listLayout, lineChartLayout;
    /**
     * 监测因子名称头文件
     **/
    private RelativeLayout mHead;
    private LinearLayout viewChild;
    LoadDialog dialog;
    boolean isSelectDialog = false;

    /***因子名称最大列数***/
    int MaxColumnCount = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_monitor_data);
//		RefWatcher refWatcher = MyApplication.getRefWatcher(this.getActivity());
//	    refWatcher.watch(this);
    }

    @Override
    protected void initView() {
        super.initView();
        listLayout = (LinearLayout) findViewById(R.id.listview_layout);
        lineChartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        ((RadioGroup) findViewById(R.id.radio_group_chart)).setOnCheckedChangeListener(this);
        mHead = (RelativeLayout) findViewById(R.id.table_title);
        viewChild = (LinearLayout) findViewById(R.id.viewChild);
        mHead.setFocusable(true);
        mHead.setClickable(true);
        mHead.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        ((TextView) mHead.findViewById(R.id.date)).setTextColor(Color.WHITE);
        mTitleView = (TextView) findViewById(R.id.title);
        startTime = (TextView) findViewById(R.id.startTime);
        startTime.setText(DataUtils.getAddDate2(DateUtil.getCurrentTime("yyyy-MM-dd"), -1));
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
        mItemList = new ArrayList<FactorProperty>();
        mDensityValueList = (ListView) findViewById(R.id.densityValueList);
        mDensityValueList.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
        mDensityValueList.setAdapter(mAdapter);
        dialog = new LoadDialog(this.mContext);
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead.findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.currentAirPortInfo != null) {
            mTitleTextView.setText(MyApplication.currentAirPortInfo.PortName);
            portId = MyApplication.currentAirPortInfo.PortId;
        }
        loadView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
//		 RefWatcher refWatcher = MyApplication.getRefWatcher(getActivity());
//		    refWatcher.watch(this);
    }


    private void loadView() {
        if (MyApplication.currentAirPortInfo != null) {
            portId = MyApplication.currentAirPortInfo.PortId;
        }
        List<PortInfo> positionList = PortCacheUtils.getAirPortList();
        if (positionList == null) {
            return;
        }
        loadData();
        requestServer();
        System.gc();
    }

    /***
     * 加载数据
     */
    private void loadData() {
        mTitleView.setText(MyApplication.currentAirPortInfo.PortName);
        portId = MyApplication.currentAirPortInfo.PortId;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.startTime:
            case R.id.arrowLeft:
                startTimeStr = DataUtils.getAddDate2(startTime.getText().toString(), -1);


                try {
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 30) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
                        startTime.setText(startTimeStr);
                        dialog.show();
                        requestServer();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.endTime:
            case R.id.arrowRight:


                endTimeStr = DataUtils.getAddDate2(endTime.getText().toString(), 1);

                try {
                    if (DateUtil.getDays(dateFormat.parse(startTimeStr), dateFormat.parse(endTimeStr)) > 30) {
                        MyApplication.showTextToast("日期范围不能超过1个月!");
                        return;
                    } else {
                        endTime.setText(endTimeStr);
                        dialog.show();
                        requestServer();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.flag: // 弹出框选择时间
                endTimeStr = endTime.getText().toString();
                startTimeStr = startTime.getText().toString();
                DateSelectDialog dateSelectDialog = new DateSelectDialog(getActivity(), R.style.Theme_Dialog_Transparent);
                dateSelectDialog.setStartAndEndDate(startTimeStr, endTimeStr);
                dateSelectDialog.setDateSelectListener(this);
                dateSelectDialog.show();
                break;
        }
    }

    /***
     * 设置时间改变
     */
    @Override
    public void selectDate(String startDate, String endDate) {
        startTime.setText(startDate);
        endTime.setText(endDate);
        isClickRefresh = false;
        startTimeStr = startDate;
        endTimeStr = endDate;
        dialog.show();
        requestServer();
    }

    @Override
    protected void requestServer() {
        super.requestServer();
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        LogUtils.d("=========================== portId: " + portId);
        requestParams.put("portId", portId);
        requestParams.put("startTime", startTime.getText().toString() + " 00:00");
        requestParams.put("endTime", endTime.getText().toString() + " 23:59");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        String UserGuid = PreferenceUtils.getValue(preferences, "UserGuid");

//		if(MyApplication.UserGuid!=null)
//		requestParams.put("userUid", MyApplication.UserGuid);
//		else
//			requestParams.put("userUid", "");

        if (PreferenceUtils.getBoolean(this.getActivity(), "IS_LOGIN")) {


            if (UserGuid != null)
                requestParams.put("userUid", UserGuid);
            else
                requestParams.put("userUid", "");
        } else
            requestParams.put("userUid", "");
        HttpClient.getJsonWithGetUrl(RequestAirActionName.GetHourDataByDatetime, requestParams, this, null);
    }

//	@Override
//	public void selectPortinfo(PortInfo portInfo) {
//		super.selectPortinfo(portInfo);
//		
//	}

    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        dialog.hide();
        mItemList.clear();
        mRefreshButton.stop();
        mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
        mDensityValueList.setAdapter(mAdapter);
    }


    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        mItemList.clear();
        JSONObject jsonObj = resData.getJson();
        JSONArray jsonArray = jsonObj.optJSONArray("HistoryData");
        if (jsonArray != null && jsonArray.length() > 0) {
            Gson gson = new Gson();
            JsonobjHistoryData re = gson.fromJson(jsonObj.toString(), JsonobjHistoryData.class);
            List<JsonobjHistoryData.HistoryDataBean> all = re.getHistoryData();
            //确定最大的 factorNames是在什么位置
            int max = 0;
            for (int i = 0; i < all.size(); i++) {
                for (int j = 0; j < all.size() - 1; j++) {
                    max = all.get(i).getValue().size() > all.get(j).getValue().size() ? i : j;
                }
            }
            //确定固定factorNames
            List<String> gudingfactorNames = new ArrayList<>();
            List<String> danwei = new ArrayList<>();
            for (int i = 0; i < all.get(max).getValue().size(); i++) {
                gudingfactorNames.add(all.get(max).getValue().get(i).getFactor());
                danwei.add(all.get(max).getValue().get(i).getMeasureUnit());
            }
//            Log.i("", "");

            for (int i = 0; i < all.size(); i++) {
                FactorProperty prop = new FactorProperty();
                prop.date = all.get(i).getDateTime();
//                if (all.get(i).getDateTime().equals("07-05 07:00")) {
//                    Log.i("", "");
//                }
                prop.factorNames = new ArrayList<String>();
                prop.factorNames = gudingfactorNames;
                prop.danwei = danwei;
                prop.factorValues = new ArrayList<String>();
                prop.isExceeded = new ArrayList<Boolean>();

                out:
                for (int a = 0; a < gudingfactorNames.size(); a++) {

                    for (int b = 0; b < all.get(i).getValue().size(); b++) {

                        if (gudingfactorNames.get(a).equals(all.get(i).getValue().get(b).getFactor())) {
                            prop.factorValues.add(all.get(i).getValue().get(b).getValue());
                            prop.isExceeded.add(all.get(i).getValue().get(b).getIsExceeded());
                            continue out;
                        }


                    }
                    prop.factorValues.add("--");
                    prop.isExceeded.add(false);

                }
                mItemList.add(prop);
            }


/***2017-7-7  scj 删除 逻辑不正确*/
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObj2 = jsonArray.optJSONObject(i);
//                String dateTime = jsonObj2.optString("DateTime");
//                FactorProperty prop = new FactorProperty();
//                prop.date = dateTime;
//                prop.factorNames = new ArrayList<String>();
//                prop.factorValues = new ArrayList<String>();
//                prop.isExceeded = new ArrayList<Boolean>();
//                JSONArray array = jsonObj2.optJSONArray("Value");
//                if (array != null) {
//                    for (int j = 0; j < array.length(); j++) {
//                        prop.factorValues.add(array.optJSONObject(j).optString("value"));
//                        prop.factorNames.add(array.optJSONObject(j).optString("factor"));
//                        prop.isExceeded.add(array.optJSONObject(j).optBoolean("isExceeded"));
//                    }
//                }
//                mItemList.add(prop);
//            }
/***2017-7-7  scj 删除 逻辑不正确*/


//		if (mItemList.size() > 0)
//			addHead(mItemList.get(0));

            MaxColumnCount = BizCommon.GetMaxFactor(mItemList);
            if (mItemList.size() > 0)
                addHead(mItemList.get(MaxColumnCount));
//		mAdapter.notifyDataSetChanged();
//		mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
//		mDensityValueList.setAdapter(mAdapter);
            if (dialog.isShowing())
                dialog.hide();
            if (mAdapter == null) {
                mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
                /***设置最大因子列数****/
                mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames.size());
                mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
                if (isSelectDialog) {
                    mAdapter.SetIsNewData(true);
                    isSelectDialog = false;
                }
                mDensityValueList.setAdapter(mAdapter);
            } else {
                /***设置最大因子列数****/
                mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames.size());
                mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
                if (isSelectDialog) {
                    mAdapter.SetIsNewData(true);
                    isSelectDialog = false;
                }

                mAdapter.notifyDataSetChanged();
            }
        } else {
            if (dialog.isShowing())
                dialog.hide();
            mItemList.clear();
            mRefreshButton.stop();
            mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
            mDensityValueList.setAdapter(mAdapter);
            showTextToast("暂无数据，请稍后刷新!");
        }
    }


    /***
     * 添加一个head
     *
     * @param FactorProperty
     */
    private void addHead(FactorProperty FactorProperty) {
        viewChild.removeAllViews();
        for (int i = 0; i < FactorProperty.factorNames.size(); i++) {
            addTv(viewChild, FactorProperty.factorNames.get(i)+"\r\n"+FactorProperty.danwei.get(i));
        }
    }

    /***
     * 添加一个view
     *
     * @param parent
     * @param name
     */
    private void addTv(LinearLayout parent, String name) {
        TextView v = (TextView) View.inflate(parent.getContext(), R.layout.tv_item, null);
        v.setText(name);
        if (name.equals("PM10"))
            v.setText(StringUtils.getPM10());
        if (name.equals("PM2.5"))
            v.setText(StringUtils.getPM25());
        if (name.equals("03"))
            v.setText(StringUtils.getO3());
        if (name.equals("O3"))
            v.setText(StringUtils.getO3());
        if (name.equals("NO2"))
            v.setText(StringUtils.getNO2());
        if (name.equals("SO2"))
            v.setText(StringUtils.getSO2());
        if (name.equals("CO"))
            v.setText(StringUtils.getCO());
        if (name.equals("NOy"))
            v.setText(StringUtils.getNOy());
        if (name.equals("Natural1"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural2"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural3"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural4"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural5"))
            v.setText(StringUtils.getNatural(name));

        if (name.equals("CO2_1"))
            v.setText(StringUtils.getCO2(name));

        if (name.equals("CO2_2"))
            v.setText(StringUtils.getCO2(name));

        if (name.equals("CO2_3"))
            v.setText(StringUtils.getCO2(name));


        v.setTextColor(Color.WHITE);


        LayoutParams params = new LayoutParams(220, LayoutParams.MATCH_PARENT);
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

    /**
     * 选中page改变
     */
    @Override
    public void onPageSelected(int arg0) {
        loadData();
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
        findViewById(R.id.date_time).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.time));
    }
}
