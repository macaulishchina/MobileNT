package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.Entity.GetAllUsingInstruments;
import com.sinoyd.environmentNT.Entity.YZCommonSelectModel;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.LvsuperAdapter;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.util.CommonSelector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SuperActivity extends RefreshBaseActivity {
    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;


    private ListView lv_super;


    List<YZCommonSelectModel> list = Arrays.asList(new YZCommonSelectModel(">0.25um", "0"), new YZCommonSelectModel("0.25um-0.4um", "1"), new YZCommonSelectModel("0.4um-2.5um", "2"), new YZCommonSelectModel(">2.5um", "3"));
    List<YZCommonSelectModel> list2 = Arrays.asList(
            new YZCommonSelectModel("一级类(ppb)", "1"),
            new YZCommonSelectModel("二级类(ppb)", "2"),
            new YZCommonSelectModel("分因子(ppb)", "3"),
            new YZCommonSelectModel("一级类(ug/m3)", "4"),
            new YZCommonSelectModel("二级类(ug/m3)", "5"),
            new YZCommonSelectModel("分因子(ug/m3)", "6")
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super);
    }


    @Override
    protected void initView() {
        super.initView();
        lv_super = (ListView) findViewById(R.id.lv_super);
        findViewById(R.id.title).setOnClickListener(null);
        findViewById(R.id.change_station_icon).setOnClickListener(null);
//        dialog.setPortTypeSelectListener(this);
        mTitleTextView.setText("超站因子");

        lv_super.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Intent intent = new Intent();
                switch (InstrumentData.get(position).getRowGuid()) {
                    case "56dd6e9b-4c8f-4e67-a70f-b6a277cb44d7"://黑碳分析仪
                    case "6e4aa38a-f68b-490b-9cd7-3b92c7805c2d"://EC/OC有机碳元素碳
                    case "1589850e-0df1-4d9d-b508-4a77def158ba"://离子色谱仪
                    case "a6b3d80c-8281-4bc6-af47-f0febf568a5c"://浊度仪
                    case "da4f968f-cc6e-4fec-8219-6167d100499d"://"太阳辐射仪"
                    case "6cd5c158-8a79-4540-a8b1-2a062759c9a0"://超级站常规参数
                        intent.setClass(getActivity(), Super2infoActivity.class);
                        intent.putExtra("name", InstrumentData.get(position).getInstrumentName());
                        intent.putExtra("level", "1");
                        intent.putExtra("instrumentUid", InstrumentData.get(position).getRowGuid());
                        intent.putExtra("key", "key");
                        startActivity(intent);
                        break;
                    case "3745f768-a789-4d58-9578-9e41fde5e5f0"://Vocs
                        CommonSelector cmo = new CommonSelector(getActivity(), findViewById(R.id.top_bar_layout), list2, new CommonSelector.OnSelectClickListener() {
                            @Override
                            public void onCommonItemSelect(int positionn) {
                                intent.setClass(getActivity(), Super2infoActivity.class);
                                intent.putExtra("name", InstrumentData.get(position).getInstrumentName());
                                intent.putExtra("level", "2");
                                intent.putExtra("instrumentUid", list2.get(positionn).getVlaue());
                                intent.putExtra("key", list2.get(positionn).getKey());

                                startActivity(intent);
                            }
                        });
                        cmo.showPop();
                        break;


                    case "9ef57f3c-8cce-4fe3-980f-303bbcfde260"://粒径谱仪
                        CommonSelector cmoo = new CommonSelector(getActivity(), findViewById(R.id.top_bar_layout), list, new CommonSelector.OnSelectClickListener() {
                            @Override
                            public void onCommonItemSelect(int positionn) {
                                intent.setClass(getActivity(), Super2infoActivity.class);
                                intent.putExtra("name", InstrumentData.get(position).getInstrumentName());
                                intent.putExtra("level", "3");
                                intent.putExtra("instrumentUid", list.get(positionn).getVlaue());
                                intent.putExtra("key", list.get(positionn).getKey());
                                startActivity(intent);
                            }
                        });
                        cmoo.showPop();
                        break;

                    case "4dbe4c32-270b-4696-ae56-d66178a3ca78"://激光雷达
                        intent.setClass(getActivity(), Super2jiguangActivity.class);
                        intent.putExtra("name", InstrumentData.get(position).getInstrumentName());
                        intent.putExtra("level", "1");
                        intent.putExtra("instrumentUid", InstrumentData.get(position).getRowGuid());
                        intent.putExtra("key", "key");
                        startActivity(intent);
                        //跳界面

                        break;
                    case "93c90000-e723-4a47-8a97-bffd8fcf36ca"://城市摄影/能见度
                        //跳界面
                        intent.setClass(getActivity(), Super2cityActivity.class);
                        intent.putExtra("name", InstrumentData.get(position).getInstrumentName());
                        intent.putExtra("level", "2");
                        intent.putExtra("instrumentUid", InstrumentData.get(position).getRowGuid());
                        intent.putExtra("key", "key");
                        startActivity(intent);
                        break;
                }

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        loadView();
    }

    private void loadView() {
        requestServer();
        System.gc();
    }


    protected void requestServer() {
        //发送请求
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        //dialog.show();
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetAllUsingInstruments, requestParams, this, null);
    }


    private List<GetAllUsingInstruments.InstrumentDataBean> InstrumentData;

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if(dialog.isShowing()) {
            dialog.hide();
        }
        InstrumentData = new ArrayList<>();
        Gson gs = new Gson();
        GetAllUsingInstruments getAllUsingInstruments = gs.fromJson(resData.getJson().toString(), GetAllUsingInstruments.class);
        InstrumentData = getAllUsingInstruments.getInstrumentData();
        LvsuperAdapter adapter = new LvsuperAdapter(getActivity(), InstrumentData);
        lv_super.setAdapter(adapter);
        Log.i("scj", "scj");
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        super.requestFailed(resData);
        if(dialog.isShowing()) {
            dialog.hide();
        }
    }

    @Override
    public void selectPortCallBack() {
        onClick(findViewById(R.id.top_refresh));
    }

}
