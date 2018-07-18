package com.sinoyd.environmentNT.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.GridView;

import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.exception.DbException;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.PmAdapter;
import com.sinoyd.environmentNT.adapter.PmAdapter.PmCallback;
import com.sinoyd.environmentNT.adapter.PmWaterAdapter;
import com.sinoyd.environmentNT.data.PortCacheUtils;
import com.sinoyd.environmentNT.database.PositionDBModel;
import com.sinoyd.environmentNT.http.EPHttpRequestUtils;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.http.HttpResult;
import com.sinoyd.environmentNT.http.HttpTask;
import com.sinoyd.environmentNT.http.HttpUtils.HttpState;
import com.sinoyd.environmentNT.json.JSON;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.model.PortHourAQIListInfo;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.util.BracaseUtils;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.PullectUtils;
import com.sinoyd.environmentNT.view.AddPositionDialog;
import com.sinoyd.environmentNT.view.AddPositionDialog.AddCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 站点管理 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：PositionManagerActivity
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PositionManagerActivity extends AbstractActivity {
    private GridView gvPm;
    private PmAdapter pmAdapter;
    private PmWaterAdapter pmWaterAdapter;
    /**
     * 添加站点弹出框
     **/
    private AddPositionDialog addDialog;
    private List<String> listIndex;
    /**
     * 站点信息实体
     **/
    private PortInfo addInfo;
    /**
     * 获取接口传入参数
     **/
    private HashMap<String, String> requestParams = null;
    private HashMap<String, String> portRequest = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listIndex = new ArrayList<String>();
        addDialog = new AddPositionDialog(this, R.style.FullHeightDialog);

        addDialog.setAddCallback(new AddCallback() {
            @Override
            public void add(PortInfo info) {
                addInfo = info;
                listIndex.add(info.PortId);
//                if (AppConfig.isWatherSystem()) {
//
//                    pmWaterAdapter.addData(info);
//
//                    try {
//                        MyApplication.mDB.save(info);
//							BracaseUtils.startUpdate(getBaseContext(), new Intent(BracaseUtils.ADD_ACTION));
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent(BracaseUtils.ADD_WATER_ACTION);
//                    intent.putExtra("portId", info.PortId);
//                    intent.putExtra("portName", info.PortName);
//                    BracaseUtils.startUpdate(getApplicationContext(), intent);
//                }
//                else
                requestServer();
            }

            @Override
            public boolean isAdd(String portId) {
                return listIndex.contains(portId);
            }
        });

        if (AppConfig.isWatherSystem()) {
            pmWaterAdapter = new PmWaterAdapter();
            pmWaterAdapter.setPmCallback(new PmWaterAdapter.PmWaterCallback() {
                @Override
                public void otherPosition(final PortInfo info) {
                    Dialog alertDialog = new AlertDialog.Builder(PositionManagerActivity.this).setTitle("是否确认删除'" + info.PortName + "'").setPositiveButton("确认", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("portId", "=", info.PortId));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            listIndex.remove(info.PortId);
                            pmWaterAdapter.remove(info);
                            Intent intent = new Intent(BracaseUtils.REMOVE_WATER_ACTION);
                            intent.putExtra("portId", info.PortId);
                            intent.putExtra("portName", info.PortName);
                            BracaseUtils.startUpdate(getApplicationContext(), intent);
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();
                    alertDialog.show();
                }
                @Override
                public void addPosition() {
                    addDialog.updateData();
                }
            });
        } else {
            pmAdapter = new PmAdapter();
            pmAdapter.setPmCallback(new PmCallback() {
                @Override
                public void otherPosition(final PortHourAQIInfo info) {
                    Dialog alertDialog = new AlertDialog.Builder(PositionManagerActivity.this).setTitle("是否确认删除'" + info.PortName + "'").setPositiveButton("确认", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                MyApplication.mDB.delete(PositionDBModel.class, WhereBuilder.b("portId", "=", info.PortId));
                            } catch (DbException e) {
                                e.printStackTrace();
                            }
                            listIndex.remove(info.PortId);
                            pmAdapter.remove(info);
                            Intent intent = new Intent(BracaseUtils.REMOVE_ACTION);
                            intent.putExtra("portId", info.PortId);
                            intent.putExtra("portName", info.PortName);
                            BracaseUtils.startUpdate(getApplicationContext(), intent);
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();
                    alertDialog.show();
                }
                @Override
                public void addPosition() {
                    addDialog.updateData();
                }
            });
        }
        setContentView(R.layout.activity_position_manager);
    }

    /***
     * 更新站点的数据
     *
     * @param portId
     * @param model
     */
    private void updatePmWaterData(final String portId, final PositionDBModel model) {
        portRequest.clear();

        portRequest.put("sysType", "water");
        portRequest.put("action", "water");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean IsLogin = PreferenceUtils.getBoolean(this, "IS_LOGIN");
        if (IsLogin) {

            String LoginId = PreferenceUtils.getValue(preferences, "LoginId");
            portRequest.put("LoginId", LoginId);
        } else
            portRequest.put("LoginId", "taicangapp");
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPortInfoBySysType, portRequest, new HttpListener() {
            @Override
            public void requestSuccess(HttpResponse resData) {
                JSONObject jsonObject = resData.getJson();
                JSONArray array = jsonObject.optJSONArray("PortInfo");
                if (array != null && array.length() > 0) {
                    PortInfo portInfo;
                    for (int i = 0; i < array.length(); i++) {
                        portInfo = new PortInfo();
                        portInfo.parse(array.optJSONObject(i));
//                        portInfo.isWaterPort = true;
//					if (isRequestLoadWater) {
                        PortCacheUtils.addWaterPortInfo(portInfo);
                        pmWaterAdapter.addData(portInfo);
//					}
//					else {
//						PortCacheUtils.addAirPortInfo(portInfo);
//					}
                    }
                }
                try {
                    //内存泄漏
//				if(!mDB.getDatabase().isOpen())
//
//                     mDB.getDatabase().openOrCreateDatabase(mDB.getDatabase().getPath(),null );


                    MyApplication.mDB.delete(PortInfo.class, WhereBuilder.b("isWaterPort", "=", "1"));
                    MyApplication.mDB.saveAll(PortCacheUtils.getWaterPortList());
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void requestFailed(HttpResponse resData) {

            }
        }, "正在加载站点信息...");
    }


    /***
     * 更新站点的数据
     *
     * @param portId
     * @param model
     */
    private void updatePmData(final String portId, final PositionDBModel model) {
        new HttpTask<PortHourAQIListInfo>() {
            @Override
            protected void doPreExecute() throws Exception {
            }

            @Override
            protected PortHourAQIListInfo doBack(Object[]... params) throws Exception {
                HttpResult result = EPHttpRequestUtils.GetLatestHourAQI(portId);
                if (result.resultCode == HttpState.HTTP_OK)
                    return JSON.parse(result.jsonData, PortHourAQIListInfo.class);
                return null;
            }

            @Override
            protected void doProgress(Integer... values) throws Exception {
            }

            @Override
            protected void doPost(PortHourAQIListInfo result) throws Exception {
                if (result.PortHourAQI.size() > 0) {
                    PortHourAQIInfo pmInfo = result.PortHourAQI.get(0);
                    pmInfo.DateTime = DateUtil.formatDate(DateUtil.getDate(pmInfo.DateTime, ""), "MM/dd HH:mm");
                    try {
                        PositionDBModel entity = MyApplication.mDB.findFirst(Selector.from(PositionDBModel.class).where("portId", "=", pmInfo.PortId));
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
                            MyApplication.mDB.update(entity);
                            pmAdapter.addData(pmInfo);
                        }
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                } else {
                    PortHourAQIInfo info = new PortHourAQIInfo();
                    info.PortId = model.getPortId();
                    info.AQI = model.getAQI();
                    info.Class = model.getpClass();
                    info.DateTime = model.getDateTime();
                    info.PortName = model.getPortName();
                    pmAdapter.addData(info);
                }
            }
        }.execute();
    }


    @Override
    protected void initView() {
        gvPm = (GridView) findViewById(R.id.gvPm);
        gvPm.setSelector(new ColorDrawable(Color.TRANSPARENT));


        if (AppConfig.isWatherSystem()) {
            gvPm.setAdapter(pmWaterAdapter);
            try {
                List<PortInfo> listDB = MyApplication.mDB.findAll(Selector.from(PortInfo.class).where("isWaterPort", "=", "1").orderBy("orderNumber", true));
                if (listDB != null && listDB.size() > 0) {
                    for (PortInfo model : listDB) {
                        listIndex.add(model.getPortId());
                        pmWaterAdapter.addData(model);
                    }
                }


            } catch (DbException e) {
                e.printStackTrace();
            }
        } else {
            gvPm.setAdapter(pmAdapter);
            try {
                List<PositionDBModel> listDB = MyApplication.mDB.findAll(Selector.from(PositionDBModel.class));
                if (listDB != null && listDB.size() > 0) {
                    for (PositionDBModel model : listDB) {
                        listIndex.add(model.getPortId());
                        updatePmData(model.getPortId(), model);
                    }
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void requestServer() {
        super.requestServer();
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        if (AppConfig.isWatherSystem()) {
            requestParams.put("portId", addInfo.PortId);
            HttpClient.getJsonWithGetUrl(AppConfig.RequestWaterActionName.GetLatestHourWQ, requestParams, this, "正在添加...");
        } else {
            requestParams.put("portId", addInfo.PortId);
            HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetLatestHourAQI, requestParams, this, "正在添加...");
        }


    }


    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if (AppConfig.RequestWaterActionName.GetLatestHourWQ.equals(resData.getUri())) {
            pmWaterAdapter.addData(addInfo);
            pmWaterAdapter.notifyDataSetChanged();
            try {
                MyApplication.mDB.save(addInfo);
//							BracaseUtils.startUpdate(getBaseContext(), new Intent(BracaseUtils.ADD_ACTION));
            } catch (DbException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(BracaseUtils.ADD_WATER_ACTION);
            intent.putExtra("portId", addInfo.PortId);
            intent.putExtra("portName", addInfo.PortName);
            if (resData != null && resData.getJson() != null) {
                intent.putExtra("Json", resData.getJson().toString());
            }


            BracaseUtils.startUpdate(getApplicationContext(), intent);
        }
        if (AppConfig.RequestAirActionName.GetLatestHourAQI.equals(resData.getUri())) {
            PortHourAQIListInfo result = JSON.parse(resData.getJson().toString(), PortHourAQIListInfo.class);
            if (result != null) {
                if (result.PortHourAQI.size() > 0) {
                    PortHourAQIInfo pmInfo = result.PortHourAQI.get(0);
                    pmInfo.DateTime = DateUtil.formatDate(DateUtil.getDate(pmInfo.DateTime, ""), "MM/dd HH:mm");
                    pmAdapter.addData(pmInfo);
                    PositionDBModel entity = new PositionDBModel(addInfo.PortName, addInfo.PortId, addInfo.X, addInfo.Y);
                    entity.setCO_IAQI(pmInfo.CO_IAQI);
                    entity.setNO2_IAQI(pmInfo.NO2_IAQI);
                    entity.setO3_IAQI(pmInfo.O3_IAQI);
                    entity.setPM25_IAQI(pmInfo.PM25_IAQI);
                    entity.setPM10_IAQI(pmInfo.PM10_IAQI);
                    entity.setSO2_IAQI(pmInfo.SO2_IAQI);
                    entity.setAQI(pmInfo.AQI);
                    entity.setpClass(pmInfo.Class);
                    entity.setDateTime(pmInfo.DateTime);
                    try {
                        pmAdapter.notifyDataSetChanged();
                        MyApplication.mDB.save(entity);
                        Intent intent = new Intent(BracaseUtils.ADD_ACTION);
                        intent.putExtra("portId", addInfo.PortId);
                        intent.putExtra("portName", addInfo.PortName);
                        BracaseUtils.startUpdate(getApplicationContext(), intent);
//					BracaseUtils.startUpdate(getBaseContext(), new Intent(BracaseUtils.ADD_ACTION));
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        PortHourAQIInfo pmInfo = new PortHourAQIInfo();
        pmInfo.PortId = addInfo.PortId;
        pmInfo.AQI = "0";
        pmInfo.Class = PullectUtils.getPullectMsgByValue(Integer.parseInt(pmInfo.AQI));
        pmInfo.DateTime = DateUtil.formatDate(new Date(), "MM/dd HH:mm");
        pmInfo.PortName = addInfo.PortName;
        pmAdapter.addData(pmInfo);
        PositionDBModel entity = new PositionDBModel(addInfo.PortName, addInfo.PortId, addInfo.X, addInfo.Y);
        try {
            MyApplication.mDB.save(entity);
            Intent intent = new Intent(BracaseUtils.ADD_ACTION);
            intent.putExtra("portId", addInfo.PortId);
            intent.putExtra("portName", addInfo.PortName);
            BracaseUtils.startUpdate(getApplicationContext(), intent);
//            BracaseUtils.startUpdate(getBaseContext(), new Intent(BracaseUtils.ADD_ACTION));
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
