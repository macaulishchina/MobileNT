package com.sinoyd.environmentNT.listener;

import com.sinoyd.environmentNT.http.HttpResponse;

/**
 * 监听网络请求的返回 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpListener


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract interface HttpListener {
	/***
	 * 请求网络成功
	 * 
	 * @param resData
	 */
	public void requestSuccess(HttpResponse resData);

	/***
	 * 请求网络失败
	 * 
	 * @param resData
	 */
	public void requestFailed(HttpResponse resData);
}
