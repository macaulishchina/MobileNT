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
 * qa弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：QADialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class QADialog extends AlertDialog {
	public QADialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public QADialog(Context context, int theme) {
		super(context, theme);
	}

	public QADialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_qa);
		TextView tvExplain=(TextView)findViewById(R.id.tv_explain);
		CharSequence text="AQI计算与评价的过程大致可分为三个步骤：第一步是对照各项污染物的分级浓度限值，以细颗粒物（"+ StringUtils.getPM25()+"）、可吸入颗粒物（PM10）、二氧化硫（SO2）、二氧化氮（NO2）、臭氧（O3）、一氧化碳（CO）等各项污染物的实测小时浓度值分别计算得出空气质量分指数（Individual Air Quality Index，简称IAQI）；第二步是从各项污染物的IAQI中选择最大值确定为AQI，当AQI大于50时将IAQI最大的污染物确定为首要污染物；第三步是对照AQI分级标准，确定空气质量级别、类别及表示颜色、健康影响与建议采取的措施。"
		+"\n\n\t\t简言之，AQI就是各项污染物的空气质量分指数（IAQI）中的最大值，当AQI大于50时对应的污染物即为首要污染物。";
		SpannableString s1 = new SpannableString("AQI计算与评价的过程大致可分为三个步骤：第一步是对照各项污染物的分级浓度限值，以细颗粒物（");
		SpannableString s2= new SpannableString("）、可吸入颗粒物（");
		SpannableString s3= new SpannableString("）、二氧化硫（");
		SpannableString s4 = new SpannableString("）、二氧化氮（");
		SpannableString s5 = new SpannableString("）、臭氧（");
		SpannableString s6 = new SpannableString("）、一氧化碳（");
		SpannableString s7 = new SpannableString("）等各项污染物的实测小时浓度值分别计算得出空气质量分指数（Individual Air Quality Index，简称IAQI）；第二步是从各项污染物的IAQI中选择最大值确定为AQI，当AQI大于50时将IAQI最大的污染物确定为首要污染物；第三步是对照AQI分级标准，确定空气质量级别、类别及表示颜色、健康影响与建议采取的措施。");
		SpannableString s8 = new SpannableString("\n\n\t\t简言之，AQI就是各项污染物的空气质量分指数（IAQI）中的最大值，当AQI大于50时对应的污染物即为首要污染物。");
		SpannableStringBuilder sb=new SpannableStringBuilder();
		sb.append(s1);
		sb.append(StringUtils.getPM25());
		sb.append(s2);
		sb.append(StringUtils.getPM10());
		sb.append(s3);
		sb.append(StringUtils.getSO2());
		sb.append(s4);
		sb.append(StringUtils.getNO2());
		sb.append(s5);
		sb.append(StringUtils.getO3());
		sb.append(s6);
		sb.append(StringUtils.getCO());
		sb.append(s7);
		sb.append(s8);
		tvExplain.setText(sb);
//		s1.StringUtils.getPM25()+s2+StringUtils.getPM10()+s3+StringUtils.getSO2()+s4+StringUtils.getNO2()+s5+StringUtils.getO3()+s6+StringUtils.getCO()+s7+s8);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return super.onTouchEvent(event);
	}
}
