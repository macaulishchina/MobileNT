package com.sinoyd.environmentNT.dialog;

import java.util.List;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.StringUtils;

/**
 * 站点选择弹出框 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：FactorDialog


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class FactorDialog extends AlertDialog implements OnItemClickListener {
	private ListView mListView;
	/** 因子列表 **/
	private List<String> factorLists;
	/** 监听因子选择事件 **/
	private FactorItemSelectListener factorItemSelectListener;

	public FactorDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public FactorDialog(Context context, int theme, List<String> factorWaterLists) {
		super(context, R.style.Theme_Dialog_Transparent);
		this.factorLists = factorWaterLists;
	}

	public FactorDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_factorinfo);
		mListView = (ListView) findViewById(R.id.factor_list_list);
//		String[] objects = new String[factorLists.size()];
		CharSequence[] objects = new CharSequence[factorLists.size()];
		for (int i = 0; i < objects.length; i++) {
			objects[i] = factorLists.get(i);
			if(objects[i].equals("PM10"))
				objects[i]=StringUtils.getPM10ForDialog();
			if(objects[i].equals("PM2.5"))
				objects[i]=StringUtils.getPM25ForDialog();
			if(objects[i].equals("O3"))
				objects[i]=StringUtils.getO3ForDialog();
			if(objects[i].equals("NO2"))
				objects[i]=StringUtils.getNO2ForDialog();
			if(objects[i].equals("SO2"))
				objects[i]=StringUtils.getSO2ForDialog();
			if(objects[i].equals("CO"))
				objects[i]=StringUtils.getCOForDialog();
			 
		}
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, objects);
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1, objects);
//		indexDataList.add(new IndexDataInfo(StringUtils.getPM10(), getInt(info.PM10_IAQI)));
//		indexDataList.add(new IndexDataInfo(StringUtils.getPM25(), getInt(info.PM25_IAQI)));
//		indexDataList.add(new IndexDataInfo(StringUtils.getNO2(), getInt(info.NO2_IAQI)));
//		indexDataList.add(new IndexDataInfo(StringUtils.getSO2(), getInt(info.SO2_IAQI)));
//		indexDataList.add(new IndexDataInfo(StringUtils.getO3(), getInt(info.O3_IAQI)));
//		indexDataList.add(new IndexDataInfo(StringUtils.getCO(), getInt(info.CO_IAQI)));
		mListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		mListView.setOnItemClickListener(this);
	}

	public void setFactorItemSelectListener(FactorItemSelectListener portItemSelectListener) {
		this.factorItemSelectListener = portItemSelectListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		dismiss();
		return super.onTouchEvent(event);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (factorItemSelectListener != null) {
			factorItemSelectListener.selectFactorinfo(factorLists.get(arg2));
		}
		dismiss();
	}

	public static interface FactorItemSelectListener {
		void selectFactorinfo(String factorInfo);
	}

	@Override
	public void show() {
		super.show();
	}
}
