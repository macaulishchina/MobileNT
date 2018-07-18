package com.sinoyd.environmentNT.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import com.lidroid.xutils.db.sqlite.Selector
import com.sinoyd.environmentNT.MyApplication
import com.sinoyd.environmentNT.R
import com.sinoyd.environmentNT.model.PortInfo
import com.sinoyd.environmentNT.util.PreferenceUtils
import com.sinoyd.environmentNT.util.getTodaytime
import kotlinx.android.synthetic.main.activity_new_aqi.*
import kotlinx.android.synthetic.main.include_water_title.*
import org.jetbrains.anko.onClick
import java.util.*

class NewAQIActivity : RefreshBaseActivity() {

    var datetime: String = ""
    var data: Date = Date()
    var listDB: MutableList<PortInfo> = ArrayList()
    var frms: ArrayList<AQIFragment> = arrayListOf()

    var currentpostion: Int = 0
    var LoginId: String = ""
    var sysType: String = ""

    override fun selectPortCallBack() {
        //切站点
        Log.i("scj", "selectPortCallBack内发请求")
        frms[currentpostion].showview(portId)
        if (mRefreshButton != null) {
            mRefreshButton.start()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_aqi)
        LoginId = PreferenceUtils.getData(activity, "UserGuid")
        sysType = PreferenceUtils.getData(activity, "SysType")
    }

    override fun initView() {
        super.initView()

        datetime = data.getTodaytime()

        try {
            listDB = MyApplication.mDB.findAll<PortInfo>(Selector.from(PortInfo::class.java).where("isWaterPort", "=", "0"))
            Log.i("scj", "AQI站点管理个数：${listDB.size}")
            if (listDB.size == 0) {
                showTextToast("站点管理内无站点信息")
                return
            }
            //清空frms
            frms = arrayListOf()
            for (item in 0 until listDB.size) {
                frms.add(AQIFragment(NewAQIActivity@ this, listDB[item], LoginId, sysType))
                if (mRefreshButton != null) {
                    mRefreshButton.start()
                }
            }
        } catch (e: Exception) {
            showTextToast("站点管理内无站点信息")
        }
        Log.i("scj", "frms个数：${frms.size}")
        //配置适配器
        viewPager.adapter = InnerFragmentPagerAdapter(fragmentManager!!)
        Log.i("scj", "initView内发请求")
        frms[0].showview(listDB[0].PortId)

        top_refresh_btn.onClick {
            frms[currentpostion].showview(listDB[currentpostion].PortId)
            if (mRefreshButton != null) {
                mRefreshButton.start()
            }
        }


    }

    override fun onResume() {
        super.onResume()

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                title.text = frms[position].pdm.portName
                currentpostion = position
            }

            override fun onPageSelected(position: Int) {
                Log.i("scj", "onPageSelected内发请求")
                frms[position].showview(listDB[position].PortId)
                if (mRefreshButton != null) {
                    mRefreshButton.start()
                }
                Log.i("scj", "当前界面名称:${listDB[position].PortName}      当前界面编号:$position")
            }
        })


    }

    /**
     * viewpager适配器
     */
    private inner class InnerFragmentPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return frms[position]
        }

        override fun getCount(): Int {
            return frms.size
        }
    }


}
