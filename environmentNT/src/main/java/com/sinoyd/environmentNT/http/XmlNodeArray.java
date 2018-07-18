package com.sinoyd.environmentNT.http;

import java.util.ArrayList;

/**
 * 自定义ArrayList对象 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：XmlNodeArray


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class XmlNodeArray extends ArrayList<Object> {
	/** 序列化 **/
	private static final long serialVersionUID = 6623000096444218849L;

	public XmlNodeArray() {
		super();
	}

	public XmlNodeData getNode(int index) {
		return (XmlNodeData) this.get(index);
	}
}
