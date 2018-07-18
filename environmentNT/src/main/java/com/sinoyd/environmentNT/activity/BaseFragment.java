package com.sinoyd.environmentNT.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.SystemType;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.PreferencesManager;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.SystemUtil;

import org.json.JSONObject;

/**
 * 抽象基类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：BaseFragment
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract class BaseFragment extends Fragment implements Callback, HttpListener {
	protected Handler mHandler;
	protected boolean needUpdateFace;

	/** 初始化加载view **/
	protected abstract void initView();

	/** http请求对象 **/
	public static HttpUtils mHttp;
	/** db对象 **/
	public static DbUtils mDB;
	private int mLayout;
	public static Context mContext;

	/** 更新皮肤处理 **/
	public void updateFace() {
		Log.i(getClass().getName(), "修改皮肤");
	}

	public View findViewById(int id) {
		if (getView() != null) {
			return getView().findViewById(id);
		}
		return null;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PreferencesManager.getInstance().setContext(getActivity());
		SystemUtil.getScreen(getActivity());
		mHandler = new Handler(this);
		if (mHttp == null) {
			mHttp = new HttpUtils();
		}
		if (mDB == null) {
			mDB = DbUtils.create(getActivity(), MyApplication.DATABASE_NAME, MyApplication.DATABASE_VERSION, null);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(mLayout, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mContext = view.getContext();
		initView();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("Fragment-onResume", getClass().getName());
		if (needUpdateFace) {
			updateFace();
			needUpdateFace = false;
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i("Fragment-onPause", getClass().getName());
	}

	/****
	 * 设置layout
	 * 
	 * @param layout
	 */
	public void setContentView(int layout) {
		mLayout = layout;
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
	 * 网络请求
	 */
	protected void requestServer() {
	}

	/***
	 * 消息处理
	 */
	@Override
	public boolean handleMessage(Message msg) {
		return false;
	}

	@Override
	public void requestFailed(HttpResponse resData) {
		JSONObject jsonObject = resData.getJson();
		if (jsonObject != null) {
			showErrorMsg(jsonObject.optString("message"));
		}
		else {
			showTextToast("暂无数据，请稍后刷新!");
		}
	}

	@Override
	public void requestSuccess(HttpResponse resData) {
	}

	/**
	 * 显示错误信息的方式，默认方式，可以重写
	 * 
	 * @param errormsg
	 */
	public void showErrorMsg(String errormsg) {
		showTextToast(errormsg);
	}

	/**
	 * 显示 提示框的方法
	 * 
	 * @param msg
	 */
	public void showTextToast(String msg) {
		MyApplication.showTextToast(msg);
	}

	/***
	 * 切换系统
	 */
	public void updateSystem() {
		if (AppConfig.isWatherSystem()) {
			PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", false);
			AppConfig.systemType = SystemType.AirType;
//			MyApplication.showTextToast("切换为空气质量系统成功");
		}
		else {
			PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", true);
			AppConfig.systemType = SystemType.WatherType;
//			MyApplication.showTextToast("切换为地表水系统成功");
		}
//		Intent resultIntent=	new Intent(getActivity().getApplicationContext(), MainActivity.class);
//		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////		startActivity(new Intent(mContext, MainActivity.class));
//		startActivity(resultIntent);
//		this.getActivity().finish();
//		((MainActivity)this.getActivity()).RefreshView();


	}

	public void more() {
		startActivity(new Intent(this.getActivity(), MoreMenuActivity.class));

	}
}
