package com.sinoyd.environmentNT.http;

import java.io.InputStream;
import org.json.JSONObject;

/**
 * 响应的数据 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：HttpResponse


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class HttpResponse {
	private XmlNodeData xml;
	private int httpStatus;
	private String uri = "";
	private JSONObject json;
	private String text;
	private String action = "";
	private InputStream inputStream = null;
	private String tag; // 加入一个标志，用于获取到的流归属
	private String httpError;

	public HttpResponse() {
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public XmlNodeData getXml() {
		return xml;
	}

	public void setXml(XmlNodeData xml) {
		this.xml = xml;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getHttpError() {
		return httpError;
	}

	public void setHttpError(String httpError) {
		this.httpError = httpError;
	}
}
