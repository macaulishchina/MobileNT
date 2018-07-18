package com.sinoyd.environmentNT.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.igexin.sdk.PushManager;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.DemoIntentService;
import com.sinoyd.environmentNT.DemoPushService;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.FragmentTabAdapter;
import com.sinoyd.environmentNT.download.DownloadDialog;
import com.sinoyd.environmentNT.download.DownloadDialog.DownloadListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.model.VersionModel;
import com.sinoyd.environmentNT.util.PreferenceUtils;
import com.sinoyd.environmentNT.util.SystemUtil;
import com.sinoyd.environmentNT.view.MenuButton;
import com.sinoyd.environmentNT.view.MenuGroup;

import java.util.ArrayList;
import java.util.List;

/* import com.umeng.analytics.MobclickAgent; */

/***
 * 主界面
 *
 * @author smz 创建时间：2013-12-8上午12:54:28
 */
public class MainActivity extends FragmentActivity implements HttpListener, DownloadListener {
    /**
     * 底部菜单
     **/
    private MenuGroup menuGroup;
    public List<BaseFragment> fragments = new ArrayList<BaseFragment>();
    /**
     * 底部菜单适配器
     **/
    private FragmentTabAdapter tabAdapter;
    public static int selelctIndex;

//	/** 底部菜单文字说明-气 **/
//	private static final String[] TAB_AIR_TITLES = { "AQI","GIS", "在线情况", "报警信息", "监测数据", "趋势","运维情况", "优良天数" };
//	/** 底部菜单文字说明-水 **/
//	private static final String[] TAB_WATER_TITLES = { "实时水质", "GIS","在线情况","报警信息", "监测数据", "水质趋势","运维情况","周报数据" };
//	/** 底部菜单图标说明-气 **/
//	private static final int[] TAB_AIR_ICONS = { R.drawable.tab_shikuang,R.drawable.tab_gis, R.drawable.tab_online, R.drawable.tab_alarm, R.drawable.tab_monitor, R.drawable.tab_lishi,R.drawable.tab_ommp, R.drawable.tab_youliang };
//	/** 底部菜单图标说明-水 **/
//	private static final int[] TAB_WATER_ICONS = { R.drawable.tab_shikuang,R.drawable.tab_gis, R.drawable.tab_online,R.drawable.tab_alarm, R.drawable.tab_monitor, R.drawable.tab_szqushi,R.drawable.tab_ommp,R.drawable.tab_weekreport};

    /**
     * 底部菜单文字说明-气
     **/
    private static final String[] TAB_AIR_TITLES = {"实时数据", "日趋势", "常规数据", "超站数据", "GIS", "优良天数", "在线情况", "报警"};
    /**
     * 底部菜单文字说明-水
     **/
//    private static final String[] TAB_WATER_TITLES = {"实时水质", "GIS", "在线情况", "报警信息", "监测数据", "水质趋势"};
    /**
     * 底部菜单图标说明-气
     **/
    private static final int[] TAB_AIR_ICONS = {R.drawable.tab_shikuang, R.drawable.tab_lishi, R.drawable.tab_monitor, R.drawable.tab_superr, R.drawable.tab_gis, R.drawable.tab_youliang, R.drawable.tab_online, R.drawable.tab_alarm};
    /**
     * 底部菜单图标说明-水
     **/
//    private static final int[] TAB_WATER_ICONS = {R.drawable.tab_shikuang, R.drawable.tab_gis, R.drawable.tab_online, R.drawable.tab_alarm, R.drawable.tab_monitor, R.drawable.tab_szqushi};
    public static int lastIndex = 0;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //Log.d("onNewIntent", getIntent().getStringExtra("SysType"));

//		MainActivity.lastIndex = 2;
//		FragmentTabAdapter.onClickItem(2);


        if (intent.getStringExtra("SysType") != null && intent.getStringExtra("SysType").equals("AIR")) {
            if (AppConfig.isWatherSystem()) {
                updateSystemNew();
//				UpdateView();
                MainActivity.lastIndex = 7;
                menuGroup.gotoIndex(7);
            } else {
                MainActivity.lastIndex = 7;
                //FragmentTabAdapter.onClickItem(3);
                menuGroup.gotoIndex(7);
            }
        }

