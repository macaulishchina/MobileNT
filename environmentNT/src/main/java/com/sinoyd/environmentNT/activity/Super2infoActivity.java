package com.sinoyd.environmentNT.activity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.Entity.GetInstrumentsFactorsData;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.MonitorDataAdapter;
import com.sinoyd.environmentNT.common.BizCommon;
import com.sinoyd.environmentNT.data.FactorProperty;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.util.DateInfoo;
import com.sinoyd.environmentNT.util.StringUtils;
import com.sinoyd.environmentNT.view.RefreshButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.sinoyd.environmentNT.MyApplication.showTextToast;

public class Super2infoActivity extends AppCompatActivity implements HttpListener {
    private String name;
    private String level;
    private TextView tv_title;
    private String key;

    LoadDialog dialog = null;

    private String instrumentUid;
    private String startTime;

    @ViewInject(R.id.rl_one)
    private LinearLayout rl_one;

    @ViewInject(R.id.super2_Time)
    private TextView super2_Time;

    @ViewInject(R.id.super2_arrowLeft)
    private ImageView super2_arrowLeft;

    @ViewInject(R.id.super2_arrowRight)
    private ImageView super2_arrowRight;

    @ViewInject(R.id.super2_densityValueList)
    private ListView super2_densityValueList;

    @ViewInject(R.id.super2info_tv)
    private TextView super2info_tv;

    @ViewInject(R.id.avi)
    private com.wang.avi.AVLoadingIndicatorView avi;
    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;

    private RefreshButton mRefreshButton;
    private LinearLayout llmore;

    /***因子名称最大列数***/
    int MaxColumnCount = 0;
    private LinearLayout viewChild;
    boolean isSelectDialog = false;

    /**
     * 监测因子名称头文件
     **/
    private RelativeLayout mHead;

