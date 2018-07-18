package com.sinoyd.environmentNT;

/**
 * 相关配置信息 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：AppConfig
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class AppConfig {
    /**
     * 是否需要换肤功能
     */
    public static final boolean HAS_CHANGE_FACE = false;
    /**
     * 百度地图的appkey
     **/
    static final String BAIDU_MAP_KEY = "PNDqzRIouVnqms207OsDDGZA";
    /**
     * 是否是发布的服务器地址
     **/
    private static final boolean RELEASE = true;
//	/** 正式版接口地址 **/
//	public static final String OFFICIAL_SERVER = "http://222.92.42.178:8083/KunShanAQIWS/KunShanAQI.asmx/";

    /** 正式版接口地址 *太仓*/
//	public static final String OFFICIAL_SERVER = "http://222.92.5.114:5030/MobileTC/AQI.asmx/";
    /**
     * 正式版接口地址 *南通
     */
    public static final String OFFICIAL_SERVER = "http://218.91.209.251:1117/NTWebServiceForMobile/AQI.asmx/";
    public static final String OFFICIAL_SERVER_SUPER = "http://218.91.209.251:1117/NTWebserviceForSuper/WebServiceForData.asmx/";
//	public static final String OFFICIAL_SERVER = "http://192.168.11.72/MobileTC/AQI.asmx/";

    /**
     * 测试版接口地址
     **/
    public static final String BETA_SERVER = "http://192.168.1.235/WebServiceForSZ/WebServiceForOutData.asmx/";
    /**
     * 接口地址前缀
     **/
    public static final String SERVER_URL_PREFIX = RELEASE ? OFFICIAL_SERVER : BETA_SERVER;
    public static final int PAGE_COUNT = 10;
    /**
     * 当前系统显示的类型
     */
    public static SystemType systemType = SystemType.WatherType;

    /**
     * 系统的类型
     */
    public static enum SystemType {
        WatherType, AirType
    }

    /**
     * 应用程序皮肤更换广播
     **/
    public static interface AppReceiverName {
        /**
         * 更新皮肤的广播
         **/
        public static final String FACE_RECEIVER_NAME = "com.sinoyd.environmentwj.update.face";
    }

    /***
     * 判断系统的类型
     *
     * @return
     */
    public static boolean isWatherSystem() {
        return systemType == SystemType.WatherType;
    }

    /**
     * 获取调用的地址
     *
     * @param pageName
     * @return
     */
    public static final String getUrlByPrefixPage(String pageName) {
        return SERVER_URL_PREFIX + pageName;
    }

