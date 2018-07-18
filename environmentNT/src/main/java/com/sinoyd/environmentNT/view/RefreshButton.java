package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * 刷新按钮 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：RefreshButton


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class RefreshButton extends android.support.v7.widget.AppCompatImageView {
	private RotateAnimation animation;

	public RefreshButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public RefreshButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public RefreshButton(Context context) {
		super(context);
		initView(context);
	}

	public void initView(Context context) {
		animation = new RotateAnimation(0, 359, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setFillAfter(true);
		animation.setDuration(800);
		LinearInterpolator lin = new LinearInterpolator();
		animation.setInterpolator(lin);
		animation.setRepeatCount(Animation.INFINITE);
		// animation.setRepeatMode(Animation.REVERSE);
		setAnimation(animation);
	}

	public void start() {
		setEnabled(false);
		animation.start();
	}

	public void stop() {
		setEnabled(true);
		animation.cancel();
	}
}
