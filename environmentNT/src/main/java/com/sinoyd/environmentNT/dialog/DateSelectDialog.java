package com.sinoyd.environmentNT.dialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.DateUtil;

/**
 * 起始结束日期选择弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：DateSelectDialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@SuppressLint("NewApi")
public class DateSelectDialog extends Dialog implements android.view.View.OnClickListener {
	public static interface DateSelectListener {
		void selectDate(String startDate, String endDate);
	}

	private DateSelectListener dateSelectListener;
	private String startTimeStr, endTimeStr;

	public void setDateSelectListener(DateSelectListener dateSelectListener) {
		this.dateSelectListener = dateSelectListener;
	}

	public DateSelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	public DateSelectDialog(Context context, int theme) {
		super(context, R.style.Theme_Dialog_Transparent);
		initView();
	}

	public DateSelectDialog(Context context) {
		super(context);
		initView();
	}

	private DatePicker startDatePicker, endDatePicker;

	private void initView() {
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.date_selelct_layout);
		findViewById(R.id.ok_btn).setOnClickListener(this);
		findViewById(R.id.cancel_btn).setOnClickListener(this);
		// findViewById(R.id.ok_btn).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.dialog_btn));
		// findViewById(R.id.cancel_btn).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.dialog_btn));
		startDatePicker = (DatePicker) findViewById(R.id.start_date_picker);
		endDatePicker = (DatePicker) findViewById(R.id.end_date_picker);
	}

	@Override
	public void show() {
		super.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_btn:
			if (dateSelectListener != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calendar = Calendar.getInstance();
				calendar.set(startDatePicker.getYear(), startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
				String startDate = dateFormat.format(calendar.getTime());
				calendar.set(endDatePicker.getYear(), endDatePicker.getMonth(), endDatePicker.getDayOfMonth());
				String endDate = dateFormat.format(calendar.getTime());
				if (Integer.parseInt(startDate.replace("-", "")) > Integer.parseInt(endDate.replace("-", ""))) {
					MyApplication.showTextToast("起始日期不能大于结束日期!");
					return;
				}
				try {
					if(DateUtil.getDays(dateFormat.parse(startDate),dateFormat.parse(endDate))>31)
                    {
						MyApplication.showTextToast("日期范围不能超过1个月!");
						return;
                    }
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dateSelectListener.selectDate(startDate, endDate);
			}
			dismiss();
			break;
		case R.id.cancel_btn:
		default:
			dismiss();
			break;
		}
	}

	/***
	 * 设置日期
	 * 
	 * @param startTimeStr
	 * @param endTimeStr
	 */
	public void setStartAndEndDate(String startTimeStr, String endTimeStr) {
		if (startTimeStr == null || endTimeStr == null)
			return;
		this.startTimeStr = startTimeStr;
		this.endTimeStr = endTimeStr;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date startDate, endDate;
		try {
			if (android.os.Build.VERSION.SDK_INT >= 11) {
				startDate = dateFormat.parse(startTimeStr);
				calendar.setTime(startDate);
				startDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
				endDate = dateFormat.parse(endTimeStr);
				calendar.setTime(endDate);
				endDatePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
			}
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