        if (intent.getStringExtra("SysType") != null && intent.getStringExtra("SysType").equals("WATER")) {
            if (AppConfig.isWatherSystem()) {
                MainActivity.lastIndex = 7;
                //FragmentTabAdapter.onClickItem(2);
                menuGroup.gotoIndex(7);
            } else {
                updateSystemNew();

                MainActivity.lastIndex = 7;
                menuGroup.gotoIndex(7);
            }

        }
        if (intent.getStringExtra("SysType") == null) {
            updateSystemNew();
        }


    }


    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }


    public void updateSystemNew() {
//		if (AppConfig.isWatherSystem()) {
//			PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", false);
//			AppConfig.systemType = AppConfig.SystemType.AirType;
//			MyApplication.showTextToast("切换为空气质量系统成功");
//		}
//		else {
//			PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", true);
//			AppConfig.systemType = AppConfig.SystemType.WatherType;
//			MyApplication.showTextToast("切换为地表水系统成功");
//		}
//		Intent intent=new Intent(MyApplication.mContext, MainActivity.class);
//		intent.putExtra("index",Index);
//		startActivity(intent);
//		this.finish();
        if (!AppConfig.isWatherSystem()) {
            PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", false);
            AppConfig.systemType = AppConfig.SystemType.AirType;
//            MyApplication.showTextToast("切换为空气质量系统成功");
        } else {
            PreferenceUtils.saveBoolean(MyApplication.mContext, "is_water", true);
            AppConfig.systemType = AppConfig.SystemType.WatherType;
//            MyApplication.showTextToast("切换为地表水系统成功");
        }
//		Intent resultIntent=	new Intent(getActivity().getApplicationContext(), MainActivity.class);
//		resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
////		startActivity(new Intent(mContext, MainActivity.class));
//		startActivity(resultIntent);
//		this.getActivity().finish();
//        ((MainActivity) this).RefreshView();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UpdateView();
        MyApplication.addActivity(this);

        PushManager.getInstance().initialize(this.getApplicationContext(), DemoPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), DemoIntentService.class);

    }


    public void UpdateView() {
        setTheme(MyApplication.currentThemeId);
        setContentView(R.layout.activity_main);
        if (false) {
//            fragments.add(new RealTimeParentWaterActivity());
//            fragments.add(new GisActivity());
//            fragments.add(new OnlineActivity());
//            fragments.add(new WaterAlarmInfo());
//            fragments.add(new WaterMonitorDataActivity());
//            fragments.add(new TrendActivity());

        } else {
            fragments.add(new NewAQIActivity());
            fragments.add(new TrendActivity());
            fragments.add(new AirMonitorDataActivity());
            fragments.add(new SuperActivity());
            fragments.add(new GisActivity());
            fragments.add(new YouLiangConditionActivity());
            fragments.add(new OnlineActivity());
            fragments.add(new AirAlarmInfo());
        }

        fragments.add(new MoreActivity());
        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content);
        if (false) {
            menuGroup = ((MenuGroup) findViewById(R.id.water_menuGroup));
            menuGroup.requestLayout();
            findViewById(R.id.water_menuGroup).setVisibility(View.VISIBLE);
            findViewById(R.id.air_menuGroup).setVisibility(View.GONE);
        } else {
            //气

            menuGroup = ((MenuGroup) findViewById(R.id.air_menuGroup));
            menuGroup.requestLayout();
            findViewById(R.id.air_menuGroup).setVisibility(View.VISIBLE);
            findViewById(R.id.water_menuGroup).setVisibility(View.GONE);


        }

        if (PreferenceUtils.getBoolean(this, "IS_LOGIN")) {
            String MenuInfo = "";
            if (!PreferenceUtils.getData(this, "MenuInfo").equals("")) {
                MenuInfo = PreferenceUtils.getData(this, "MenuInfo");
            }

            boolean[] b_menu = new boolean[13];
            for (int i = 0; i < MenuInfo.length(); i++) {
                if (MenuInfo.length() >= 13) {

                    if (String.valueOf(MenuInfo.charAt(i)).equals("1"))
                        b_menu[i] = true;
                    else
                        b_menu[i] = false;
                }


            }
            if (false) {
                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_WATER_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_WATER_ICONS[i]);
                    if (b_menu[i + 7])
                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.VISIBLE);
                    else
                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.GONE);
                }
            } else {

                //气

                for (int i = 0; i < menuGroup.getChildCount(); i++) {

                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_AIR_TITLES[i]);
                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_AIR_ICONS[i]);

                    if (b_menu[i])
                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.VISIBLE);
                    else
                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.GONE);

                }


            }
        } else {
            if (false) {
                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_WATER_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_WATER_ICONS[i]);


                }

            } else {

                for (int i = 0; i < menuGroup.getChildCount(); i++) {
                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_AIR_TITLES[i]);
                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_AIR_ICONS[i]);


                }
            }
            menuGroup.setVisibility(View.GONE);
        }
