package com.sinoyd.environmentNT.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.Entity.GetImageUrl2;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.Gvadapter;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.util.DateInfoo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Super2cityActivity extends AppCompatActivity implements HttpListener {

    private String instrumentUid;
    private String startTime;
    private String times;
    private String level;
    private String name;

    LoadDialog dialog = null;

    @ViewInject(R.id.super2_city_tv)
    private TextView super2_city_tv;

    @ViewInject(R.id.super2_city_arrowLeft)
    private ImageView super2_city_arrowLeft;

    @ViewInject(R.id.super2_city_Time)
    private TextView super2_city_Time;

    @ViewInject(R.id.super2_city_Times)
    private TextView super2_city_Times;

    @ViewInject(R.id.super2_city_arrowRight)
    private ImageView super2_city_arrowRight;

    @ViewInject(R.id.company_gv)
    private ListView company_gv;

    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super2city);
        ViewUtils.inject(this);
        dialog = new LoadDialog(this,R.style.frm_dialog);
        name = getIntent().getStringExtra("name");
        super2_city_tv.setText(name);
        instrumentUid = getIntent().getStringExtra("instrumentUid");
        level = getIntent().getStringExtra("level");
        startTime = DateInfoo.getToday();
        super2_city_Time.setText(startTime);
        super2_city_arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayBefore(super2_city_Time.getText().toString());
                super2_city_Time.setText(startTime);
                request();
            }
        });
        final Calendar c = Calendar.getInstance();
        super2_city_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Super2cityActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startTime = DateInfoo.gettimeforstring4(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                super2_city_Time.setText(startTime);
                                //发请求
                                request();
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        super2_city_arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayAfter(super2_city_Time.getText().toString());
                super2_city_Time.setText(startTime);
                request();
            }
        });

        request();
    }

    private void request() {
//        mRefreshButton.start();
        //发送请求
        dialog.show();
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        requestParams.put("instrumentUid", instrumentUid);
        requestParams.put("startTime", startTime);
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetImageUrl, requestParams, Super2cityActivity.this, null);
    }

    List<GetImageUrl2.CityImageBean> CityImagemage = new ArrayList<>();
    Gvadapter adapter;

    @Override
    public void requestSuccess(HttpResponse resData) {
        if(dialog.isShowing()) {
            dialog.hide();
        }
        CityImagemage = new ArrayList<>();
        Gson gson = new Gson();
        GetImageUrl2 re = gson.fromJson(resData.getJson().toString(), GetImageUrl2.class);
        CityImagemage = re.getCityImage();
        adapter = new Gvadapter(Super2cityActivity.this, CityImagemage);
        if (CityImagemage == null || CityImagemage.size() == 0) {
            Toast.makeText(this, "无数据加载", Toast.LENGTH_SHORT).show();
            company_gv.setAdapter(adapter);
        } else {
            super2_city_Times.setText(CityImagemage.get(0).getImageDate().substring(11, 16));
            company_gv.setAdapter(adapter);
        }


    }

    @Override
    public void requestFailed(HttpResponse resData) {
        if(dialog.isShowing()) {
            dialog.hide();
        }
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }


}
