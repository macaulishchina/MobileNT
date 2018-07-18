package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.iceman.paintdemo.Globe;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.database.PositionDBModel;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.model.PortListInfo;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.PullectUtils;
import com.sinoyd.environmentNT.util.SystemUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * load界面 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：LoadActivity
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class LoadActivity extends AbstractActivity {
    /** 请求接口传入参数 **/
//	HashMap<String, String> portRequest = new HashMap<String, String>();
    /**
     * 站点信息列表
     **/
    private PortListInfo portListInfo;
    private TextView check_msg_text;
    private String systemType = "";
    private final String LOGIN_KEY = "IS_LOGIN";
    private HashMap<String, String> portRequest = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        Log.i("scj", "LoadActivity_onCreate");


        PreferenceUtils.saveBoolean(this, "start_app", true);
//
//        if (PreferenceUtils.getBoolean(this, "noFirstStart")) {
//
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            if (PreferenceUtils.getValue(preferences, "Password").equals("")) {
//                Log.i("scj", "loading界面进去LoginActivity");
//                startActivity(new Intent(this, LoginActivity.class));
//            } else {
//                Log.i("scj", "loading界面进去MainActivity");
//                startActivity(new Intent(this, MainActivity.class));
//            }
//        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        boolean noFirstStart = PreferenceUtils.getBoolean(this, "noFirstStart");

        AppConfig.systemType = AppConfig.SystemType.AirType;
        PreferenceUtils.saveBoolean(this, "is_water", false);
        systemType = "环境空气";
        //startActivity(new Intent(this, ChangeSystemActivity.class));
        if (PortCacheUtils.getWaterPortList() != null && PortCacheUtils.getWaterPortList().size() > 0) {
            MyApplication.currentWaterPortInfo = PortCacheUtils.getWaterPortList().get(0);
        }
        if (PortCacheUtils.getAirPortList() != null && PortCacheUtils.getAirPortList().size() > 0) {
            MyApplication.currentAirPortInfo = PortCacheUtils.getAirPortList().get(0);
        }
        if (noFirstStart) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (PreferenceUtils.getValue(preferences, "Password").equals("")) {
                Log.i("scj", "loading界面进去LoginActivity");
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            } else {
                Log.i("scj", "loading界面进去MainActivity");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        } else {
            Intent intent = new Intent(this, HelpActivity.class);
            intent.putExtra("from", false);
            startActivity(intent);
            finish();
        }

        return super.handleMessage(msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {

//        StyleController.freeViewBg(findViewById(R.id.load_view));
        super.onDestroy();
// 		Debug.stopMethodTracing();//关闭跟踪日志记录
        //内存泄漏
// 		mDB.close();
        Log.i("scj", "LoadActivity_onDestroy");
    }

    /***
     * 初始化加载数据
     */
    @Override
    protected void initView() {
        if (Globe.density == 0f) {
            Globe.density = getResources().getDisplayMetrics().density;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Globe.fullScreenHeight = dm.heightPixels;
        Globe.fullScreenWidth = dm.widthPixels;
        System.out.println(Globe.density + ";" + Globe.fullScreenHeight);
        check_msg_text = (TextView) findViewById(R.id.check_msg_text);
        SystemUtil.getVersion(getApplicationContext());
        check_msg_text.setText("V" + SystemUtil.versionName);
        check_msg_text.setTextSize(getApplicationContext().getResources().getDimension(R.dimen.loadversionname_size));
        List<PortInfo> tempList = null;
//		try {
//			//mDB.deleteAll(PortInfo.class);
//			tempList = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("isWaterPort", "=", "0"));
//		}
//		catch (DbException e) {
//			e.printStackTrace();
//			tempList = null;
//		}
//		if (tempList == null || tempList.size() < 1) {
        portRequest.clear();
        portRequest.put("sysType", "air");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
        if (IsLogin) {

            String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
            portRequest.put("LoginId", LoginId);
        } else
            portRequest.put("LoginId", "nantongapp");


        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPortInfoBySysType, portRequest, this, null);


//		}
//		else {
//			mHandler.sendEmptyMessageDelayed(1, 10);
//		}
    }


    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        //空气站点
        if (resData.getUri().equals(RequestAirActionName.GetPortInfoBySysType) && portRequest != null && portRequest.get("sysType").equals("air")) {
            try {
                JSONObject jsonObject = resData.getJson();
                JSONArray array = jsonObject.optJSONArray("PortInfo");
                if (array != null && array.length() > 0) {
                    PortInfo portInfo;
                    for (int i = 0; i < array.length(); i++) {
                        portInfo = new PortInfo();
                        portInfo.parse(array.optJSONObject(i));
//                        portInfo.isWaterPort = false;
                        PortCacheUtils.addAirPortInfo(portInfo);
                    }
                }
                if (PortCacheUtils.getAirPortList() != null && PortCacheUtils.getAirPortList().size() > 0) {
                    MyApplication.currentAirPortInfo = PortCacheUtils.getAirPortList().get(0);
                    try {
                        MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "0"));
                        MyApplication.mDB.saveAll(PortCacheUtils.getAirPortList());
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (portListInfo == null) {
                    portListInfo = new PortListInfo();
                }
                portListInfo.parse(resData.getJson());
                List<PortInfo> array1 = portListInfo.PortInfo;
                if (array1 != null && array1.size() > 0) {
                    PortInfo addInfo;
                    MyApplication.mDB.deleteAll(PositionDBModel.class);
                    for (int i = 0; i < array1.size(); i++) {
                        addInfo = array1.get(i);
                        PortHourAQIInfo pmInfo = new PortHourAQIInfo();
                        pmInfo.PortId = addInfo.PortId;
                        pmInfo.AQI = "0";
                        pmInfo.Class = PullectUtils.getPullectMsgByValue(Integer.parseInt(pmInfo.AQI));
                        pmInfo.DateTime = DateUtil.formatDate(new Date(), "MM/dd HH:mm");
                        pmInfo.PortName = addInfo.PortName;
                        PositionDBModel entity = new PositionDBModel(addInfo.PortName, addInfo.PortId, addInfo.X, addInfo.Y);
                        try {
                            MyApplication.mDB.save(entity);
//							BracaseUtils.startUpdate(getBaseContext(), new Intent(BracaseUtils.ADD_ACTION));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


//            //获取水的站点
//            if (MyApplication.mDB == null) {
//                MyApplication.mDB = DbUtils.create(this, MyApplication.DATABASE_NAME, MyApplication.DATABASE_VERSION, null);
//            }
//            PortCacheUtils.clearWaterModel();
////			PortCacheUtils.clearAirModel();
//            if (NetworkUtil.getNetworkType() == NetworkUtil.NO_NET_CONNECT) {
//                MyApplication.showTextToast("获取站点失败，请连接网络后再试");
//                return;
//            }
//            else {
//                portRequest.clear();
//
//                portRequest.put("sysType", "water");
//                portRequest.put("action", "water");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
//                if (IsLogin) {
//
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "nantongapp");
//                HttpClient.getJsonWithGetUrl(RequestAirActionName.GetPortInfoBySysType, portRequest, this, "正在加载站点信息...");
//            }
            HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);
        }

//        //水站点
//        if (resData.getUri().equals(RequestAirActionName.GetPortInfoBySysType) && resData.getTag() != null && resData.getTag().equals("water")) {
//            JSONObject jsonObject = resData.getJson();
//            JSONArray array = jsonObject.optJSONArray("PortInfo");
//            if (array != null && array.length() > 0) {
//                PortInfo portInfo;
//                for (int i = 0; i < array.length(); i++) {
//                    portInfo = new PortInfo();
//                    portInfo.parse(array.optJSONObject(i));
//                    portInfo.isWaterPort = true;
////					if (isRequestLoadWater) {
//                    PortCacheUtils.addWaterPortInfo(portInfo);
////					}
////					else {
////						PortCacheUtils.addAirPortInfo(portInfo);
////					}
//                }
//            }
//            try {
//                //内存泄漏
////				if(!mDB.getDatabase().isOpen())
////
////                     mDB.getDatabase().openOrCreateDatabase(mDB.getDatabase().getPath(),null );
//                MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "1"));
//                MyApplication.mDB.saveAll(PortCacheUtils.getWaterPortList());
//                //获取空气因子
//                portRequest.clear();
//                portRequest.put("sysType", "air");
//
//                portRequest.put("action", "air");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
//                if (IsLogin) {
//
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "admin");
//                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);
//
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//        }

        //空气因子
        if (RequestAirActionName.GetFactorList.equals(resData.getUri())) {
            try {
                JSONObject obj = resData.getJson();
                JSONArray array = obj.getJSONArray("FactorList");
                MyApplication.factorAirList.clear();
                for (int i = 0; i < array.length(); i++) {
                    MyApplication.factorAirList.add(array.getJSONObject(i).getString("name"));
                }

                mHandler.sendEmptyMessageDelayed(1, 10);
                //refreshActivity();
//                //获取水因子
//                portRequest.clear();
//                portRequest.put("sysType", "water");
//                portRequest.put("action", "water");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
//                if (IsLogin) {
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "nantongapp");
//                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);

            } catch (Exception e) {
                // TODO: handle exception
                Log.i("scj", e.getMessage());
            }
        }
//        //水因子
//        if (RequestAirActionName.GetFactorList.equals(resData.getUri()) && resData.getTag() != null && resData.getTag().equals("water")) {
//            try {
//                JSONObject obj = resData.getJson();
//                JSONArray array = obj.getJSONArray("FactorList");
//                MyApplication.factorWaterList.clear();
//                for (int i = 0; i < array.length(); i++) {
//                    MyApplication.factorWaterList.add(array.getJSONObject(i).getString("name"));
//                }
//                mHandler.sendEmptyMessageDelayed(1, 10);
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        if (mHandler != null) {
            mHandler.sendEmptyMessageDelayed(1, 10);
        }
    }
}
