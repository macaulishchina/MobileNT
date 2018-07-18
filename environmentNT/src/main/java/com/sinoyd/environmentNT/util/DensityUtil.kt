package com.sinoyd.environmentNT.util

import android.content.Context

/**
 * 作者： scj
 * 创建时间： 2018/1/8
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.example.sinoyd.gridviewcolumnargraphapplication.Adatper
 */

fun px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}


fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}