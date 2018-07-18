package com.sinoyd.environmentNT.activity

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler.Callback
import android.os.Message
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shenchuanjiang.kotlin1013test.CommonSelectorString
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.gson.Gson
import com.iceman.paintdemo.RecordView
import com.lidroid.xutils.HttpUtils
import com.lidroid.xutils.exception.HttpException
import com.lidroid.xutils.http.ResponseInfo
import com.lidroid.xutils.http.callback.RequestCallBack
import com.lidroid.xutils.http.client.HttpRequest
import com.sinoyd.environmentNT.AppConfig
import com.sinoyd.environmentNT.MyApplication.showTextToast
import com.sinoyd.environmentNT.R
import com.sinoyd.environmentNT.R.id.*
import com.sinoyd.environmentNT.R.style.dialog
import com.sinoyd.environmentNT.dialog.FactorDialog
import com.sinoyd.environmentNT.dialog.LoadDialog
import com.sinoyd.environmentNT.model.Get24HoursFactorDataAir
import com.sinoyd.environmentNT.model.GetDayAQI
import com.sinoyd.environmentNT.model.PortInfo
import com.sinoyd.environmentNT.util.GridviewColumdata
import com.sinoyd.environmentNT.util.PreferenceUtils
import com.sinoyd.environmentNT.util.getAQIcolor
import com.sinoyd.environmentNT.util.showcolumgridview
import kotlinx.android.synthetic.main.new_aqi_layout.*
import org.jetbrains.anko.onClick
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@SuppressLint("ValidFragment")
class AQIFragment(var ac: NewAQIActivity, var pdm: PortInfo, val LoginId: String, val sysType: String) : Fragment(), Callback, FactorDialog.FactorItemSelectListener {

    var dialog:LoadDialog = LoadDialog(ac.context)

    override fun handleMessage(p0: Message?): Boolean {
        return false
    }

    //dialog选择因子
    override fun selectFactorinfo(factorInfo: String?) {

        if (factorInfo.toString().toLowerCase() == "co") {
            tv_danwei.text = "毫克/立方米"
        } else {
            tv_danwei.text = "微克/立方米"
        }

        aqiair_sprGene.text = factorInfo


    }

