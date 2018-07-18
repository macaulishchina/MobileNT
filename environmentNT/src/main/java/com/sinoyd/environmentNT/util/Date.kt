@file:Suppress("DEPRECATION")

package com.sinoyd.environmentNT.util

import java.text.Format
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * 作者： scj
 * 创建时间： 2017/10/24
 * 版权： 一个灵魂无处安放的诗人
 * 描述： 扩展Date类的诸多方法
 *************************************************************
 * 获取今天是星期几(英文) getToday_Englishname
 * 获取今天是星期几(中文) getToday_Chinaname
 * 获取当前日期 getToday
 * 获取上周的今天的日期 getlastweekToday
 * 获取上个月今天的日期 getlastmonthToday
 * 获取昨天的日期 getDateofYesterday
 * 获取上个月的第一天 getFirstDayOfLastMonth
 * 获取上个月的最后一天 getLastDayOfLastMonth
 * 判断是否是闰年 isLeapYear
 * 字符串日期 获取想要格式的日期格式 getTime4String
 * 判断两个日期大小  如，第一个日期大于第二个日期，返回true  反之false    isDateOneBigger
 * 获取某个日期的前一天 getSpecifiedDayBefore
 * 获取某个日期的后一天 getSpecifiedDayAfter
 *************************************************************
 */

//获取date对象
var data: Date = Date()
//获取日历对象
//var calendar: Calendar = Calendar.getInstance()

////获取年份
fun Date.getYears(): String {
    var sdf: Format = SimpleDateFormat("yyyy")
    return sdf.format(data)
}

////获取月份
fun Date.getMonthsEnglish(): String {
    var sdf: Format = SimpleDateFormat("MM")
    val re = when (sdf.format(data)) {
        "01" -> "January"
        "02" -> "February"
        "03" -> "March"
        "04" -> "April"
        "05" -> "May"
        "06" -> "June"
        "07" -> "July"
        "08" -> "August"
        "09" -> "September"
        "10" -> "October"
        "11" -> "November"
        "12" -> "December"
        else -> ""
    }
    return re
}

fun Date.getMonthsChina(): String {
    var sdf: Format = SimpleDateFormat("MM")
    val re = when (sdf.format(data)) {
        "01" -> "一月"
        "02" -> "二月"
        "03" -> "三月"
        "04" -> "四月"
        "05" -> "五月"
        "06" -> "六月"
        "07" -> "七月"
        "08" -> "八月"
        "09" -> "九月"
        "10" -> "十月"
        "11" -> "十一月"
        "12" -> "十二月"
        else -> ""
    }
    return re
}


//获取今天是星期几
fun Date.getToday_Englishname(): String {
    var calendargetToday_Englishname: Calendar = Calendar.getInstance()
    var list = arrayOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    calendargetToday_Englishname.time = data
    var index: Int = calendargetToday_Englishname.get(Calendar.DAY_OF_WEEK) - 1
    if (index < 0) {
        index = 0
    }
    return list[index]
}

//获取今天是星期几
fun Date.getToday_Chinaname(): String {
    var list = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
    var calendargetToday_Chinaname: Calendar = Calendar.getInstance()
    calendargetToday_Chinaname.time = data
    var index: Int = calendargetToday_Chinaname.get(Calendar.DAY_OF_WEEK) - 1
    if (index < 0) {
        index = 0
    }
    return list[index]
}

//获取当前日期
fun Date.getToday(): String {
//需要得到的格式
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    return sdf.format(data)
}

fun Date.getTodaytime(): String {
//需要得到的格式
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd HH:mm")
    return sdf.format(data)
}

//获取当前月份
fun Date.getTodayofmonth(): String {
//需要得到的格式
    var sdf: Format = SimpleDateFormat("yyyy/MM")
    return sdf.format(data)
}

