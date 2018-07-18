package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/7/7.
 */

public class JsonobjHistoryData {


    private List<HistoryDataBean> HistoryData;

    public List<HistoryDataBean> getHistoryData() {
        return HistoryData;
    }

    public void setHistoryData(List<HistoryDataBean> HistoryData) {
        this.HistoryData = HistoryData;
    }

    public static class HistoryDataBean {
        /**
         * DateTime : 07-07 13:00
         * Value : [{"factor":"PM2.5","value":"0.018","isExceeded":"False"},{"factor":"PM10","value":"0.039","isExceeded":"False"},{"factor":"CO","value":"0.5","isExceeded":"False"},{"factor":"O3","value":"0.136","isExceeded":"False"},{"factor":"SO2","value":"0.028","isExceeded":"False"},{"factor":"NO2","value":"0.019","isExceeded":"False"}]
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
             * factor : PM2.5
             * value : 0.018
             * isExceeded : False
             */

            private String factor;
            private String value;
            private Boolean isExceeded;

            private String measureUnit;
            public String getMeasureUnit() {
                return measureUnit;
            }

            public void setMeasureUnit(String measureUnit) {
                this.measureUnit = measureUnit;
            }



            public String getFactor() {
                return factor;
            }

            public void setFactor(String factor) {
                this.factor = factor;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public Boolean getIsExceeded() {
                return isExceeded;
            }

            public void setIsExceeded(Boolean isExceeded) {
                this.isExceeded = isExceeded;
            }
        }
    }
}
