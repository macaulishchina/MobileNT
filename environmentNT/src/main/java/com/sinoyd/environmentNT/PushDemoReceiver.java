package com.sinoyd.environmentNT;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushReceiver;
import com.sinoyd.environmentNT.activity.MainActivity;
import com.sinoyd.environmentNT.util.PreferenceUtils;

import java.util.List;


@SuppressLint("NewApi")
public class PushDemoReceiver extends PushReceiver {
    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView ==
     * null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("GetuiSdkDemo", "进入onReceive");
        Bundle bundle = null;
        try {
            bundle = intent.getExtras();
        } catch (Exception e) {
            Log.i("GetuiSdkDemo", e.getMessage());
        }
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                Log.i("GetuiSdkDemo", "payload   " + payload.toString());
                String taskid = bundle.getString("taskid");
                Log.i("GetuiSdkDemo", "taskid   " + taskid);
                String messageid = bundle.getString("messageid");
                Log.i("GetuiSdkDemo", "messageid   " + messageid);
                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                // boolean result =
                // PushManager.getInstance().sendFeedbackMessage(context, taskid,
                // messageid, 90001);
                String SysType = "";
                String Title = "";
                String Text = "";
                String PushTime = "";
                if (payload != null) {
                    String data = new String(payload);
                    // try {
                    // JSONObject object=new JSONObject(data);
                    //
                    // SysType=object.getString("SysType");
                    // Title=object.getString("Title");
                    // Text=object.getString("Text");
                    // PushTime=object.getString("PushTime");
                    //
                    // }
                    // catch (JSONException e) {
                    // e.printStackTrace();
                    // }
                    if (data.indexOf("【地表水报警】") > -1) {
                        SysType = "WATER";
                        Title = "【地表水报警】";
                        Text = data;
                        // PushTime=object.getString("PushTime");
                    }
                    if (data.indexOf("【空气报警】") > -1) {
                        SysType = "AIR";
                        Title = "【空气报警】";
                        Text = data;
                    }
                    Log.d("GetuiSdkDemo", "receiver payload : " + data);
                    ActivityManager am = (ActivityManager) context
                            .getSystemService(Context.ACTIVITY_SERVICE);
                    List<RunningTaskInfo> list = am.getRunningTasks(100);
                    boolean isAppRunning = false;
                    String MY_PKG_NAME = "com.sinoyd.environmentTaiCang";
                    for (RunningTaskInfo info : list) {
                        if (info.topActivity.getPackageName().equals(MY_PKG_NAME)
                                || info.baseActivity.getPackageName().equals(
                                MY_PKG_NAME)) {
                            isAppRunning = true;
                            Log.i("GetuiSdkDemo", info.topActivity.getPackageName()
                                    + " info.baseActivity.getPackageName()="
                                    + info.baseActivity.getPackageName());
                            break;
                        }
                    }
                    NotificationManager manager = (NotificationManager) context
                            .getSystemService(Context.NOTIFICATION_SERVICE);
                    PendingIntent pendingIntent3 = null;
                    // Intent newintent = new Intent(context,
                    // com.sinoyd.environmentwx.activity.MainActivity.class);//代表fragment所绑定的activity，这个需要写全路径
                    if (isAppRunning) {
                        Intent newintent = new Intent(context, MainActivity.class);// 代表fragment所绑定的activity，这个需要写全路径

                        if (android.os.Build.VERSION.SDK_INT >= 12) {
                            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);// 3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                        }
                        // if (!ClientAPI.getInstance().isDocInited()) {
                        // Intent intentGCM = new
                        // Intent(FragmentManagerActivity.this,
                        // LoginActivity.class);
                        // startActivity(intentGCM);
                        // }

                        if (PreferenceUtils.getData(context, "LoginId").toString()
                                .toLowerCase().equals("taicangapp")) {
                            // intent.putExtra("message",
                            // "12");//传递参数，然后根据参数进行判断需要跳转的fragment界面
                        } else {
                            newintent.putExtra("SysType", SysType);// 传递参数，然后根据参数进行判断需要跳转的fragment界面
                            pendingIntent3 = PendingIntent.getActivity(context, 0, newintent, PendingIntent.FLAG_UPDATE_CURRENT);

                            // 通过Notification.Builder来创建通知，注意API Level
                            // API16之后才支持
                            Notification notify3 = new Notification.Builder(context)
                                    .setSmallIcon(R.drawable.logo)
                                    .setTicker("您有新短消息，请注意查收！")
                                    .setContentTitle(Title).setContentText(Text)
                                    .setContentIntent(pendingIntent3).setNumber(1)
                                    .build(); // 需要注意build()是在API
                            // level16及之后增加的，API11可以使用getNotificatin()来替代
                            notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                            manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                            // if (GetuiSdkDemoActivity.tLogView != null) {
                            // GetuiSdkDemoActivity.tLogView.append(data + "\n");
                        }
                    } else {
                        Intent newintent = new Intent(context, MainActivity.class);// 代表fragment所绑定的activity，这个需要写全路径
                        if (android.os.Build.VERSION.SDK_INT >= 12) {
                            intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);// 3.1以后的版本需要设置Intent.FLAG_INCLUDE_STOPPED_PACKAGES
                        }
                        // if (!ClientAPI.getInstance().isDocInited()) {
                        // Intent intentGCM = new
                        // Intent(FragmentManagerActivity.this,
                        // LoginActivity.class);
                        // startActivity(intentGCM);
                        // }
                        if (PreferenceUtils.getData(context, "LoginId").toString()
                                .toLowerCase().equals("taicangapp")) {
                            // intent.putExtra("message",
                            // "12");//传递参数，然后根据参数进行判断需要跳转的fragment界面
                        } else {
                            newintent.putExtra("SysType", SysType);// 传递参数，然后根据参数进行判断需要跳转的fragment界面
                            pendingIntent3 = PendingIntent.getActivity(context, 0,
                                    newintent, PendingIntent.FLAG_UPDATE_CURRENT);
                            // 通过Notification.Builder来创建通知，注意API Level
                            // API16之后才支持
                            Notification notify3 = new Notification.Builder(context)
                                    .setSmallIcon(R.drawable.logo)
                                    .setTicker("您有新短消息，请注意查收！")
                                    .setContentTitle(Title).setContentText(Text)
                                    .setContentIntent(pendingIntent3).setNumber(1)
                                    .build(); // 需要注意build()是在API
                            // level16及之后增加的，API11可以使用getNotificatin()来替代
                            notify3.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
                            manager.notify(1, notify3);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
                            // if (GetuiSdkDemoActivity.tLogView != null) {
                            // GetuiSdkDemoActivity.tLogView.append(data + "\n");
                        }
                    }
                    // }
                }
                break;
            case PushConsts.GET_CLIENTID:
                break;
            case PushConsts.GET_SDKONLINESTATE:
                break;
            case PushConsts.SET_TAG_RESULT:
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                break;
            default:
                break;
        }
    }
}
