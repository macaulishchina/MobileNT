package com.sinoyd.environmentNT.util

import android.graphics.Color

/**
 * 作者： scj
 * 创建时间： 2018/3/1
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentsz.Kotlin
 */

fun getAQIcolor(value: Double): Int = when {
    value in 0.0..50.0 -> {
        Color.rgb(61, 207, 0)
    }
    value in 50.0..100.0 -> {
        Color.rgb(255, 255, 4)
    }
    value in 100.0..150.0 -> {
        Color.rgb(247, 128, 19)
    }
    value in 150.0..200.0 -> {
        Color.rgb(246, 29, 22)
    }
    value in 200.0..300.0 -> {
        Color.rgb(147, 9, 76)
    }
    value in 300.0..500.0 -> {
        Color.rgb(121, 8, 36)
    }
    else -> {
        Color.rgb(255, 255, 255)
    }
}