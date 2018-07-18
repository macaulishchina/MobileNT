package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinoyd.environmentNT.Entity.GetAllUsingInstruments;
import com.sinoyd.environmentNT.R;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/6.
 */

public class LvsuperAdapter extends BaseAdapter<GetAllUsingInstruments.InstrumentDataBean> {

    private List<GetAllUsingInstruments.InstrumentDataBean> data;
    private Context context;

    public LvsuperAdapter(Context context, List<GetAllUsingInstruments.InstrumentDataBean> data) {
        super(context, data);
        this.data = data;
        this.context = context;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.lvsuper_layout, null);
            holder = new ViewHolder();
            holder.item_super_tv = (TextView) convertView.findViewById(R.id.item_super_tv);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();

        GetAllUsingInstruments.InstrumentDataBean da = data.get(position);

        holder.item_super_tv.setText(da.getInstrumentName() + "");

        if (position % 2 == 1) {
            convertView.setBackgroundResource(R.color.youliang_line);
        } else {

        }
        return convertView;

    }

    class ViewHolder {
        TextView item_super_tv;

    }
}
