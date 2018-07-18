package com.sinoyd.environmentNT.http;

import android.support.v4.app.FragmentActivity;

import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.listener.HttpListener;

import org.json.JSONObject;

/**
 * 网络请求逻辑类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpRequestActivity


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract class HttpRequestActivity extends FragmentActivity implements HttpListener {
	/** 网络请求成功 **/
	public abstract void requestSuccess(HttpResponse resData);

	/** 网络请求失败 **/
	public void requestFailed(HttpResponse resData) {
		JSONObject jsonObject = resData.getJson();
		if (jsonObject != null) {
			showErrorMsg(jsonObject.optString("message"));
		}
		else {
			showTextToast("暂无数据，请稍后刷新!");
		}
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
}
