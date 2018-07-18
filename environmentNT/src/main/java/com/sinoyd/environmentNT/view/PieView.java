package com.sinoyd.environmentNT.view;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.xclcharts.chart.PieChart;
import org.xclcharts.chart.PieData;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.view.GraphicalView;
import com.sinoyd.environmentNT.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 饼图View Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：PieView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class PieView extends GraphicalView {
	private String TAG = "PieView";
	private PieChart chart = new PieChart();
	Paint mPaintToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

	public PieView(Context context) {
		super(context);
		initView();
	}

	public PieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	public PieView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView();
	}

	private void initView() {
		chart.setLabelBrokenLineLength(10);
		chart.setLabelLineLength(2.4f);
		chartRender();
	}

	public void setData(LinkedList<PieData> mChartData) {
		// 设置图表数据源
		Log.i(TAG, "setData");
		if (chart.getDataSource() != null) {
			chart.getDataSource().clear();
		}
		// 设定数据源
		chart.setDataSource(mChartData);
		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 图所占范围大小
		chart.setChartRange(w, h);
	}

	public void chartRender() {
		try {
			Log.i(TAG, "chartRender");
			// 标签显示(隐藏，显示在中间，显示在扇区外面,折线注释方式)
			chart.setLabelPosition(XEnum.SliceLabelPosition.LINE);
			// 图的内边距
			// 注释折线较长，缩进要多些
			int[] ltrb = new int[4];
			ltrb[0] = DensityUtil.dip2px(getContext(), 40); // left
			ltrb[1] = DensityUtil.dip2px(getContext(), 40); // top
			ltrb[2] = DensityUtil.dip2px(getContext(), 40); // right
			ltrb[3] = DensityUtil.dip2px(getContext(), 40); // bottom
			chart.setPadding(ltrb[0], ltrb[1], ltrb[2], ltrb[3]);
			// 标题
			chart.setTitle("");
			chart.addSubtitle("");
			chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
			chart.getLabelPaint().setTextSize(getResources().getDimension(R.dimen.youliang_mintext_size));
			chart.getLabelPaint().setColor(Color.WHITE);
			// 隐藏渲染效果
			chart.hideGradient();
			// 显示边框
			// chart.showRoundBorder();
			// 激活点击监听
			// chart.ActiveListenItemClick();
			// chart.showClikedFocus();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
		}
	}

	public List<XChart> bindChart() {
		// TODO Auto-generated method stub
		List<XChart> lst = new ArrayList<XChart>();
		lst.add(chart);
		return lst;
	}

	@Override
	public void render(Canvas canvas) {
		// TODO Auto-generated method stub
		try {
			chart.render(canvas);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
