package com.sinoyd.environmentNT.model;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/3/8
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT.model
 */


public class GetPrimaryPollutant {


    /**
     * reason : 成功的返回
     * data : [{"DateTime":"2018-03-07 14:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 15:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 16:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 17:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 18:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 19:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 20:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 21:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 22:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-07 23:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 00:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 01:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 02:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 03:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 04:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 05:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 06:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 07:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 08:00:00","Value":[{"factor":"PM2.5","factorValue":"42.000"}]},{"DateTime":"2018-03-08 09:00:00","Value":[{"factor":"PM2.5","factorValue":"36.000"}]},{"DateTime":"2018-03-08 10:00:00","Value":[{"factor":"","factorValue":""}]},{"DateTime":"2018-03-08 11:00:00","Value":[{"factor":"PM2.5","factorValue":"44.000"}]},{"DateTime":"2018-03-08 12:00:00","Value":[{"factor":"PM2.5","factorValue":"83.000"}]},{"DateTime":"2018-03-08 13:00:00","Value":[{"factor":"PM2.5","factorValue":"92.000"}]}]
     */

    private String reason;
    private List<DataBean> data;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * DateTime : 2018-03-07 14:00:00
         * Value : [{"factor":"","factorValue":""}]
         */

        private String DateTime;
        private List<ValueBean> Value;

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public List<ValueBean> getValue() {
            return Value;
        }

        public void setValue(List<ValueBean> Value) {
            this.Value = Value;
        }

        public static class ValueBean {
            /**
             * factor :
             * factorValue :
             */

            private String factor;
            private String factorValue;

            public String getFactor() {
                return factor;
            }

            public void setFactor(String factor) {
                this.factor = factor;
            }

            public String getFactorValue() {
                return factorValue;
            }

            public void setFactorValue(String factorValue) {
                this.factorValue = factorValue;
            }
        }
    }
}
