package com.sinoyd.environmentNT.http;

import java.net.HttpURLConnection;
import java.net.URL;
import com.sinoyd.environmentNT.AppConfig;
import com.sinoyd.environmentNT.model.HoursFactorDataInfo;
import com.sinoyd.environmentNT.model.PortHourAQIListInfo;

public class EPHttpRequestUtils {
	private static HttpResult getInfo(String url, Object... params) throws Exception {
		URL u = new URL(String.format(url, params));
		System.out.println(u);
		return HttpUtils.getTextResult((HttpURLConnection) u.openConnection());
	}
	
	/**
	 * 获取站点信息 对应数据类型{@link PortListInfo PortListInfo}
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws Exception
	 */
	public static HttpResult GetPortInfo(String sysType,String LoginId) throws Exception {
		return getInfo(AppConfig.RequestAirActionName.GetPortInfoBySysType+"?sysType=%s&LoginId=%s",sysType,LoginId);
	}

	/**
	 * 【实况，GIS】最新小时AQI <br/>
	 * portId=0代表全市，不填显示全部点位的信息。<br />
	 * 对应数据类型{@link PortHourAQIListInfo PortHourAQIListInfo}
	 * 
	 * @param portId
	 * @return
	 */
	public static HttpResult GetLatestHourAQI(String portId) throws Exception {
		return getInfo(AppConfig.RequestAirActionName.GetLatestHourAQI + "?portId=%s", portId);
	}

	/**
	 * 【实况_气】获取首要污染物最新24小时浓度值<br />
	 * 对应数据类型{@link HoursFactorDataInfo HoursFactorDataInfo}
	 * 
	 * @return
	 */
	public static HttpResult Get24HoursFactorDataAir(String portId) throws Exception {
		return getInfo(AppConfig.getUrlByPrefixPage(AppConfig.RequestAirActionName.Get24HoursFactorDataAir), portId);
	}

	/**
	 * 【浓度】最新小时浓度值<br />
	 * 
	 * @param portId
	 * @return
	 * @throws Exception
	 */
	public static HttpResult GetHourConcentration(String portId) throws Exception {
		return getInfo(AppConfig.getUrlByPrefixPage(AppConfig.RequestAirActionName.GetHourConcentration), portId);
	}

	/**
	 * 【历史（专业版）】 天 <br/>
	 * 时间格式：yyyy-MM-dd<br/>
	 * 对应数据类型{@link DayAQIHistoryInfo DayAQIHistoryInfo}
	 * 
	 * @param portId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws Exception
	 */
	public static HttpResult GetDayAQIByDatetime(String portId, String startTime, String endTime) throws Exception {
		return getInfo(AppConfig.getUrlByPrefixPage(AppConfig.RequestAirActionName.GetDayAQIByDatetime), portId, startTime, endTime);
	}
}
