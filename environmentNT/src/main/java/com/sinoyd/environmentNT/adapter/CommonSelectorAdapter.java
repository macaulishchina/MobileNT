package com.sinoyd.environmentNT.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sinoyd.environmentNT.Entity.YZCommonSelectModel;
import com.sinoyd.environmentNT.R;

import java.util.List;

/**
 * Created by shenchuanjiang on 2017/9/7.
 */

public class CommonSelectorAdapter extends BaseAdapter<YZCommonSelectModel> {
    private List<YZCommonSelectModel> data;
    private Context context;

    public CommonSelectorAdapter(Context context, List<YZCommonSelectModel> data) {
        super(context, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = getLayoutInflater().inflate(R.layout.item_yz_common_selector, null);
            holder = new ViewHolder();
            holder.item_select_tv = (TextView) convertView.findViewById(R.id.item_select_tv);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        YZCommonSelectModel da = data.get(position);
        holder.item_select_tv.setText(da.getKey());

//        if (position % 2 == 1) {
//            convertView.setBackgroundResource(R.color.youliang_line);
//        } else {
//
//        }
        return convertView;
    }


    class ViewHolder {
        TextView item_select_tv;

    }
}
