package com.sinoyd.environmentNT.database;

import com.lidroid.xutils.db.annotation.Table;

/***
 * 天气信息
 *
 * @author smz
 *
 */
@Table(name = "weather")
public class WeatherDBModel extends DBBaseModel {


    private String tempMax;
    private String tempMin;
    private String weatherEnd;
    private String weatherStart;
    private String windDirectionStart;
    private String city;
    private String weather;

    public String getWeatherEnd() {
        return weatherEnd;
    }

    public void setWeatherEnd(String weatherEnd) {
        this.weatherEnd = weatherEnd;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    private String Time;

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTempMax() {
        return tempMax;
    }

    public void setTempMax(String tempMax) {
        this.tempMax = tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }

    public void setTempMin(String tempMin) {
        this.tempMin = tempMin;
    }

    public String getWeatherStart() {
        return weatherStart;
    }

    public void setWeatherStart(String weatherStart) {
        this.weatherStart = weatherStart;
    }

    public String getWindDirectionStart() {
        return windDirectionStart;
    }

    public void setWindDirectionStart(String windDirectionStart) {
        this.windDirectionStart = windDirectionStart;
    }

    //	private String city; // 城市
//	private int cityId; // 城市编号
//	private String date = DateUtil.getCurrentTime("yyyy-MM-dd");
//	private String highTemp; // 最高温
//	private String lowTemp; // 最低温
//	private String temp; // 临时信息
//	private String time; // 时间
//	private String WD; // 方向
//	private String WS; // 方向
//	private int WSE; // wse
//	private String SD; // sd
//    private String Weather;
//	private String Temperature;
//	private String Wind;
//
//	public String getTemperature() {
//		return Temperature;
//	}
//
//	public void setTemperature(String temperature) {
//		Temperature = temperature;
//	}
//
//	public String getWeather() {
//		return Weather;
//	}
//
//	public void setWeather(String weather) {
//		Weather = weather;
//	}
//
//	public String getWind() {
//		return Wind;
//	}
//
//	public void setWind(String wind) {
//		Wind = wind;
//	}
//
//	public String getSD() {
//		return SD;
//	}
//
//	public void setSD(String sD) {
//		SD = sD;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public int getCityId() {
//		return cityId;
//	}
//
//	public String getDate() {
//		return date;
//	}
//
//	public String getHighTemp() {
//		return highTemp;
//	}
//
//	public String getLowTemp() {
//		return lowTemp;
//	}
//
//	public String getTemp() {
//		return temp;
//	}
//
//	public String getTime() {
//		return time;
//	}
//
//	public String getWD() {
//		return WD;
//	}
//
//	public String getWS() {
//		return WS;
//	}
//
//	public int getWSE() {
//		return WSE;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public void setCityId(int cityId) {
//		this.cityId = cityId;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//	}
//
//	public void setHighTemp(String highTemp) {
//		this.highTemp = highTemp;
//	}
//
//	public void setLowTemp(String lowTemp) {
//		this.lowTemp = lowTemp;
//	}
//
//	public void setTemp(String temp) {
//		this.temp = temp;
//	}
//
//	public void setTime(String time) {
//		this.time = time;
//	}
//
//	public void setWD(String wD) {
//		WD = wD;
//	}
//
//	public void setWS(String wS) {
//		WS = wS;
//	}
//
//	public void setWSE(int wSE) {
//		WSE = wSE;
//	}
}
