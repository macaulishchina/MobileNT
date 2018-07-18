package com.sinoyd.environmentNT.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;

/**
 * 加载数据弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：LoadDialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class LoadDialog extends AlertDialog {
	private TextView loadMsgText;

	public LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public LoadDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoadDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_progress_layout);
		setCancelable(false);
		loadMsgText = (TextView) findViewById(R.id.load_msg);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
