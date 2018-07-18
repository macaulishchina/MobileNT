package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.GetPrimaryPollutant;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/6.
 */

public class ChildAdapter extends BaseAdapter<GetPrimaryPollutant.DataBean.ValueBean> {

    private List<GetPrimaryPollutant.DataBean.ValueBean> data;
    private Context context;

    public ChildAdapter(Context context, List<GetPrimaryPollutant.DataBean.ValueBean> data) {
        super(context, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.child_itemlayout, null);
            holder = new ViewHolder();
            holder.tv_factor = (TextView) convertView.findViewById(R.id.tv_factor);
            holder.tv_factorvalue = (TextView) convertView.findViewById(R.id.tv_factorvalue);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        GetPrimaryPollutant.DataBean.ValueBean da = data.get(position);
        holder.tv_factor.setText(da.getFactor());
        holder.tv_factorvalue.setText(da.getFactorValue());
        return convertView;
    }

    class ViewHolder {
        TextView tv_factor;
        TextView tv_factorvalue;
    }
}