    var getDayAQI: GetDayAQI = GetDayAQI()
    var mRecordView: RecordView? = null
    var conview: View? = null
    var datatime: String = ""
    var data: Date = Date()
    val utils_Get = HttpUtils()
    var gson: Gson = Gson()
    var gvdatas: HashMap<String, Float> = HashMap()
    var getFactorLists: ArrayList<String> = ArrayList()
    var columlist: ArrayList<GridviewColumdata> = ArrayList()
    var listfactorname = listOfNotNull("PM10", "PM2.5", "NO2", "SO2", "O3", "CO", "O3-8h")
    var factorname: String = "PM10"
    var portIds: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        conview = inflater!!.inflate(R.layout.new_aqi_layout, null)
        return conview
    }

    override fun onResume() {
        super.onResume()
        Log.i("scj", "onResume内发请求")
        showview(portIds)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_temp
        tv_wind
        tv_sunny
        tv_danwei
        aqi_tip
        lv2AQI
        tvPrimaryName
        tvAQI
        tvdate
        aqi_chart_layout
        aqiair_sprGene
        ll_dialog.onClick {
            var comm: CommonSelectorString = CommonSelectorString(activity, ll_dialog, listfactorname as ArrayList<String>, object : CommonSelectorString.OnSelectClickListener {
                override fun onCommonItemSelect(postions: Int) {
                    factorname = listfactorname[postions]
                    sendGet24HoursFactorDataAir(portIds, listfactorname[postions])
                    aqiair_sprGene.text = factorname
                    if (factorname == "CO") {
                        tv_danwei.text = "单位：毫克/立方米"
                    } else {
                        tv_danwei.text = "单位：微克/立方米"
                    }
                }
            })
            comm.showPop()
        }

        aqiair_sprGene.text = "PM10"
    }

    //刷界面
    fun showview(portId: String) {
        portIds = portId
        Log.i("scj", "刷界面：端口" + pdm.portId)
        //发请求上中
        sendGetLatestHourAQI(portId)

        //获取首要污染物最新24小时浓度值
        sendGet24HoursFactorDataAir(portId, factorname)

        //发送天气预报
        sendweather()
    }

    //发送请求  上半部分
    fun sendGetLatestHourAQI(portId: String) {
        var url = AppConfig.RequestAirActionName.GetLatestHourAQI + "?portId=$portId"
        Log.i("scj", "请求地址：$url")
        utils_Get.configDefaultHttpCacheExpiry(0)
        utils_Get.send(HttpRequest.HttpMethod.GET, url, object : RequestCallBack<String>() {
            override fun onFailure(p0: HttpException?, p1: String?) {
                showTextToast("实时数据请求空气质量状况失败")
                ac.StopAnimation()
            }

            override fun onSuccess(p0: ResponseInfo<String>?) {
                ac.StopAnimation()
                Log.i("scj", "返回数据：${p0!!.result}")
                try {
                    getDayAQI = gson.fromJson(p0!!.result, GetDayAQI::class.java)
                    if (getDayAQI.portHourAQI.size == 0) {
                        showTextToast("没有获取到数据")
                        return
                    }
                    zhenglishuju(getDayAQI)


                } catch (e: Exception) {
//                    showTextToast("数据格式不正确")
                }

            }
        })
    }

    //界面上半部分显示之数据整理
    private fun zhenglishuju(dayAQI: GetDayAQI?) {
        gvdatas = HashMap()
        try {
            gvdatas.put("SO2", getDayAQI.portHourAQI[0].sO2_IAQI.toFloat())
        } catch (e: Exception) {
            gvdatas.put("SO2", 0f)
        }

        try {
            gvdatas.put("NO2", getDayAQI.portHourAQI[0].nO2_IAQI.toFloat())
        } catch (e: Exception) {
            gvdatas.put("NO2", 0f)
        }

        try {
            gvdatas.put("PM10", getDayAQI.portHourAQI[0].pM10_IAQI.toFloat())
        } catch (e: Exception) {
            gvdatas.put("PM10", 0f)
        }

        try {
            gvdatas.put("CO", getDayAQI.portHourAQI[0].cO_IAQI.toFloat())
        } catch (e: Exception) {
            gvdatas.put("CO", 0f)
        }

        try {
            gvdatas.put("O3", getDayAQI.portHourAQI[0].o3_IAQI.toFloat())
        } catch (e: Exception) {
            gvdatas.put("O3", 0f)
        }

        try {
            gvdatas.put("PM2.5", getDayAQI.portHourAQI[0].`_$PM25_IAQI90`.toFloat())
        } catch (e: Exception) {
            gvdatas.put("PM2.5", 0f)
        }

        showone(getDayAQI.portHourAQI[0])

        columlist = ArrayList()
        try {
            columlist.add(GridviewColumdata("SO2", getDayAQI.portHourAQI[0].sO2_IAQI.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].sO2_IAQI.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("SO2", 0.0, getAQIcolor(0.0)))

        }
        try {
            columlist.add(GridviewColumdata("NO2", getDayAQI.portHourAQI[0].nO2_IAQI.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].nO2_IAQI.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("NO2", 0.0, getAQIcolor(0.0)))

        }
        try {
            columlist.add(GridviewColumdata("PM10", getDayAQI.portHourAQI[0].pM10_IAQI.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].pM10_IAQI.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("PM10", 0.0, getAQIcolor(0.0)))
        }
        try {
            columlist.add(GridviewColumdata("CO", getDayAQI.portHourAQI[0].cO_IAQI.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].cO_IAQI.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("CO", 0.0, getAQIcolor(0.0)))

        }
        try {
            columlist.add(GridviewColumdata("O3", getDayAQI.portHourAQI[0].o3_IAQI.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].o3_IAQI.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("O3", 0.0, getAQIcolor(0.0)))

        }
        try {
            columlist.add(GridviewColumdata("PM2.5", getDayAQI.portHourAQI[0].`_$PM25_IAQI90`.toDouble(), getAQIcolor(getDayAQI.portHourAQI[0].`_$PM25_IAQI90`.toDouble())))
        } catch (e: Exception) {
            columlist.add(GridviewColumdata("PM2.5", 0.0, getAQIcolor(0.0)))

        }
        //显示树状图
        showcolumgridview(activity, columlist, gv_column)
    }

    //显示最上面部分
    private fun showone(portHourAQIBean: GetDayAQI.PortHourAQIBean?) {
        aqi_tip.text = "空气质量 ${portHourAQIBean!!.classname}"
        tvdate.text = portHourAQIBean!!.dateTime.toString()
        tvAQI.text = portHourAQIBean!!.aqi.toString()
        tvPrimaryName.text = portHourAQIBean!!.primaryPollutant
        tvPrimaryValue.text = portHourAQIBean!!.primaryPollutantValue
        tv_date.text = portHourAQIBean!!.dateTime
        var rgb = 0
        when (portHourAQIBean!!.classname) {
            "优" -> {
                rgb = Color.rgb(1, 228, 1)
            }
            "良" -> {
                rgb = Color.rgb(255, 255, 1)
            }
            "轻度污染" -> {
                rgb = Color.rgb(254, 165, 0)
            }
            "中度污染" -> {
                rgb = Color.rgb(251, 0, 17)
            }
            "重度污染" -> {
                rgb = Color.rgb(155, 1, 73)
            }
            "严重污染" -> {
                rgb = Color.rgb(114, 7, 37)
            }
        }
        var myGrad: GradientDrawable = lv2AQI.background as GradientDrawable
        myGrad.setColor(rgb)
        tvAQI.setTextColor(rgb)
    }

    //发送天气预报
    fun sendweather() {

        var url = "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=101190501"
        Log.i("scj", "请求地址：$url")

        utils_Get.send(HttpRequest.HttpMethod.GET, url, object : RequestCallBack<String>() {
            override fun onFailure(p0: HttpException?, p1: String?) {
//                showTextToast("天气预报失败")
            }

            override fun onSuccess(p0: ResponseInfo<String>?) {
                Log.i("scj", "返回数据：${p0!!.result}")
                val jsonObject: JSONObject = JSONObject(p0!!.result).getJSONObject("today")
                tv_temp?.text = "${jsonObject.getString("tempMin")}℃～${jsonObject.getString("tempMax")}℃"
                tv_wind?.text = jsonObject.getString("windDirectionStart")
                tv_sunny?.text = jsonObject.getString("weatherEnd")
            }
        })
    }

    //发送获取首要污染物最新24小时浓度值
    fun sendGet24HoursFactorDataAir(portId: String, facname: String) {
        var url = AppConfig.RequestAirActionName.Get24HoursFactorDataAir + "?portId=$portId&factorName=$facname"
        Log.i("scj", "请求地址：$url")
        dialog.show()
        utils_Get.configDefaultHttpCacheExpiry(0)
        utils_Get.send(HttpRequest.HttpMethod.GET, url, object : RequestCallBack<String>() {
            override fun onFailure(p0: HttpException?, p1: String?) {
                dialog.dismiss()
                showTextToast("发送获取首要污染物最新24小时浓度值失败")
                ac.StopAnimation()
            }

            override fun onSuccess(p0: ResponseInfo<String>?) {
                dialog.dismiss()
                ac.StopAnimation()
                Log.i("scj", "返回数据：${p0!!.result}")
                try {
                    var get24 = gson.fromJson(p0!!.result, Get24HoursFactorDataAir::class.java)
                    if (get24.hoursFactorData.size == 0) {
                    } else {
                        showquexiantu(get24.hoursFactorData, chart1)
                    }
                } catch (e: Exception) {
//                    showTextToast("数据格式不正确")
                }

            }
        })
    }

    //显示折线图
    private fun showquexiantu(hoursFactorData: List<Get24HoursFactorDataAir.HoursFactorDataBean>, mChart: LineChart) {

//        mChart.setOnChartGestureListener(this)
//        mChart.setOnChartValueSelectedListener(this)
        mChart.setDrawGridBackground(false)

        // no description text
        mChart.description.isEnabled = false

        // enable touch gestures
        mChart.setTouchEnabled(true)

        // enable scaling and dragging
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(true)
        mChart.isScaleYEnabled = false
        mChart.isScaleXEnabled = true
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true)

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        val mv = MyMarkerView(this, R.layout.custom_marker_view)
//        mv.setChartView(mChart) // For bounds control
//        mChart.marker = mv // Set the marker to the chart

        // x-axis limit line
