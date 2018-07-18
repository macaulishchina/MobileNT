package com.sinoyd.environmentNT.activity;

import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.PreferencesManager;
import com.sinoyd.environmentNT.http.HttpRequestActivity;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.util.SystemUtil;

/**
 * 抽象类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：AbstractActivity
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract class AbstractActivity extends HttpRequestActivity implements Callback  {
	/** 打印log **/
	private static final String TAG = "AbstractActivity";
	/** 线程处理 **/
	protected Handler mHandler;
	

	/** 初始化加载view **/
	protected abstract void initView();
//内存泄漏
//	/**
//	 * 数据库存储统一接口
//	 */
//	public static DbUtils mDB;
//	/** 全局变量Context **/
//	private static Context mContext;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("onCreate", getClass().getName());
		
		PreferencesManager.getInstance().setContext(this);
		SystemUtil.getScreen(this);
		mHandler = new Handler(this);
//		mContext = this;
//		CrashHandler.getInstance().init(mContext);
		//内存泄漏
//		if (mDB == null) {
//			mDB = DbUtils.create(this, MyApplication.DATABASE_NAME, MyApplication.DATABASE_VERSION, null);
//		}
	}

	/***
	 * 发送消息
	 * 
	 * @param what
	 */
	protected void sendMessage(int what) {
		if (mHandler.hasMessages(what)) {
			mHandler.removeMessages(what);
		}
		mHandler.sendEmptyMessage(what);
	}

	/***
	 * 处理消息
	 */
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		initView();
	}

	/***
	 * 请求网络
	 */
	protected void requestServer() {
	}

	@Override
	public void requestSuccess(HttpResponse resData) {
		Log.i(TAG, "rspJSON=" + resData.getJson());
	}

	/***
	 * 发送网络请求
	 * 
	 * @param serverUrl 请求的地址
	 * @param params 请求的参数
	 * @param method 请求的方式
	 * @param obj 请求的json数据
	 * @throws Exception 异常
	 */
	protected void sendHttpRequest(String serverUrl, RequestParams params, HttpRequest.HttpMethod method, JSONObject obj) throws Exception {
		if (obj != null) {
			StringEntity entity = new StringEntity(obj.toString(), HTTP.UTF_8);
			entity.setContentType("application/json;charset=UTF-8");
			entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;charset=UTF-8"));
			params.setBodyEntity(entity);
		}
	}






	@Override
	protected void onDestroy() {
		mHandler = null;
		super.onDestroy();
//		mDB.close();
	}

	@Override
	protected void onResume() {
		super.onResume();
 		MyApplication.activityContext = this;
	}






}
