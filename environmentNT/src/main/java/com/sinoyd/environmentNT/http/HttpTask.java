package com.sinoyd.environmentNT.http;

import android.os.AsyncTask;

/**
 * 异步处理网络请求 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpTask


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public abstract class HttpTask<T> extends AsyncTask<Object[], Integer, T> {
	/** 执行任务 */
	protected abstract void doPreExecute() throws Exception;

	/** 准备执行 */
	protected abstract T doBack(Object[]... params) throws Exception;

	/** 执行进度 */
	protected abstract void doProgress(Integer... values) throws Exception;

	/** 执行中 */
	protected abstract void doPost(T result) throws Exception;

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			doPreExecute();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected T doInBackground(Object[]... params) {
		try {
			return doBack(params);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		try {
			doProgress(values);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPostExecute(T result) {
		super.onPostExecute(result);
		try {
			doPost(result);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
