package com.sinoyd.environmentNT.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.database.PositionDBModel;
import com.sinoyd.environmentNT.database.WeatherDBModel;
import com.sinoyd.environmentNT.download.DownloadDialog.DownloadListener;
import com.sinoyd.environmentNT.hometouchview.MyViewPager;
import com.sinoyd.environmentNT.hometouchview.RealTimeShowView;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.json.JSON;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.model.PortHourAQIListInfo;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.BracaseUtils;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.view.ArrowView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * AQI Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：AQIActivity
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@SuppressLint("UseSparseArrays")
public class AQIActivity extends RefreshBaseActivity implements DownloadListener {
    /**
     * 滑动视图
     **/
    private MyViewPager myScrollViewPager;
    /**
     * 滑动适配器
     **/
    private MyAdapter myAdapter;
    /**
     * 健康提示
     **/
    public static String[] heal_tips;
    /**
     * 实况界面列表
     **/
    public static ArrayList<RealTimeShowView> listViewPager;
    /**
     * 实况界面HashMap
     **/
    HashMap<Integer, RealTimeShowView> realTimeViewHashMap = new HashMap<Integer, RealTimeShowView>();
    /**
     * 标题
     **/
    private TextView tvTitle;
    /**
     * 站点信息列表
     **/
    private List<PortInfo> listPort = new ArrayList<PortInfo>();
    /**
     * 获取接口传入参数
     **/
    private HashMap<String, String> chartParams;
    /**
     * 当前第几页
     **/
    public static int currentItem = 0;
    /**
     * 被选中站点的当前请求时刻
     **/
    private HashMap<String, Long> requestTimeMap = new HashMap<String, Long>();

    /**
     * 滑动监听
     **/
    private MyListener pagerListener;

    private final int UPDATE_TIME = 2;// 更新时间,小时

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aqi);
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetWeather, chartParams, this, null);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (currentItem < listViewPager.size()) {
            if (!MyApplication.currentAirPortInfo.PortId.equals(portId)) {
                setCurrentItem(MyApplication.currentAirPortInfo.PortId);
            }
        }


    }

    @Override
    public void onPause() {
        super.onPause();
//		if(receiver!=null)
//			try{
//
//				BracaseUtils.unregister(mContext, receiver);
//
//			}catch(IllegalArgumentException e){
//
//			}
    }

    @Override
    protected void initView() {
        super.initView();
        heal_tips = getResources().getStringArray(R.array.heal_tips);
        pagerListener = new MyListener();

        listViewPager = new ArrayList<RealTimeShowView>();
        findViewById(R.id.add_station).setOnClickListener(this);
        tvTitle = ((TextView) findViewById(R.id.title));
        myScrollViewPager = (MyViewPager) findViewById(R.id.myScrollViewPager);
        myScrollViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    listViewPager.get(currentItem).closeDown();
                return false;
            }
        });
        myAdapter = new MyAdapter();
        myScrollViewPager.setAdapter(myAdapter);
        myScrollViewPager.setOnPageChangeListener(pagerListener);


        initData();
