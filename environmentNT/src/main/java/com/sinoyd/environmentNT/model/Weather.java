package com.sinoyd.environmentNT.model;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/3/2
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT.model
 */


public class Weather {

    /**
     * code : 0
     * data : {"city":{"cityId":284855,"counname":"中国","name":"崇川区","pname":"江苏省","timezone":"8"},"forecast":[{"conditionDay":"多云","conditionIdDay":"1","conditionIdNight":"7","conditionNight":"小雨","predictDate":"2018-03-02","tempDay":"11","tempNight":"3","updatetime":"2018-03-02 08:16:00","windDirDay":"东南风","windDirNight":"东南风","windLevelDay":"3-4","windLevelNight":"3-4"},{"conditionDay":"多云","conditionIdDay":"1","conditionIdNight":"2","conditionNight":"阴","predictDate":"2018-03-03","tempDay":"20","tempNight":"11","updatetime":"2018-03-02 08:16:00","windDirDay":"东南风","windDirNight":"东南风","windLevelDay":"3-4","windLevelNight":"3-4"},{"conditionDay":"小雨","conditionIdDay":"7","conditionIdNight":"7","conditionNight":"小雨","predictDate":"2018-03-04","tempDay":"25","tempNight":"8","updatetime":"2018-03-02 08:16:00","windDirDay":"南风","windDirNight":"北风","windLevelDay":"4-5","windLevelNight":"4-5"}]}
     * msg : success
     * rc : {"c":0,"p":"success"}
     */

    private int code;
    private DataBean data;
    private String msg;
    private RcBean rc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RcBean getRc() {
        return rc;
    }

    public void setRc(RcBean rc) {
        this.rc = rc;
    }

    public static class DataBean {
        /**
         * city : {"cityId":284855,"counname":"中国","name":"崇川区","pname":"江苏省","timezone":"8"}
         * forecast : [{"conditionDay":"多云","conditionIdDay":"1","conditionIdNight":"7","conditionNight":"小雨","predictDate":"2018-03-02","tempDay":"11","tempNight":"3","updatetime":"2018-03-02 08:16:00","windDirDay":"东南风","windDirNight":"东南风","windLevelDay":"3-4","windLevelNight":"3-4"},{"conditionDay":"多云","conditionIdDay":"1","conditionIdNight":"2","conditionNight":"阴","predictDate":"2018-03-03","tempDay":"20","tempNight":"11","updatetime":"2018-03-02 08:16:00","windDirDay":"东南风","windDirNight":"东南风","windLevelDay":"3-4","windLevelNight":"3-4"},{"conditionDay":"小雨","conditionIdDay":"7","conditionIdNight":"7","conditionNight":"小雨","predictDate":"2018-03-04","tempDay":"25","tempNight":"8","updatetime":"2018-03-02 08:16:00","windDirDay":"南风","windDirNight":"北风","windLevelDay":"4-5","windLevelNight":"4-5"}]
         */

        private CityBean city;
        private List<ForecastBean> forecast;

        public CityBean getCity() {
            return city;
        }

        public void setCity(CityBean city) {
            this.city = city;
        }

        public List<ForecastBean> getForecast() {
            return forecast;
        }

        public void setForecast(List<ForecastBean> forecast) {
            this.forecast = forecast;
        }

        public static class CityBean {
            /**
             * cityId : 284855
             * counname : 中国
             * name : 崇川区
             * pname : 江苏省
             * timezone : 8
             */

            private int cityId;
            private String counname;
            private String name;
            private String pname;
            private String timezone;

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getCounname() {
                return counname;
            }

            public void setCounname(String counname) {
                this.counname = counname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }
        }

        public static class ForecastBean {
            /**
             * conditionDay : 多云
             * conditionIdDay : 1
             * conditionIdNight : 7
             * conditionNight : 小雨
             * predictDate : 2018-03-02
             * tempDay : 11
             * tempNight : 3
             * updatetime : 2018-03-02 08:16:00
             * windDirDay : 东南风
             * windDirNight : 东南风
             * windLevelDay : 3-4
             * windLevelNight : 3-4
             */

            private String conditionDay;
            private String conditionIdDay;
            private String conditionIdNight;
            private String conditionNight;
            private String predictDate;
            private String tempDay;
            private String tempNight;
            private String updatetime;
            private String windDirDay;
            private String windDirNight;
            private String windLevelDay;
            private String windLevelNight;

            public String getConditionDay() {
                return conditionDay;
            }

            public void setConditionDay(String conditionDay) {
                this.conditionDay = conditionDay;
            }

            public String getConditionIdDay() {
                return conditionIdDay;
            }

            public void setConditionIdDay(String conditionIdDay) {
                this.conditionIdDay = conditionIdDay;
            }

            public String getConditionIdNight() {
                return conditionIdNight;
            }

            public void setConditionIdNight(String conditionIdNight) {
                this.conditionIdNight = conditionIdNight;
            }

            public String getConditionNight() {
                return conditionNight;
            }

            public void setConditionNight(String conditionNight) {
                this.conditionNight = conditionNight;
            }

            public String getPredictDate() {
                return predictDate;
            }

            public void setPredictDate(String predictDate) {
                this.predictDate = predictDate;
            }

            public String getTempDay() {
                return tempDay;
            }

            public void setTempDay(String tempDay) {
                this.tempDay = tempDay;
            }

            public String getTempNight() {
                return tempNight;
            }

            public void setTempNight(String tempNight) {
                this.tempNight = tempNight;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getWindDirDay() {
                return windDirDay;
            }

            public void setWindDirDay(String windDirDay) {
                this.windDirDay = windDirDay;
            }

            public String getWindDirNight() {
                return windDirNight;
            }

            public void setWindDirNight(String windDirNight) {
                this.windDirNight = windDirNight;
            }

            public String getWindLevelDay() {
                return windLevelDay;
            }

            public void setWindLevelDay(String windLevelDay) {
                this.windLevelDay = windLevelDay;
            }

            public String getWindLevelNight() {
                return windLevelNight;
            }

            public void setWindLevelNight(String windLevelNight) {
                this.windLevelNight = windLevelNight;
            }
        }
    }

    public static class RcBean {
        /**
         * c : 0
         * p : success
         */

        private int c;
        private String p;

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public String getP() {
            return p;
        }

        public void setP(String p) {
            this.p = p;
        }
    }
}
