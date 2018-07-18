package com.sinoyd.environmentNT;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.sinoyd.environmentNT.activity.CrashHandler;
import com.sinoyd.environmentNT.model.PortInfo;
import com.sinoyd.environmentNT.reflect.StyleController;
import com.sinoyd.environmentNT.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.watcher.RefWatcher;

/**
 * 全局程序变量 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：MyApplication
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyApplication extends Application {
    public static final String DATABASE_NAME = "environment.db";
    /**
     * 数据库版本
     **/
    public static final int DATABASE_VERSION = 100;
    /**
     * 当前Context
     **/
    public static Context mContext;
    /**
     * 全局程序变量初始化
     **/
    public static MyApplication mApplication;
    /**
     * 弹出提示
     **/
    private static Toast mToast;
    /**
     * 获取输入法权限
     **/
    private static InputMethodManager mInputMethodManager;
    /**
     * 所有Activity集合,用户资源回收
     **/
    private static List<Activity> activityList;
    /**
     * 百度地图控制引擎
     **/
    public static BMapManager mBMapManager = null;
    /**
     * 当前水站点信息
     **/
    public static PortInfo currentWaterPortInfo;
    /**
     * 当前气站点信息
     **/
    public static PortInfo currentAirPortInfo;
    /**
     * 当前使用主题
     **/
    public static int currentThemeId = R.style.Theme_Default;
    /**
     * 当前气因子列表
     **/
    public static List<String> factorAirList = new ArrayList<String>();
    /**
     * 当前水因子列表
     **/
    public static List<String> factorWaterList = new ArrayList<String>();
    /**
     * 当前Activity
     **/
//	内存泄漏
    public static Context activityContext;

    public static String UserGuid;
    /**
     * 数据库存储统一接口
     */
    public static DbUtils mDB;

    public static HttpUtils http;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApplication = this;
//        int index = PreferenceUtils.getInteger(this, AppConfig.PageBg.THEME_INDEX_KEY);
//        if (index >= PageBg.THEME_ARRAYS.length) {
//            index = 0;
//        }
//		refWatcher = LeakCanary.install(this);
//        MyApplication.currentThemeId = AppConfig.PageBg.THEME_ARRAYS[index];
        StyleController.setThemeStyle(this, MyApplication.currentThemeId);
        activityList = new ArrayList<Activity>();
        SystemUtil.getVersion(this);

        if (mDB == null) {
            mDB = DbUtils.create(this, MyApplication.DATABASE_NAME, MyApplication.DATABASE_VERSION, null);
        }
        http = new HttpUtils();
        mContext = this;
        CrashHandler.getInstance().init(mContext);
        SDKInitializer.initialize(this);
        // 加载系统默认设置，字体不随用户设置变化
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        //初始化ImageLoader
        ImageLoaderConfiguration configg = new ImageLoaderConfiguration.Builder(this)
                .threadPoolSize(3) // 线程池的大小 默认3
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程优先级
                .denyCacheImageMultipleSizesInMemory() // 当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)// 设置缓存的最大字节
                .tasksProcessingOrder(QueueProcessingType.LIFO)//设置图片下载和显示的工作队列排序
                .defaultDisplayImageOptions

                        (DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000))// connectTimeout 超时时间
                .build();// 开始构建
        ImageLoader.getInstance().init(configg);// 全局初始化此配置


    }


//	public static RefWatcher getRefWatcher(Context context) {
//		MyApplication application = (MyApplication) context.getApplicationContext();
//	    return application.refWatcher;
//	  }

//	  private RefWatcher refWatcher;

    /**
     * 设置背景
     *
     * @param view
     * @param imgId
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    @SuppressWarnings("deprecation")
    public static void setBackage(View view, int imgId) {
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), imgId);
        view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        //内存泄漏
        bitmap.recycle();
        bitmap = null;
    }

    /**
     * 获取输入法权限
     *
     * @return
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public static InputMethodManager getInputMethodManager() {
        if (null == mInputMethodManager) {
            mInputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return mInputMethodManager;
    }

    /**
     * 增加每一个Activity
     *
     * @param activity
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    /**
     * 移除每一个Activity
     *
     * @param activityClass
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public static void removewActivity(Class<?> activityClass) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().getName().equals(activityClass.getName())) {
                Activity activity = activityList.get(i);
                if (activity != null && (!activity.isFinishing())) {
                    activityList.get(i).finish();
                }
            }
        }
    }

    /**
     * 结束所有Activity
     *
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public static void finishAllActivity() {
        for (int i = 0, size = activityList.size(); i < size; i++) {
            if (null != activityList.get(i)) {
                activityList.get(i).finish();
            }
        }
//        activityList.clear();
//        System.exit(0);
    }

    /**
     * 获取所有Activity
     *
     * @return
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public List<Activity> getActivityList() {
        return activityList;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    /**
     * 显示 提示框的方法
     *
     * @param msg
     */
    public static void showTextToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mApplication.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }


    public static String GetMetaData(String Name) {

        ApplicationInfo appInfo = null;
        try {
            appInfo = mContext.getPackageManager()
                    .getApplicationInfo(mContext.getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String msg = appInfo.metaData.getString("data_Name");
        return msg;
    }


}