//	public static class RequestAirActionName {
//		public static final String GetLoginInfo = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetLoginInfo";
//		/** 【气_GIS】获取所有点位小时空气质量状况 **/
//		public static final String GetLatestHourAQI = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetLatestHourAQI";
//		/** 【实况_气】获取首要污染物最新24小时浓度值 **/
//		public static final String Get24HoursFactorDataAir = "http://222.92.191.210:8083/AQIWS/AQI.asmx/Get24HoursFactorDataAir";
//		/** 【浓度】最新小时浓度值 **/
//		public static final String GetHourConcentration = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetHourConcentration";
//		/** 【在线情况】获取在线情况信息 **/
//		public static final String GetOnlineInfo = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetOnlineInfo";
//		/** 【气_优良天数】获取站点优良天数统计 **/
//		public static final String GetAirClassDayStruct = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetAirClassDayStruct";
//		/** 【气_优良天数】获取站点前N个月的优良天数统计 **/
//		public static final String GetAirClassMonthStruct = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetAirClassMonthStruct";
//		/** 【气_监测数据】获取时间区间内小时浓度值 **/
//		public static final String GetHourDataByDatetime = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetHourDataByDatetime";
//		/** 【水_监测数据】获取自动站水质监测数据 **/
//		public static final String GetWaterHistoryDatas = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetWaterHistoryDatas";
//		/** 【历史（专业版）】 天 **/
//		public static final String GetDayAQIByDatetime = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetDayAQIByDatetime";
//		/** 更新版本 */
//		public static final String GetVersionForPro = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetVersionForPro";
//		/** 获取所有站点 */
//		public static final String GetPortInfoBySysType = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetPortInfoBySysType";
//		/** 【空气实况】获取因子可选列表 **/
//		public static final String GetFactorList = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetFactorList";
//		/** 【趋势（公众版）】 N小时 **/
//		public static final String GetTopNHourAQI = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetTopNHourAQI";
//		/** 【趋势（公众版）】 N天 **/
//		public static final String GetTopNDayAQI = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetTopNDayAQI";
//		/** 【报警信息】查看报警信息 **/
//		public static final String GetAlarmInfo = SERVER_URL_PREFIX + "GetAlarmInfo";
//	}
//
//	/***
//	 * 获取水的接口action
//	 * 
//	 * @author smz 创建时间：2014-1-21上午12:33:41
//	 */
//	public static interface RequestWaterActionName {
//		/** 历史 专业版 */
//		public static final String GetDayWQByDatetime = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetDayWQByDatetime";
//		/** 【水_实时水质】获取实时水质的各个因子的水质等级情况 **/
//		public static final String GetLatestHourWQ = "http://222.92.191.210:8083/AQIWS/AQI.asmx/GetLatestHourWQ";
//		/** 【水_实时水质】获取实时水质的各个因子的水质浓度情况 **/
//		public static final String Get24HoursFactorDataWater = "http://222.92.191.210:8083/AQIWS/AQI.asmx/Get24HoursFactorDataWater";
//		/** 【报警信息】查看报警信息 **/
//		public static final String GetWaterAlarmInfo = "{\"AlarmInfo\":{\"IsSuccess\":\"true\",\"Data\":[{\"PortName\":\"大浦口\",\"AlarmTime\":\"04-14 09:00\",\"AlarmContent\":\"二氧化氮超出下限\"},{\"PortName\":\"大浦口\",\"AlarmTime\":\"04-15 13:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"大浦口\",\"AlarmTime\":\"04-15 05:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"大浦口\",\"AlarmTime\":\"04-15 00:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"沙渚水厂\",\"AlarmTime\":\"04-15 08:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"沙渚水厂\",\"AlarmTime\":\"04-15 01:00\",\"AlarmContent\":\"断线报警\"},{\"PortName\":\"沙渚水厂\",\"AlarmTime\":\"04-15 16:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"沙渚水厂\",\"AlarmTime\":\"04-15 14:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"沙渚水厂\",\"AlarmTime\":\"04-15 21:00\",\"AlarmContent\":\"PM10超上限\"},{\"PortName\":\"锡东水厂\",\"AlarmTime\":\"04-14 23:00\",\"AlarmContent\":\"PM2.5超出下限\"},{\"PortName\":\"锡东水厂\",\"AlarmTime\":\"04-14 10:00\",\"AlarmContent\":\"断线报警\"},{\"PortName\":\"锡东水厂\",\"AlarmTime\":\"04-14 14:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"观景台\",\"AlarmTime\":\"04-15 13:00\",\"AlarmContent\":\"PM2.5超上限\"},{\"PortName\":\"观景台\",\"AlarmTime\":\"04-15 09:00\",\"AlarmContent\":\"PM10超上限\"},{\"PortName\":\"观景台\",\"AlarmTime\":\"04-15 02:00\",\"AlarmContent\":\"PM10超上限8888888888888888888888888888888888888888888888888888888888888888888\"}]}}";
//	}


    public static class RequestAirActionName {

        public static final String GetPrimaryPollutant = SERVER_URL_PREFIX + "GetPrimaryPollutant";
        public static final String GetLoginInfo = SERVER_URL_PREFIX + "GetLoginInfo";
        public static final String GetLoginInfoNew = SERVER_URL_PREFIX + "GetLoginInfoNew";
        /**
         * 【气_GIS】获取所有点位小时空气质量状况
         **/
        public static final String GetLatestHourAQI = SERVER_URL_PREFIX + "GetLatestHourAQI";
        /**
         * 【实况_气】获取首要污染物最新24小时浓度值
         **/
        public static final String Get24HoursFactorDataAir = SERVER_URL_PREFIX + "Get24HoursFactorDataAir";

                /**临时使用下**/
//        public static final String Get24HoursFactorDataAir = SERVER_URL_PREFIX + "Get24HoursFactorDataAir_ForAndorid";


        /**
         * 【浓度】最新小时浓度值
         **/
        public static final String GetHourConcentration = SERVER_URL_PREFIX + "GetHourConcentration";
        /**
         * 【在线情况】获取在线情况信息
         **/
        public static final String GetOnlineInfo = SERVER_URL_PREFIX + "GetOnlineInfo";


        /**scj
         *2017-9-5  新增*/
        /**
         * 定义：获取所有启用的仪器菜单
         * <p>
         * <p>
         * GetAllUsingInstruments
         */

        public static final String GetAllUsingInstruments = OFFICIAL_SERVER_SUPER + "GetAllUsingInstruments";

        /***GetInstrumentsFactorsData*/
        public static final String GetInstrumentsFactorsData = OFFICIAL_SERVER_SUPER + "GetInstrumentsFactorsData";

        /**
         * GetParticlesizeOfSpectrometerData
         */
        public static final String GetParticlesizeOfSpectrometerData = OFFICIAL_SERVER_SUPER + "GetParticlesizeOfSpectrometerData";

         /**GetVocData*/

         public static final String GetVocData = OFFICIAL_SERVER_SUPER + "GetVocData";


        /**GetImageUrl*/

        public static final String GetImageUrl = OFFICIAL_SERVER_SUPER + "GetImageUrl";


        /**scj
         *2017-9-5  新增*/

        /**
         * 【气_优良天数】获取站点优良天数统计
         **/
        public static final String GetAirClassDayStruct = SERVER_URL_PREFIX + "GetAirClassDayStruct";
        /**
         * 【气_优良天数】获取站点前N个月的优良天数统计
         **/
        public static final String GetAirClassMonthStruct = SERVER_URL_PREFIX + "GetAirClassMonthStruct";
        /**
         * 【气_监测数据】获取时间区间内小时浓度值
         **/
        public static final String GetHourDataByDatetime = SERVER_URL_PREFIX + "GetHourDataByDatetime";
        /**
         * 【水_监测数据】获取自动站水质监测数据
         **/
        public static final String GetWaterHistoryDatas = SERVER_URL_PREFIX + "GetWaterHistoryDatas";
        /**
         * 【历史（专业版）】 天
         **/
        public static final String GetDayAQIByDatetime = SERVER_URL_PREFIX + "GetDayAQIByDatetime";
        /**
         * 更新版本
         */
        public static final String GetVersionForPro = SERVER_URL_PREFIX + "GetVersionForPro";
//		public static final String GetVersionForPro = "http://192.168.1.138/GetVersion/UpdateApk.asmx/HelloWorld";
        /**
         * 获取所有站点
         */
        public static final String GetPortInfoBySysType = SERVER_URL_PREFIX + "GetPortInfoBySysType";
        /**
         * 【空气实况】获取因子可选列表
         **/
        public static final String GetFactorList = SERVER_URL_PREFIX + "GetFactorList";
        /**
         * 【趋势（公众版）】 N小时
         **/
        public static final String GetTopNHourAQI = SERVER_URL_PREFIX + "GetTopNHourAQI";
                /**临时使用**/
//        public static final String GetTopNHourAQI = SERVER_URL_PREFIX + "GetTopNHourAQI_ForAndorid";
        /**
         * 【趋势（公众版）】 N天
         **/
        public static final String GetTopNDayAQI = SERVER_URL_PREFIX + "GetTopNDayAQI";
        /**
         * 【报警信息】查看报警信息
         **/
        public static final String GetAlarmInfo = SERVER_URL_PREFIX + "GetAlarmInfo";
        /**
         * 暂时固化  获取点位信息
         **/
        public static final String GetPOINT = "http://222.92.77.251:8083/V02WebServiceForOutSZ/WebServiceForOutData.asmx/GetMonitoringPointLocationJson";
        /**
         * 【水质日报数据
         **/
        public static final String GetWaterWeekReportData = SERVER_URL_PREFIX + "GetWaterWeekReportData";

        public static final String GetWeather = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=101190501";
    }

    /***
     * 获取水的接口action
     *
     * @author smz 创建时间：2014-1-21上午12:33:41
     */
    public static interface RequestWaterActionName {
        /**
         * 历史 专业版
         */
        public static final String GetDayWQByDatetime = SERVER_URL_PREFIX + "GetDayWQByDatetime";
        /**
         * 【水_实时水质】获取实时水质的各个因子的水质等级情况
         **/
        public static final String GetLatestHourWQ = SERVER_URL_PREFIX + "GetLatestHourWQ";
        /**
         * 【水_实时水质】获取实时水质的各个因子的水质浓度情况
         **/
        public static final String Get24HoursFactorDataWater = SERVER_URL_PREFIX + "Get24HoursFactorDataWater";
        /**
         * 【报警信息】查看报警信息
         **/
        public static final String GetWaterAlarmInfo = SERVER_URL_PREFIX + "GetAlarmInfo";
        /**
         * 暂时固化  获取点位信息
         **/
        public static final String GetPOINT = "http://222.92.77.251:8083/V02WebServiceForOutSZ/WebServiceForOutData.asmx/GetMonitoringPointLocationJson";
        /**
         * 【趋势（公众版）】 N天
         **/
        public static final String GetTopNDayWQ = SERVER_URL_PREFIX + "GetTopNDayWQ";
    }

    /***
     * 背景图片
     *
     * @author smz 创建时间：2014-3-15下午8:12:44
     */
