package com.sinoyd.environmentNT.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.sinoyd.environmentNT.MyApplication;

/***
 * 网络工具类
 * 
 * @author smz
 * 
 */
public class NetworkUtil {
	/** 没有可用网络 */
	public static final int NO_NET_CONNECT = -1;
	/** wap网络可用 */
	public static final int WAP_CONNECTED = 0;
	/** net网 可用 */
	public static final int NET_CONNECTED = 1;
	/** wifi网络可用 */
	public static final int WIFI_CONNECT = 2;
	/** 当前网络不是WAP */
	public static final int NO_WAP_NET = 3;
	private static ConnectivityManager mConnMgr = null;
	private static WifiManager wifiManager = null;

	/**
	 * 
	 * 
	 * @return -1: 没有可用网络; 0: wap 网络可用; 1：net 网络可用; 2：wifi 网络可用;
	 */
	public static int getNetworkType() {
		mConnMgr = (ConnectivityManager) MyApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		wifiManager = (WifiManager) MyApplication.mContext.getSystemService(Context.WIFI_SERVICE);
		int wifiState = wifiManager.getWifiState();
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		Log.d("network", "网络状态=" + wifiInfo.getNetworkId());
		if (wifiInfo.getNetworkId() != -1 && (wifiState == WifiManager.WIFI_STATE_ENABLED || wifiState == WifiManager.WIFI_STATE_ENABLING)) {
			return WIFI_CONNECT;
		}
		else {
			NetworkInfo netInfo = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (netInfo == null || !netInfo.isConnected() || netInfo.getExtraInfo() == null) {
				return NO_NET_CONNECT;
			}
			else {
				if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE && netInfo.getExtraInfo().toLowerCase().equals("cmwap")) {
					return WAP_CONNECTED;
				}
				else {
					return NET_CONNECTED;
				}
			}
		}
	}
}
