package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.common.Logger;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.database.PositionDBModel;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.model.PortListInfo;
import com.sinoyd.environmentNT.reflect.StyleController;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.PullectUtils;
import com.sinoyd.environmentNT.util.SystemUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/***
 * 登陆
 *
 * @author smz 创建时间：2014-2-20上午12:11:56
 */
public class LoginActivity extends AbstractActivity implements OnClickListener {
    private final String LOGIN_KEY = "IS_LOGIN";
    private EditText loginIdText, pwdText;
    private HashMap<String, String> requestParams = new HashMap<String, String>();
    private TextView txt_version;
    private HashMap<String, String> portRequest = new HashMap<String, String>();
    private PortListInfo portListInfo;
    public CheckBox check_loginpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        if (PreferenceUtils.getBoolean(this, LOGIN_KEY)&&!PreferenceUtils.getValue(preferences, "Password").equals("")) {
////            startActivity(new Intent(this, ChangeSystemActivity.class));
//            startActivity(new Intent(this, MainActivity.class));
//            finish();
//        }
    }

    @Override
    protected void initView() {
        findViewById(R.id.login_btn).setOnClickListener(this);
        txt_version = (TextView) findViewById(R.id.txt_version);
        loginIdText = (EditText) findViewById(R.id.user_name_edit);
        pwdText = (EditText) findViewById(R.id.user_pass_edit);
        check_loginpwd = (CheckBox) findViewById(R.id.check_loginpwd);
        loginIdText.setText("");
        pwdText.setText("");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (PreferenceUtils.getValue(preferences, "LoginId") != null)
            loginIdText.setText(PreferenceUtils.getValue(preferences, "LoginId"));


        if (!PreferenceUtils.getValue(preferences, "Password").equals(""))
            pwdText.setText(PreferenceUtils.getValue(preferences, "Password"));


        txt_version.setText("V" + SystemUtil.versionName);
        txt_version.setTextSize(getApplicationContext().getResources().getDimension(R.dimen.loadversionname_size));
    }

    @Override
    protected void onDestroy() {
        loginIdText.setBackgroundResource(0);
        pwdText.setBackgroundResource(0);
        ((ImageView) findViewById(R.id.login_btn)).setImageResource(0);
        loginIdText = null;
        pwdText = null;
        StyleController.freeViewBg(findViewById(R.id.page_bg));
        super.onDestroy();
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        JSONObject jsonObject = resData.getJson();
//		boolean login = jsonObject.optBoolean("Login");
//		String UserUid = jsonObject.optString("UserUid");
//		if (login) {
//			PreferenceUtils.saveBoolean(this, LOGIN_KEY, true);
//			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//			PreferenceUtils.saveValue(preferences, "UserGuid", UserUid);
//			MyApplication.UserGuid=UserUid;
//			startActivity(new Intent(this, ChangeSystemActivity.class));
//			finish();
//		}
//		else {
//			showTextToast("登陆失败，请确认账号和密码");
//		}
//		boolean login = jsonObject.optBoolean("Login");
        //	String UserUid = jsonObject.optString("UserUid");
        if (resData.getUri().equals(AppConfig.RequestAirActionName.GetLoginInfo) && requestParams != null) {
            String UserUid = "";
            String Alias = "";
            String ErrorInfo = "";
            boolean login = false;
            String MenuInfo = "1111111111111";
            try {
//                MenuInfo = jsonObject.optJSONObject("Data").getString("MenuInfo");
//                UserUid = jsonObject.optJSONObject("Data").getString("UserGuid");
//                ErrorInfo = jsonObject.optJSONObject("Data").getString("ErrorInfo");
//                login = jsonObject.getBoolean("IsSuccess");

                login = jsonObject.getBoolean("Login");
                UserUid = jsonObject.getString("UserUid");
                Alias = jsonObject.getString("Alias");
                PreferenceUtils.saveValue(PreferenceManager.getDefaultSharedPreferences(this), "Alias", Alias);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (login) {
                PreferenceUtils.saveBoolean(this, LOGIN_KEY, true);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                PreferenceUtils.saveValue(preferences, "UserGuid", UserUid);
                PreferenceUtils.saveValue(preferences, "MenuInfo", MenuInfo);


                PreferenceUtils.saveValue(preferences, "LoginId", loginIdText.getText().toString());


                if (check_loginpwd.isChecked()) {

                    PreferenceUtils.saveValue(preferences, "Password", pwdText.getText().toString());

                } else {


                    PreferenceUtils.saveValue(preferences, "Password", "");
                }

                MyApplication.UserGuid = UserUid;
                portRequest.clear();
                portRequest.put("sysType", "air");
                portRequest.put("LoginId", loginIdText.getText().toString());

                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPortInfoBySysType, portRequest, this, null);//				startActivity(new Intent(this, ChangeSystemActivity.class));
//				finish();
            } else {
                if (ErrorInfo.equals(""))
                    showTextToast("登陆失败，请确认账号和密码");
                else
                    showTextToast(ErrorInfo);
            }
        }
        //空气站点
        if (resData.getUri().equals(AppConfig.RequestAirActionName.GetPortInfoBySysType) && portRequest != null && portRequest.get("sysType").equals("air")) {
            try {
                jsonObject = resData.getJson();
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
                        MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b().and("isWaterPort", "=", 0));
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
//            } else {
//                portRequest.clear();
//                portRequest.put("sysType", "water");
//                portRequest.put("action", "water");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
//                if (IsLogin) {
//
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "taicangapp");
//
//                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPortInfoBySysType, portRequest, this, "正在加载站点信息...");
//            }

            portRequest.clear();
            portRequest.put("sysType", "air");

            portRequest.put("action", "air");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
            if (IsLogin) {
                String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
                portRequest.put("LoginId", LoginId);
            } else
                portRequest.put("LoginId", "taicangapp");
            HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);

        }

