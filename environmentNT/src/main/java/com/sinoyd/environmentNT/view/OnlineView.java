package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sinoyd.environmentNT.Entity.GetOnlineInfosuper;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.adapter.OnlineValueAdapter;
import com.sinoyd.environmentNT.adapter.OnlineValueAdapter2;
import com.sinoyd.environmentNT.dialog.LevelExplainDialog;
import com.sinoyd.environmentNT.model.Online;

import org.json.JSONObject;

import java.util.List;

/**
 * 实时浓度view控件 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：OnlineView
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class OnlineView extends LinearLayout implements OnClickListener {
    private View baseView;
    private ListView mOnlineValueList;
    private TextView beizhu;
    private OnlineValueAdapter mAdapter;
    private OnlineValueAdapter2 mAdapter2;
    private Online mOnline;

    public OnlineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public OnlineView(Context context) {
        super(context);
        initView();
    }

    /***
     * 初始化加载
     */
    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        baseView = inflater.inflate(R.layout.include_online_viewpager, this);
        mOnline = new Online();
        mOnlineValueList = (ListView) baseView.findViewById(R.id.densityValueList);
        beizhu = (TextView) baseView.findViewById(R.id.beizhu);
        beizhu.setText(Html.fromHtml("<font color=white>(备注：记录条数为最近24小时记录条数)</font>"));


        mAdapter = new OnlineValueAdapter(getContext(), mOnline.values);
        mOnlineValueList.setAdapter(mAdapter);

    }

    /***
     * 设置数据
     *
     * @param jsonObject
     */
    public void setDensity(JSONObject jsonObject) {
        try {
            mOnline.parse(jsonObject);
            mAdapter.setList(mOnline.values);

            mOnlineValueList.setAdapter(mAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRefresh() {
    }

    public boolean isNotData() {
        return mOnline.values == null || mOnline.values.size() < 1;
    }

    @Override
    public void onClick(View v) {
        new LevelExplainDialog(getContext(), R.style.dialog).show();
    }


    /**********************************接口不按规则来，我也没办法*************************************************/
    public void setDensitysuper(List<GetOnlineInfosuper.OnlineInfoBean> onlineInfo) {

        mAdapter2 = new OnlineValueAdapter2(getContext(), onlineInfo);

        mOnlineValueList.setAdapter(mAdapter2);


    }
}