    /**
     * 监测数据适配器
     **/
    private MonitorDataAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super2info);
        ViewUtils.inject(this);
        name = getIntent().getStringExtra("name");
        level = getIntent().getStringExtra("level");
        key = getIntent().getStringExtra("key");
        instrumentUid = getIntent().getStringExtra("instrumentUid");
        startTime = DateInfoo.getToday();
        super2_Time.setText(startTime);
        initview();
        super2_arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayBefore(super2_Time.getText().toString());
                super2_Time.setText(startTime);
                //发请求
                request(level);
            }
        });

        super2_arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayAfter(super2_Time.getText().toString());
                super2_Time.setText(startTime);
                //发请求
                request(level);
            }
        });
        final Calendar c = Calendar.getInstance();
        super2_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Super2infoActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startTime = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                super2_Time.setText(startTime);
                                //发请求
                                request(level);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(level);
            }
        });

        llmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    private void initview() {
        mHead = (RelativeLayout) findViewById(R.id.super2_table_title);
        mAdapter = new MonitorDataAdapter(this, mItemList, mHead);
        super2_densityValueList.setAdapter(mAdapter);
        super2_densityValueList.setOnTouchListener(new ListViewAndHeadViewTouchLinstener());
        dialog = new LoadDialog(this,R.style.frm_dialog);
        viewChild = (LinearLayout) findViewById(R.id.viewChild);
        tv_title = (TextView) findViewById(R.id.Super2_top_bar_layout).findViewById(R.id.title);
        tv_title.setGravity(Gravity.CENTER);

        super2info_tv.setText(name);

        if (key.equals("key")) {
            super2info_tv.setText(name);
        } else {
            super2info_tv.setText(name + "(" + key + ")");
        }
        mRefreshButton = (RefreshButton) findViewById(R.id.Super2_top_bar_layout).findViewById(R.id.top_refresh_btn);
        mRefreshButton.setVisibility(View.GONE);
        llmore = (LinearLayout) findViewById(R.id.Super2_top_bar_layout).findViewById(R.id.top_more);
        llmore.setVisibility(View.GONE);
        request(level);

    }

    private List<FactorProperty> mItemList = new ArrayList<>();

    //发送请求
    private void request(String level) {

        avi.show();
        dialog.show();
        mRefreshButton.start();
        switch (level) {
            case "1":
                //发送请求
                if (requestParams == null) {
                    requestParams = new HashMap<String, String>();
                }
                requestParams.put("instrumentUid", instrumentUid);
                requestParams.put("startTime", startTime);
                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetInstrumentsFactorsData, requestParams, Super2infoActivity.this, null);
                break;
            case "3":
                //发送请求
                if (requestParams == null) {
                    requestParams = new HashMap<String, String>();
                }
                requestParams.put("kind", instrumentUid);
                requestParams.put("startTime", startTime);
                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetParticlesizeOfSpectrometerData, requestParams, Super2infoActivity.this, null);
                break;
            case "2":
                //发送请求
                if (requestParams == null) {
                    requestParams = new HashMap<String, String>();
                }
                requestParams.put("type", instrumentUid);
                requestParams.put("startTime", startTime);
                HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetVocData, requestParams, Super2infoActivity.this, null);
                break;
        }


    }


    @Override
    public void requestSuccess(HttpResponse resData) {
        if (dialog.isShowing())
            dialog.hide();
        if (dialog.isShowing())
            dialog.hide();
        mRefreshButton.stop();
        mItemList.clear();
        JSONObject jsonObj = resData.getJson();
        JSONArray jsonArray = jsonObj.optJSONArray("HistoryData");
        if (jsonArray != null && jsonArray.length() > 0) {
            Gson gson = new Gson();
            GetInstrumentsFactorsData re = gson.fromJson(jsonObj.toString(), GetInstrumentsFactorsData.class);
            List<GetInstrumentsFactorsData.HistoryDataBean> all = re.getHistoryData();
            //确定最大的 factorNames是在什么位置
            int max = 0;
            for (int i = 0; i < all.size(); i++) {
                for (int j = 0; j < all.size() - 1; j++) {
                    max = all.get(i).getValue().size() > all.get(j).getValue().size() ? i : j;
                }
            }
            //确定固定factorNames
            List<String> gudingfactorNames = new ArrayList<>();
            List<String> danwei = new ArrayList<>();
            for (int i = 0; i < all.get(max).getValue().size(); i++) {
                gudingfactorNames.add(all.get(max).getValue().get(i).getFactorName());
                danwei.add(all.get(max).getValue().get(i).getMeasureUnit());
            }

            Log.i("", "");

            for (int i = 0; i < all.size(); i++) {
                FactorProperty prop = new FactorProperty();
                prop.date = all.get(i).getTstamp().substring(0, all.get(i).getTstamp().length() - 3);
//                if (all.get(i).getDateTime().equals("07-05 07:00")) {
//                    Log.i("", "");
//                }
                prop.factorNames = new ArrayList<String>();
                prop.factorNames = gudingfactorNames;
                prop.danwei = danwei;
                prop.factorValues = new ArrayList<String>();
                prop.isExceeded = new ArrayList<Boolean>();

                out:
                for (int a = 0; a < gudingfactorNames.size(); a++) {

                    for (int b = 0; b < all.get(i).getValue().size(); b++) {

                        if (gudingfactorNames.get(a).equals(all.get(i).getValue().get(b).getFactorName())) {

                            if (all.get(i).getValue().get(b).getFlag().equals("")) {
                                prop.factorValues.add(all.get(i).getValue().get(b).getValue());
                                prop.isExceeded.add(false);
                            } else {
                                prop.factorValues.add(all.get(i).getValue().get(b).getValue() + "(" + all.get(i).getValue().get(b).getFlag() + ")");
                                prop.isExceeded.add(true);
                            }

//                            prop.isExceeded.add(all.get(i).getValue().get(b).getIsExceeded());

                            continue out;
                        }


                    }

                    prop.factorValues.add("--");
//                    prop.isExceeded.add(false);

                }

                mItemList.add(prop);
            }


/***2017-7-7  scj 删除 逻辑不正确*/
//            for (int i = 0; i < jsonArray.length(); i++) {
//                JSONObject jsonObj2 = jsonArray.optJSONObject(i);
//                String dateTime = jsonObj2.optString("DateTime");
//                FactorProperty prop = new FactorProperty();
//                prop.date = dateTime;
//                prop.factorNames = new ArrayList<String>();
//                prop.factorValues = new ArrayList<String>();
//                prop.isExceeded = new ArrayList<Boolean>();
//                JSONArray array = jsonObj2.optJSONArray("Value");
//                if (array != null) {
//                    for (int j = 0; j < array.length(); j++) {
//                        prop.factorValues.add(array.optJSONObject(j).optString("value"));
//                        prop.factorNames.add(array.optJSONObject(j).optString("factor"));
//                        prop.isExceeded.add(array.optJSONObject(j).optBoolean("isExceeded"));
//                    }
//                }
//                mItemList.add(prop);
//            }
/***2017-7-7  scj 删除 逻辑不正确*/


//		if (mItemList.size() > 0)
//			addHead(mItemList.get(0));

            MaxColumnCount = BizCommon.GetMaxFactor(mItemList);
            if (mItemList.size() > 0)
                addHead(mItemList.get(MaxColumnCount));
//		mAdapter.notifyDataSetChanged();
//		mAdapter = new MonitorDataAdapter(getActivity(), mItemList, mHead);
//		mDensityValueList.setAdapter(mAdapter);
            if (dialog.isShowing())
                dialog.hide();

            if (mAdapter == null) {
                mAdapter = new MonitorDataAdapter(this, mItemList, mHead);
                /***设置最大因子列数****/
                mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames.size());
                mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
                if (isSelectDialog) {
                    mAdapter.SetIsNewData(true);
                    isSelectDialog = false;
                }
                super2_densityValueList.setAdapter(mAdapter);
            } else {
                /***设置最大因子列数****/
                mAdapter.SetMaxColumnCount(mItemList.get(MaxColumnCount).factorNames.size());
                mAdapter.SetMaxColumnRowIndex(MaxColumnCount);
                if (isSelectDialog) {
                    mAdapter.SetIsNewData(true);
                    isSelectDialog = false;
                }
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mItemList.clear();
            mRefreshButton.stop();
            mAdapter = new MonitorDataAdapter(this, mItemList, mHead);
            super2_densityValueList.setAdapter(mAdapter);
            showTextToast("暂无数据，请稍后刷新!");
        }
    }


    @Override
    public void requestFailed(HttpResponse resData) {
        if (dialog.isShowing())
            dialog.hide();
        avi.hide();
        mRefreshButton.stop();
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }

    /***
     * 添加一个head
     *
     * @param FactorProperty
     */
    private void addHead(FactorProperty FactorProperty) {
        viewChild.removeAllViews();
        for (int i = 0; i < FactorProperty.factorNames.size(); i++) {
            addTv(viewChild, FactorProperty.factorNames.get(i)+"\r\n"+FactorProperty.danwei.get(i));
        }
    }

    /***
     * 添加一个view
     *
     * @param parent
     * @param name
     */
    private void addTv(LinearLayout parent, String name) {
        TextView v = (TextView) View.inflate(parent.getContext(), R.layout.tv_item, null);
        v.setText(name);

        if (name.equals("PM10"))
            v.setText(StringUtils.getPM10());
        if (name.equals("PM2.5"))
            v.setText(StringUtils.getPM25());
        if (name.equals("03"))
            v.setText(StringUtils.getO3());
        if (name.equals("O3"))
            v.setText(StringUtils.getO3());
        if (name.equals("NO2"))
            v.setText(StringUtils.getNO2());
        if (name.equals("SO2"))
            v.setText(StringUtils.getSO2());
        if (name.equals("CO"))
            v.setText(StringUtils.getCO());
        if (name.equals("NOy"))
            v.setText(StringUtils.getNOy());
        if (name.equals("Natural1"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural2"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural3"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural4"))
            v.setText(StringUtils.getNatural(name));
        if (name.equals("Natural5"))
            v.setText(StringUtils.getNatural(name));

        if (name.equals("CO2_1"))
            v.setText(StringUtils.getCO2(name));

        if (name.equals("CO2_2"))
            v.setText(StringUtils.getCO2(name));

        if (name.equals("CO2_3"))
            v.setText(StringUtils.getCO2(name));


        v.setTextColor(Color.WHITE);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(220, LinearLayout.LayoutParams.MATCH_PARENT);
        params.height = findViewById(R.id.super2_table_title).getHeight() + 100;
        params.leftMargin = 10;
        params.rightMargin = 10;
        params.gravity = Gravity.CENTER;
        parent.addView(v, params);
    }

    class ListViewAndHeadViewTouchLinstener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            HorizontalScrollView headSrcrollView = (HorizontalScrollView) mHead.findViewById(R.id.horizontalScrollView1);
            headSrcrollView.onTouchEvent(arg1);
            return false;
        }
    }

}
