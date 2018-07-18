package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sinoyd.environmentNT.Entity.GetLatestHourAQI;
import com.sinoyd.environmentNT.Entity.GetPortInfoBySysType;
import com.sinoyd.environmentNT.R;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/7/21.
 */

public class Gislvadapter extends BaseAdapter<GetPortInfoBySysType.PortInfoBean> {
    private List<GetPortInfoBySysType.PortInfoBean> lists;
    private Context context;


    public Gislvadapter(Context context, List<GetPortInfoBySysType.PortInfoBean> data) {
        super(context, data);
        lists = data;
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_gis_listview, null);
            holder = new ViewHolder();
            holder.item_gis_zhandianmingcheng = (TextView) convertView.findViewById(R.id.item_gis_zhandianmingcheng);
            holder.item_gis_aqi = (TextView) convertView.findViewById(R.id.item_gis_aqi);
            holder.item_gis_wuranchengdu = (TextView) convertView.findViewById(R.id.item_gis_wuranchengdu);
            holder.item_gis_PrimaryPollutant = (TextView) convertView.findViewById(R.id.item_gis_PrimaryPollutant);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.item_gis_zhandianmingcheng.setText(lists.get(position).getPortName());

        holder.item_gis_aqi.setText(lists.get(position).getAQI().equals("") ? "--" : lists.get(position).getAQI());
        holder.item_gis_wuranchengdu.setText(lists.get(position).getGrade().equals("") ? "--" : lists.get(position).getGrade());

        holder.item_gis_PrimaryPollutant.setText(lists.get(position).getPrimaryPollutant());

        switch (lists.get(position).getGrade()) {
            case "一级":
            case "--":
            case "优":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.yiji));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.yiji));
                break;
            case "良":
            case "二级":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.erji));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.erji));
                break;
            case "轻度污染":
            case "三级":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.sanji));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.sanji));
                break;
            case "四级":
            case "中度污染":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.siji));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.siji));
                break;
            case "五级":
            case "重度污染":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.wuji));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.wuji));
                break;
            case "严重污染":
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.q_yanzhong));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.q_yanzhong));
                break;
            default:
                holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.white));
                holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.hui));
                break;
        }
        //发送网络请求
//        GetLatestHourAQI(lists.get(position).getPortId(), holder);

        if (position % 2 == 1) {
            convertView.setBackgroundResource(R.color.item);
        } else {
            convertView.setBackgroundResource(R.color.hui);
        }
        return convertView;
    }

    private void GetLatestHourAQI(String portId, final ViewHolder holder) {

        HttpUtils utils_Get = new HttpUtils();
        utils_Get.send(HttpRequest.HttpMethod.GET, "http://218.91.209.251:1117/NTWebServiceForMobile/AQI.asmx/GetLatestHourAQI?portId=" + portId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                GetLatestHourAQI result = gson.fromJson(responseInfo.result, GetLatestHourAQI.class);

                if (result.getPortHourAQI().size() == 0) {

                } else {


                    holder.item_gis_aqi.setText(result.getPortHourAQI().get(0).getAQI());
                    holder.item_gis_wuranchengdu.setText(result.getPortHourAQI().get(0).getGrade());


                    switch (result.getPortHourAQI().get(0).getGrade()) {
                        case "一级":
                        case "--":
                            holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.yiji));
                            holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.yiji));
                            break;
                        case "良":
                        case "二级":
                            holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.erji));
                            holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.erji));
                            break;
                        case "轻度污染":
                        case "三级":
                            holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.sanji));
                            holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.sanji));
                            break;
                        case "四级":
                            holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.siji));
                            holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.siji));
                            break;
                        case "五级":
                            holder.item_gis_aqi.setTextColor(context.getResources().getColor(R.color.wuji));
                            holder.item_gis_wuranchengdu.setBackgroundColor(context.getResources().getColor(R.color.wuji));
                            break;

                    }
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {


            }
        });
    }


    class ViewHolder {
        TextView item_gis_zhandianmingcheng;
        TextView item_gis_aqi;
        TextView item_gis_wuranchengdu;
        TextView item_gis_PrimaryPollutant;
    }


}
