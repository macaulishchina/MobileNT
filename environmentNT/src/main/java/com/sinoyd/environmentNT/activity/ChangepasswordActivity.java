package com.sinoyd.environmentNT.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.Entity.ChangePassword;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.PreferenceUtils;

public class ChangepasswordActivity extends Activity {

    private ImageView iv_back;
    private TextView tv_back;
    private EditText et_degnluzhanghao;
    private EditText et_yuanmima;
    private EditText et_xinmima;
    private EditText et_querenmima;
    private Button bt_querenxiugai;

    private String loginId;
    private String UserGuid;
    private String pwd;
    private String pwdNew;
    private String pwdNew2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_back = (TextView) findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        et_degnluzhanghao = (EditText) findViewById(R.id.degnluzhanghao);
        et_yuanmima = (EditText) findViewById(R.id.yuanmima);
        et_xinmima = (EditText) findViewById(R.id.xinmima);
        et_querenmima = (EditText) findViewById(R.id.querenmima);
        bt_querenxiugai = (Button) findViewById(R.id.xiugaimima);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        et_degnluzhanghao.setText(PreferenceUtils.getValue(preferences, "LoginId"));

        UserGuid = PreferenceUtils.getValue(preferences, "UserGuid");

        bt_querenxiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginId = et_degnluzhanghao.getText().toString();
                pwd = et_yuanmima.getText().toString();
                pwdNew = et_xinmima.getText().toString();
                pwdNew2 = et_querenmima.getText().toString();
                if ("".equals(pwd)) {
                    Toast.makeText(ChangepasswordActivity.this, "原密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!pwdNew.equals(pwdNew2)) {
                    Toast.makeText(ChangepasswordActivity.this, "两次输入密码有误", Toast.LENGTH_SHORT).show();
                    return;
                }

                utilDoGet(loginId, pwd, pwdNew, UserGuid);

            }
        });


    }

    /**
     * 用Get方法向服务器提交数据
     *
     * @param loginId
     * @param pwd
     * @param pwdNew
     */
    private void utilDoGet(String loginId, String pwd, String pwdNew, String UserGuid) {
        HttpUtils utils_Get = new HttpUtils();
        utils_Get.send(HttpRequest.HttpMethod.GET, "http://218.91.209.251:1117/NTWebServiceForMobile/AQI.asmx/GetModifyPassword?loginId=" + loginId + "&pwd=" + pwd + "&pwdNew=" + pwdNew + "&useruid=" + UserGuid, new RequestCallBack<String>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Gson gson = new Gson();

                ChangePassword result = gson.fromJson(responseInfo.result, ChangePassword.class);


                if ("1".equals(result.getUpdate())) {
                    Toast.makeText(ChangepasswordActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ChangepasswordActivity.this,MoreActivity.class));
                    finish();
                } else {
                    Toast.makeText(ChangepasswordActivity.this, "原密码错误", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ChangepasswordActivity.this, "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
