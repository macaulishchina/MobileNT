package com.sinoyd.environmentNT.model;

import com.sinoyd.environmentNT.json.JSONModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 在线情况 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：Online
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class Online extends JSONModel {


    public ArrayList<OnlineItem> values = new ArrayList<Online.OnlineItem>();

    public static class OnlineItem {
        /**
         * 站点名称
         **/
        public String name;
        /**
         * 状态
         **/
        public String status;
        /**
         * 记录条数
         **/
        public String recordcount;
        /**
         * 最近数据时间
         **/
        public String recentDataTime;
        /**
         * 密度
         **/
        public String density;

        public OnlineItem() {
        }

        public OnlineItem(JSONObject jsonObject) {
            this.name = jsonObject.optString("PortName");
            this.status = jsonObject.optString("IsOnline");
            this.recordcount = jsonObject.optString("RecordCount");
            this.recentDataTime = jsonObject.optString("LastestTime");
        }
    }

    @Override
    public void parse(JSONObject jb) throws Exception {
        JSONArray array = jb.optJSONArray("OnlineInfo");
        if (values != null && values.size() > 0) {
            values.clear();
        }
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                values.add(new OnlineItem(array.optJSONObject(i)));
            }
        }
    }

    @Override
    protected JSONObject putJSON() throws Exception {
        return null;
    }
}
