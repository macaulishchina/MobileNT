package com.sinoyd.environmentNT.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.json.JSON;
import com.sinoyd.environmentNT.model.WaterPortInfo;
import com.sinoyd.environmentNT.model.WaterPortInfoModel;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.view.ExpandableViewGroup;
import com.sinoyd.environmentNT.view.ExpandableViewGroup.CallBack;

/**
 * 专业版 站点管理 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PortManagerActivity

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PortManagerActivity extends AbstractActivity {
	/** 水质站点Map **/
	private Map<String, List<WaterPortInfo>> map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pm);
		String result = getString("waterPorts.json");
		WaterPortInfoModel jsonData = JSON.parse(result, WaterPortInfoModel.class);
		if (jsonData != null)
			map = jsonData.getTypePortInfo();
		((ExpandableViewGroup) findViewById(R.id.egPm)).setMapData(map);
		((ExpandableViewGroup) findViewById(R.id.egPm)).setCallBack(new CallBack() {
			@Override
			public void onBack() {
				onBackPressed();
			}
		});
	}

	/***
	 * 从文件中获取字符
	 * 
	 * @param file
	 * @return
	 */
	private String getString(String file) {
		StringBuffer buffer = new StringBuffer();
		try {
			InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open(file));
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line = bufferedReader.readLine()) != null)
				buffer.append(line.trim());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	@Override
	protected void initView() {
	}

	private HashMap<String, String> requestParams = new HashMap<String, String>();

	@Override
	protected void requestServer() {
		super.requestServer();
		requestParams.clear();
		requestParams.put("sysType", AppConfig.isWatherSystem() ? "water" : "air");
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		Boolean IsLogin=PreferenceUtils.getBoolean(this, "IS_LOGIN");
		if(IsLogin) {
			String LoginId=PreferenceUtils.getValue(preferences, "LoginId");
			requestParams.put("LoginId", LoginId);
		}
		else
			requestParams.put("LoginId", "taicangapp");
		HttpClient.getJsonWithGetUrl(AppConfig.RequestAirActionName.GetPortInfoBySysType, requestParams, this, "正在添加...");
	}

	@Override
	public void requestSuccess(HttpResponse resData) {
		super.requestSuccess(resData);
	}
}
