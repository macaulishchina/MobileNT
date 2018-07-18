package com.sinoyd.environmentNT.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.MotionEvent;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.StringUtils;

/**
 * 系统说明弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：SystemExplainDialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class SystemExplainDialog extends AlertDialog {
	public SystemExplainDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public SystemExplainDialog(Context context, int theme) {
		super(context, theme);
	}

	public SystemExplainDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_system_explain);
//		String explaintextString="\t\t本系统每小时发布的是环境空气中二氧化硫（"+StringUtils.getSO2()+"）、二氧化氮（"+StringUtils.getNO2()+"）、臭氧（"+StringUtils.getO3()+"）、一氧化碳（"+StringUtils.getCO()
//				+"）、颗粒物PM2.5（粒径小于等于2.5μm）和颗粒物PM10  （粒径小于等于10μm）的1小时平均值以及空气质量指数（AQI）。"
//				+" \n\n\t\t本系统数据更新频率为每小时一次。由于数据传输需要一定的时间，发布的数据或会有所延滞，或不能及时更新。"
//				+"\n\n\t\t当遇到监测仪器校零、校标等日常维护行为，或出现仪器故障、通信故障、停电等情况，可能会出现不能及时更新或某些站点没有数据。"
//			    +"\n\n\t\t为及时满足公众的环境知情权，本系统所发布的是实时监测数据，未经审核。";
//		  android:text="\t\t本系统每小时发布的是环境空气中二氧化硫（SO2）、二氧化氮（NO2）、臭氧（O3）、一氧化碳（CO）、颗粒物PM2.5（粒径小于等于2.5μm）和颗粒物PM10  （粒径小于等于10μm）的1小时平均值以及空气质量指数（AQI）。
//				    \n\n\t\t本系统数据更新频率为每小时一次。由于数据传输需要一定的时间，发布的数据或会有所延滞，或不能及时更新。
//				    \n\n\t\t当遇到监测仪器校零、校标等日常维护行为，或出现仪器故障、通信故障、停电等情况，可能会出现不能及时更新或某些站点没有数据。
//				    \n\n\t\t为及时满足公众的环境知情权，本系统所发布的是实时监测数据，未经审核。"
		String explaintextString="\t\t本系统每小时发布的是环境空气中二氧化硫（SO2）、二氧化氮（NO2）、臭氧（O3）、一氧化碳（CO）、颗粒物PM2.5（粒径小于等于2.5μm）和颗粒物PM10  （粒径小于等于10μm）的1小时平均值以及空气质量指数（AQI）。"
		+" \n\n\t\t本系统数据更新频率为每小时一次。由于数据传输需要一定的时间，发布的数据或会有所延滞，或不能及时更新。"
		+"\n\n\t\t当遇到监测仪器校零、校标等日常维护行为，或出现仪器故障、通信故障、停电等情况，可能会出现不能及时更新或某些站点没有数据。"
	    +"\n\n\t\t为及时满足公众的环境知情权，本系统所发布的是实时监测数据，未经审核。";

		SpannableString s1 = new SpannableString("\t\t本系统每小时发布的是环境空气中二氧化硫（");
		SpannableString s2= new SpannableString("）、二氧化氮（");
		SpannableString s3= new SpannableString("）、臭氧（");
		SpannableString s4 = new SpannableString("）、一氧化碳（");
		SpannableString s5 = new SpannableString("）、颗粒物");
		SpannableString s6 = new SpannableString("（粒径小于等于2.5μm）和颗粒物");
		SpannableString s7 = new SpannableString("  （粒径小于等于10μm）的1小时平均值以及空气质量指数（AQI）。");
		SpannableString s8 = new SpannableString(" \n\n\t\t本系统数据更新频率为每小时一次。由于数据传输需要一定的时间，发布的数据或会有所延滞，或不能及时更新。");
		SpannableString s9 = new SpannableString("\n\n\t\t当遇到监测仪器校零、校标等日常维护行为，或出现仪器故障、通信故障、停电等情况，可能会出现不能及时更新或某些站点没有数据。");
		SpannableString s10 = new SpannableString("\n\n\t\t为及时满足公众的环境知情权，本系统所发布的是实时监测数据，未经审核。");
		SpannableStringBuilder sb=new SpannableStringBuilder();
		sb.append(s1);
		sb.append(StringUtils.getSO2());
		sb.append(s2);
		sb.append(StringUtils.getNO2());
		sb.append(s3);
		sb.append(StringUtils.getO3());
		sb.append(s4);
		sb.append(StringUtils.getCO());
		sb.append(s5);
		sb.append(StringUtils.getPM25());
		sb.append(s6);
		sb.append(StringUtils.getPM10());
		sb.append(s7);
		sb.append(s8);
		sb.append(s9);
		sb.append(s10);
		((TextView)findViewById(R.id.tv1)).setText(sb);
//		((TextView)findViewById(R.id.tv1)).setText(StringUtils.getExplainCharSequence(explaintextString));
//		((TextView)findViewById(R.id.tv1)).setText(explaintextString);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return super.onTouchEvent(event);
	}
}