//        val llXAxis = LimitLine(10f, "Index 10")
//        llXAxis.lineWidth = 4f
//        llXAxis.enableDashedLine(10f, 10f, 0f)
//        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//        llXAxis.textSize = 10f

        val xAxis = mChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.WHITE
        xAxis.gridColor = Color.WHITE
        xAxis.axisLineColor = Color.WHITE
        xAxis.granularity = 1f  //刻度的最小间隔
        xAxis.labelCount = 7
        xAxis.setValueFormatter { value, axis ->

            hoursFactorData[value.toInt()].dateTime.substring(10, hoursFactorData[value.toInt()].dateTime.length)
        }
//        xAxis.enableGridDashedLine(10f, 10f, 0f)
//        xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
//        xAxis.addLimitLine(llXAxis); // add x-axis limit line


//        val tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf")

//        val ll1 = LimitLine(150f, "Upper Limit")
//        ll1.lineWidth = 4f
//        ll1.enableDashedLine(10f, 10f, 0f)
//        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
//        ll1.textSize = 10f
//        ll1.typeface = tf

//        val ll2 = LimitLine(-30f, "Lower Limit")
//        ll2.lineWidth = 4f
//        ll2.enableDashedLine(10f, 10f, 0f)
//        ll2.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
//        ll2.textSize = 10f
//        ll2.typeface = tf

        val leftAxis = mChart.axisLeft
        leftAxis.removeAllLimitLines() // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1)
