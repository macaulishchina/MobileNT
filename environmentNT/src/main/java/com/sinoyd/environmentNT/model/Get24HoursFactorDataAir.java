package com.sinoyd.environmentNT.model;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/3/2
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT.model
 */


public class Get24HoursFactorDataAir {

    private List<HoursFactorDataBean> HoursFactorData;

    public List<HoursFactorDataBean> getHoursFactorData() {
        return HoursFactorData;
    }

    public void setHoursFactorData(List<HoursFactorDataBean> HoursFactorData) {
        this.HoursFactorData = HoursFactorData;
    }

    public static class HoursFactorDataBean {
        /**
         * DateTime : 2018-03-01 09:00
         * value : 1.4
         */

        private String DateTime;
        private String value;

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
