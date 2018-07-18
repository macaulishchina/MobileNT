package com.sinoyd.environmentNT.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.http.EPHttpRequestUtils;
import com.sinoyd.environmentNT.http.HttpResult;
import com.sinoyd.environmentNT.http.HttpTask;
import com.sinoyd.environmentNT.http.HttpUtils.HttpState;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.model.PortListInfo;
import com.sinoyd.environmentNT.util.PreferenceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 添加站点弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：AddPositionDialog
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class AddPositionDialog extends Dialog {
    private GridView gvSelect;
    /**
     * 添加站点列表
     **/
    private List<PortInfo> portList;
    private List<Map<String, String>> adapterData;
    private SimpleAdapter adapter;
    Context mcontext;

    public interface AddCallback {
        void add(PortInfo info);

        boolean isAdd(String portId);
    }

    private AddCallback addCallback;

    public void setAddCallback(AddCallback addCallback) {
        this.addCallback = addCallback;
    }

    public AddPositionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mcontext = context;
        initView();
    }

    public AddPositionDialog(Context context, int theme) {
        super(context, theme);
        mcontext = context;
        initView();
    }

    public AddPositionDialog(Context context) {
        super(context);
        mcontext = context;
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        adapterData = new ArrayList<Map<String, String>>();
        portList = new ArrayList<PortInfo>();
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.addposition_dialog);
        gvSelect = (GridView) findViewById(R.id.gvSelect);
        adapter = new SimpleAdapter(getContext(), adapterData, R.layout.select_position_gv_item, new String[]{"name"}, new int[]{R.id.tvPMName});
        gvSelect.setAdapter(adapter);
        gvSelect.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int location, long arg3) {
                if (addCallback != null)
                    addCallback.add(portList.get(location));
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
    }

    /***
     * 更新数据
     */
    public void updateData() {
        new HttpTask<PortListInfo>() {
            @Override
            protected void doPreExecute() throws Exception {
                adapterData.clear();
                portList.clear();
                adapter.notifyDataSetChanged();
            }

            @Override
            protected PortListInfo doBack(Object[]... params) throws Exception {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mcontext);
                HttpResult result = null;

                if (AppConfig.isWatherSystem()) {

                    if (PreferenceUtils.getValue(preferences, "LoginId") != null && !PreferenceUtils.getValue(preferences, "LoginId").equals(""))

                        result = EPHttpRequestUtils.GetPortInfo("water", PreferenceUtils.getValue(preferences, "LoginId").toString());
                    else
                        result = EPHttpRequestUtils.GetPortInfo("water", "taicangapp");
                    if (result.resultCode == HttpState.HTTP_OK) {
                        JSONObject jsonObject = new JSONObject(result.jsonData);
                        PortListInfo portListInfo = new PortListInfo();
                        PortInfo portInfo = new PortInfo();
                        portListInfo.PortInfo = new ArrayList<PortInfo>();
                        for (int i = 0; i < jsonObject.getJSONArray("PortInfo").length(); i++) {

                            try {
                                portInfo = new PortInfo();
//                                portInfo.isWaterPort = true;
                                portInfo.PortId = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortId");
                                portInfo.PortName = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortName");
                                portInfo.X = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("X");
                                portInfo.Y = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("Y");
                                portInfo.PortType = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortType");
                                portInfo.RegionType = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("RegionType");
                                portInfo.orderNumber = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("orderNumber");

                            } catch (JSONException e) {

                            }

                            portListInfo.PortInfo.add(portInfo);
                        }

                        return portListInfo;

//						return JSON.parse(result.jsonData, PortListInfo.class);
                    }

                } else {
                    if (PreferenceUtils.getValue(preferences, "LoginId") != null && !PreferenceUtils.getValue(preferences, "LoginId").equals(""))
                        result = EPHttpRequestUtils.GetPortInfo("air", PreferenceUtils.getValue(preferences, "LoginId").toString());
                    else
                        result = EPHttpRequestUtils.GetPortInfo("air", "taicangapp");
                    if (result.resultCode == HttpState.HTTP_OK)
//						return JSON.parse(result.jsonData, PortListInfo.class);
                    {
                        JSONObject jsonObject = new JSONObject(result.jsonData);
                        PortListInfo portListInfo = new PortListInfo();
                        PortInfo portInfo = new PortInfo();
                        portListInfo.PortInfo = new ArrayList<PortInfo>();
                        for (int i = 0; i < jsonObject.getJSONArray("PortInfo").length(); i++) {

                            try {
                                portInfo = new PortInfo();
//                                portInfo.isWaterPort = false;
                                portInfo.PortId = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortId");
                                portInfo.PortName = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortName");
                                portInfo.X = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("X");
                                portInfo.Y = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("Y");
                                portInfo.PortType = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("PortType");
                                portInfo.RegionType = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("RegionType");
                                portInfo.orderNumber = jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("orderNumber");
//							portInfo.Level=jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("Level");
//							portInfo.IsOnline=jsonObject.getJSONArray("PortInfo").optJSONObject(i).getString("IsOnline");
                            } catch (JSONException e) {

                            }
                            portListInfo.PortInfo.add(portInfo);
                        }
                        return portListInfo;
                    }
                }


                return null;
            }

            @Override
            protected void doProgress(Integer... values) throws Exception {
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void doPost(PortListInfo result) throws Exception {
                if (result != null) {
                    int index = 0;
                    for (PortInfo info : result.PortInfo) {
                        if (addCallback != null && !addCallback.isAdd(info.PortId)) {
                            Map<String, String> data = new HashMap<String, String>();
                            data.put("name", info.PortName);
                            adapterData.add(data);
                            portList.add(info);
                            publishProgress(index++);
                        }
                    }
                }
                if (portList.size() == 0) {
                    MyApplication.showTextToast("没有测点可选择");
                } else {
                    show();
                }
            }
        }.execute();
    }
}
