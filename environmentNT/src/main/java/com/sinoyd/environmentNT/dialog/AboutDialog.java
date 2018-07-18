package com.sinoyd.environmentNT.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.SystemUtil;

/**
 * 关于弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：AboutDialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class AboutDialog extends AlertDialog implements android.view.View.OnClickListener {
	public AboutDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public AboutDialog(Context context, int theme) {
		super(context, theme);
	}

	public AboutDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_about);

		((TextView)findViewById(R.id.txt_version)).setText("V" + SystemUtil.versionName);
		// StyleController.updateViewBgAttrId(findViewById(R.id.dialog_bg),
		// R.attr.dialog_bg);
		// ((ImageView)findViewById(R.id.dialog_bg)).setImageDrawable(MyApplication.getDrawableByIdName("dialog_bg"));
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return super.onTouchEvent(event);
	}
}