//        ((RefreshBaseActivity) fragments.get(0)).loadPort(false);
        menuGroup.setOnMenuItemClickListener(tabAdapter);
        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                selelctIndex = index;
                System.out.println("Extra---- " + index + " checked!!! ");
                System.out.println("Extra---- " + index + " checked!!! ");
                System.out.println("Extra---- " + index + " checked!!! ");
                System.out.println("Extra---- " + index + " checked!!! ");
            }
        });

        boolean startApp = PreferenceUtils.getBoolean(this, "start_app");
        if (startApp) {
            HttpClient.getJsonWithGetUrl(RequestAirActionName.GetVersionForPro, null, this, null); // 更新版本
        }
    }


//    public void RefreshView() {
//        setTheme(MyApplication.currentThemeId);
//        ((LinearLayout) findViewById(R.id.tab_content)).removeAllViews();
////		setContentView(R.layout.activity_main);
//        fragments.clear();
//        if (PreferenceUtils.getBoolean(this, "is_water")) {
////            fragments.add(new RealTimeParentWaterActivity());
////            fragments.add(new GisActivity());
////            fragments.add(new OnlineActivity());
////            fragments.add(new WaterAlarmInfo());
////            fragments.add(new WaterMonitorDataActivity());
////            fragments.add(new TrendActivity());
//
//        } else {
//            fragments.add(new NewAQIActivity());
//            fragments.add(new TrendActivity());
//            fragments.add(new AirMonitorDataActivity());
//            fragments.add(new SuperActivity());
//            fragments.add(new GisActivity());
//            fragments.add(new YouLiangConditionActivity());
//            fragments.add(new OnlineActivity());
//            fragments.add(new AirAlarmInfo());
//        }
//        fragments.add(new MoreActivity());
//        tabAdapter = new FragmentTabAdapter(this, fragments, R.id.tab_content);
//        if (AppConfig.isWatherSystem()) {
//            menuGroup = ((MenuGroup) findViewById(R.id.water_menuGroup));
//            findViewById(R.id.water_menuGroup).setVisibility(View.VISIBLE);
//            findViewById(R.id.air_menuGroup).setVisibility(View.GONE);
//        } else {
//            menuGroup = ((MenuGroup) findViewById(R.id.air_menuGroup));
//            findViewById(R.id.air_menuGroup).setVisibility(View.VISIBLE);
//            findViewById(R.id.water_menuGroup).setVisibility(View.GONE);
//        }
//
//        if (PreferenceUtils.getBoolean(this, "IS_LOGIN")) {
//            String MenuInfo = "";
//            if (!PreferenceUtils.getData(this, "MenuInfo").equals("")) {
//                MenuInfo = PreferenceUtils.getData(this, "MenuInfo");
//            }
//
//            boolean[] b_menu = new boolean[13];
//            for (int i = 0; i < MenuInfo.length(); i++) {
//                if (MenuInfo.length() >= 13) {
//
//                    if (String.valueOf(MenuInfo.charAt(i)).equals("1"))
//                        b_menu[i] = true;
//                    else
//                        b_menu[i] = false;
//                }
//
//
//            }
//            if (AppConfig.isWatherSystem()) {
//                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_WATER_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_WATER_ICONS[i]);
//                    if (b_menu[i + 7])
//                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.VISIBLE);
//                    else
//                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.GONE);
//                }
//            } else {
//                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_AIR_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_AIR_ICONS[i]);
//                    if (b_menu[i])
//                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.VISIBLE);
//                    else
//                        ((MenuButton) menuGroup.getChildAt(i)).setVisibility(View.GONE);
//                }
//            }
//        } else {
//            if (AppConfig.isWatherSystem()) {
//                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_WATER_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_WATER_ICONS[i]);
//
//
//                }
//
//            } else {
//
//                for (int i = 0; i < menuGroup.getChildCount(); i++) {
//                    ((MenuButton) menuGroup.getChildAt(i)).setText(TAB_AIR_TITLES[i]);
//                    ((MenuButton) menuGroup.getChildAt(i)).setICON(TAB_AIR_ICONS[i]);
//
//
//                }
//            }
//            menuGroup.setVisibility(View.GONE);
//        }
//        ((RefreshBaseActivity) fragments.get(0)).loadPort(false);
//        menuGroup.setOnMenuItemClickListener(tabAdapter);
//        tabAdapter.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
//            @Override
//            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
//                selelctIndex = index;
//                System.out.println("Extra---- " + index + " checked!!! ");
//            }
//        });
//
//        boolean startApp = PreferenceUtils.getBoolean(this, "start_app");
//        if (startApp) {
//            HttpClient.getJsonWithGetUrl(RequestAirActionName.GetVersionForPro, null, this, null); // 更新版本
//        }
//    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy() {


        super.onDestroy();
    }


    /***
     * 跳转到指定的站点界面
     *
     * @param port
     */
    public void navToRealTime(PortInfo port) {
        menuGroup.gotoIndex(0);
        ((RefreshBaseActivity) fragments.get(0)).selectPortinfo(port);
    }

    @Override
    public void onBackPressed() {
        Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("是否要退出环境监测客户端？").setPositiveButton("确认", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//                PreferenceUtils.saveBoolean(MainActivity.this, "IS_LOGIN", false);
//                PreferenceUtils.saveBoolean(MainActivity.this, "LOGIN_KEY", false);
//                PreferenceUtils.setData(MainActivity.this, "MenuInfo", "");
//                finish();
                MyApplication.finishAllActivity();
//                MainActivity.this.finish();
//                try {
//                    System.exit(0);
//                } catch (Exception e) {
//                    Log.i("scj", e.getMessage());
//                }


            }
        }).setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        alertDialog.show();
    }


    @Override
    public void requestSuccess(HttpResponse resData) {
        if (resData.getUri().equals(RequestAirActionName.GetVersionForPro)) {
            PreferenceUtils.saveBoolean(this, "start_app", false);
            final VersionModel versionModel = new VersionModel();
            SystemUtil.getVersion(this);
            try {
                versionModel.parse(resData.getJson());
                if (SystemUtil.versionCode < versionModel.verCode) { // 有新版本
                    AlertDialog.Builder newVersionDialog = new AlertDialog.Builder(this);
                    newVersionDialog.setTitle("版本更新");
                    newVersionDialog.setMessage("当前版本：" + SystemUtil.versionName + ",发现新版本：" + versionModel.verName + "。\n更新内容：" + versionModel.verDesc + "。\n是否更新版本？");
                    newVersionDialog.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            paramDialogInterface.dismiss();
                            DownloadDialog downloadDialog = new DownloadDialog(MainActivity.this, versionModel.verPath);
                            downloadDialog.setDownloadListener(MainActivity.this);
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
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {

    }

    /***
     * 下载
     */
    @Override
    public void endDownload(String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
        startActivity(intent);
    }

//	@Override
//	public void finish() {
//
//		menuGroup = null;
//		//内存泄漏
////	    fragments.get(0).mDB.close();
////	    fragments.clear();
//		super.finish();
//	}

    @Override
    public void startDownload() {
    }
}