//获取上周的今天的日期
fun Date.getlastweekToday(): String {
    var calendargetlastweekToday: Calendar = Calendar.getInstance()
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    val t: Long = calendargetlastweekToday.timeInMillis
    val l: Long = t - 24 * 3600 * 1000 * 7
    return sdf.format(l)
}

fun getlastweekToday(): String {
    var calendargetlastweekToday: Calendar = Calendar.getInstance()
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    val t: Long = calendargetlastweekToday.timeInMillis
    val l: Long = t - 24 * 3600 * 1000 * 7
    return sdf.format(l)
}

//获取上个月今天的日期
fun Date.getlastmonthToday(): String {
    var calendargetlastmonthToday: Calendar = Calendar.getInstance()
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    calendargetlastmonthToday.add(Calendar.MONTH, -1)
    return sdf.format(calendargetlastmonthToday.time)
}


//获取某个日期的前一天
fun Date.getlastmonthTodaylastweek(): String {
    var calendargetSpecifiedDayBefore: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    //自定义过来的String格式的日期
    var date: Date = Date(getlastmonthToday())
    calendargetSpecifiedDayBefore.time = date
    var day = calendargetSpecifiedDayBefore.get(Calendar.DATE)
    calendargetSpecifiedDayBefore.set(Calendar.DATE, day - 7)
    return sdf.format(calendargetSpecifiedDayBefore.time)
}


//获取昨天的日期
fun Date.getDateofYesterday(): String? {
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    var calendar2: Calendar = Calendar.getInstance()
    calendar2.add(Calendar.DATE, -1)
    return sdf.format(calendar2.time)
}
//获取昨天的日期
fun getDateofYesterday(): String? {
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    var calendar2: Calendar = Calendar.getInstance()
    calendar2.add(Calendar.DATE, -1)
    return sdf.format(calendar2.time)
}

//获取上个月的第一天
fun Date.getFirstDayOfLastMonth(): String {
    var calendargetFirstDayOfLastMonth: Calendar = Calendar.getInstance()
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    calendargetFirstDayOfLastMonth.set(Calendar.DATE, 1)
    calendargetFirstDayOfLastMonth.add(Calendar.MONTH, -1)
    return sdf.format(calendargetFirstDayOfLastMonth.time)
}

//获取上个月的最后一天
fun Date.getLastDayOfLastMonth(): String {
    var calendargetLastDayOfLastMonth: Calendar = Calendar.getInstance()
    var sdf: Format = SimpleDateFormat("yyyy/MM/dd")
    calendargetLastDayOfLastMonth.set(Calendar.DATE, 1)
    calendargetLastDayOfLastMonth.add(Calendar.MONTH, -1)
    calendargetLastDayOfLastMonth.roll(Calendar.DATE, -1)
    return sdf.format(calendargetLastDayOfLastMonth.getTime());
}

//判断是否是闰年
fun Date.isLeapYear(year: Int): Boolean {
    if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
        return true
    }
    return false
}


