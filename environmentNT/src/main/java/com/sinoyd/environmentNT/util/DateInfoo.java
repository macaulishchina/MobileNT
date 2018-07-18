package com.sinoyd.environmentNT.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by shenchuanjiang on 2017/4/7.
 * 获取各种时间
 */

public class DateInfoo {
    private static Format sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static Format sdf1 = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-M-d");

    private static SimpleDateFormat sdf33 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/M/d H:mm:ss");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH");

    private static SimpleDateFormat format1 = new SimpleDateFormat("H:m");
    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat format2 = new SimpleDateFormat("MM/dd");
    private static SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");

    public static String getriqiforstring2(String time) {

        Date date = null;
        try {
            date = sdf33.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format.format(date);

        return str;

    }

    //字符串转为日期格式，并获取HH:mm 格式字符串数据
    public static String gettimeforstring(String time) {
        Date date = null;
        try {
            date = sdf2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format.format(date);
        return str;
    }

    public static String gettimeforstring1(String time) {
        Date date = null;
        try {
            date = sdf3.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format.format(date);
        return str;
    }

    public static String getriqiforstring(String time) {

        Date date = null;
        try {
            date = sdf2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format2.format(date);

        return str;

    }
    public static String getriqiforstring1(String time) {

        Date date = null;
        try {
            date = sdf3.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format2.format(date);

        return str;

    }
    //字符串转为日期格式，yyyy-M-d 转  yyyy-MM-dd
    public static String gettimeforstring4(String time) {
        Date date = null;
        try {
            date = sdf4.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format4.format(date);
        return str;
    }

    //字符串转为日期格式，H:m 转  HH:mm
    public static String gettimeforstring5(String time) {
        Date date = null;
        try {
            date = format1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String str = format.format(date);
        return str;
    }


    //获取当前日期

    public static String getToday() {
        Date d = new Date();

        String date = sdf.format(d);
        return date;
    }
    //获取当前时间

    public static String getTodaytime() {
        Date d = new Date();

        String date = sdf1.format(d);
        return date;
    }

    //获取隔一周上周的日期
    public static String getlastweekToday() {
        Calendar c = Calendar.getInstance();
        long t = c.getTimeInMillis();
        long l = t - 24 * 3600 * 1000 * 7;
        Date d = new Date(l);
        String date = sdf.format(d);
        return date;
    }

    //获取隔一个月上月的日期
    public static String getlastmonthToday() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        String date = sdf.format(c.getTime());
        return date;
    }


    //截取本月
    public static String getCurrentMonth() {
        Date d = new Date();
        String t = sdf.format(d);
        String m = t.substring(4, 6);
        return m;
    }

    //截取本年
    public static String getCurrentYear() {
        Date d = new Date();
        String t = sdf.format(d);
        String y = t.substring(0, 4);
        return y;
    }


    //获取昨天的日期
    public static String getDateOfYesterday() {
        Calendar c = Calendar.getInstance();
        long t = c.getTimeInMillis();
        long l = t - 24 * 3600 * 1000;
        Date d = new Date(l);
        String s = sdf.format(d);
        return s;
    }

    //获取上个月的第一天
    public static String getFirstDayOfLastMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1); //set the date to be 1
        lastDate.add(Calendar.MONTH, -1);//reduce a month to be last month
//		lastDate.add(Calendar.DATE,-1);//reduce one day to be the first day of last month

        str = sdf.format(lastDate.getTime());
        return str;
    }

    // 获取上个月的最后一天
    public static String getLastDayOfLastMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, -1);//
        lastDate.roll(Calendar.DATE, -1);//
        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取本月第一天
    public static String getFirstDayOfThisMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
//		lastDate.add(Calendar.MONTH,-1);//
//		lastDate.add(Calendar.DATE,-1);//

        str = sdf.format(lastDate.getTime());
        return str;
    }

    //获取本月最后一天
    public static String getLastDayOfThisMonth() {
        String str = "";
        Calendar lastDate = Calendar.getInstance();
        lastDate.set(Calendar.DATE, 1);//
        lastDate.add(Calendar.MONTH, 1);//
        lastDate.add(Calendar.DATE, -1);//

        str = sdf.format(lastDate.getTime());
        return str;
    }

    //判断闰年
    public static boolean isLeapYear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        }
        return false;
    }


    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() >= dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }


    //获取某个日子的前一天
    public static String getSpecifiedDayBefore(String specifiedDay) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

}
