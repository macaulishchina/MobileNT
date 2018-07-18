package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.SystemUtil;

/**
 * 污染物因子水平多彩色带 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：ColourBarView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class ColourBarView extends LinearLayout {
	private Context mContext;
	private View arrows;
	private LinearLayout cursorView;
	private int currentValue;
	private float mToX;

	public ColourBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public ColourBarView(Context context) {
		super(context);
		initView(context);
	}

	/***
	 * 初始化view
	 * 
	 * @param context
	 */
	void initView(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View baseView = inflater.inflate(R.layout.rt_cursor_bar, this);
		cursorView = (LinearLayout) baseView.findViewById(R.id.cursor);
		arrows = baseView.findViewById(R.id.viewArrows);
		setCurrentValue(0);
	}

	/***
	 * 显示显示的点
	 * 
	 * @param pullectValue
	 */
	public void setCurrentValue(int pullectValue) {
		this.currentValue = pullectValue;
		if (pullectValue <= 50) { // 小于50的数据，按照比例进行计算
			mToX = 1.0f / 6.0f * (pullectValue / 50.0f);
		}
		if (pullectValue > 50 && pullectValue <= 100) { // 50~100之间的数据，按照比例计算
			mToX = 1.0f / 6.0f * (pullectValue / 100.0f) * 2.0f;
		}
		if (pullectValue > 100 && pullectValue <= 150) { // 50~100之间的数据，按照比例计算
			mToX = 1.0f / 6.0f * (pullectValue / 150.0f) * 3.0f;
		}
		if (pullectValue > 150 && pullectValue <= 200) { // 150~200之间的数据，按照比例计算
			mToX = 1.0f / 6.0f * (pullectValue / 200.0f) * 4.0f;
		}
		if (pullectValue > 200 && pullectValue <= 300) { // 200~300之间的数据，按照比例计算
			mToX = 1.0f / 6.0f * 4.0f + 1.0f / 6.0f * ((pullectValue - 200.0f) / 100.0f);
		}
		if (pullectValue > 300 && pullectValue < 500) { // 300~500之间的数据，按照比例计算
			mToX = 1.0f / 6.0f * 5.0f + 1.0f / 6.0f * ((pullectValue - 300.0f) / 200.0f);
		}
		if (pullectValue >= 500) { // 最大值500，超过500，显示最大的值
			mToX = 1.0f;
		}
		startArrowAnim();
	}

	/***
	 * 让view呈现动画效果
	 */
	private void startArrowAnim() {
		float toX = cursorView.getMeasuredWidth() * mToX - SystemUtil.dipToPx(mContext, 2);
		TranslateAnimation animation = new TranslateAnimation(0.0f, toX, 1.0f, 1.0f);
		animation.setDuration(1500);
		animation.setFillAfter(true);
		arrows.startAnimation(animation);
	}

	public int getCurrentValue() {
		return currentValue;
	}
}