//		weatherResult.searchWeather();
//		weatherResult.setCallBack(new CallBack() {
//			@Override
//			public void onCallBack() {
//				for (RealTimeShowView viewItem : listViewPager) {
//					viewItem.setWeather(weatherResult);
//				}
//			}
//		});
        BracaseUtils.register(mContext, receiver, 100);
    }

    /**
     * 切换至当前站点
     *
     * @param portId
     */
    public void setCurrentItem(String portId) {
        for (int i = 0; i < listPort.size(); i++) {
            if (listPort.get(i).PortId.equals(portId)) {
                currentItem = i;
                break;
            }
        }
        myScrollViewPager.setCurrentItem(currentItem);
        pagerListener.onPageSelected(currentItem);
    }

    /***
     * 加载空气站点的viewpaper的子页面
     */
    private void initData() {
        if (PortCacheUtils.airIsEmpty()) {
            try {
                List<PortInfo> mList = mDB.findAll(Selector.from(PortInfo.class).where("isWaterPort", "=", "0"));
                PortCacheUtils.setAirPortList(mList);
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        listPort = PortCacheUtils.getAirPortList();
        if (listPort != null) {
            /**scj 2017-7-12*/
//            if (!PreferenceUtils.getBoolean(this.getActivity(), "IS_LOGIN")) {
//                addRealTimeView(Integer.parseInt(listPort.get(0).PortId));
//            } else {
            for (int i = 0; i < listPort.size(); i++) {
                addRealTimeView(Integer.parseInt(listPort.get(i).PortId));
            }
//            }
        }
        try {
            myAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            // TODO: handle exception
        }
        myScrollViewPager.setCurrentItem(currentItem);
        pagerListener.onPageSelected(currentItem);
    }

    /***
     * 在实时AQI的父页面中通过广播接收新增一个子页面的通知处理
     *
     * @param portId
     */
    private void addRealTimeView(int portId) {
        RealTimeShowView showView = new RealTimeShowView(mContext);
        ((ArrowView) showView.findViewById(R.id.arrowView)).setNumber(1);
        ((ArrowView) showView.findViewById(R.id.arrowView)).setCheck(0);
        ((ArrowView) showView.findViewById(R.id.arrowView)).setVisibility(View.INVISIBLE);

        listViewPager.add(showView);


        realTimeViewHashMap.put(portId, showView);
        try {
            myAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /***
     * 根据站点删除站点view
     *
     * @param portId
     */
    private void removeRealTimeView(int portId) {
        for (PortInfo info : listPort) {
            if (Integer.parseInt(info.PortId) == portId) {
                listPort.remove(info);
                break;
            }
        }
        listViewPager.remove(realTimeViewHashMap.get(portId));
        realTimeViewHashMap.remove(portId);
        if (currentItem > listViewPager.size() - 1)
            currentItem = 0;
        myScrollViewPager.setAdapter(myAdapter);
        myScrollViewPager.setCurrentItem(currentItem);
        pagerListener.onPageSelected(currentItem);
    }

    @Override
    protected void requestServer() {
        super.requestServer();
        if (chartParams == null) {
            chartParams = new HashMap<String, String>();
        }
        chartParams.put("portId", MyApplication.currentAirPortInfo.PortId);
        requestTimeMap.put(MyApplication.currentAirPortInfo.PortId, System.currentTimeMillis());
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLatestHourAQI, chartParams, this, null);


        try {
//            AQIActivity.mDB.delete(WeatherDBModel.class, WhereBuilder.b("date", "<", DateUtil.getCurrentTime("yyyy-MM-dd")));
            WeatherDBModel weather = AQIActivity.mDB.findFirst(Selector
                    .from(WeatherDBModel.class).where("city", "=", "南通")
//                    .where("date", "=", DateUtil.getCurrentTime("yyyy-MM-dd"))
                    .orderBy("time", true));
            if (weather != null) {
                long updateCha = DateUtil.getTime(DateUtil.getCurrentTime("HH:mm"),
                        "HH:mm") - DateUtil.getTime(weather.getTime(), "HH:mm");
                if (updateCha < UPDATE_TIME * 60 * 60 * 1000) {
                    System.out.println("from db");
                    for (RealTimeShowView viewItem : listViewPager) {
                        viewItem.setWeather(weather);
                    }
                } else {
                    chartParams.put("location", "南通");
                    chartParams.put("output", "json");
                    chartParams.put("ak", "FK9mkfdQsloEngodbFl4FeY3");
                    HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetWeather, chartParams, this, null);
                }
            } else {
                chartParams.put("location", "南通");
                chartParams.put("output", "json");
                chartParams.put("ak", "FK9mkfdQsloEngodbFl4FeY3");
                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetWeather, chartParams, this, null);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


//		HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetWeather, null, this, null);

    }

    /***
     * 请求24小时浓度曲线接口方法
     */
    public void requestRefreshRecord() {
        if (chartParams == null) {
            chartParams = new HashMap<String, String>();
        }
        chartParams.put("portId", MyApplication.currentAirPortInfo.PortId);
        if (RealTimeShowView.selectedFactor != null)
            chartParams.put("factorName", RealTimeShowView.selectedFactor);
        else {
            chartParams.put("factorName", "");
        }
        HttpClient.getJsonWithGetUrl(RequestAirActionName.Get24HoursFactorDataAir, chartParams, this, null);
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if (RequestAirActionName.GetWeather.equals(resData.getUri())) {
            WeatherDBModel newModel = new WeatherDBModel();
            JSONArray jsonResult = null;
            try {
//              JSONObject jsonObject = resData.getJson().getJSONArray("results").getJSONObject(0);
                JSONObject jsonObject = resData.getJson().getJSONObject("today");
//              JSONArray jsonArray = jsonObject.getJSONArray("weather_data");
//              newModel.setCity(jsonObject.getString("currentCity"));
                newModel.setCity("南通");
                newModel.setTempMax(jsonObject.getString("tempMax"));
                newModel.setTempMin(jsonObject.getString("tempMin"));
                newModel.setWeatherStart(jsonObject.getString("weatherStart"));
                newModel.setWeatherEnd(jsonObject.getString("weatherEnd"));
                newModel.setWindDirectionStart(jsonObject.getString("windDirectionStart"));
//              newModel.setWind(jsonArray.optJSONObject(0).getString("wind"));
                newModel.setWeather(jsonObject.getString("windDirectionStart"));
                newModel.setTime(jsonObject.getString("date"));
                AQIActivity.mDB.save(newModel);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (DbException e) {
                e.printStackTrace();
            }


            for (RealTimeShowView viewItem : listViewPager) {
                viewItem.setWeather(newModel);
            }
        }
        if (RequestAirActionName.Get24HoursFactorDataAir.equals(resData.getUri())) {
            if (listViewPager.size() < 1)
                return;

            RealTimeShowView showView = listViewPager.get(currentItem);

            showView.set24HourAQIChartJson(resData.getJson());
            showView.mCursorView.setCurrentValue(showView.mCursorView.getCurrentValue());
        }
        if (RequestAirActionName.GetLatestHourAQI.equals(resData.getUri())) {
            PortHourAQIListInfo result = JSON.parse(resData.getJson().toString(), PortHourAQIListInfo.class);
            if (result != null) {
                if (result.PortHourAQI.size() > 0) {
                    PortHourAQIInfo pmInfo = result.PortHourAQI.get(0);
                    listViewPager.get(currentItem).updateData(pmInfo);
                    pmInfo.DateTime = DateUtil.formatDate(DateUtil.getDate(pmInfo.DateTime, ""), "MM/dd HH:mm");
                    try {
                        PositionDBModel entity = mDB.findFirst(Selector.from(PositionDBModel.class).where("portId", "=", pmInfo.PortId));
                        if (entity != null) {
                            entity.setCO_IAQI(pmInfo.CO_IAQI);
                            entity.setNO2_IAQI(pmInfo.NO2_IAQI);
                            entity.setO3_IAQI(pmInfo.O3_IAQI);
                            entity.setPM25_IAQI(pmInfo.PM25_IAQI);
                            entity.setPM10_IAQI(pmInfo.PM10_IAQI);
                            entity.setSO2_IAQI(pmInfo.SO2_IAQI);
                            entity.setAQI(pmInfo.AQI);
                            entity.setpClass(pmInfo.Class);
                            entity.setDateTime(pmInfo.DateTime);
                            mDB.update(entity);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
            requestRefreshRecord();
        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        // TODO Auto-generated method stub
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

    /***
     * 自定义适配器
     *
     * @author smz
     *
     */
    class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            // return Integer.MAX_VALUE;
            return listViewPager.size();

        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
        }

        /***
         * 选中后处理
         */
        @Override
        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPager) arg0).addView(listViewPager.get(arg1 % listViewPager.size()), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return listViewPager.get(arg1 % listViewPager.size());
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    /**
     * 同一个站点两次请求数据的时间间隔不能小于此时间，小于此时间则第二次不再请求
     **/
    private long cacheTime = 60 * 1000;

    /**
     * 滑动监听事件 Copyright (c) 2015 江苏远大信息股份有限公司
     *
     * @类型名称：MyListener
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     * @功能摘要：
     */
    class MyListener implements OnPageChangeListener {
        // 当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        // 当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        // 当新的页面被选中时调用
        @Override
        public void onPageSelected(int position) {
            System.out.println("++" + position);


            if (listPort == null || listPort.size() < 1)
                return;
            currentItem = position;
            if (currentItem >= listPort.size()) {
                currentItem = 0;
            }
            MyApplication.currentAirPortInfo = listPort.get(currentItem);
            if (!requestTimeMap.containsKey(MyApplication.currentAirPortInfo.PortId)) {
                requestServer();
            } else {
//				long cha = System.currentTimeMillis() - requestTimeMap.get(MyApplication.currentAirPortInfo.PortId);
//				if (cha > cacheTime)
                requestServer();
            }
            tvTitle.setText(listPort.get(currentItem).PortName);
            try {
                listViewPager.get(currentItem).startMarq();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {

        if (receiver != null)
            try {

                BracaseUtils.unregister(mContext, receiver);

            } catch (IllegalArgumentException e) {

            }
        super.onDestroyView();
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotifyUpdate(intent);
        }
    };


    /***
     * 监听事件的处理，修改站点的数量
     *
     * @param intent
     */
    public void NotifyUpdate(Intent intent) {
        if (BracaseUtils.ADD_ACTION.equalsIgnoreCase(intent.getAction()))
            try {
                PositionDBModel pd = mDB.findFirst(Selector.from(PositionDBModel.class).orderBy("id", true));
                PortInfo i = new PortInfo();
                i.PortId = pd.getPortId();
                i.PortName = pd.getPortName();
                listPort.add(i);
                addRealTimeView(Integer.parseInt(i.PortId));
            } catch (DbException e) {
                e.printStackTrace();
            }
        else if (BracaseUtils.REMOVE_ACTION.equalsIgnoreCase(intent.getAction()))
            removeRealTimeView(Integer.parseInt(intent.getStringExtra("portId")));
    }

    /***
     * 选择站点后的回调
     */
    @Override
    public void selectPortCallBack() {
        // TODO Auto-generated method stub
        onClick(findViewById(R.id.top_refresh));
        /*if (listViewPager.get(currentItem).getTopSlidingLayout().isOpenDown())
            listViewPager.get(currentItem).getTopSlidingLayout().closeDown();*/
    }

    @Override
    public void endDownload(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    @Override
    public void startDownload() {
    }

    /***
     * 更新皮肤
     */
    @Override
    public void updateFace() {
        super.updateFace();
        if (listViewPager == null || listViewPager.size() < 1) {
            return;
        }
        if (getView() == null) {
            return;
        }
        for (int i = 0; i < listViewPager.size(); i++) {
            listViewPager.get(i).updateFace();
        }
    }
}
