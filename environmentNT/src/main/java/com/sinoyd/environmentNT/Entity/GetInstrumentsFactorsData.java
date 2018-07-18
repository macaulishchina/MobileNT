package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/7.
 */

public class GetInstrumentsFactorsData {


    private List<HistoryDataBean> HistoryData;

    public List<HistoryDataBean> getHistoryData() {
        return HistoryData;
    }

    public void setHistoryData(List<HistoryDataBean> HistoryData) {
        this.HistoryData = HistoryData;
    }

    public static class HistoryDataBean {
        /**
         * Tstamp : 2017/9/12 13:00:00
         * Value : [{"factor":"a04003","factorName":"太阳总辐射","value":"409.1246","flag":""},{"factor":"a51039","factorName":"紫外平均辐射","value":"22.1137","flag":""},{"factor":"a05041","factorName":"最大太阳辐射","value":"--","flag":""},{"factor":"a05040","factorName":"最大紫外辐射","value":"--","flag":""},{"factor":"a05024","factorName":"O\u2083","value":"105","flag":"B"}]
         */

        private String Tstamp;
        private List<ValueBean> Value;

        public String getTstamp() {
            return Tstamp;
        }

        public void setTstamp(String Tstamp) {
            this.Tstamp = Tstamp;
        }

        public List<ValueBean> getValue() {
            return Value;
        }

        public void setValue(List<ValueBean> Value) {
            this.Value = Value;
        }

        public static class ValueBean {
            /**
             * factor : a04003
             * factorName : 太阳总辐射
             * value : 409.1246
             * flag :
             */

            private String factor;
            private String factorName;
            private String value;
            private String flag;

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

            public String getFactorName() {
                return factorName;
            }

            public void setFactorName(String factorName) {
                this.factorName = factorName;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }
        }
    }
}
