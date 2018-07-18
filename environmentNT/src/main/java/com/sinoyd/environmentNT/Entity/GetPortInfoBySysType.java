package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/7/21.
 */

public class GetPortInfoBySysType {


    private List<PortInfoBean> PortInfo;

    public List<PortInfoBean> getPortInfo() {
        return PortInfo;
    }

    public void setPortInfo(List<PortInfoBean> PortInfo) {
        this.PortInfo = PortInfo;
    }

    public static class PortInfoBean {
        /**
         * PortId : 0
         * PortName : 南通市
         * X : 0
         * Y : 0
         * PortType : 南通市
         * RegionType : 南通市
         * orderNumber : --
         * AQI : 29
         * PrimaryPollutant : --
         * Grade : 优
         */

        private String PortId;
        private String PortName;
        private String X;
        private String Y;
        private String PortType;
        private String RegionType;
        private String orderNumber;
        private String AQI;
        private String PrimaryPollutant;
        private String Grade;

        public PortInfoBean(String portId, String portName, String x, String y, String portType, String regionType, String orderNumber, String AQI, String primaryPollutant, String grade) {
            PortId = portId;
            PortName = portName;
            X = x;
            Y = y;
            PortType = portType;
            RegionType = regionType;
            this.orderNumber = orderNumber;
            this.AQI = AQI;
            PrimaryPollutant = primaryPollutant;
            Grade = grade;
        }

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

        public String getPortType() {
            return PortType;
        }

        public void setPortType(String PortType) {
            this.PortType = PortType;
        }

        public String getRegionType() {
            return RegionType;
        }

        public void setRegionType(String RegionType) {
            this.RegionType = RegionType;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
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

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String Grade) {
            this.Grade = Grade;
        }
    }
}
