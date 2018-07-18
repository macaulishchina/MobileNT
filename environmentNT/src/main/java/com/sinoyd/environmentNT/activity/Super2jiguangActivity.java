package com.sinoyd.environmentNT.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.Entity.GetImageUrl;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.util.DateInfoo;
import com.sinoyd.environmentNT.view.RefreshButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class Super2jiguangActivity extends AppCompatActivity implements HttpListener {
    /**
     * 请求接口传入参数
     **/
    private HashMap<String, String> requestParams;

    private RefreshButton mRefreshButton;
    LoadDialog dialog = null;

    private String instrumentUid;
    private String startTime;
    private String times;
    private String level;
    private String name;
    private TextView tv_title;
    /**
     * 监测因子名称头文件
     **/
    private RelativeLayout mHead;

    private LinearLayout llmore;

    @ViewInject(R.id.super2_jiguang_Time)
    private TextView super2_jiguang_Time;


    @ViewInject(R.id.super2_jiguang_arrowLeft)
    private ImageView super2_jiguang_arrowLeft;

    @ViewInject(R.id.super2_jiguang_arrowRight)
    private ImageView super2_jiguang_arrowRight;

    @ViewInject(R.id.super2_jiguang_Times)
    private TextView super2_jiguang_Times;

    @ViewInject(R.id.wb_jiguang)
    private WebView wb_jiguang;

    @ViewInject(R.id.super2jijiang_tv)
    private TextView super2jijiang_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super2jiguang);
        ViewUtils.inject(this);
        name = getIntent().getStringExtra("name");
        instrumentUid = getIntent().getStringExtra("instrumentUid");
        level = getIntent().getStringExtra("level");
        startTime = DateInfoo.getToday();
        times = DateInfoo.getTodaytime();
        super2_jiguang_Time.setText(startTime);
        Log.i("hyd",times);
        super2_jiguang_Times.setText(times.substring(0, 2) + ":00");
        initview();
        super2_jiguang_arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayBefore(super2_jiguang_Time.getText().toString());
                super2_jiguang_Time.setText(startTime);
                //发请求
                request(level);
            }


        });

        super2_jiguang_arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = DateInfoo.getSpecifiedDayAfter(super2_jiguang_Time.getText().toString());
                super2_jiguang_Time.setText(startTime);
                //发请求
                request(level);
            }
        });

        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(level);
            }
        });
        final Calendar c = Calendar.getInstance();

        super2_jiguang_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Super2jiguangActivity.this,
                        // 绑定监听器
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                startTime = DateInfoo.gettimeforstring4(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                super2_jiguang_Time.setText(startTime);
                                //发请求
                                request(level);
                            }
                        }
                        // 设置初始日期
                        , c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        final Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        super2_jiguang_Times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(Super2jiguangActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        times = DateInfoo.gettimeforstring5(hourOfDay + ":" + minute);
                        super2_jiguang_Times.setText(times);
                        //发请求
                        request(level);
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

            }
        });


    }

    private void initview() {
        llmore = (LinearLayout) findViewById(R.id.Super2_jiguang_top_bar_layout).findViewById(R.id.top_more);
        tv_title = (TextView) findViewById(R.id.Super2_jiguang_top_bar_layout).findViewById(R.id.title);
        tv_title.setText(name);
        super2jijiang_tv.setText(name);
        dialog = new LoadDialog(this,R.style.frm_dialog);
        //支持javascript
        wb_jiguang.getSettings().setJavaScriptEnabled(true);
// 设置可以支持缩放
        wb_jiguang.getSettings().setSupportZoom(true);
// 设置出现缩放工具
        wb_jiguang.getSettings().setBuiltInZoomControls(true);
//扩大比例的缩放
        wb_jiguang.getSettings().setUseWideViewPort(true);
//自适应屏幕
        wb_jiguang.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb_jiguang.getSettings().setLoadWithOverviewMode(true);
        mHead = (RelativeLayout) findViewById(R.id.Super2_jiguang_top_bar_layout);
        mRefreshButton = (RefreshButton) findViewById(R.id.Super2_jiguang_top_bar_layout).findViewById(R.id.top_refresh_btn);
        mRefreshButton.setVisibility(View.GONE);
        llmore = (LinearLayout) findViewById(R.id.Super2_jiguang_top_bar_layout).findViewById(R.id.top_more);
        llmore.setVisibility(View.GONE);
        request(level);
    }

    private void request(String level) {
        dialog.show();
        mRefreshButton.start();
        //发送请求
        if (requestParams == null) {
            requestParams = new HashMap<String, String>();
        }
        requestParams.put("instrumentUid", instrumentUid);
        requestParams.put("startTime", startTime + " " + times.substring(0, 2) + ":00");
        HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetImageUrl, requestParams, Super2jiguangActivity.this, null);
    }

    List<GetImageUrl.JGLDImageBean> JGLDImage = new ArrayList<>();
    String customHtml = "";

    @Override
    public void requestSuccess(HttpResponse resData) {
        if(dialog.isShowing()) {
            dialog.hide();
        }
        mRefreshButton.stop();
        Gson gson = new Gson();
        GetImageUrl re = gson.fromJson(resData.getJson().toString(), GetImageUrl.class);
        JGLDImage = re.getJGLDImage();

        if (JGLDImage == null || JGLDImage.size() == 0) {
            Toast.makeText(this, "无数据加载", Toast.LENGTH_SHORT).show();
            wb_jiguang.clearView();
        } else {
            customHtml = "";
            for (GetImageUrl.JGLDImageBean jl : JGLDImage) {
                customHtml += "<html><img width=100% src=" + jl.getImageUrl() + "><html>";
            }
            wb_jiguang.loadData(customHtml, "text/html", "UTF-8");
        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        if(dialog.isShowing()) {
            dialog.hide();
        }
        mRefreshButton.stop();
        Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
    }
}
