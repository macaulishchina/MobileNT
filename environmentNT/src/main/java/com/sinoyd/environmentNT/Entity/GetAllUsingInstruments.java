package com.sinoyd.environmentNT.Entity;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/6.
 */

public class GetAllUsingInstruments {


    private List<InstrumentDataBean> InstrumentData;

    public List<InstrumentDataBean> getInstrumentData() {
        return InstrumentData;
    }

    public void setInstrumentData(List<InstrumentDataBean> InstrumentData) {
        this.InstrumentData = InstrumentData;
    }

    public static class InstrumentDataBean {
        /**
         * RowGuid : 56dd6e9b-4c8f-4e67-a70f-b6a277cb44d7
         * InstrumentName : 黑碳分析仪
         */

        private String RowGuid;
        private String InstrumentName;

        public String getRowGuid() {
            return RowGuid;
        }

        public void setRowGuid(String RowGuid) {
            this.RowGuid = RowGuid;
        }

        public String getInstrumentName() {
            return InstrumentName;
        }

        public void setInstrumentName(String InstrumentName) {
            this.InstrumentName = InstrumentName;
        }
    }
}
