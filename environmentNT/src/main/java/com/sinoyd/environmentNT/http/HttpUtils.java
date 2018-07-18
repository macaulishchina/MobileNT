package com.sinoyd.environmentNT.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * HttpUtils工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpUtils


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HttpUtils {
	/** HTTP结果状态 */
	public interface HttpState {
		/** 数据OK */
		int HTTP_OK = 1000;
		/** 网络异常 */
		int NET_ERROR = 2000;
	}

	private final static String encoding = "utf-8";
	/** 网络超时时长 */
	private final static int TIMEOUTTIME = 10 * 1000;

	public static HttpResult getTextResult(HttpURLConnection http) {
		HttpResult result = new HttpResult();
		InputStream in = null;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			http.setConnectTimeout(TIMEOUTTIME);
			http.setReadTimeout(5000);
			if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
				in = http.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
//				int count = 1024;
//				int res = -1;
//				char[] readChars = new char[count];
//				String temp = null;
//				do {
//					res = reader.read(readChars, 0, count);
//					if (res > 0) {
//						temp = new String(readChars, 0, res);
//						stringBuffer.append(temp);
//					}
//				}
//				while (res != -1);
				String temp = " ";
				while ((temp = reader.readLine()) != null){
					stringBuffer.append(temp);
				}
				result.jsonData = stringBuffer.toString();
				result.resultCode = HttpState.HTTP_OK;
			}
			else {
				result.resultCode = HttpState.NET_ERROR;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof SocketException || e instanceof ConnectException || e instanceof SocketTimeoutException) {
				result.resultCode = HttpState.NET_ERROR;
			}
		}
		finally {
			try {
				if (in != null)
					in.close();
			}
			catch (Exception e2) {
			}
			in = null;
			stringBuffer = null;
		}
		return result;
	}

	public static final String REQUEST_SUCCESS = "0000";
	/** 登陆超时 */
	public static final String ERROR_TMOUT_CODE = "5348";

	private HttpUtils() {
	}

	/*public static String getXMLByMap(Map<String, String> map) {
		String xml = "";
		if (map == null) {
			return xml;
		}
		Iterator<String> it = map.keySet().iterator();
		Element element = new DOMElement("");
		String key = "";
		Element chind = null;
		while (it.hasNext()) {
			key = it.next();
			chind = new DOMElement(key);
			chind.setText(map.get(key));
			element.add(chind);
		}
		xml = element.asXML();

		return xml;
	}*/
	public static List<NameValuePair> getNamePairsByMap(HashMap maps) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		Iterator<String> keys = maps.keySet().iterator();
		String key = null;
		while (keys.hasNext()) {
			key = keys.next();
			list.add(new BasicNameValuePair(key, maps.get(key).toString()));
		}
		return list;
	}

	public static String getStringByMap(Map<String, String> map) {
		StringBuffer str = new StringBuffer();
		if (map == null || map.isEmpty()) {
			return str.toString();
		}
		Iterator<String> it = map.keySet().iterator();
		String key = "";
		while (it.hasNext()) {
			key = it.next();
			 
			str.append(key).append("=").append(URLEncoder.encode(map.get(key))).append("&");
		}
		return str.toString().substring(0, str.length() - 1);
	}
}