//    public static class PageBg {
//        public static String[] AppFacePackageNames = {"com.sinoyd.environmentwj.avanced", // 默认皮肤
//                "com.sinoyd.environmentwj.mhdh", // 梦幻的海
//                "com.sinoyd.environmentwj.smfg", // 沙漠风光
//                "com.sinoyd.environmentwj.tyfg", // 田园风光
//                "com.sinoyd.environmentwj.qxkq", // 清新空气
//                "com.sinoyd.environmentwj.xrdq", // 旭日东升
//                "com.sinoyd.environmentwj.ysml", // 夜色朦胧
//        };
//        public static final String IMAGE_KEY = "bg_image";
//        public static final String PAGE_PACKAGE_NAME_KEY = "page_package_name";
//        public static final String[] PAGE_BG_NAME = {"default", "menghuandehai", "shamofengguang", "tianuanfengguang", "qingxinkongqi", "xuridongsheng", "yesemenglong"};
//        public static final int[] PAGE_BG_EXAMPLE_IMAGE = {R.drawable.bg_default, R.drawable.bg_menghuandehai, R.drawable.bg_shamofengguang, R.drawable.bg_tianyuanfengguang, R.drawable.bg_qingxinkongqi, R.drawable.bg_xuridongqi, R.drawable.bg_yesemenghong,};
//        public static final int[] PAGE_BG_EXAMPLE_IMAGE_USE = {R.drawable.bg_default_use, R.drawable.bg_menghuandehai_use, R.drawable.bg_shamofengguang_use, R.drawable.bg_tianyuanfengguang_use, R.drawable.bg_qingxinkongqi_use, R.drawable.bg_xuridongqi_use, R.drawable.bg_yesemenghong_use,};
//        public static final int[] THEME_ARRAYS = {R.style.Theme_Default};
//        public static final String THEME_INDEX_KEY = "page_theme_index";
//
//        public static enum FaceType {
//            FactTypeIndex, FaceTypeExampleImage, FaceTypeExampleImageUse, FaceTypePageImage
//        }
//
//        /***
//         * 从图片的名称获取图片的地址
//         *
//         * @param
//         * @param faceType
//         * @return
//         */
//        public static int getFaceByName(FaceType faceType) {
//            return getFaceByName(PreferenceUtils.getData(MyApplication.mContext, PageBg.IMAGE_KEY), faceType);
//        }
//
//        /***
//         * 从图片的名称获取图片的地址
//         *
//         * @param bgName
//         * @param faceType
//         * @return
//         */
//        public static int getFaceByName(String bgName, FaceType faceType) {
//            int index = 0;
//            for (int i = 0; i < PAGE_BG_NAME.length; i++) {
//                if (PAGE_BG_NAME[i].equals(bgName)) {
//                    index = i;
//                    break;
//                }
//            }
//            int image = 0;
//            switch (faceType) {
//                case FactTypeIndex:
//                    image = index;
//                    break;
//                case FaceTypeExampleImage:
//                    image = PAGE_BG_EXAMPLE_IMAGE[index];
//                    break;
//                case FaceTypeExampleImageUse:
//                    image = PAGE_BG_EXAMPLE_IMAGE_USE[index];
//                    break;
//                case FaceTypePageImage:
//                    image = PAGE_BG_EXAMPLE_IMAGE[index];
//                    break;
//            }
//            return image;
//        }
//    }
}
