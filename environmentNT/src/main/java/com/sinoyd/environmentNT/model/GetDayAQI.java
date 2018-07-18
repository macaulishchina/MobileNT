package com.sinoyd.environmentNT.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 作者： scj
 * 创建时间： 2018/3/1
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT.model
 */


public class GetDayAQI {


    private List<PortHourAQIBean> PortHourAQI;

    public List<PortHourAQIBean> getPortHourAQI() {
        return PortHourAQI;
    }

    public void setPortHourAQI(List<PortHourAQIBean> PortHourAQI) {
        this.PortHourAQI = PortHourAQI;
    }

    public static class PortHourAQIBean {
        /**
         * PortId : 0
         * PortName : 南通市
         * DateTime : 2018-03-01 10:00
         * SO2_IAQI : 6
         * NO2_IAQI : 13
         * PM10_IAQI : 82
         * CO_IAQI : 13
         * O3_IAQI : 29
         * PM2.5_IAQI : 98
         * AQI : 98
         * PrimaryPollutant : PM2.5
         * Class : 良
         * Grade : 二级
         * RGBValue : #ffff00
         * HealthEffect : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
         * TakeStep : 极少数异常敏感人群应减少户外活动
         * PrimaryPollutantValue : 73
         */

        private String PortId;
        private String PortName;
        private String DateTime;
        private String SO2_IAQI;
        private String NO2_IAQI;
        private String PM10_IAQI;
        private String CO_IAQI;
        private String O3_IAQI;
        @SerializedName("PM2.5_IAQI")
        private String _$PM25_IAQI90; // FIXME check this code
        private String AQI;
        private String PrimaryPollutant;
        @SerializedName("Class")
        private String Classname;
        private String Grade;
        private String RGBValue;
        private String HealthEffect;
        private String TakeStep;
        private String PrimaryPollutantValue;

        public String getPortId() {
            return PortId;
        }

        public void setPortId(String PortId) {
            this.PortId = PortId;
        }

        public String getPortName() {
            return PortName;
        }

        public void setPortName(String PortName) {
            this.PortName = PortName;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getSO2_IAQI() {
            return SO2_IAQI;
        }

        public void setSO2_IAQI(String SO2_IAQI) {
            this.SO2_IAQI = SO2_IAQI;
        }

        public String getNO2_IAQI() {
            return NO2_IAQI;
        }

        public void setNO2_IAQI(String NO2_IAQI) {
            this.NO2_IAQI = NO2_IAQI;
        }

        public String getPM10_IAQI() {
            return PM10_IAQI;
        }

        public void setPM10_IAQI(String PM10_IAQI) {
            this.PM10_IAQI = PM10_IAQI;
        }

        public String getCO_IAQI() {
            return CO_IAQI;
        }

        public void setCO_IAQI(String CO_IAQI) {
            this.CO_IAQI = CO_IAQI;
        }

        public String getO3_IAQI() {
            return O3_IAQI;
        }

        public void setO3_IAQI(String O3_IAQI) {
            this.O3_IAQI = O3_IAQI;
        }

        public String get_$PM25_IAQI90() {
            return _$PM25_IAQI90;
        }

        public void set_$PM25_IAQI90(String _$PM25_IAQI90) {
            this._$PM25_IAQI90 = _$PM25_IAQI90;
        }

        public String getAQI() {
            return AQI;
        }

        public void setAQI(String AQI) {
            this.AQI = AQI;
        }

        public String getPrimaryPollutant() {
            return PrimaryPollutant;
        }

        public void setPrimaryPollutant(String PrimaryPollutant) {
            this.PrimaryPollutant = PrimaryPollutant;
        }

        public String getClassname() {
            return Classname;
        }

        public void setClassname(String Class) {
            this.Classname = Classname;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String Grade) {
            this.Grade = Grade;
        }

        public String getRGBValue() {
            return RGBValue;
        }

        public void setRGBValue(String RGBValue) {
            this.RGBValue = RGBValue;
        }

        public String getHealthEffect() {
            return HealthEffect;
        }

        public void setHealthEffect(String HealthEffect) {
            this.HealthEffect = HealthEffect;
        }

        public String getTakeStep() {
            return TakeStep;
        }

        public void setTakeStep(String TakeStep) {
            this.TakeStep = TakeStep;
        }

        public String getPrimaryPollutantValue() {
            return PrimaryPollutantValue;
        }

        public void setPrimaryPollutantValue(String PrimaryPollutantValue) {
            this.PrimaryPollutantValue = PrimaryPollutantValue;
        }
    }


}
