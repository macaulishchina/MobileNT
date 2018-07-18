package com.sinoyd.environmentNT.http;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.dialog.LoadDialog;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.util.DialogUtils;
import com.sinoyd.environmentNT.util.NetworkUtil;

/**
 * Http工具类 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpClient


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HttpClient {
	public static final String FILE_KEY = "_file_name_";
	private final static int RESPONSE_TYPE_TEXT = 0;
	private final static int RESPONSE_TYPE_JSON = 1;
	private final static int RESPONSE_TYPE_XML = 2;
	/** 返回流数据 */
	public final static int RESPONSE_TYPE_INPUTSTREAM = RESPONSE_TYPE_XML + 1;
	public static final String TAG = "action";
	public final static String baseURL = com.sinoyd.environmentNT.AppConfig.SERVER_URL_PREFIX;
	private static DefaultHttpClient client;
	private static BasicHttpContext httpContext;
	private static final int CONN_TIMEOUT = 40 * 1000;
	private static final int RECV_TIMEOUT = 80 * 1000;
	private static final int POOL_TIMEOUT = 10 * 1000;
	private static final String CHARSET = HTTP.UTF_8;
	private static final int PER_ROUTE = 100;
	private static final int MAX_CONNECT = 100;
	static {
		BasicHttpParams params = new BasicHttpParams();
		// http协议参数
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		 
		// http连接参数
		HttpConnectionParams.setSoTimeout(params, RECV_TIMEOUT);
		HttpConnectionParams.setConnectionTimeout(params, CONN_TIMEOUT);
		 
		// 注册http/https scheme
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		// Conmanger参数设置
		ConnManagerParams.setMaxTotalConnections(params, MAX_CONNECT);
		ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(PER_ROUTE));
		ConnManagerParams.setTimeout(params, POOL_TIMEOUT);
		 
		// 实例化ClientManager
		ThreadSafeClientConnManager connManager = new ThreadSafeClientConnManager(params, schemeRegistry);
		client = new DefaultHttpClient(connManager, params);
		CookieStore cookieStore = new BasicCookieStore();
		client.setCookieStore(cookieStore);
		httpContext = new BasicHttpContext();
		 
	}

	public static void getXMLWithPostUrl(String uri, HashMap datas, HttpRequestActivity callback, String showHUD) {
		HttpClient.postURL(uri, HttpClient.RESPONSE_TYPE_XML, datas, callback, showHUD);
	}

	public static void getJsonWithPostUrl(String uri, HashMap datas, HttpRequestActivity callback, String showHUD) {
		HttpClient.postURL(uri, HttpClient.RESPONSE_TYPE_JSON, datas, callback, showHUD);
	}

	public static void getJsonWithGetUrl(String uri, HashMap datas, HttpListener callback, String showHUD) {
		HttpClient.requestURLByGet(uri, HttpClient.RESPONSE_TYPE_JSON, datas, callback, showHUD);
	}

	public static void getTextWithPostUrl(String uri, HashMap datas, HttpRequestActivity callback, String showHUD) {
		HttpClient.postURL(uri, HttpClient.RESPONSE_TYPE_TEXT, datas, callback, showHUD);
	}

	public static void getInputstreamWithPostUrl(String uri, HashMap datas, HttpRequestActivity callback, String showHUD) {
		HttpClient.postURL(uri, HttpClient.RESPONSE_TYPE_INPUTSTREAM, datas, callback, showHUD);
	}

	/**
	 * 文件上传
	 */
	public static void getJsonWithPostUrlForFile(String uri, HashMap<String, String> datas, File file, HttpRequestActivity callback, String showHUD) {
		try {
			HttpClient.uploadSubmit(uri, datas, file, callback, showHUD);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 文件上传
	 */
	public static void getJsonWithPostUrlForFiles(String uri, HashMap<String, String> datas, HashMap<String, String> files, HttpRequestActivity callback, String showHUD) {
		try {
			HttpClient.uploadSubmit(uri, datas, files, callback, showHUD);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 提交参数里有文件的数据
	 * 
	 * @param url 服务器地址
	 * @param param 参数
	 * @return 服务器返回结果
	 * @throws Exception
	 */
	public static void uploadSubmit(String uri, HashMap<String, String> datas, File file, HttpRequestActivity callback, String showHUD) throws Exception {
		String url = baseURL + (uri == null ? "" : uri) + "?" + HttpUtils.getStringByMap(datas);
		Log.i(TAG, "url=" + url);
		HttpPost post = new HttpPost(url);
		// 添加文件参数
		String fileKey = "pic"; // 默认上传的key名称
		if (datas.containsKey(FILE_KEY)) {
			fileKey = datas.get(FILE_KEY);
			datas.remove(FILE_KEY);
		}
		try {
			if (file != null && file.exists()) { // 是否包含对文件的操作
				MultipartEntity entity = new MultipartEntity();
				entity.addPart(fileKey, new FileBody(file));
				post.setEntity(entity);
			}
		}
		catch (Exception e) { // 异常处理
			HttpResponse resData = new HttpResponse();
			resData.setHttpStatus(0000);
			HttpClient.executeCallBack(-1, HttpClient.RESPONSE_TYPE_JSON, resData, callback, null);
			e.printStackTrace();
		}
		String action = "";
		HttpClient.asyncSendRquest((uri == null ? "" : uri), post, action, HttpClient.RESPONSE_TYPE_JSON, callback, showHUD);
	}

	public static void uploadSubmit(String uri, HashMap<String, String> datas, HashMap<String, String> files, HttpRequestActivity callback, String showHUD) throws Exception {
		String tag = datas.get(TAG);
		String url = baseURL + uri;
		if (uri.startsWith("http://")) { // 对url进行校验
			url = uri + "?" + HttpUtils.getStringByMap(datas);
			;
		}
		Log.i(TAG, "url=" + url);
		HttpPost post = new HttpPost(url);
		try {
			MultipartEntity entity = new MultipartEntity();
			Iterator<String> stringKeys = datas.keySet().iterator();
			String stringKey = null;
			while (stringKeys.hasNext()) { // 加入参数到http捂手中
				stringKey = stringKeys.next();
				Log.i(TAG, stringKey + "=" + datas.get(stringKey));
				entity.addPart(stringKey, new StringBody(datas.get(stringKey), Charset.defaultCharset()));
			}
			if (files != null && files.size() > 0) { // 如果有文件 ，上传文件内容
				Iterator<String> fileKeys = files.keySet().iterator();
				String fileKey = null;
				while (fileKeys.hasNext()) {
					fileKey = fileKeys.next();
					entity.addPart(fileKey, new FileBody(new File(files.get(fileKey))));
				}
				post.setEntity(entity);
			}
		}
		catch (Exception e) { // 出现异常了
			HttpResponse resData = new HttpResponse();
			resData.setHttpStatus(0000);
			HttpClient.executeCallBack(-1, HttpClient.RESPONSE_TYPE_JSON, resData, callback, null);
			e.printStackTrace();
		}
		String action = "";
		HttpClient.asyncSendRquest((uri == null ? "" : uri), post, action, HttpClient.RESPONSE_TYPE_JSON, callback, showHUD, tag);
	}

	/***
	 * post发送数据
	 * 
	 * @param uri
	 * @param responseType
	 * @param datas
	 * @param callback
	 * @param showHUD
	 */
	public static void postURL(String uri, int responseType, HashMap datas, HttpRequestActivity callback, String showHUD) {
		String tag = null;
		if (datas.containsKey(TAG)) {
			tag = (String) datas.get(TAG);
			// datas.remove(TAG);
		}
		String url = baseURL + (uri == null ? "" : uri) + (tag == null ? "" : "?action=" + tag);
		Log.d("http", "url=" + url);
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(HttpUtils.getNamePairsByMap(datas), "utf-8"));
		}
		catch (UnsupportedEncodingException e) { // 出现异常
			HttpResponse resData = new HttpResponse();
			resData.setHttpStatus(0000);
			HttpClient.executeCallBack(-1, responseType, resData, callback, null);
			e.printStackTrace();
		}
		String action = "";
		
		HttpClient.asyncSendRquest((uri == null ? "" : uri), post, action, responseType, callback, showHUD, tag);
	}

	/***
	 * get方式发送网络请求
	 * 
	 * @param uri
	 * @param responseType
	 * @param datas
	 * @param callback
	 * @param showHUD
	 */
	public static void requestURLByGet(String uri, int responseType, HashMap datas, HttpListener callback, String showHUD) {
		String tag = null;
		if (datas != null && datas.containsKey(TAG)) {
			tag = (String) datas.get(TAG);
		}
		String url;
		if (uri.startsWith("http://")) {
			url = uri + "?" + HttpUtils.getStringByMap(datas);
		}
		else {
			url = baseURL + (uri == null ? "" : uri) + "?" + HttpUtils.getStringByMap(datas);
		}
		Log.i("request_url", url);
		HttpGet get = new HttpGet(url);
		String action = "";
		HttpClient.asyncSendRquest((uri == null ? "" : uri), get, action, responseType, callback, showHUD, tag);
	}

	/***
	 * 异步发送数据
	 * 
	 * @param uri
	 * @param request
	 * @param action
	 * @param responseType
	 * @param callBack
	 * @param showHUD
	 * @param tag
	 */
	private static void asyncSendRquest(final String uri, final HttpUriRequest request, final String action, final int responseType, final HttpListener callBack, final String showHUD, final String tag) {
		if (NetworkUtil.getNetworkType() == NetworkUtil.NO_NET_CONNECT) {
			if (showHUD != null) {
				DialogUtils.showDialog(MyApplication.activityContext, "无网络连接，请检测网络设置");
			}
			return;
		}
		// 下载图片 或者检测版本升级 的话 不显示对话框
		final Dialog dialog = HttpClient.showDialog(MyApplication.activityContext, showHUD);
		HandlerThread thread = new HandlerThread("request");
		thread.start();
		Handler handler = new Handler(thread.getLooper()) {
			@Override
			public void handleMessage(Message msg) {
				HttpClient.sendRequest(uri, request, action, responseType, callBack, showHUD, tag, dialog);
			}
		};
		handler.sendEmptyMessage(0);
	}

	/***
	 * 异步发送数据
	 * 
	 * @param uri
	 * @param request
	 * @param action
	 * @param responseType
	 * @param callBack
	 * @param showHUD
	 * @param tag
	 */
	private static void asyncSendRquest(final String uri, final HttpUriRequest request, final String action, final int responseType, final HttpRequestActivity callBack, final String showHUD) {
		asyncSendRquest(uri, request, action, responseType, callBack, showHUD, null);
	}

	/***
	 * 发送数据
	 * 
	 * @param uri
	 * @param request
	 * @param action
	 * @param responseType
	 * @param callBack
	 * @param showHUD
	 * @param tag
	 */
	private static void sendRequest(String uri, HttpUriRequest request, String action, int responseType, final HttpListener callBack, String showHUD, String tag, Dialog dialog) {
		HttpResponse resData = new HttpResponse();
		try {
			// request.setHeader("Cookie",
			// "session_id="+PreferenceUtils.getData(MyApplication.mContext,
			// "cookie")+"");
			 
		
			org.apache.http.HttpResponse response = client.execute(request, httpContext);
			resData.setHttpStatus(response.getStatusLine().getStatusCode());
			resData.setUri(uri);
			 
			// resData.setAction(action);
			if (tag != null) {
				resData.setTag(tag);
			}
			if (response.getStatusLine().getStatusCode() != 200) {
				HttpClient.executeCallBack(-1, responseType, resData, callBack, dialog);
			}
			else {
				int result = 0;
				if (HttpClient.RESPONSE_TYPE_INPUTSTREAM == responseType) {
					resData.setInputStream(response.getEntity().getContent());
				}
				else {
					String reponseString = EntityUtils.toString(response.getEntity(), "UTF-8");
					Log.d("http", reponseString);
					switch (responseType) {
					case HttpClient.RESPONSE_TYPE_TEXT:
						resData.setText(reponseString);
						break;
					case HttpClient.RESPONSE_TYPE_JSON:
						JSONObject jsonObject = new JSONObject(reponseString);
						result = jsonObject.optInt("responsecode", 1);
						resData.setJson(jsonObject);
						break;
					case HttpClient.RESPONSE_TYPE_XML:
						XmlNodeData resXml = HttpClient.xmlStringToHashMap(reponseString);
						resData.setXml(resXml);
						break;
					}
				}
				HttpClient.executeCallBack(result, responseType, resData, callBack, dialog);
			}
		}
		catch (Exception e) { // 不分错误类型，统一捕获
			resData.setHttpError(e.getMessage());
			HttpClient.executeCallBack(-1, responseType, resData, callBack, dialog);
			e.printStackTrace();
		}
	}

	/***
	 * 执行处理
	 * 
	 * @param isSuccess
	 * @param responseType
	 * @param resData
	 * @param callBack
	 * @param dialog
	 */
	private static void executeCallBack(final int isSuccess, final int responseType, final HttpResponse resData, final HttpListener callBack, final Dialog dialog) {
		Handler handler = new Handler(Looper.getMainLooper()) {
			@Override
			public void handleMessage(Message msg) {
				HttpClient.hideDialog(dialog);
				if (isSuccess == 1) {
					if (responseType == RESPONSE_TYPE_JSON) {
					}
					callBack.requestSuccess(resData);
				}
				else {
					resData.setHttpStatus(isSuccess);
					callBack.requestFailed(resData);
				}
			}
		};
		handler.sendMessage(new Message());
	}

	// 解析xml成hashMap
	private static XmlNodeData xmlStringToHashMap(String responseString) throws ParserConfigurationException, IOException, SAXException {
		XmlNodeData hashMap = new XmlNodeData();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(responseString.getBytes()));
		NodeList nodeList = doc.getFirstChild().getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			HttpClient.paserNode(node, hashMap);
		}
		return hashMap;
	}

	/***
	 * 解析节点
	 * 
	 * @param pNode
	 * @param hashMap
	 */
	private static void paserNode(Node pNode, XmlNodeData hashMap) {
		NodeList nodeList = pNode.getChildNodes();
		if (nodeList.getLength() == 1 && nodeList.item(0).getNodeType() == Node.TEXT_NODE) {
			HttpClient.addNodeValue(hashMap, pNode.getNodeName(), nodeList.item(0).getNodeValue());
		}
		else {
			XmlNodeData inHashMap = new XmlNodeData();
			HttpClient.addNodeValue(hashMap, pNode.getNodeName(), inHashMap);
			for (int i = 0; i < nodeList.getLength(); i++) {
				HttpClient.paserNode(nodeList.item(i), inHashMap);
			}
		}
	}

	/***
	 * 添加一个节点
	 * 
	 * @param hashMap
	 * @param key
	 * @param value
	 */
	private static void addNodeValue(XmlNodeData hashMap, String key, Object value) {
		if (hashMap.get(key) == null) {
			hashMap.put(key, value);
		}
		else if ((hashMap.get(key)) instanceof XmlNodeArray) {
			((XmlNodeArray) hashMap.get(key)).add(value);
		}
		else {
			XmlNodeArray array = new XmlNodeArray();
			array.add(hashMap.get(key));
			array.add(value);
			hashMap.remove(key);
			hashMap.put(key, array);
		}
	}

	// 显示提示框
	private static Dialog showDialog(final Context callBack, String showHUD) {
		if (showHUD == null)
			return null;
		// ProgressDialog dialog = new ProgressDialog(callBack);
		// dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// dialog.setProgressDrawable(MyApplication.mContext.getResources().getDrawable(R.drawable.load_anim_image));
		// dialog.setCancelable(false);
		// dialog.setMessage(showHUD);
		// try {
		// dialog.show();
		// } catch (Exception e) {
		// dialog.dismiss();
		// }
		LoadDialog dialog = new LoadDialog(callBack, R.style.Theme_Dialog_Transparent);
		try {
			dialog.show();
		}
		catch (Exception e) {
		}
		return dialog;
	}

	// 关闭提示框
	private static void hideDialog(Dialog dialog) {
		if (dialog != null) {
			try {
				dialog.dismiss();
			}
			catch (Exception e) {
			}
		}
	}
}
