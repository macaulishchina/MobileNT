package com.sinoyd.environmentNT.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.sinoyd.environmentNT.R;

/**
 * 版本更新弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：VesionCheckDialog

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class VesionCheckDialog extends AlertDialog implements android.view.View.OnClickListener {
	public VesionCheckDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public VesionCheckDialog(Context context, int theme) {
		super(context, theme);
	}

	public VesionCheckDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_check_version);
		findViewById(R.id.dialog_ok_btn).setOnClickListener(this);
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