// 字符串日期 获取想要格式的日期格式，栗子："2017—10-10 10:10:10"
fun Date.getTime4String(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-M-d")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}

fun Date.getTime5String(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}

// 字符串日期 获取想要格式的日期格式，栗子："2017—10-10 10:10:10"
fun Date.getTime3String(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-M-d")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}


// 字符串日期 获取想要格式的日期格式，栗子："2017—10-10 10:10:10"
fun getTime2String(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy/M/d H:mm:ss")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("HH:mm")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}


// 字符串日期 获取想要格式的日期格式，栗子："2017—10-10 10:10:10"
fun Date.getTime1String(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}


// 判断两个日期大小  如，第一个日期大于第二个日期，返回true  反之false
fun Date.isDateOneBigger(str1: String, str2: String): Boolean {
    var isBigger: Boolean
    //输入的格式，选择性更改
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    isBigger = sdf.parse(str1).time >= sdf.parse(str2).time
    return isBigger
}

//获取某个日期的前一天
fun Date.getSpecifiedDayBefore(specifiedDay: String): String {
    var calendargetSpecifiedDayBefore: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay)
    calendargetSpecifiedDayBefore.time = date
    var day = calendargetSpecifiedDayBefore.get(Calendar.DATE)
    calendargetSpecifiedDayBefore.set(Calendar.DATE, day - 1)
    return sdf.format(calendargetSpecifiedDayBefore.time)
}

fun Date.getSpecifiedDaytimeBefore(specifiedDay: String): String {
    var calendargetSpecifiedDayBefore: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(specifiedDay)
    calendargetSpecifiedDayBefore.time = date
    var day = calendargetSpecifiedDayBefore.get(Calendar.DATE)
    calendargetSpecifiedDayBefore.set(Calendar.DATE, day - 1)
    return sdf.format(calendargetSpecifiedDayBefore.time)
}


fun Date.getSpecifiedDaytimeBeforetime(specifiedDay: String): String {
    var calendargetSpecifiedDayBefore: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(specifiedDay)
    calendargetSpecifiedDayBefore.time = date
    var day = calendargetSpecifiedDayBefore.get(Calendar.HOUR)
    calendargetSpecifiedDayBefore.set(Calendar.HOUR, day - 1)
    return sdf.format(calendargetSpecifiedDayBefore.time)
}

//获取某个日期的前一天
fun Date.getSpecifiedDayBefore2(specifiedDay: String): String {
    var calendargetSpecifiedDayBefore: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay)
    calendargetSpecifiedDayBefore.time = date
    var day = calendargetSpecifiedDayBefore.get(Calendar.DATE)
    calendargetSpecifiedDayBefore.set(Calendar.DATE, day - 1)
    return sdf.format(calendargetSpecifiedDayBefore.time)
}

//获取某个日期的后一天
fun Date.getSpecifiedDayAfter(specifiedDay: String): String {
    var calendargetSpecifiedDayAfter: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay)
    calendargetSpecifiedDayAfter.time = date
    var day = calendargetSpecifiedDayAfter.get(Calendar.DATE)
    calendargetSpecifiedDayAfter.set(Calendar.DATE, day + 1)
    return sdf.format(calendargetSpecifiedDayAfter.time)
}



fun Date.getSpecifiedDaytimeAfter(specifiedDay: String): String {
    var calendargetSpecifiedDayAfter: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(specifiedDay)
    calendargetSpecifiedDayAfter.time = date
    var day = calendargetSpecifiedDayAfter.get(Calendar.DATE)
    calendargetSpecifiedDayAfter.set(Calendar.DATE, day + 1)
    return sdf.format(calendargetSpecifiedDayAfter.time)
}

fun Date.getSpecifiedDaytimeAftertime(specifiedDay: String): String {
    var calendargetSpecifiedDayAfter: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    //自定义过来的String格式的日期
    var date: Date = SimpleDateFormat("yyyy/MM/dd HH:mm").parse(specifiedDay)
    calendargetSpecifiedDayAfter.time = date
    var day = calendargetSpecifiedDayAfter.get(Calendar.HOUR)
    calendargetSpecifiedDayAfter.set(Calendar.HOUR, day + 1)
    return sdf.format(calendargetSpecifiedDayAfter.time)
}


//获取某个日期的后一天
fun getSpecifiedDayAfter2(specifiedDay: String): String {
    var calendargetSpecifiedDayAfter: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    //自定义过来的String格式的日期
    var dategetSpecifiedDayAfter2: Date = SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay)
    calendargetSpecifiedDayAfter.time = dategetSpecifiedDayAfter2
    var day = calendargetSpecifiedDayAfter.get(Calendar.DATE)
    calendargetSpecifiedDayAfter.set(Calendar.DATE, day + 1)
    return sdf.format(calendargetSpecifiedDayAfter.time)
}

