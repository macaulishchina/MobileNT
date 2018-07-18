package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 因子选择 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：SelectGene


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class SelectGene extends Button {
	public SelectGene(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public SelectGene(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SelectGene(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return true;
	}
}
