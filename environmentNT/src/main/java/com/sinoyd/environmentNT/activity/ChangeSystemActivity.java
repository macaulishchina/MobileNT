package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.AppConfig.SystemType;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.reflect.StyleController;
import com.sinoyd.environmentNT.util.NetworkUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.SystemUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 切换系统 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：ChangeSystemActivity
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class ChangeSystemActivity extends AbstractActivity implements OnClickListener {
    private HashMap<String, String> portRequest = new HashMap<String, String>();

    /**
     * 是否请求水站点或者水因子
     **/
    private static boolean isRequestLoadWater;
    private TextView check_msg_text;
    LoadDialog dialog;

    private String systemType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_sytem_layout);

    }

    @Override
    protected void initView() {
        try {
            isRequestLoadWater = true;
            findViewById(R.id.air_btn).setOnClickListener(this);
            findViewById(R.id.water_btn).setOnClickListener(this);
            check_msg_text = (TextView) findViewById(R.id.check_msg_text);
            SystemUtil.getVersion(getApplicationContext());
            check_msg_text.setText("V" + SystemUtil.versionName);
            check_msg_text.setTextSize(getApplicationContext().getResources().getDimension(R.dimen.loadversionname_size));

        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {

        dialog = new LoadDialog(ChangeSystemActivity.this);
        dialog.show();
        switch (v.getId()) {

            case R.id.air_btn:
                AppConfig.systemType = SystemType.AirType;
                PreferenceUtils.saveBoolean(this, "is_water", false);
                systemType = "环境空气";
                break;
            case R.id.water_btn:
                AppConfig.systemType = SystemType.WatherType;
                PreferenceUtils.saveBoolean(this, "is_water", true);

                systemType = "地表水";
                break;
            default:
                break;
        }
        loadPort();
    }

    /***
     * 刷新主界面
     *
     */
    private void refreshActivity() {

        if (PortCacheUtils.getWaterPortList() != null && PortCacheUtils.getWaterPortList().size() > 0) {
            MyApplication.currentWaterPortInfo = PortCacheUtils.getWaterPortList().get(0);
        }
        if (PortCacheUtils.getAirPortList() != null && PortCacheUtils.getAirPortList().size() > 0) {
            MyApplication.currentAirPortInfo = PortCacheUtils.getAirPortList().get(0);
        }
        MyApplication.showTextToast("切换为" + systemType + "系统成功");
        startActivity(new Intent(this, MainActivity.class));
        finish();
        dialog.dismiss();
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if (RequestAirActionName.GetFactorList.equals(resData.getUri())) {
            try {
                if (isRequestLoadWater) {
                    JSONObject obj = resData.getJson();
                    JSONArray array = obj.getJSONArray("FactorList");
                    MyApplication.factorWaterList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        MyApplication.factorWaterList.add(array.getJSONObject(i).getString("name"));
                    }
                    portRequest.clear();
                    isRequestLoadWater = false;
                    portRequest.put("sysType", "air");
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
                    if (IsLogin) {

                        String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
                        portRequest.put("LoginId", LoginId);
                    } else
                        portRequest.put("LoginId", "taicangapp");
                    HttpClient.getJsonWithGetUrl(RequestAirActionName.GetPortInfoBySysType, portRequest, this, null);
                } else {
                    JSONObject obj = resData.getJson();
                    JSONArray array = obj.getJSONArray("FactorList");
                    MyApplication.factorAirList.clear();
                    for (int i = 0; i < array.length(); i++) {
                        MyApplication.factorAirList.add(array.getJSONObject(i).getString("name"));
                    }
                    refreshActivity();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        if (RequestAirActionName.GetPortInfoBySysType.equals(resData.getUri())) {
            JSONObject jsonObject = resData.getJson();
            JSONArray array = jsonObject.optJSONArray("PortInfo");
            if (array != null && array.length() > 0) {
                PortInfo portInfo;
                for (int i = 0; i < array.length(); i++) {
                    portInfo = new PortInfo();
                    portInfo.parse(array.optJSONObject(i));
//                    portInfo.isWaterPort = isRequestLoadWater;
                    if (isRequestLoadWater) {
                        PortCacheUtils.addWaterPortInfo(portInfo);
                    } else {
                        PortCacheUtils.addAirPortInfo(portInfo);
                    }
                }
            }
            try {
                //内存泄漏
//				if(!mDB.getDatabase().isOpen())
//				 
//                     mDB.getDatabase().openOrCreateDatabase(mDB.getDatabase().getPath(),null );

                if (isRequestLoadWater) {
                    MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "1"));
                    MyApplication.mDB.saveAll(PortCacheUtils.getWaterPortList());
                    HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);
                } else {
                    MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "0"));
                    MyApplication.mDB.saveAll(PortCacheUtils.getAirPortList());
                    HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取站点信息
     *
     * @功能描述：TODO
     * @创建者：张津明
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public synchronized void loadPort() {
        if (MyApplication.mDB == null) {
            MyApplication.mDB = DbUtils.create(this, MyApplication.DATABASE_NAME, MyApplication.DATABASE_VERSION, null);
        }
        PortCacheUtils.clearWaterModel();
        PortCacheUtils.clearAirModel();
        if (NetworkUtil.getNetworkType() == NetworkUtil.NO_NET_CONNECT) {
            MyApplication.showTextToast("获取站点失败，请连接网络后再试");
            return;
        } else {
            portRequest.clear();
            isRequestLoadWater = true;
            portRequest.put("sysType", "water");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
            if (LoginId != null && !LoginId.equals(""))
                portRequest.put("LoginId", LoginId);
            else
                portRequest.put("LoginId", "taicangapp");
            HttpClient.getJsonWithGetUrl(RequestAirActionName.GetPortInfoBySysType, portRequest, this, "正在加载站点信息...");
        }
    }

    @Override
    protected void onDestroy() {
        findViewById(R.id.water_btn).setBackgroundResource(0);
        findViewById(R.id.air_btn).setBackgroundResource(0);
        StyleController.freeViewBg(findViewById(R.id.page_bg));
        portRequest = null;

        //内存泄漏
        MyApplication.activityContext = null;

        super.onDestroy();
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        // TODO Auto-generated method stub
        super.requestFailed(resData);
        showTextToast("获取数据失败！");
    }
}