//        leftAxis.addLimitLine(ll2)
        leftAxis.axisMaximum = 200f
        leftAxis.axisMinimum = 0f
        //leftAxis.setYOffset(20f);
//        leftAxis.enableGridDashedLine(10f, 0f, 0f)
//        leftAxis.setDrawZeroLine(false)

        // limit lines are drawn behind data (and not on top)
//        leftAxis.setDrawLimitLinesBehindData(false)
        leftAxis.setDrawZeroLine(false)
        leftAxis.textColor = Color.WHITE
        leftAxis.gridColor = Color.WHITE
        leftAxis.axisLineColor = Color.WHITE
        leftAxis.setDrawGridLines(false)
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true)
        mChart.axisRight.isEnabled = false
        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(hoursFactorData, mChart)

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500)
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        val l = mChart.legend
        l.isEnabled = false
//
//        // modify the legend ...
//        l.form = Legend.LegendForm.LINE

        // // dont forget to refresh the drawing
        // mChart.invalidate();
    }

    private fun setData(count: List<Get24HoursFactorDataAir.HoursFactorDataBean>, mChart: LineChart) {

        val values = java.util.ArrayList<Entry>()

        count.mapIndexedTo(values) { n, item ->
            try {
                Entry(n.toFloat(), item.value.toFloat())
            } catch (E: Exception) {
                Entry(n.toFloat(), 0.toFloat())
            }

        }

        var set1 = LineDataSet(values, "")

//        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
//            set1 = mChart.getData().getDataSetByIndex(0)
//        set1.values = values
//        mChart.getData().notifyDataChanged()
//        mChart.notifyDataSetChanged()
//        } else {
        // create a dataset and give it a type


        set1.setDrawIcons(false)
        set1.valueTextColor = Color.WHITE
        // set the line to be drawn like this "- - - - - -"
//        set1.enableDashedLine(10f, 5f, 0f)
//        set1.enableDashedHighlightLine(10f, 5f, 0f)
        set1.color = Color.WHITE
        set1.setCircleColor(Color.rgb(172, 231, 255))
        set1.lineWidth = 1f
        set1.circleRadius = 3f
        set1.setDrawCircleHole(true)
        set1.setCircleColorHole(Color.rgb(13, 89, 157))
        set1.valueTextSize = 9f
        set1.setDrawFilled(false)
        set1.formLineWidth = 1f
//        set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
        set1.formSize = 15f
        set1.setValueFormatter { value, entry, dataSetIndex, viewPortHandler ->
            count.get(entry.x.toInt()).value
        }

//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
//                set1.fillDrawable = drawable
//            } else {
//                set1.fillColor = Color.BLACK
//            }

        val dataSets = java.util.ArrayList<ILineDataSet>()
        dataSets.add(set1) // add the datasets

        // create a data object with the datasets
        val data = LineData(dataSets)

        // set data
        mChart.data = data
    }
}