package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.Entity.GetOnlineInfosuper;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.OnlinePagerAdapter;
import com.sinoyd.environmentNT.dialog.PortsDialog.PortTypeSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.view.OnlineView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 在线情况 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：OnlineActivity
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class OnlineActivity extends RefreshBaseActivity implements PortTypeSelectListener {
    /**
     * 滑动控件
     **/
    private ViewPager mViewPager;
    /**
     * 在线情况界面适配器
     **/
    private OnlinePagerAdapter pageAdapter;
    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;
    /**
     * 当前第几页数
     **/
    private int mSelectIndex = 0;
    /**
     * 浓度view控件
     **/
    private OnlineView mCurrentView;
    /**
     * 浓度view列表
     **/
    private List<OnlineView> mViewList;
    private ImageView imgChange;


    private RadioGroup online_rg;
    private RadioButton online_rb_yinzi;
    private RadioButton online_rb_chaoji;
    TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将背景设为空，防止重绘
        this.getActivity().getWindow().setBackgroundDrawable(null);
        setContentView(R.layout.activity_online);

    }

    @Override
    protected void initView() {
        super.initView();
        online_rg = (RadioGroup) findViewById(R.id.online_rg);
        online_rb_yinzi = (RadioButton) findViewById(R.id.online_rb_yinzi);
        online_rb_yinzi.setChecked(true);
        online_rb_chaoji = (RadioButton) findViewById(R.id.online_rb_chaoji);

        imgChange = (ImageView) findViewById(R.id.change_system);
        mViewPager = (ViewPager) findViewById(R.id.vPager);
        pageAdapter = new OnlinePagerAdapter();
        mViewPager.setAdapter(pageAdapter);
        findViewById(R.id.title).setOnClickListener(null);
        findViewById(R.id.change_station_icon).setOnClickListener(null);
        dialog.setPortTypeSelectListener(this);
        mTitleTextView.setText("南通市");



        online_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.online_rb_yinzi:
                        online_rb_yinzi.setChecked(true);
                        online_rb_chaoji.setChecked(false);
                        //发请求

                        Log.i("scj", "发送常规因子请求");

                        requestServer();
                        tv = (TextView) mCurrentView.findViewById(R.id.status);
                        mCurrentView.findViewById(R.id.recordcount).setVisibility(View.VISIBLE);
                        mCurrentView.findViewById(R.id.redwarm).setVisibility(View.VISIBLE);
                        tv.setText("状态");
                        break;
                    case R.id.online_rb_chaoji:
                        online_rb_chaoji.setChecked(true);
                        online_rb_yinzi.setChecked(false);
                        //发请求

                        Log.i("scj", "发送超级站请求");
                        requestServersuper();
                        tv = (TextView) mCurrentView.findViewById(R.id.status);
                        mCurrentView.findViewById(R.id.recordcount).setVisibility(View.GONE);
                        mCurrentView.findViewById(R.id.redwarm).setVisibility(View.GONE);
                        tv.setText("联网信息");

                        break;
                }
            }


        });
    }

    private void requestServersuper() {

        String u = "http://218.91.209.251:1117/NTWebServiceForMobile/AQI.asmx/GetOnlineInfo?sysType=air_Super";

        final HttpUtils utils_Get = new HttpUtils();
        utils_Get.send(HttpRequest.HttpMethod.GET, u, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gs = new Gson();
                GetOnlineInfosuper goif = gs.fromJson(responseInfo.result, GetOnlineInfosuper.class);
                mCurrentView.setDensitysuper(goif.getOnlineInfo());
            }

            @Override
            public void onFailure(HttpException e, String s) {


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        loadView();
    }

    /***
     * 加载网络
     */
    private void loadView() {
        if (mViewList == null) {
            mViewList = new ArrayList<OnlineView>();
        } else {
            mViewPager.removeAllViews();
            mViewList.clear();
            mSelectIndex = 0;
            portId = "0";
        }
        if (MyApplication.currentAirPortInfo != null) {
            portId = MyApplication.currentAirPortInfo.PortId;
        }
        OnlineView densityView = null;
        densityView = new OnlineView(getView().getContext());
        mViewList.add(densityView);
        pageAdapter.setListViews(mViewList);
        mViewPager.setAdapter(pageAdapter);
        if (mSelectIndex >= mViewPager.getAdapter().getCount()) {
            mSelectIndex = 0;
        }

        mViewPager.setCurrentItem(mSelectIndex);
        mCurrentView = mViewList.get(mSelectIndex);


        requestServer();

        System.gc();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void requestServer() {
        super.requestServer();
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }

        mCurrentView.startRefresh();

        if (AppConfig.isWatherSystem()) {
            requestParams.put("sysType", "water");
            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.change_station_icon));
        } else {
            requestParams.put("sysType", "air");
            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.more_change_system));
        }


        requestParams.put("GroupType", "");
        if (!portId.equals("0"))
            requestParams.put("PointId", portId);
        else {
            requestParams.put("PointId", "");
        }


        HttpClient.getJsonWithGetUrl(RequestAirActionName.GetOnlineInfo, requestParams, this, null);
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        mCurrentView.setDensity(resData.getJson());
    }

    @Override
    public void selectPortCallBack() {
        // TODO Auto-generated method stub
        onClick(findViewById(R.id.top_refresh));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_station:
                if (AppConfig.isWatherSystem()) {
                    startActivity(new Intent(getActivity(), PortManagerActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), PositionManagerActivity.class));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void selectPortTypeinfo(String PortType) {

        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        mCurrentView.startRefresh();
        if (AppConfig.isWatherSystem()) {
            requestParams.put("sysType", "water");
            if (PortType.equals("水源地"))
                requestParams.put("GroupType", "1");
            if (PortType.equals("城区河道"))
                requestParams.put("GroupType", "2");
            if (PortType.equals("省建站"))
                requestParams.put("GroupType", "3");
            if (PortType.equals("国家站"))
                requestParams.put("GroupType", "4");
            if (PortType.equals("区县自建站"))
                requestParams.put("GroupType", "5");
            if (PortType.equals("湖体站"))
                requestParams.put("GroupType", "6");
            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.change_station_icon));
        } else {
            requestParams.put("sysType", "air");
            if (PortType.equals("国控"))
                requestParams.put("GroupType", "1");
            if (PortType.equals("省控"))
                requestParams.put("GroupType", "2");
            if (PortType.equals("路边站"))
                requestParams.put("GroupType", "3");
            if (PortType.equals("市控"))
                requestParams.put("GroupType", "4");

            imgChange.setImageDrawable(getResources().getDrawable(R.drawable.more_change_system));
        }
        if (mTitleTextView != null) {
            mTitleTextView.setText(PortType);
        }
        requestParams.put("PointId", "");
        HttpClient.getJsonWithGetUrl(RequestAirActionName.GetOnlineInfo, requestParams, this, null);
    }

    @Override
    public void selectPortinfo(PortInfo portInfo) {

        super.selectPortinfo(portInfo);
        portId = portInfo.PortId;


    }
}
