package com.sinoyd.environmentNT.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * 弹出框工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：DialogUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class DialogUtils {
	/***
	 * 显示弹出框
	 * 
	 * @param context
	 */
	public static void showProgressDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setCancelable(false);
		dialog.show();
	}

	/***
	 * 隐藏弹出框
	 * 
	 * @param context
	 */
	public static void hideProgressDialog(Context context) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.show();
	}

	/***
	 * 提示内容的弹出框
	 * 
	 * @param context
	 * @param string
	 */
	public static void showDialog(Context context, String string) {
		DialogUtils.showDialog(context, string, "提示");
	}

	/***
	 * 显示一个弹出框
	 * 
	 * @param context
	 * @param string 显示的内容
	 * @param title 标题
	 */
	@SuppressWarnings("deprecation")
	public static void showDialog(Context context, String string, String title) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(string);
			builder.setTitle(title);
			final AlertDialog alertDialog = builder.create();
			alertDialog.setButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					alertDialog.dismiss();
				}
			});
			alertDialog.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 显示一个日期弹出框
	 * 
	 * @param context
	 */
	@SuppressLint("SimpleDateFormat")
	public static void showDateSelectDialog(final Context context, final EditText editText, String title) {
		Date date = null;
		if (!editText.getText().equals("")) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date = simpleDateFormat.parse(editText.getText().toString());
			}
			catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (date == null) {
			date = new Date();
		}
		DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
				editText.setText(datePicker.getYear() + "-" + ((datePicker.getMonth() + 1) < 9 ? ("0" + (datePicker.getMonth() + 1)) : (datePicker.getMonth() + 1)) + "-" + ((datePicker.getDayOfMonth()) < 9 ? ("0" + (datePicker.getDayOfMonth())) : (datePicker.getDayOfMonth())));
			}
		};
		@SuppressWarnings("deprecation")
		DatePickerDialog datePickerDialog = new DatePickerDialog(context, onDateSetListener, date.getYear(), date.getMonth(), date.getDay());
		datePickerDialog.setTitle(title);
		datePickerDialog.show();
	}
}
