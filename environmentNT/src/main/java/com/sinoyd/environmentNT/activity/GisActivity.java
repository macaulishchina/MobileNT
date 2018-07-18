package com.sinoyd.environmentNT.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.Entity.GetPortInfoBySysType;
import com.sinoyd.environmentNT.Entity.GetPortMessageAround_ForAPP;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.Gislvadapter;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.DateInfoo;
import com.sinoyd.environmentNT.util.PreferenceUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/***
 * 地图界面
 *
 * @author smz 创建时间：2013-11-14下午10:32:54
 */
public class GisActivity extends RefreshBaseActivity {
    /* MapView 是地图主控件
    */
    private MapView mMapView;
    private BaiduMap mbaiduMap;
    private List<Marker> mMarkers = new ArrayList<Marker>();
    /**
     * 定位相关
     **/
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();// 定位监听
    private boolean isFirstLoc = false;// 是否首次定位
    private LocationMode mCurrentMode;// 定位模式
    protected LocationManager locationManager;// 定位管理器
    BitmapDescriptor mCurrentMarker;// 当前标记


    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcoding);// 异常
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_gcodingdone);// 正常
    BitmapDescriptor bdair1 = BitmapDescriptorFactory
            .fromResource(R.drawable.air1);// 正常
    BitmapDescriptor bdair2 = BitmapDescriptorFactory
            .fromResource(R.drawable.air2);// 正常
    BitmapDescriptor bdair3 = BitmapDescriptorFactory
            .fromResource(R.drawable.air3);// 正常
    BitmapDescriptor bdair4 = BitmapDescriptorFactory
            .fromResource(R.drawable.air4);// 正常
    BitmapDescriptor bdair5 = BitmapDescriptorFactory
            .fromResource(R.drawable.air5);// 正常
    BitmapDescriptor bdair6 = BitmapDescriptorFactory
            .fromResource(R.drawable.air6);// 正常


    BitmapDescriptor bdwater1 = BitmapDescriptorFactory
            .fromResource(R.drawable.water1);// 正常

    BitmapDescriptor bdwater2 = BitmapDescriptorFactory
            .fromResource(R.drawable.water2);// 正常

    BitmapDescriptor bdwater3 = BitmapDescriptorFactory
            .fromResource(R.drawable.water3);// 正常
    BitmapDescriptor bdwater4 = BitmapDescriptorFactory
            .fromResource(R.drawable.water4);// 正常
    BitmapDescriptor bdwater5 = BitmapDescriptorFactory
            .fromResource(R.drawable.water5);// 正常
    BitmapDescriptor bdoffline = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_offline);// 正常


    /** 定位相关 **/
    /**
     * 获取接口传入参数
     **/
    private HashMap<String, String> portRequest;
    /**
     * 被选中站点的当前请求时刻
     **/
    private HashMap<String, Long> requestTimeMap = new HashMap<String, Long>();
    JSONArray objArray;// Json解析工具
    List<PortInfo> portlist = new ArrayList<PortInfo>();// 点位列表


    //弹出点位信息
    // 弹出视图
    private View viewContent;
    // 弹出上下的箭头
    private ImageView arrowImageView;

    private ListView listView;
    private ImageView iv_gis;
    private ListView lv_gis;
    private LinearLayout ll_gis;
    private RelativeLayout rl_gis;

    private int n = 0;

    private RelativeLayout.LayoutParams params;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    iv_gis.setImageResource(R.drawable.jiantoushang);
                    break;
                case 2:
                    iv_gis.setImageResource(R.drawable.jiantouxia);
                    break;
            }

        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gis);
    }

    @Override
    protected void initView() {
        super.initView();
        isFirstLoc = true;
        initBaidu();
        loadPort(false);
        // GetStation();
        StopAnimation();


        ll_gis = (LinearLayout) findViewById(R.id.ll_gis);
        iv_gis = (ImageView) findViewById(R.id.iv_gis);
        lv_gis = (ListView) findViewById(R.id.lv_gis);
        params = (RelativeLayout.LayoutParams) ll_gis.getLayoutParams();
        rl_gis = (RelativeLayout) findViewById(R.id.rl_gis);


        iv_gis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n++;
                Message message = handler.obtainMessage();
                if (n % 2 == 1) {
                    params.height = rl_gis.getHeight() * 2 / 5;
                    message.what = 1;
                    handler.sendMessage(message);
                } else {
                    params.height = iv_gis.getHeight();

                    message.what = 2;
                    handler.sendMessage(message);
                }
                ll_gis.setLayoutParams(params);
            }


        });
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        LoginId = PreferenceUtils.getValue(preferences, "LoginId");
        GetPortInfoBySysType(LoginId, sysType);


        lv_gis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetPortInfoBySysType.PortInfoBean ss = listPortInfo.get(position);
                Log.i("X", ss.getX());
                Log.i("Y", ss.getY());

                if (ss.getX().equals("0") || ss.getY().equals("0.0000")) {
                    MyApplication.showTextToast("该点位没有经纬度");
                    return;
                }


                LatLng point = null;
                point = new LatLng(Double.valueOf(ss.getY()), Double.valueOf(ss.getX()));
                MapStatus mMapStatus = new MapStatus.Builder()
                        .target(point)
                        .zoom(15)
                        .build();
                MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
                mbaiduMap.setMapStatus(mMapStatusUpdate);


                iv_gis.performClick();
            }
        });
    }

    List<GetPortInfoBySysType.PortInfoBean> listPortInfo = new ArrayList<>();
    List<GetPortInfoBySysType.PortInfoBean> listPortInfo2 = new ArrayList<>();

    //【站点信息】获取站点信息
    private void GetPortInfoBySysType(String loginId, String sysType) {
        final HttpUtils utils_Get = new HttpUtils();
        utils_Get.send(HttpRequest.HttpMethod.GET, "http://218.91.209.251:1117/NTWebServiceForMobile/AQI.asmx/GetPortInfoBySysType?sysType=" + sysType + "&LoginId=" + LoginId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                GetPortInfoBySysType result = gson.fromJson(responseInfo.result, GetPortInfoBySysType.class);
                listPortInfo = result.getPortInfo();


                listPortInfo2 = new ArrayList<>();
                for (int aa = 0; aa < listPortInfo.size(); aa++) {
                    if (listPortInfo.get(aa).getY().equals("0") || listPortInfo.get(aa).getY().equals("0.0000")) {
                    } else {
                        listPortInfo2.add(listPortInfo.get(aa));
                    }
                }


                /**********************************我也没办法，写接口的不按标准来****************************************************/

                String u = "http://218.91.209.251:1117/NTWebService/WebServiceForOutData.asmx/GetPortMessageAround_ForAPP?type=AQI" + "&dt1=" + DateInfoo.getToday() + "&dt2=" + DateInfoo.getToday() + "&pId=&fac=";
                utils_Get.send(HttpRequest.HttpMethod.GET, u, new RequestCallBack<Object>() {
                    @Override
                    public void onSuccess(ResponseInfo<Object> responseInfo) {
                        Gson gson2 = new Gson();
                        GetPortMessageAround_ForAPP result2 = gson2.fromJson((String) responseInfo.result, GetPortMessageAround_ForAPP.class);

                        for (int iii = 0; iii < result2.getPortMessagesAround().size(); iii++) {

                            GetPortMessageAround_ForAPP.PortMessagesAroundBean pab = result2.getPortMessagesAround().get(iii);

                            if (pab.getMonitoringPointName().equals("南通市")) {
                            } else {

//                                PortId = portId;
//                                PortName = portName;
//                                X = x;
//                                Y = y;
//                                PortType = portType;
//                                RegionType = regionType;
//                                this.orderNumber = orderNumber;
//                                this.AQI = AQI;
//                                Grade = grade;

                                GetPortInfoBySysType.PortInfoBean pfb =
                                        new GetPortInfoBySysType.PortInfoBean(pab.getPointId(),
                                                pab.getMonitoringPointName(),
                                                pab.getX(),
                                                pab.getY(),
                                                "",
                                                "",
                                                "",
                                                pab.getIAQI(),
                                                pab.getPrimaryPollutant(),
                                                pab.getLevel());
                                listPortInfo.add(pfb);
                                listPortInfo2.add(pfb);
                            }

                        }


                        //listview 显示数据
                        showlistview(listPortInfo);
                        initOverlay(listPortInfo2);


                    }

                    @Override
                    public void onFailure(HttpException e, String s) {


                    }
                });
                /**************************************************************************************/

            }

            @Override
            public void onFailure(HttpException e, String s) {


            }
        });


    }

    private void showlistview(List<GetPortInfoBySysType.PortInfoBean> listPortInfo) {
        Gislvadapter adapter = new Gislvadapter(getActivity(), listPortInfo);
        lv_gis.setAdapter(adapter);
    }


    private String sysType = "air";
    private String LoginId;

    public void intialPosition() {
        viewContent = findViewById(R.id.viewContent);
        listView = (ListView) findViewById(R.id.listview);
        arrowImageView = (ImageView) findViewById(R.id.arrow_imageview);
        findViewById(R.id.show_list).setOnClickListener(this);
//		listView.setOnItemClickListener(this);
    }


    protected void GetStation() {
        portRequest = new HashMap<String, String>();
        if (AppConfig.isWatherSystem()) {
            // if (PortCacheUtils.waterIsEmpty()) {
            portRequest.put("sysType", "water");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            Boolean IsLogin = PreferenceUtils.getBoolean(this.getActivity(), "IS_LOGIN");
            if (IsLogin) {
                String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
                portRequest.put("LoginId", LoginId);
            } else
                portRequest.put("LoginId", "taicangapp");
            HttpClient.getJsonWithGetUrl(
                    RequestAirActionName.GetPortInfoBySysType, portRequest,
                    this, null);
            // }
        } else {
            // if (PortCacheUtils.airIsEmpty()) {
            portRequest.put("sysType", "air");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
            Boolean IsLogin = PreferenceUtils.getBoolean(this.getActivity(), "IS_LOGIN");
            if (IsLogin) {

                String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
                portRequest.put("LoginId", LoginId);
            } else
                portRequest.put("LoginId", "taicangapp");
            HttpClient.getJsonWithGetUrl(
                    RequestAirActionName.GetPortInfoBySysType, portRequest,
                    this, null);
            // }
        }
    }

    /**** 百度地图相关方法开始
     * @param listPortInfo22***/
    /* 监听覆盖物
    */
    public void initOverlay(List<GetPortInfoBySysType.PortInfoBean> listPortInfo22) {
        // 注意该方法要再setContentView方法之前实现
        // if (chartParams == null) {
        // chartParams = new HashMap<String, String>();
        // }
        // HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPOINT,
        // chartParams, this, null);
        try {
            if (AppConfig.isWatherSystem())
                //
                if (!PortCacheUtils.waterIsEmpty())
                    portlist = PortCacheUtils.getWaterPortList();
                else
                    portlist = MyApplication.mDB.findAll(Selector.from(
                            PortInfo.class).where("isWaterPort", "=", "0"));
            else {
                if (!PortCacheUtils.airIsEmpty())
                    portlist = PortCacheUtils.getAirPortList();
                else {
                    portlist = MyApplication.mDB.findAll(Selector.from(
                            PortInfo.class).where("isWaterPort", "=", "1"));
                }
            }


            for (int i = 0; i < listPortInfo22.size(); i++) {
                try {
                    String latitude = listPortInfo22.get(i).getY();
                    String longitude = listPortInfo22.get(i).getX();
                    String entname = listPortInfo22.get(i).getPortName();
                    String entId = listPortInfo22.get(i).getPortId();
                    String grade = listPortInfo22.get(i).getGrade();
                    String aqi = listPortInfo22.get(i).getAQI();
                    String PrimaryPollutant = listPortInfo22.get(i).getPrimaryPollutant();
//                    String level = listPortInfo.get(i).Level;
//                    String isOnLine = listPortInfo.get(i).IsOnline;

//
//                    if (isOnLine == null)
//                        isOnLine = "";
//                    if (level == null)
//                        level = "";

                    LatLng llLatLng = new LatLng(Float.parseFloat(latitude),
                            Float.parseFloat(longitude));
//                    MarkerOptions ooa = new MarkerOptions().position(llLatLng)
//                            .icon(bdB).zIndex(9).draggable(false);

                    OverlayOptions ooa = null;
                    //自定义地图图标
                    View v_temp = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.marker_layout, null);
                    TextView tv_temp = (TextView) v_temp.findViewById(R.id.baidumap_custom_text);//获取自定义布局中的textview

                    ImageView img_temp = (ImageView) v_temp.findViewById(R.id.baidumap_custom_img);//获取自定义布局中的imageview


                    switch (this.listPortInfo2.get(i).getGrade()) {
                        case "优":
                            img_temp.setImageResource(R.drawable.water2);//设置marker的图标
                            break;
                        case "良":
                            img_temp.setImageResource(R.drawable.water3);//设置marker的图标
                            break;
                        case "轻度污染":
                            img_temp.setImageResource(R.drawable.water4);//设置marker的图标
                            break;
                        case "中度污染":
                            img_temp.setImageResource(R.drawable.water5);//设置marker的图标
                            break;
                        case "重度污染":
                            img_temp.setImageResource(R.drawable.air5);//设置marker的图标
                            break;
                        case "严重污染":
                            img_temp.setImageResource(R.drawable.air6);//设置marker的图标
                            break;
                        case "":
                            img_temp.setImageResource(R.drawable.nthui);//设置marker的图标
                            break;
                    }

//                    if (isOnLine != null && isOnLine.equals("0")) {
//                        ooa = new MarkerOptions().position(llLatLng).icon(bdoffline).zIndex(9).draggable(false);
//                    } else {
//                        if (!AppConfig.isWatherSystem()) {
//                            if (level.equals("1"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair1).zIndex(9).draggable(false);
//                            if (level.equals("2"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair2).zIndex(9).draggable(false);
//                            if (level.equals("3"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair3).zIndex(9).draggable(false);
//                            if (level.equals("4"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair4).zIndex(9).draggable(false);
//                            if (level.equals("5"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair5).zIndex(9).draggable(false);
//                            if (level.equals("6"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdair6).zIndex(9).draggable(false);
//                        } else {
//                            if (level.equals("1"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdwater1).zIndex(9).draggable(false);
//                            if (level.equals("2"))

                    tv_temp.setText(aqi);//设置要显示的文本


                    BitmapDescriptor bd_temp = BitmapDescriptorFactory.fromView(v_temp);
                    ooa = new MarkerOptions().position(llLatLng)
                            .icon(bd_temp).zIndex(9).draggable(false);

//                            if (level.equals("3"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdwater3).zIndex(9).draggable(false);
//                            if (level.equals("4"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdwater4).zIndex(9).draggable(false);
//                            if (level.equals("5"))
//                                ooa = new MarkerOptions().position(llLatLng)
//                                        .icon(bdwater5).zIndex(9).draggable(false);
//                        }
//                    }
//                    ooa.title(entname);
                    // mbaiduMap.addOverlay(ooa);
                    Marker cMarker = (Marker) (mbaiduMap.addOverlay(ooa));

//                    cMarker.setTitle("你好");
                    Bundle cBundle = new Bundle();
                    cBundle.putString("name", entname);
                    cBundle.putString("grade", grade);
                    cBundle.putString("aqi", aqi);
                    cBundle.putString("PrimaryPollutant", PrimaryPollutant);


                    cMarker.setExtraInfo(cBundle);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }


            // 点击覆盖物
            /*******************************************************************************************************************/
            mbaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
                public boolean onMarkerClick(final Marker marker) {
                    if (marker.getExtraInfo() != null) {
                        LatLng ll = marker.getPosition();
                        // 创建InfoWindow展示的view

                        String a = marker.getExtraInfo().getString("name").equals("")?"--":marker.getExtraInfo().getString("name");
                        String b = marker.getExtraInfo().getString("grade").equals("")?"--":marker.getExtraInfo().getString("grade");
                        String c = marker.getExtraInfo().getString("aqi").equals("")?"--":marker.getExtraInfo().getString("aqi");
                        String d = marker.getExtraInfo().getString("PrimaryPollutant").equals("")?"--":marker.getExtraInfo().getString("PrimaryPollutant");

                        final LinearLayout bubbleLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.popup_layout, mMapView, false);

                        TextView tv0 = (TextView) bubbleLayout.findViewById(R.id.title_name);
                        tv0.setText(a);
                        TextView tv1 = (TextView) bubbleLayout.findViewById(R.id.popup_tv_name);
                        tv1.setText("AQI:" + c);
                        TextView tv2 = (TextView) bubbleLayout.findViewById(R.id.popup_tv_yl);
                        tv2.setText("环境质量:" + b);
                        TextView tv3 = (TextView) bubbleLayout.findViewById(R.id.popup_tv_PrimaryPollutant);
                        tv3.setText("首要污染物:" + d);


                        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
                        final InfoWindow mInfoWindow = new InfoWindow(bubbleLayout,
                                ll, -100);


                        // 显示InfoWindow
                        mbaiduMap.showInfoWindow(mInfoWindow);
                        mbaiduMap
                                .setOnMapClickListener(new OnMapClickListener() {
                                    @Override
                                    public void onMapClick(LatLng arg0) {
                                        mbaiduMap.hideInfoWindow();
                                    }

                                    @Override
                                    public boolean onMapPoiClick(MapPoi arg0) {
                                        return false;
                                    }
                                });
                    }
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        if (AppConfig.isWatherSystem()) {
            if (MyApplication.currentWaterPortInfo != null) {
                mTitleTextView
                        .setText(MyApplication.currentWaterPortInfo.PortName);
                portId = MyApplication.currentWaterPortInfo.PortId;
            }
        } else {
            if (MyApplication.currentAirPortInfo != null) {
                mTitleTextView
                        .setText(MyApplication.currentAirPortInfo.PortName);
                portId = MyApplication.currentAirPortInfo.PortId;
            }
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        super.onDestroy();
        // 回收 bitmap 资源
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mbaiduMap.setMyLocationEnabled(false);
    }

    public void initBaidu() {
        // 获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);
        // 获取BaiduMap实例
        mbaiduMap = mMapView.getMap();
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(5);
        mbaiduMap.setMapStatus(msu);
        // 删除百度地图logo
        mMapView.removeViewAt(1);
        // 开启定位图层
        mbaiduMap.setMyLocationEnabled(true);
        mMapView.showZoomControls(false);
        // 默认中心地图
        // LatLng llLatLng = new LatLng(Double.parseDouble(longitude),
        // Double.parseDouble(latitude));
        // MyLocationListenner;
        // 定位初始化
        mLocClient = new LocationClient(this.getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCurrentMode = LocationMode.NORMAL;
        mbaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(11);
        mbaiduMap.animateMapStatus(u);
        locationManager = (LocationManager) this.getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        mbaiduMap.setMyLocationEnabled(true);
    }

    /* 定位SDK监听函数
    */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mbaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {

                //定死进入地图显示
                LatLng ll = new LatLng(32.046304, 120.814221
                );
                // OverlayOptions ooA = new
                // MarkerOptions().position(ll).icon(bdA)
                // .zIndex(9).draggable(true);
                // mbaiduMap.addOverlay(ooA);
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                // mbaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));
                mbaiduMap.animateMapStatus(u);
                isFirstLoc = false;
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    /**** 百度地图相关方法结束 ***/
    @SuppressWarnings("deprecation")
    @Override
    protected void requestServer() {
        super.requestServer();
        GetStation();
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        // initOverlay();
        // try {
        // objArray = new JSONArray(resData.getJson());
        // List<Station> list = new ArrayList<Station>();
        // if (result.length() > 5) {
        // if (objArray != null
        // && objArray.length() > 0) {
        // for (int i = 0; i < objArray.length(); i++) {
        // Station station = new Station();
        // station.parse(new JSONObject(
        // objArray.get(i).toString()));
        // if (station.longitude.equals(null)
        // || station.longitude
        // .equals("null")
        // || station.latitude
        // .equals(null)
        // || station.latitude
        // .equals("null")) {
        // continue;
        // }
        // list.add(station);
        // }
        // if (list != null && list.size() > 0) {
        // MyApplication.mDB
        // .deleteAll(Station.class);
        // MyApplication.mDB.saveAll(list);
        // }
        // }
        // }
        //
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public void selectPortCallBack() {
        LatLng llLatLng;
        if (AppConfig.isWatherSystem()) {
            llLatLng = new LatLng(Float.parseFloat(MyApplication.currentWaterPortInfo.Y), Float.parseFloat(MyApplication.currentWaterPortInfo.X));


            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llLatLng);
            mbaiduMap.animateMapStatus(u);
        } else {
            llLatLng = new LatLng(Float.parseFloat(MyApplication.currentAirPortInfo.Y), Float.parseFloat(MyApplication.currentAirPortInfo.X));

            if (llLatLng.latitude == 0.0 || llLatLng.latitude == 0.0000) {

                MyApplication.showTextToast("该点位没有经纬度");
                return;

            }

            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(llLatLng);
            mbaiduMap.animateMapStatus(u);
        }
//        LatLngBounds clLatLngBounds = new LatLngBounds.Builder().include(llLatLng).build();

//        String aa = clLatLngBounds.southwest.toString();

//        Marker marker = mbaiduMap.getMarkersInBounds(clLatLngBounds).get(0);
        // 创建InfoWindow展示的view
//        Button button = new Button(GisActivity.mContext);
//        button.setBackgroundResource(R.drawable.gis_pop_bg);
//        button.setText(marker.getExtraInfo().getString("name"));
        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
//        InfoWindow mInfoWindow = new InfoWindow(button, llLatLng, -100);
        // 显示InfoWindow
//        mbaiduMap.showInfoWindow(mInfoWindow);
        mbaiduMap.setOnMapClickListener(new OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                mbaiduMap.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }
        });
    }
}