//        //水站点
//        if (resData.getUri().equals(AppConfig.RequestAirActionName.GetPortInfoBySysType) && resData.getTag() != null && resData.getTag().equals("water")) {
//            jsonObject = resData.getJson();
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
//
//
//                MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "1"));
//                MyApplication.mDB.saveAll(PortCacheUtils.getWaterPortList());
        //获取空气因子

//
//            } catch (DbException e) {
//                e.printStackTrace();
//            }
//        }

        //空气因子想
        if (AppConfig.RequestAirActionName.GetFactorList.equals(resData.getUri()) && resData.getTag() != null && resData.getTag().equals("air")) {
            try {

                JSONObject obj = resData.getJson();
                JSONArray array = obj.getJSONArray("FactorList");
                MyApplication.factorAirList.clear();
                for (int i = 0; i < array.length(); i++) {
                    MyApplication.factorAirList.add(array.getJSONObject(i).getString("name"));
                }

                //refreshActivity();
                startActivity(new Intent(this, MainActivity.class));
                finish();

//                //获取水因子
//                portRequest.clear();
//                portRequest.put("sysType", "water");
//                portRequest.put("action", "water");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//                Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
//                if (IsLogin) {
//
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "taicangapp");
//                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetFactorList, portRequest, this, null);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
//        //水因子
//        if (AppConfig.RequestAirActionName.GetFactorList.equals(resData.getUri()) && resData.getTag() != null && resData.getTag().equals("water")) {
//            try {
//
//                JSONObject obj = resData.getJson();
//                JSONArray array = obj.getJSONArray("FactorList");
//                MyApplication.factorWaterList.clear();
//                for (int i = 0; i < array.length(); i++) {
//                    MyApplication.factorWaterList.add(array.getJSONObject(i).getString("name"));
//                }
//
//
//                AppConfig.systemType = AppConfig.SystemType.AirType;
//                PreferenceUtils.saveBoolean(this, "is_water", false);
//                systemType = "环境空气";
//
//                startActivity(new Intent(this, MainActivity.class));
////               startActivity(new Intent(this,ChangeSystemActivity.class));
//                finish();
////				mHandler.sendEmptyMessageDelayed(1, 1000);
//
//            } catch (Exception e) {
//                // TODO: handle exception
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (loginIdText.getText() == null || loginIdText.getText().length() < 1) {
                    showErrorMsg("请输入登陆账号");
                    return;
                }
                if (pwdText.getText() == null || pwdText.getText().length() < 1) {
                    showErrorMsg("请输入登陆密码");
                    return;
                }
                requestParams.put("loginId", loginIdText.getText().toString());
                requestParams.put("pwd", pwdText.getText().toString());
                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLoginInfo, requestParams, this, "正在登陆...");
//                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLoginInfoNew, requestParams, this, "正在登陆...");
                break;
            default:
                break;
        }
    }
}
