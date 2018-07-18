package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/26.
 */

public class GetOnlineInfosuper {


    private List<OnlineInfoBean> OnlineInfo;

    public List<OnlineInfoBean> getOnlineInfo() {
        return OnlineInfo;
    }

    public void setOnlineInfo(List<OnlineInfoBean> OnlineInfo) {
        this.OnlineInfo = OnlineInfo;
    }

    public static class OnlineInfoBean {
        /**
         * InstrumentName : 离子色谱仪
         * IsOnline : True
         * NetWorkInfo : 在线
         * LastestTime : 2017/9/26 9:00:00
         */

        private String InstrumentName;
        private String IsOnline;
        private String NetWorkInfo;
        private String LastestTime;

        public String getInstrumentName() {
            return InstrumentName;
        }

        public void setInstrumentName(String InstrumentName) {
            this.InstrumentName = InstrumentName;
        }

        public String getIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(String IsOnline) {
            this.IsOnline = IsOnline;
        }

        public String getNetWorkInfo() {
            return NetWorkInfo;
        }

        public void setNetWorkInfo(String NetWorkInfo) {
            this.NetWorkInfo = NetWorkInfo;
        }

        public String getLastestTime() {
            return LastestTime;
        }

        public void setLastestTime(String LastestTime) {
            this.LastestTime = LastestTime;
        }
    }
}
