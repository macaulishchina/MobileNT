package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.PullectUtils;

/**
 * 主界面柱状view Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：IndexLabelView
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class IndexLabelView extends RelativeLayout {
    private TextView tvValue, tvName;
    private View view;
    private int MainHeight;

    public IndexLabelView(Context context, final View Mainiew) {
        super(context);
        setGravity(Gravity.CENTER);
        View.inflate(getContext(), R.layout.index_label_item, this);


//		Mainiew.post(new Runnable() {
//			@Override
//			public void run() {
//
//
//			}
//		});
        Log.i("Mainiew.getHeight()", Mainiew.getHeight() + "");
        MainHeight = Mainiew.getHeight() * 2 / 3;

        tvValue = (TextView) findViewById(R.id.tvValue);
        tvName = (TextView) findViewById(R.id.tvName);
        view = findViewById(R.id.labelView);
    }

    /***
     * 设置view的值
     *
     * @param value
     */
    public void setValue(int value, int maxvalue) {
        if (value == -1)
            tvValue.setText("--");
        else
            tvValue.setText(value + "");
        tvName.setTextSize(getContext().getResources().getDimension(R.dimen.tvName_size));
        /*final float centerX = view.getWidth();
        final float centerY = view.getHeight();
		// 这个是设置需要旋转的角度，我设置的是180度
		RotateAnimation rotateAnimation = new RotateAnimation(0, 90, centerX, centerY);
		rotateAnimation.setDuration(1000 * 20);
		rotateAnimation.setFillAfter(true);
		view.startAnimation(rotateAnimation);*/
        try {
            view.setBackgroundResource(PullectUtils.getPullectBgByValue(value));
        } catch (Exception e) {
            // TODO: handle exception
            view.setBackgroundColor(getResources().getColor(R.color.white));
        }

//		view.setBackgroundResource(PullectUtils.getPullectBgByValue(value));
        RelativeLayout.LayoutParams viewParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        //viewParams.height = getH(value);
//		viewParams.height = getH(value);
        viewParams.height = getH(value, maxvalue);
        view.setLayoutParams(viewParams);
//		tvValue.setBackgroundResource(PullectUtils.getPullectBgByValue(value));
    }

    private int getH(int value, int maxvalue) {
        //int h = (int) (value /Float.parseFloat(getContext().getResources().getString(R.string.index_label_bili)));
//		int h=  (int)(value/500.00f *getContext().getResources().getDimension(R.dimen.realtimenew_pullect_number_minheight));
        int h = (int) (MainHeight * value * 1f / (maxvalue * 1f))/2;
        Log.i("hhhh", h + "");
        //	if (h > 100) {
        //h = 100;
        //}
        return h;
    }

    public void setName(CharSequence name) {
        tvName.setText("");
        tvName.append(name);
    }
}
