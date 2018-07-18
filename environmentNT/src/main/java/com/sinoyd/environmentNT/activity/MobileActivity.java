
package com.sinoyd.environmentNT.activity;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;


public class MobileActivity extends Activity implements OnClickListener{


	private EditText etPhoneNum;  //手机号码输入框
	private ImageView ivClear;  //清除已经输入内容的图标
	private Button btnNext; //下一步按钮

	private EditText etCaptchaNum; //验证码输入框
	private Button btnSubmit; //提交验证码
	private ImageView ivSmsClear;
	private TextView tvUnreceiveIdentify;

	private static final int RETRY_INTERVAL = 60; //设置一个倒计时时间
	private int time = RETRY_INTERVAL;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_mobile);
		initData();
		setupViews();
	}

	private void initData(){
		//初始化sdk主操作对象

	}

	/**
	 * 初始化界面控件
	 */
	private void setupViews(){
		btnNext = (Button) findViewById(R.id.btn_next);
		ivClear = (ImageView) findViewById(R.id.iv_clear);
		btnNext.setOnClickListener(this);
		ivClear.setOnClickListener(this);

		etCaptchaNum = (EditText) findViewById(R.id.et_sms_captcha);
		btnSubmit = (Button) findViewById(R.id.btn_submit);
		ivSmsClear = (ImageView) findViewById(R.id.iv_sms_clear);
		btnSubmit.setOnClickListener(this);
		ivSmsClear.setOnClickListener(this);
		tvUnreceiveIdentify = (TextView) findViewById(R.id.tv_unreceive_identify);

		etPhoneNum = (EditText) findViewById(R.id.et_write_phone);
		etPhoneNum.setText("");
		etPhoneNum.requestFocus();

		etPhoneNum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				refreshViews(arg0);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
										  int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	//根据edittext的内容来判断是否应当出现“清除所有”的按钮x
	private void refreshViews(CharSequence s){
		if(s.length() >0){
			btnNext.setEnabled(true);
			ivClear.setVisibility(View.VISIBLE);
		}else{
			btnNext.setEnabled(false);
			ivClear.setVisibility(View.GONE);
		}
	}

	//倒计时方法
	private void countDown(){
		new Thread(new  Runnable() {
			public void run() {
				while(time-- > 0){
					final String unReceive =MobileActivity.this.getResources().getString(R.string.smssdk_receive_msg, time);

					runOnUiThread(new Runnable() {
						public void run() {
							tvUnreceiveIdentify.setText(Html.fromHtml(unReceive));
							tvUnreceiveIdentify.setEnabled(false);
						}
					});

					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO: handle exception
					}

				}
				time = RETRY_INTERVAL;
			}
		}).start();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_next:
				//删除掉字符串中所有的空格
				String phone = etPhoneNum.getText().toString().trim().replace("\\s*", "");

				/**
				 * 请求短信验证码
				 *
				 * code	返回码:
				 服务器: 0 成功; 1 错误;
				 本地: -2 本地网络异常; -3 服务器网络异常;-4 解析错误;-5初始化异常
				 reason	返回信息 成功或错误原因.
				 result	返回结果,JSON格式.错误或者无返回值时为空.

				 */
//				mCaptcha.sendCaptcha(phone, new ResultCallBack() {
//
//					@Override
//					public void onResult(int code, String reason, String result) {
//						// TODO Auto-generated method stub
//						if(code == 0){
//							//调用成功
//						}
//					}
//				});

				countDown();
				break;
			case R.id.iv_clear:
				etPhoneNum.getText().clear();
				break;

			case R.id.btn_submit:
				String phoneNumber = etPhoneNum.getText().toString().trim().replace("\\s*", "");
				String code = etCaptchaNum.getText().toString().trim().replace("\\s*", "");

				/**
				 * 验证短信验证码
				 *
				 * code	返回码:
				 服务器: 0 成功; 1 错误;
				 本地: -2 本地网络异常; -3 服务器网络异常;-4 解析错误;-5初始化异常
				 reason	返回信息 成功或错误原因.
				 result	返回结果,JSON格式.错误或者无返回值时为空.

				 */
//				mCaptcha.commitCaptcha(phoneNumber, code, new ResultCallBack() {
//
//					@Override
//					public void onResult(int code, String reason, String result) {
//						// TODO Auto-generated method stub
//						if(code == 0){
//							//短信验证成功
//							Toast.makeText(MainActivity.this, "验证成功！！！！！！！！", Toast.LENGTH_LONG).show();
//						}else{
//							//验证失败
//							Toast.makeText(MainActivity.this, "失败！！！！！！！！" + reason, Toast.LENGTH_LONG).show();
//						}
//
//					}
//				});


				break;

			case R.id.iv_sms_clear:

				break;

			default:
				break;
		}
	}


}
