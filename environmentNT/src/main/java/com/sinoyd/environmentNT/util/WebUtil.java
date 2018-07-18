package com.sinoyd.environmentNT.util;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;

/***
 * 网络工具类
 * 
 * @author smz
 * 
 */
public class WebUtil {

	private static HttpUtils http = new HttpUtils();

	public void onLoading(long total, long current, boolean isUploading) {

	}

	public void onSuccess(ResponseInfo<String> responseInfo) {

	}

	public void onSuccessFile(ResponseInfo<File> responseInfo) {

	}


	public void onStart()
	{

	}
	public void onFailure(HttpException error, String msg)
	{

	}

	public void GetMessage(String HttpUrl,RequestParams params)
	{

		http.send(HttpRequest.HttpMethod.GET,
				HttpUrl,
				new RequestCallBack<String>(){
					@Override
					public void onLoading(long total, long current, boolean isUploading) {
						onLoading(  total,   current,   isUploading);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						onSuccess( responseInfo);
					}

					@Override
					public void onStart() {
						onStart();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						onFailure( error,  msg);
					}
				});
	}

	public void PostMessage(String HttpUrl,RequestParams params){
//		RequestParams params = new RequestParams();
//		params.addHeader("name", "value");
//		params.addQueryStringParameter("name", "value");
//
//// 只包含字符串参数时默认使用BodyParamsEntity，
//// 类似于UrlEncodedFormEntity（"application/x-www-form-urlencoded"）。
//		params.addBodyParameter("name", "value");
//
//// 加入文件参数后默认使用MultipartEntity（"multipart/form-data"），
//// 如需"multipart/related"，xUtils中提供的MultipartEntity支持设置subType为"related"。
//// 使用params.setBodyEntity(httpEntity)可设置更多类型的HttpEntity（如：
//// MultipartEntity,BodyParamsEntity,FileUploadEntity,InputStreamUploadEntity,StringEntity）。
//// 例如发送json参数：params.setBodyEntity(new StringEntity(jsonStr,charset));
//		params.addBodyParameter("file", new File("path"));



		http.send(HttpRequest.HttpMethod.POST,
				HttpUrl,
				params,
				new RequestCallBack<String>() {

					@Override
					public void onStart() {
						onStart();
					}

					@Override
					public void onLoading(long total, long current, boolean isUploading) {
						onLoading(  total,   current,   isUploading);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						onSuccess( responseInfo);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						onFailure( error,  msg);
					}
				});

	}



	public void GetFile(String HttpUrl,RequestParams params)
	{
		http.send(HttpRequest.HttpMethod.GET,
				HttpUrl,
				params,
				new RequestCallBack<File>() {

					@Override
					public void onStart() {
						onStart();
					}

					@Override
					public void onLoading(long total, long current, boolean isUploading) {
						onLoading( total,  current,  isUploading);
					}

					@Override
					public void onSuccess(ResponseInfo<File> responseInfo) {
						onSuccessFile( responseInfo);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						onFailure( error, msg);
					}
				});

	}
}
