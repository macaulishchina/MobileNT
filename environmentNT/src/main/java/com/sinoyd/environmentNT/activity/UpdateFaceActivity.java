//package com.sinoyd.environmentNT.activity;
//
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.sinoyd.environmentNT.AppConfig.AppReceiverName;
//import com.sinoyd.environmentNT.MyApplication;
//import com.sinoyd.environmentNT.R;
//import com.sinoyd.environmentNT.adapter.UpdateFaceAdapter;
//import com.sinoyd.environmentNT.reflect.StyleController;
//
///**
// * 换肤界面 Copyright (c) 2015 江苏远大信息股份有限公司
// *
// * @类型名称：UpdateFaceActivity
//
//
// * @维护人员：
// * @维护日期：
// * @功能摘要：
// */
//public class UpdateFaceActivity extends AbstractActivity implements OnItemClickListener {
//	private GridView mGridView;
//	private UpdateFaceAdapter mAdapter;
//	private String useImageName;
//	private int useImageIndex;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setTheme(MyApplication.currentThemeId);
//		setContentView(R.layout.activity_update_face);
//	}
//
//	@Override
//	protected void initView() {
//		((TextView) findViewById(R.id.title)).setText("皮肤");
//		findViewById(R.id.top_refresh).setVisibility(View.GONE);
////		useImageName = PreferenceUtils.getData(this, PageBg.IMAGE_KEY);
////		useImageIndex = PageBg.getFaceByName(useImageName, FaceType.FactTypeIndex);
//		mGridView = (GridView) findViewById(R.id.gridview);
//		mAdapter = new UpdateFaceAdapter(this);
//		mAdapter.setUseIndex(useImageIndex);
//		mGridView.setAdapter(mAdapter);
//		mGridView.setOnItemClickListener(this);
//		mAdapter.notifyDataSetChanged();
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
//		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//		dialog.setTitle("提示");
//		dialog.setMessage("是否使用该皮肤？");
//		dialog.setPositiveButton("使用", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//				useImageIndex = arg2;
//				installFaceDone();
//			}
//		});
//		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//	}
//
//	/***
//	 * 使用选中的皮肤
//	 */
//	private void installFaceDone() {
////		int selectThemeId = AppConfig.PageBg.THEME_ARRAYS[useImageIndex];
////		if (MyApplication.currentThemeId == selectThemeId) {
////			MyApplication.showTextToast("当前皮肤已经在应用中！");
////			return;
////		}
////		MyApplication.currentThemeId = selectThemeId;
////		PreferenceUtils.saveInteger(MyApplication.mContext, PageBg.THEME_INDEX_KEY, useImageIndex);
////		useImageName = PageBg.PAGE_BG_NAME[useImageIndex];
//		mAdapter.setUseIndex(useImageIndex);
//		mAdapter.notifyDataSetChanged();
////		PreferenceUtils.setData(UpdateFaceActivity.this, PageBg.IMAGE_KEY, useImageName);
//		MyApplication.showTextToast("切换皮肤成功!");
//		StyleController.setThemeStyle(this, MyApplication.currentThemeId);
//		sendBroadcast(new Intent(AppReceiverName.FACE_RECEIVER_NAME));
//		StyleController.updateViewBgAttrId(findViewById(R.id.mainRLayout), R.attr.page_bg);
//	}
//
//	@Override
//	protected void onDestroy() {
//		releaseData();
//		super.onDestroy();
//	}
//
//	/***
//	 * 释放内容
//	 */
//	private void releaseData() {
//		mGridView.setAdapter(null);
//		mAdapter.setRelease(true);
//		mAdapter = null;
//		mGridView = null;
//		System.gc();
//	}
//}
