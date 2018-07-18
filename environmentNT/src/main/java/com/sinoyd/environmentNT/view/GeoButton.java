package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.sinoyd.environmentNT.R;

/**
 * 
 * Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：GeoButton


 * @维护人员：
 * @维护日期：
 * @功能摘要：经纬度按钮
 */
public class GeoButton extends LinearLayout {
	/** 经纬度信息 */
	private com.baidu.mapapi.model.inner.GeoPoint geoPoint;
	private TextView button;

	public GeoButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GeoButton(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = inflater.inflate(R.layout.marker_gis_layout, this);
		button = (TextView) baseView.findViewById(R.id.button);
	}

	public void setText(String text) {
		button.setText(text);
	}

	public void setButtonBg(int resid) {
		button.setBackgroundResource(resid);
	}

	public GeoPoint getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPoint geoPoint) {
		this.geoPoint = geoPoint;
	}
}
