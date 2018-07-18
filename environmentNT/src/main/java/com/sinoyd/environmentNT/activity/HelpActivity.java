package com.sinoyd.environmentNT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.reflect.StyleController;
import com.sinoyd.environmentNT.util.BMapUtil;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.view.ArrowView;
import com.sinoyd.environmentNT.view.MyScrollLayout;
import com.sinoyd.environmentNT.view.OnViewChangeListener;

/**
 * 帮助页面 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HelpActivity

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HelpActivity extends AbstractActivity implements OnViewChangeListener, OnClickListener {
	/** 帮助图片 **/
	private static int[] backRes = { R.drawable.help_1, R.drawable.help_2, R.drawable.help_3 };
	/** 滑动控件 **/
	private MyScrollLayout mScrollLayout;
	/** 图片数 **/
	private int count;
	/** 当前滑动到了第几张 **/
	private int currentItem = -1;
	/** 是否进入帮助页面 **/
	private boolean startFromHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startFromHelp = getIntent().getBooleanExtra("from", false);
		setContentView(R.layout.activity_help);
	}

	@SuppressWarnings("deprecation")
	protected void initView() {
		mScrollLayout = (MyScrollLayout) findViewById(R.id.ScrollLayout);
		count = mScrollLayout.getChildCount();
		if (count > 2) {
			mScrollLayout.getChildAt(0).setBackgroundDrawable(BMapUtil.getDrawableFromCache(this, backRes[0]));
			mScrollLayout.getChildAt(1).setBackgroundDrawable(BMapUtil.getDrawableFromCache(this, backRes[1]));
		}
		mScrollLayout.getChildAt(count - 1).setOnClickListener(this);
		setcurrentPoint(0);
		mScrollLayout.SetOnViewChangeListener(this);
	}

	@Override
	public void OnViewChange(int position) {
		setcurrentPoint(position);
	}

	@SuppressWarnings("deprecation")
	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		currentItem = position;
		if (position + 1 < count) {
			mScrollLayout.getChildAt(position + 1).setBackgroundDrawable(BMapUtil.getDrawableFromCache(this, backRes[position + 1]));
		}
		((ArrowView) findViewById(R.id.arrowView)).setCheck(currentItem);
	}

	@Override
	public void onClick(View v) {
		if (startFromHelp) {
			setResult(RESULT_OK);
			finish();
		}
		else {
			startActivity(new Intent(this, LoginActivity.class));
//			startActivity(new Intent(this, MainActivity.class));
			PreferenceUtils.saveBoolean(this, "noFirstStart", true);
			finish();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		BMapUtil.removeCache(); // 销毁
		((ArrowView) findViewById(R.id.arrowView)).removeAllViews();
		for (int i = 0; i < mScrollLayout.getChildCount(); i++) {
			StyleController.freeViewBg(mScrollLayout.getChildAt(i));
		}
		mScrollLayout = null;
		System.gc();
		super.onDestroy();
	}
}
