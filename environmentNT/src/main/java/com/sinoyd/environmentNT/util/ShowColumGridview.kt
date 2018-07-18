package com.sinoyd.environmentNT.util

import android.content.Context
import android.util.DisplayMetrics
import android.widget.GridView
import android.widget.RelativeLayout
import com.sinoyd.environmentNT.adapter.ColumAdapter
import org.jetbrains.anko.windowManager

/**
 * 作者： scj
 * 创建时间： 2018/1/8
 * 版权： scj所有
 * 描述： Girdview 树状图显示
 */


/***
 * gridview 实现 树状图显示
 *
 * ***/

fun showcolumgridview(context: Context, list: List<GridviewColumdata>, gv: GridView) {

    var size = list.size
    val dm = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(dm)
    val density = dm.density
//    val allWidth = (70f * size.toFloat() * density).toInt()
    val itemWidth = (px2dip(context, dm.widthPixels.toFloat()) / 6 * density).toInt()
    val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

    gv.layoutParams = params// 设置GirdView布局参数
    gv.columnWidth = itemWidth// 列表项宽
    gv.horizontalSpacing = 1// 列表项水平间距
    gv.stretchMode = GridView.NO_STRETCH
    gv.numColumns = size//总长度

    //定义适配器
    var columAdapter = ColumAdapter(context, list)

    gv.adapter = columAdapter


}