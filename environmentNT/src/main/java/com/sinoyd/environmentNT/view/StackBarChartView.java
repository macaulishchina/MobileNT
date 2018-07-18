package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.model.YouLiangMonths;

import org.xclcharts.chart.BarData;
import org.xclcharts.chart.StackBarChart;
import org.xclcharts.common.DensityUtil;
import org.xclcharts.common.IFormatterDoubleCallBack;
import org.xclcharts.common.IFormatterTextCallBack;
import org.xclcharts.renderer.XChart;
import org.xclcharts.renderer.XEnum;
import org.xclcharts.view.ChartView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 柱状图自定义视图 Copyright (c) 2015 江苏远大信息股份有限公司
 *
 * @类型名称：StackBarChartView
 * @创建人：张津明
 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class StackBarChartView extends ChartView {
    /**
     * 打印log
     **/
    private String TAG = "StackBarChartView";
    /**
     * 实例化柱状图视图
     **/
    private StackBarChart chart = new StackBarChart();
    /**
     * 标签集合
     **/
    List<String> chartLabels = new LinkedList<String>();
    /**
     * 数据集合
     **/
    List<BarData> BarDataSet = new LinkedList<BarData>();
    /**
     * 画笔
     **/
    Paint pToolTip = new Paint(Paint.ANTI_ALIAS_FLAG);

    public StackBarChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        initView();
    }

    public StackBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StackBarChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        chartRender();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 图所占范围大小
        chart.setChartRange(w, h);
    }

    // Demo中bar chart所使用的默认偏移值。
    // 偏移出来的空间用于显示tick,axistitle....
    protected int[] getBarLnDefaultSpadding() {
        int[] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 50); // left
        ltrb[1] = DensityUtil.dip2px(getContext(), 40); // top
        ltrb[2] = DensityUtil.dip2px(getContext(), 10); // right
        ltrb[3] = DensityUtil.dip2px(getContext(), 0); // bottom
        return ltrb;
    }

    /**
     * 绘图
     *
     * @功能描述：TODO
     * @创建者：张津明
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    private void chartRender() {
        try {
            // 设置绘图区默认缩进px值,留置空间显示Axis,Axistitle....
            int[] ltrb = getBarLnDefaultSpadding();
            chart.getPlotTitle().getSubtitlePaint().setTextSize(35);
            chart.setPadding(ltrb[0], ltrb[1], ltrb[2], DensityUtil.dip2px(getContext(), 55));
            chart.setChartDirection(XEnum.Direction.VERTICAL);
            chart.hideBarLabelItem();
            chart.getPlotLegend().hideLegend();
            // 数据源
            chart.setCategories(chartLabels);
            chart.setDataSource(BarDataSet);
            chart.getBar().setItemLabelVisible(false);
            //


            // 坐标系
            chart.getDataAxis().setTickMarksVisible(false);
            chart.getDataAxis().setAxisLineVisible(true);
            chart.getDataAxis().getAxisPaint().setStrokeWidth(1f);
            chart.getDataAxis().setAxisMax(30);
            chart.getDataAxis().setAxisMin(0);
            chart.getDataAxis().setAxisSteps(10);
            // 指定数据轴标签旋转-45度显示
            chart.getCategoryAxis().setTickLabelRotateAngle(-45f);
            chart.getCategoryAxis().setTickMarksVisible(false);
            Paint labelPaint = chart.getCategoryAxis().getTickLabelPaint();
            labelPaint.setTextAlign(Align.RIGHT);
            //labelPaint.setColor((int) Color.rgb(0, 75, 106));
            labelPaint.setColor(Color.WHITE);
            // 标题
            chart.setTitle("");
            chart.addSubtitle("优良天数月统计");
            chart.getPlotTitle().getSubtitlePaint().setColor(Color.WHITE);
            chart.getPlotTitle().getSubtitlePaint().setTextSize(getResources().getDimension(R.dimen.youliang_titletext_size));
            chart.setTitleAlign(XEnum.ChartTitleAlign.MIDDLE);
            chart.setTitleVerticalAlign(XEnum.VerticalAlign.MIDDLE);
            // 轴标题
            chart.getAxisTitle().setLeftAxisTitle("天数");
            chart.getAxisTitle().getLeftAxisTitlePaint().setColor(getResources().getColor(R.color.white));
            chart.getAxisTitle().getLeftAxisTitlePaint().setTextSize(getResources().getDimension(R.dimen.youliang_text_size));
            // 设置Y轴的数字大小
            chart.getDataAxis().getTickLabelPaint().setTextSize(getResources().getDimension(R.dimen.youliang_text_size));
            // 设置X轴的标签大小
            chart.getCategoryAxis().getTickLabelPaint().setTextSize(getResources().getDimension(R.dimen.youliang_text_size));
            // 设置纵坐标刻度为白线
            chart.getDataAxis().getTickLabelPaint().setColor(Color.WHITE);
            // 定义数据轴标签显示格式
            chart.getDataAxis().setLabelFormatter(new IFormatterTextCallBack() {
                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df = new DecimalFormat("#0");
                    Double tmp = Double.parseDouble(value);
                    String label = df.format(tmp).toString();
                    return label;
                }
            });
            // 定义标签轴标签显示格式
            chart.getCategoryAxis().setLabelFormatter(new IFormatterTextCallBack() {
                @Override
                public String textFormatter(String value) {
                    // TODO Auto-generated method stub
                    // String label = value.replace("-", "年") + "月";
                    String label = value;
                    return label;
                }
            });


            // 定义柱形上标签显示格式
            chart.getBar().setItemLabelVisible(true);
            chart.setItemLabelFormatter(new IFormatterDoubleCallBack() {
                @Override
                public String doubleFormatter(Double value) {
                    // TODO Auto-generated method stub
                    DecimalFormat df = new DecimalFormat("#0");
                    String label = df.format(value).toString();
                    return label;
                }
            });
            // 定义柱形上标签显示颜色
            // chart.getBar().getItemLabelPaint().setColor(Color.rgb(77, 184,
            // 73));
            chart.getPlotArea().extWidth(50);
            //仅能横向移动
            chart.setPlotPanMode(XEnum.PanMode.FREE);
            chart.getBar().getItemLabelPaint().setColor(Color.WHITE);
            chart.getBar().getItemLabelPaint().setTypeface(Typeface.DEFAULT_BOLD);
            chart.getPlotGrid().showHorizontalLines();
            chart.getPlotGrid().getHorizontalLinePaint().setStrokeWidth(0.5f);
            chart.getPlotGrid().setHorizontalLineStyle(XEnum.LineStyle.SOLID);
            chart.getPlotGrid().getHorizontalLinePaint().setColor(getResources().getColor(R.color.youliang_line));
            chart.showClikedFocus();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Log.e(TAG, e.toString());
        }
    }

    /**
     * 设置数据
     *
     * @param mYouLiangMonths
     * @功能描述：TODO
     * @创建者：刘敏
     * @创建日期：2015-1-26
     * @维护人员：
     * @维护日期：
     */
    public void setChartDataSet(List<YouLiangMonths> mYouLiangMonths) {
        BarDataSet.clear();
        chartLabels.clear();
        List<Double> dataSeriesYou = new LinkedList<Double>();
        List<Double> dataSeriesLiang = new LinkedList<Double>();
        List<Double> dataSeriesQindu = new LinkedList<Double>();
        List<Double> dataSeriesZhongdu1 = new LinkedList<Double>();
        List<Double> dataSeriesZhongdu2 = new LinkedList<Double>();
        List<Double> dataSeriesYanzhong = new LinkedList<Double>();
        if (mYouLiangMonths.size() < 10) {
            for (int i = 0; i < 10; i++) {
                if (i < mYouLiangMonths.size()) {
                    chartLabels.add(mYouLiangMonths.get(i).Month);
                    dataSeriesYou.add((double) mYouLiangMonths.get(i).Class1);
                    dataSeriesLiang.add((double) mYouLiangMonths.get(i).Class2);
                    dataSeriesQindu.add((double) mYouLiangMonths.get(i).Class3);
                    dataSeriesZhongdu1.add((double) mYouLiangMonths.get(i).Class4);
                    dataSeriesZhongdu2.add((double) mYouLiangMonths.get(i).Class5);
                    dataSeriesYanzhong.add((double) mYouLiangMonths.get(i).Class6);
                } else {
                    chartLabels.add("");
                    dataSeriesYou.add((double) 0);
                    dataSeriesLiang.add((double) 0);
                    dataSeriesQindu.add((double) 0);
                    dataSeriesZhongdu1.add((double) 0);
                    dataSeriesZhongdu2.add((double) 0);
                    dataSeriesYanzhong.add((double) 0);
                }
            }
        } else {
            for (int i = 0; i < mYouLiangMonths.size(); i++) {
                chartLabels.add(mYouLiangMonths.get(i).Month);
                dataSeriesYou.add((double) mYouLiangMonths.get(i).Class1);
                dataSeriesLiang.add((double) mYouLiangMonths.get(i).Class2);
                dataSeriesQindu.add((double) mYouLiangMonths.get(i).Class3);
                dataSeriesZhongdu1.add((double) mYouLiangMonths.get(i).Class4);
                dataSeriesZhongdu2.add((double) mYouLiangMonths.get(i).Class5);
                dataSeriesYanzhong.add((double) mYouLiangMonths.get(i).Class6);
            }
        }
        BarDataSet.add(new BarData("优", dataSeriesYou, (int) Color.rgb(1, 228, 1)));
        BarDataSet.add(new BarData("良", dataSeriesLiang, (int) Color.rgb(255, 255, 1)));
        BarDataSet.add(new BarData("轻度污染", dataSeriesQindu, (int) Color.rgb(254, 165, 0)));
        BarDataSet.add(new BarData("中度污染", dataSeriesZhongdu1, (int) Color.rgb(251, 0, 17)));
        BarDataSet.add(new BarData("重度污染", dataSeriesZhongdu2, (int) Color.rgb(155, 1, 73)));
        BarDataSet.add(new BarData("严重污染", dataSeriesYanzhong, (int) Color.rgb(114, 7, 37)));
    }

    @Override
    public void render(Canvas canvas) {
        try {
            chart.render(canvas);
        } catch (Exception e) {
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
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
