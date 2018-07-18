package com.sinoyd.environmentNT.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.dialog.PortsDialog;
import com.sinoyd.environmentNT.dialog.PortsDialog.PortItemSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.NetworkUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.view.RefreshButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/***
 * 界面有刷新的功能
 *
 * @author smz 创建时间：2013-12-17下午11:49:26
 */
public abstract class RefreshBaseActivity extends BaseFragment implements OnClickListener, PortItemSelectListener {
    protected boolean isClickRefresh;
    protected RefreshButton mRefreshButton;
    private View changeSystemView;
    /**
     * 当前站点
     **/
    protected PortInfo portInfo;
    /**
     * 当前站点Id
     **/
    public String portId = "0";
    /**
     * 站点名称控件
     **/
    protected TextView mTitleTextView;
    private boolean showDialog = true;
    private HashMap<String, String> portRequest = new HashMap<String, String>();
    PortsDialog dialog = null;
    private final String LOGIN_KEY = "IS_LOGIN";

    /***
     * 初始化加载view
     */
    protected void initView() {
        try {
            updateFace();
            View view = findViewById(R.id.title);
            if (view != null) {
                mTitleTextView = (TextView) view;
                mTitleTextView.setOnClickListener(this);
                if (portInfo != null) {
                    mTitleTextView.setText(portInfo.PortName);
                }
                View changeStationIcon = findViewById(R.id.change_station_icon);

                if (changeStationIcon != null) {

                    changeStationIcon.setOnClickListener(this);
                }

            }
            mRefreshButton = (RefreshButton) findViewById(R.id.top_refresh_btn);
            findViewById(R.id.top_refresh).setOnClickListener(this);
            findViewById(R.id.top_more).setOnClickListener(this);
            changeSystemView = findViewById(R.id.top_qiehuan);
            if (PreferenceUtils.getBoolean(this.getActivity(), LOGIN_KEY)) {
                if (changeSystemView != null) {
                    changeSystemView.setOnClickListener(this);
                }
            } else
                changeSystemView.setVisibility(View.GONE);
//            if (AppConfig.isWatherSystem()) {
//                dialog = new PortsDialog(mContext, R.style.dialog, PortCacheUtils.getWaterPortList());
//            } else {
                dialog = new PortsDialog(mContext, R.style.dialog, PortCacheUtils.getAirPortList());
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }


    public void StopAnimation() {
        if (mRefreshButton != null) {
            mRefreshButton.stop();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == null)
            return;
        switch (v.getId()) {
            case R.id.top_refresh:
                isClickRefresh = true;
                requestServer();
                break;
            case R.id.top_more:
                more();
                break;
            case R.id.top_qiehuan:
                updateSystem();
                break;
            case R.id.change_station_icon:
            case R.id.title:
                /**scj  2017-7-12*/
                loadPort(true);
                break;
        }
    }

    public static boolean isHasNetWork() {
        if (NetworkUtil.getNetworkType() == NetworkUtil.NO_NET_CONNECT) {
            MyApplication.showTextToast("网络连接失败");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void requestServer() {
        super.requestServer();
        if (mRefreshButton != null) {
            mRefreshButton.start();
        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        if (mRefreshButton != null) {
            mRefreshButton.stop();
        }
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if (mRefreshButton != null) {
            mRefreshButton.stop();
        }
        if (RequestAirActionName.GetPortInfoBySysType.equals(resData.getUri())) {
            JSONObject jsonObject = resData.getJson();
            JSONArray array = jsonObject.optJSONArray("PortInfo");
            boolean isWater = AppConfig.isWatherSystem();
            if (isWater)
                PortCacheUtils.clearWaterModel();
            else
                PortCacheUtils.clearAirModel();

            if (array != null && array.length() > 0) {
                PortInfo portInfo;
                for (int i = 0; i < array.length(); i++) {
                    portInfo = new PortInfo();
                    portInfo.parse(array.optJSONObject(i));
//                    portInfo.isWaterPort = isWater;
                    if (isWater) {
                        PortCacheUtils.addWaterPortInfo(portInfo);
                    } else {
                        PortCacheUtils.addAirPortInfo(portInfo);
                    }
                }
            }
            if (PortCacheUtils.getAirPortList() != null && PortCacheUtils.getAirPortList().size() > 0) {
                MyApplication.currentAirPortInfo = PortCacheUtils.getAirPortList().get(0);
            }
            if (PortCacheUtils.getWaterPortList() != null && PortCacheUtils.getWaterPortList().size() > 0) {
                MyApplication.currentWaterPortInfo = PortCacheUtils.getWaterPortList().get(0);
            }
            if (showDialog)
                showPortDialog();
        }
    }


    public synchronized void loadPort(boolean showDialog) {
        this.showDialog = showDialog;
//        if (AppConfig.isWatherSystem()) {
//            if (PortCacheUtils.waterIsEmpty()) {
//                portRequest.put("sysType", "water");
//                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                Boolean IsLogin = PreferenceUtils.getBoolean(getActivity(), "IS_LOGIN");
//                if (IsLogin) {
//
//                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
//                    portRequest.put("LoginId", LoginId);
//                } else
//                    portRequest.put("LoginId", "taicangapp");
//
//                HttpClient.getJsonWithGetUrl(RequestAirActionName.GetPortInfoBySysType, portRequest, this, null);
//
//            } else {
//                if (showDialog)
//                    showPortDialog();
//            }
//        } else {
            if (PortCacheUtils.airIsEmpty()) {
                portRequest.put("sysType", "air");
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                Boolean IsLogin = PreferenceUtils.getBoolean(getActivity(), "IS_LOGIN");
                if (IsLogin) {
                    String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
                    portRequest.put("LoginId", LoginId);
                } else
                    portRequest.put("LoginId", "taicangapp");
                HttpClient.getJsonWithGetUrl(RequestAirActionName.GetPortInfoBySysType, portRequest, this, null);
            } else {
                if (showDialog)
                    showPortDialog();
            }
//        }
    }


    public void showPortDialog() {
        dialog.setPortItemSelectListener(this);


        List<PortInfo> aa = PortCacheUtils.getAirPortList();
        for (int i = 0; i < aa.size(); i++)
            Log.e("站点", aa.get(i).getPortName());

        dialog.show();
    }


    /***
     * 设置当前的站点信息
     */
    @Override
    public void selectPortinfo(PortInfo portInfo) {
        portId = portInfo.PortId;
        if (AppConfig.isWatherSystem())
            MyApplication.currentWaterPortInfo = portInfo;
        else
            MyApplication.currentAirPortInfo = portInfo;
        if (mTitleTextView != null) {
            mTitleTextView.setText(portInfo.PortName);
        }
        selectPortCallBack();
    }

    public abstract void selectPortCallBack();
}
