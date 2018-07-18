package com.sinoyd.environmentNT.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView

import com.sinoyd.environmentNT.R
import com.sinoyd.environmentNT.model.GetPrimaryPollutant

/**
 * 作者： scj
 * 创建时间： 2018/3/8
 * 版权： 江苏远大信息股份有限公司
 * 描述： com.sinoyd.environmentNT.adapter
 */


class CatAdapter(var context: Context, val datas: List<GetPrimaryPollutant.DataBean>) : RecyclerView.Adapter<CatAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cat_itemlayout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = datas[position]


        //显示时间【上】
        if (data.dateTime.contains(":")) {
            holder.tv_time_name.text = data.dateTime.substring(11, 16)
        } else {
            holder.tv_time_name.text = data.dateTime.substring(5, 10)
        }

        //显示下列【下】
        var childAdapter: ChildAdapter = ChildAdapter(context, data.value)
        holder.lv_data_value.adapter = childAdapter
    }

    override fun getItemCount(): Int {
        return datas.size
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_time_name: TextView = view.findViewById<View>(R.id.tv_time_name) as TextView
        var lv_data_value: ListView = view.findViewById<View>(R.id.lv_time_name) as ListView
    }


}
