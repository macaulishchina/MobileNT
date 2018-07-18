package com.sinoyd.environmentNT;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.igexin.sdk.GTServiceManager;

import static android.content.ContentValues.TAG;

/**
 * 作者： scj
 * 创建时间： 2018/3/28
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT
 */


public class DemoPushService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        GTServiceManager.getInstance().onCreate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "GetuiSdkDemo" + "  onStartCommand startId=    " + startId);
        Log.i(TAG, "GetuiSdkDemo" + "  onStartCommand flags=    " + flags);
        return GTServiceManager.getInstance().onStartCommand(this, intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "GetuiSdkDemo" + "IBinder = ");

        return GTServiceManager.getInstance().onBind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GTServiceManager.getInstance().onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        GTServiceManager.getInstance().onLowMemory();
    }
}
