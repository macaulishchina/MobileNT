package com.sinoyd.environmentNT.http;

import java.util.HashMap;

/**
 * 自定义字典对象 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：XmlNodeData


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class XmlNodeData extends HashMap<Object, Object> {
	/** 序列化 **/
	private static final long serialVersionUID = -474930809860388732L;

	public XmlNodeData() {
		super();
	}

	public String getText(String key) {
		return (String) this.get(key);
	}

	public XmlNodeArray getList(String key) {
		return (XmlNodeArray) this.get(key);
	}

	public XmlNodeData getMap(String key) {
		return (XmlNodeData) this.get(key);
	}
}