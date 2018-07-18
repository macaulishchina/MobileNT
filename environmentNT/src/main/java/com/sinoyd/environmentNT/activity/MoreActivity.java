package com.sinoyd.environmentNT.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.igexin.sdk.PushManager;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.MoreAdapter;
import com.sinoyd.environmentNT.dialog.AboutDialog;
import com.sinoyd.environmentNT.dialog.LevelExplainDialog;
import com.sinoyd.environmentNT.dialog.QADialog;
import com.sinoyd.environmentNT.dialog.SystemExplainDialog;
import com.sinoyd.environmentNT.dialog.VesionCheckDialog;
import com.sinoyd.environmentNT.download.DownloadDialog;
import com.sinoyd.environmentNT.download.DownloadDialog.DownloadListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.model.VersionModel;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.SystemUtil;


/**
 * 更多 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：MoreActivity
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MoreActivity extends BaseFragment implements OnItemClickListener, DownloadListener {
    /**
     * 更多页面文字内容  非登陆状态
     **/
    public static final String[] AIR_TITLE = {"检测新版版本", "空气质量指数等级说明", "系统介绍", "科普知识", "帮助", "关于", "登录"};
    /**
     * 更多页面图标
     **/
    public static final int[] AIR_ICONS = {R.drawable.more_check_version, R.drawable.more_kongqi_explain, R.drawable.more_jieshao, R.drawable.more_kepuzhishi, R.drawable.more_help, R.drawable.more_about, R.drawable.more_zhuxiao};


    /**
     * 更多页面文字内容  登陆状态
     **/
    public static final String[] AIR_TITLE_Login = {"检测新版版本", "空气质量指数等级说明", "系统介绍", "科普知识", "修改密码", "帮助", "关于", "注销"};
    /**
     * 更多页面图标
     **/
    public static final int[] AIR_ICONS_Login = {R.drawable.more_check_version, R.drawable.more_kongqi_explain, R.drawable.more_jieshao, R.drawable.more_kepuzhishi, R.drawable.changepassword, R.drawable.more_help, R.drawable.more_about, R.drawable.more_zhuxiao};


    /**
     * 更新版本实体
     **/
    private VersionModel versionModel;
    private ListView mListView;
    private String[] mItemStrings = AIR_TITLE;
    private int[] mItemIcons = AIR_ICONS;
    /**
     * 版本更新弹出框
     **/
    private VesionCheckDialog checkDialog;
    /**
     * 更多界面适配器
     **/
    private MoreAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        mListView.setOnItemClickListener(this);
        mAdapter = new MoreAdapter(mContext);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLogin = PreferenceUtils.getBoolean(MoreActivity.mContext, "IS_LOGIN");
        if (!isLogin) {
            mItemStrings = AIR_TITLE;
            mItemIcons = AIR_ICONS;
            mAdapter.setIconAndTitle(mItemIcons, mItemStrings);
        } else {
            mItemStrings = AIR_TITLE_Login;
            mItemIcons = AIR_ICONS_Login;
            mAdapter.setIconAndTitle(mItemIcons, mItemStrings);
        }
    }

    private Intent intent;

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        mListView.setSelection(arg2);
        Dialog dialog = null;
        boolean isLogin = PreferenceUtils.getBoolean(MoreActivity.mContext, "IS_LOGIN");

        switch (arg2) {
            case 0: // 检测新版版本
                checkDialog = new VesionCheckDialog(mContext, R.style.Theme_Dialog_Transparent);
                checkDialog.show();
                HttpClient.getJsonWithGetUrl(RequestAirActionName.GetVersionForPro, null, this, null);
                break;
            case 1: // 空气质量指数等级说明
                dialog = new LevelExplainDialog(mContext, R.style.dialog);
                dialog.show();
                break;

            case 2: // 系统介绍
                new SystemExplainDialog(mContext, R.style.dialog).show();
                break;
            case 3: // 科普知识
                new QADialog(mContext, R.style.dialog).show();
                break;
            case 4: // 帮助
                if (isLogin) {
                    intent = new Intent(MoreActivity.mContext, ChangepasswordActivity.class);
                    intent.putExtra("from", true);
                    startActivity(intent);
                } else {
                    intent = new Intent(mContext, HelpActivity.class);
                    intent.putExtra("from", true);
                    startActivity(intent);
                }
                break;
            case 5: // 关于
                if (isLogin) {
                    intent = new Intent(mContext, HelpActivity.class);
                    intent.putExtra("from", true);
                    startActivity(intent);
                } else {
                    new AboutDialog(mContext, R.style.Theme_Dialog_Transparent).show();
                }
                break;
            case 6: // 系统
                if (isLogin) {
                    new AboutDialog(mContext, R.style.Theme_Dialog_Transparent).show();
                } else {
                    intent = new Intent(MoreActivity.mContext, LoginActivity.class);
                    MoreActivity.mContext.startActivity(intent);
                    MoreActivity.this.getActivity().finish();

                }
                break;


            case 7:
                if (isLogin) {
                    Dialog alertDialog = new AlertDialog.Builder(mContext).setTitle("提示").setMessage("是否要注销账号？").setPositiveButton("确认", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
//						mDB.deleteAll(User.class);
//						mDB.deleteAll(AlarmItem.class);
//						mDB.deleteAll(FactorName.class);
//						mDB.deleteAll(PortInfo.class);
//						mDB.deleteAll(PortInfoSelect.class);
//						mDB.deleteAll(RealTimeQuality.class);
//						mDB.deleteAll(WaterTrendData.class);
                                //接触绑定
                                PushManager.getInstance().unBindAlias(getActivity(), PreferenceUtils.getData(getActivity(), "Alias"), true, PreferenceUtils.getData(getActivity(), "UserGuid"));

                                PreferenceUtils.saveBoolean(mContext, "IS_LOGIN", false);
                                PreferenceUtils.saveBoolean(mContext, "LOGIN_KEY", false);
                                PreferenceUtils.setData(mContext, "MenuInfo", "");
//						    PreferenceUtils.setData(mContext, "LoginId","taicangapp");
                                Intent intent2 = new Intent(mContext, LoginActivity.class);
                                intent2.putExtra("from", true);
                                startActivity(intent2);
                                MoreActivity.this.getActivity().finish();
//                                MyApplication.finishAllActivity();


                            } catch (Exception e) {
                                e.printStackTrace();
                                showTextToast("注销失败");
                            }
                        }
                    }).setNegativeButton("取消", new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();
                    alertDialog.show();

                } else {


                }
                break;

        }
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        super.requestSuccess(resData);
        if (checkDialog.isShowing()) {
            if (resData.getUri().equals(RequestAirActionName.GetVersionForPro)) {
                versionModel = new VersionModel();
                SystemUtil.getVersion(mContext);
                checkDialog.dismiss();
                try {
                    versionModel.parse(resData.getJson());
                    if (SystemUtil.versionCode < versionModel.verCode) { // 有新版本
                        AlertDialog.Builder newVersionDialog = new AlertDialog.Builder(mContext);
                        newVersionDialog.setTitle("版本更新");
                        newVersionDialog.setMessage("当前版本：" + SystemUtil.versionName + ",发现新版本：" + versionModel.verName + "。\n更新内容：" + versionModel.verDesc + "。\n是否更新版本？");
                        newVersionDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                paramDialogInterface.dismiss();
                                DownloadDialog downloadDialog = new DownloadDialog(mContext, versionModel.verPath);
                                downloadDialog.setDownloadListener(MoreActivity.this);
                                downloadDialog.show();
                            }
                        });
                        newVersionDialog.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                paramDialogInterface.dismiss();
                            }
                        });
                        newVersionDialog.show();
                    } else {
                        MyApplication.showTextToast("目前版本已经是最新版本了");
                    }
                } catch (Exception e) {
                    MyApplication.showTextToast("获取版本信息失败");
                    e.printStackTrace();
                }
            }
        }
    }

    /***
     * 下载文件
     */
    @Override
    public void endDownload(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    @Override
    public void startDownload() {
    }

    @Override
    public void updateFace() {
        super.updateFace();
    }
}
