package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

/**
 * 圆球滚动view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：NeedleImageView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class NeedleImageView extends LinearLayout {
	public static final int MAX_DEGREE = 250;
	public static final int MIN_DEGREE = -72;
	private float lastDegree;
	private float curDegree;
	private long runTime = 1500;

	public NeedleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public NeedleImageView(Context context) {
		super(context);
		init(context);
	}

	void init(Context context) {
		runTime = 0;
		setDegree(MIN_DEGREE);
		runTime = 1500;
	}

	/****
	 * 设置进度
	 * 
	 * @param degree
	 */
	public void setDegree(float degree) {
		this.curDegree = degree;
		RotateAnimation animation = new RotateAnimation(lastDegree, curDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(runTime);
		animation.setFillAfter(true);
		startAnimation(animation);
		this.lastDegree = degree;
	}
}