//获取某个日期的后一天  格式
fun getSpecifiedDayAfter22(specifiedDay: String): String {
    var calendargetSpecifiedDayAfter: Calendar = Calendar.getInstance()
    //输出的日期格式
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    //自定义过来的String格式的日期
    var dategetSpecifiedDayAfter22: Date = SimpleDateFormat("yyyy/MM/dd").parse(specifiedDay)
    calendargetSpecifiedDayAfter.time = dategetSpecifiedDayAfter22
    var day = calendargetSpecifiedDayAfter.get(Calendar.DATE)
    calendargetSpecifiedDayAfter.set(Calendar.DATE, day + 1)
    return sdf.format(calendargetSpecifiedDayAfter.time)
}

//获取某个日期是星期几
fun getdayofweek(pTime: String): Int {
    var week: Int = 0
    val c = Calendar.getInstance()
    try {
        c.time = Date(pTime)
    } catch (e: ParseException) {
        // TODO Auto-generated catch block
        e.printStackTrace()
    }
    when (c.get(Calendar.DAY_OF_WEEK)) {
        1 -> week = 0
        2 -> week = 1
        3 -> week = 2
        4 -> week = 3
        5 -> week = 4
        6 -> week = 5
        7 -> week = 6
    }
    return week
}


//获取本月份的第一天
fun getfirtdayofmonth(): String {
    var calendargetfirtdayofmonth: Calendar = Calendar.getInstance()
    val dateFormater = SimpleDateFormat("yyyy/MM/dd")
    calendargetfirtdayofmonth.set(Calendar.DAY_OF_MONTH, 1)
    return dateFormater.format(calendargetfirtdayofmonth.time).toString()
}


//获取本月份的最后一天
fun  getenddayofmonth(): String {
    var calendargetenddayofmonth: Calendar = Calendar.getInstance()
    val dateFormater = SimpleDateFormat("yyyy/MM/dd")
    calendargetenddayofmonth.set(Calendar.DAY_OF_MONTH, calendargetenddayofmonth.getActualMaximum(Calendar.DAY_OF_MONTH))
    return dateFormater.format(calendargetenddayofmonth.time)
}


//获取某个月份的上个月第一天
fun getfirtdayofmongthbefor(mon: String): String {

    var calendargetfirtdayofmongthbefor: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = Date(mon)
    calendargetfirtdayofmongthbefor.time = date
    var month = calendargetfirtdayofmongthbefor.get(Calendar.MONTH)
    calendargetfirtdayofmongthbefor.set(Calendar.MONTH, month - 1)
    calendargetfirtdayofmongthbefor.set(Calendar.DAY_OF_MONTH, 1)
    return sdf.format(calendargetfirtdayofmongthbefor.time).toString()
}


//获取某个月份的上个月最后一天

fun getenddayofmongthbefor(mon: String): String {
    var calendargetenddayofmongthbefor: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var dategetenddayofmongthbefor: Date = Date(mon)
    calendargetenddayofmongthbefor.time = dategetenddayofmongthbefor
    var month = calendargetenddayofmongthbefor.get(Calendar.MONTH)
    calendargetenddayofmongthbefor.set(Calendar.MONTH, month - 1)
    calendargetenddayofmongthbefor.set(Calendar.DAY_OF_MONTH, calendargetenddayofmongthbefor.getActualMaximum(Calendar.DAY_OF_MONTH))
    return sdf.format(calendargetenddayofmongthbefor.time).toString()
}


//获取某个月份的下个月第一天
fun getfirtdayofmongthafter(mon: String): String {
    var calendargetfirtdayofmongthafter: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var dategetfirtdayofmongthafter: Date = Date(mon)
    calendargetfirtdayofmongthafter.time = dategetfirtdayofmongthafter
    var month = calendargetfirtdayofmongthafter.get(Calendar.MONTH)
    calendargetfirtdayofmongthafter.set(Calendar.MONTH, month + 1)
    calendargetfirtdayofmongthafter.set(Calendar.DAY_OF_MONTH, 1)
    return sdf.format(calendargetfirtdayofmongthafter.time).toString()
}

