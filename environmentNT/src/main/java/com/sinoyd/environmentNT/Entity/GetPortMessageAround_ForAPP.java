package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/26.
 */

public class GetPortMessageAround_ForAPP {


    private List<PortMessagesAroundBean> PortMessagesAround;

    public List<PortMessagesAroundBean> getPortMessagesAround() {
        return PortMessagesAround;
    }

    public void setPortMessagesAround(List<PortMessagesAroundBean> PortMessagesAround) {
        this.PortMessagesAround = PortMessagesAround;
    }

    public static class PortMessagesAroundBean {
        /**
         * PointId : 1
         * MonitoringPointName : 苏州市
         * DateTime : 2017-09-30 14:00
         * Value :
         * IAQI : 29
         * level : 优
         * PrimaryPollutant : --
         * X : 120.585
         * Y : 31.299
         */

        private String PointId;
        private String MonitoringPointName;
        private String DateTime;
        private String Value;
        private String IAQI;
        private String level;
        private String PrimaryPollutant;
        private String X;
        private String Y;

        public String getPointId() {
            return PointId;
        }

        public void setPointId(String PointId) {
            this.PointId = PointId;
        }

        public String getMonitoringPointName() {
            return MonitoringPointName;
        }

        public void setMonitoringPointName(String MonitoringPointName) {
            this.MonitoringPointName = MonitoringPointName;
        }

        public String getDateTime() {
            return DateTime;
        }

        public void setDateTime(String DateTime) {
            this.DateTime = DateTime;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }

        public String getIAQI() {
            return IAQI;
        }

        public void setIAQI(String IAQI) {
            this.IAQI = IAQI;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPrimaryPollutant() {
            return PrimaryPollutant;
        }

        public void setPrimaryPollutant(String PrimaryPollutant) {
            this.PrimaryPollutant = PrimaryPollutant;
        }

        public String getX() {
            return X;
        }

        public void setX(String X) {
            this.X = X;
        }

        public String getY() {
            return Y;
        }

        public void setY(String Y) {
            this.Y = Y;
        }
    }
}