//获取某个月份的下个月最后一天
fun getenddayofmongthafter(mon: String): String {
    var calendargetenddayofmongthafter: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var sdf1: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = sdf1.parse(mon)
    calendargetenddayofmongthafter.time = date
    var month = calendargetenddayofmongthafter.get(Calendar.MONTH)
    calendargetenddayofmongthafter.set(Calendar.MONTH, month + 1)
    calendargetenddayofmongthafter.set(Calendar.DAY_OF_MONTH, calendargetenddayofmongthafter.getActualMaximum(Calendar.DAY_OF_MONTH))
    return sdf.format(calendargetenddayofmongthafter.time).toString()
}

////获取某个日期的最后一天
fun timeforendtime(time: String): String {
    var calendar11: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var datetimeforendtime: Date = sdf.parse(time)
    calendar11.time = datetimeforendtime
    var month = calendar11.get(Calendar.MONTH)
    calendar11.set(Calendar.MONTH, month)
    calendar11.set(Calendar.DAY_OF_MONTH, calendar11.getActualMaximum(Calendar.DAY_OF_MONTH))
    return sdf.format(calendar11.time).toString()
}

fun Date.timeforstarttime(time: String): String {
    var calendar11: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = sdf.parse(time)
    calendar11.time = date
    var month = calendar11.get(Calendar.MONTH)
    calendar11.set(Calendar.MONTH, month)
    calendar11.set(Calendar.DAY_OF_MONTH, 1)
    return sdf.format(calendar11.time).toString()
}

////获取某个日期的年份
fun Date.timeforyear(time: String): String {
    var calendartimeforyear: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("yyyy")
    var sdf1: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = sdf1.parse(time)
    calendartimeforyear.time = date
    var month = calendartimeforyear.get(Calendar.MONTH)
    calendartimeforyear.set(Calendar.MONTH, month)
    calendartimeforyear.set(Calendar.DAY_OF_MONTH, calendartimeforyear.getActualMaximum(Calendar.DAY_OF_MONTH))
    return sdf.format(calendartimeforyear.time).toString()
}


fun Date.timeformonth(time: String): String {
    var calendartimeformonth: Calendar = Calendar.getInstance()
    var sdf: SimpleDateFormat = SimpleDateFormat("MM")
    var sdf1: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    var date: Date = sdf1.parse(time)
    calendartimeformonth.time = date
    var month = calendartimeformonth.get(Calendar.MONTH)
    calendartimeformonth.set(Calendar.MONTH, month)
    calendartimeformonth.set(Calendar.DAY_OF_MONTH, calendartimeformonth.getActualMaximum(Calendar.DAY_OF_MONTH))
    val re = when (sdf.format(calendartimeformonth.time).toString()) {
        "01" -> "一月"
        "02" -> "二月"
        "03" -> "三月"
        "04" -> "四月"
        "05" -> "五月"
        "06" -> "六月"
        "07" -> "七月"
        "08" -> "八月"
        "09" -> "九月"
        "10" -> "十月"
        "11" -> "十一月"
        "12" -> "十二月"
        else -> ""
    }
    return re
}


fun Date.getspecifulyear(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-M-d")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("yyyy")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}

fun Date.getspecifulmonth(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-M-d")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("M")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}

fun Date.getspecifulday(time: String): String {
    //代转日期的字符串格式(输入的字符串格式)
    var inputsdf: SimpleDateFormat = SimpleDateFormat("yyyy-M-d")
    //获取想要的日期格式(输出的日期格式)
    var outputsdf: SimpleDateFormat = SimpleDateFormat("d")
    var date: Date = inputsdf.parse(time)
    return outputsdf.format(date)
}