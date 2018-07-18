package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.iceman.paintdemo.JsonUtil;
import com.iceman.paintdemo.RecordData;
import com.iceman.paintdemo.RecordData.DataItem;
import com.iceman.paintdemo.RecordView;
import com.lidroid.xutils.util.LogUtils;
import com.sinoyd.environmentNT.AppConfig.RequestAirActionName;
import com.sinoyd.environmentNT.MyApplication;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.activity.AQIActivity;
import com.sinoyd.environmentNT.adapter.HealthTipAdapter;
import com.sinoyd.environmentNT.adapter.IndexDataAdapter;
import com.sinoyd.environmentNT.data.IndexDataInfo;
import com.sinoyd.environmentNT.database.WeatherDBModel;
import com.sinoyd.environmentNT.dialog.FactorDialog;
import com.sinoyd.environmentNT.dialog.FactorDialog.FactorItemSelectListener;
import com.sinoyd.environmentNT.http.HttpClient;
import com.sinoyd.environmentNT.http.HttpResponse;
import com.sinoyd.environmentNT.listener.HttpListener;
import com.sinoyd.environmentNT.model.DateInfo;
import com.sinoyd.environmentNT.model.HoursFactorDataInfo;
import com.sinoyd.environmentNT.model.PortHourAQIInfo;
import com.sinoyd.environmentNT.util.DateInfoo;
import com.sinoyd.environmentNT.util.DateUtil;
import com.sinoyd.environmentNT.util.HealTipUtil;
import com.sinoyd.environmentNT.util.PullectUtils;
import com.sinoyd.environmentNT.util.StringUtils;
import com.sinoyd.environmentNT.view.ColourBarView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.sinoyd.environmentNT.MyApplication.showTextToast;

/**
 * 实况界面(单个站点)
 *
 * @author zz
 */
public class RealTimeShowView extends LinearLayout implements OnClickListener, HttpListener, FactorItemSelectListener {
    TopSlidingLayout topSlidingLayout;
    private GridView gvIndexData, gvHealthTips;
    private IndexDataAdapter indexDataAdapter;
    private List<IndexDataInfo> indexDataList;
    private HealthTipAdapter healthTipAdapter;
    private LinearLayout recordViewLayout;
    private RecordView mRecordView;
    private RecordData record;
    private HoursFactorDataInfo factorListInfo;
    public ColourBarView mCursorView;
    private Button btnSelectedfactor;
    private Context mContext;
    private ImageView sprGene;
    private RelativeLayout pullect_number_layout;

    private LineChart mChart;
    /**
     * 所选因子名称
     **/
    public static String selectedFactor = "";
    public static boolean isTouchRecordView = false;

    public RealTimeShowView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private int getInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return -1;
        }
    }

    // public boolean isSliding(){
    // return topSlidingLayout.isSliding();
    // }
    private void initView() {
        record = new RecordData();
        record.yFields = JsonUtil.getYFiledItem(JsonUtil.getJsonFromAssets(getContext(), "YFiled.json"));
        View.inflate(getContext(), R.layout.include_realtime, this);
        loadRecordView();
        pullect_number_layout = (RelativeLayout) findViewById(R.id.pullect_number_layout);
        mChart = (LineChart) findViewById(R.id.chart_zhexiantu);
        btnSelectedfactor = (Button) findViewById(R.id.aqiair_sprGene);
        btnSelectedfactor.setTextColor(Color.WHITE);
        sprGene = (ImageView) findViewById(R.id.sprGene);
        btnSelectedfactor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showFactorDialog();
                LogUtils.d("========== onTouch  isTouchRecordView set true");
                isTouchRecordView = true;
            }
        });
        sprGene.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showFactorDialog();
                isTouchRecordView = true;
            }
        });
        topSlidingLayout = (TopSlidingLayout) findViewById(R.id.topSliding);
        mCursorView = ((ColourBarView) findViewById(R.id.cursorView));
// 		((TextView) findViewById(R.id.tvPrimaryName)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        findViewById(R.id.health_bg_layout).setOnClickListener(this);
        findViewById(R.id.health_layout).setOnClickListener(this);
        // findViewById(R.id.tvTip).setOnClickListener(this);
        gvIndexData = (GridView) findViewById(R.id.gvIndexData);
        gvHealthTips = (GridView) findViewById(R.id.gvHealthTips);
        indexDataList = new ArrayList<IndexDataInfo>(6);

        indexDataAdapter = new IndexDataAdapter(indexDataList);
        gvIndexData.setSelector(new ColorDrawable(Color.TRANSPARENT));

        gvIndexData.setAdapter(indexDataAdapter);
        gvHealthTips.setSelector(new ColorDrawable(Color.TRANSPARENT));
        ((TextView) findViewById(R.id.tvUnit)).setText(StringUtils.getUnit());


    }

    public void resetView() {
        // if (mCursorView != null)
        // mCursorView.smoothScrollTo(0, 1);
    }

    /***
     * 加载数据记录
     */
    private void loadRecordView() {
        if (factorListInfo == null) {
            factorListInfo = new HoursFactorDataInfo();
        }
        recordViewLayout = (LinearLayout) findViewById(R.id.aqi_chart_layout);
        recordViewLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                LogUtils.d("========== onTouch  isTouchRecordView set true");
                isTouchRecordView = true;
                return true;
            }
        });
        mRecordView = (RecordView) findViewById(R.id.record_view);
        mRecordView.setType(RecordView.RECORD_LINE);
        mRecordView.setShowColorVertical(false);
        mRecordView.setShowColorRect(true);
        mRecordView.setAutoPaddingType(true);
    }

    /***
     * 刷新图表
     *
     * @param aqiList
     */
    /*private void refreshRecordView(List<DateInfo> aqiList) {
        *//***
     * 初始化处理
     */
    /*
    if (record.dataList == null) {
	record.dataList = new ArrayList<RecordData.DataItem[]>();
	}
	else {
	record.dataList.clear();
	}
	int count = aqiList.size();
	int[] values = new int[count];
	for (int i = 0; i < count; i++) {
	values[i] = Integer.parseInt(aqiList.get(i).value);
	}
	record.yFields = JsonUtil.getCenterYFiledItem(JsonUtil.getMaxValue(values));
	DataItem[] data = new DataItem[aqiList.size()];
	DateInfo model = null;
	for (int i = 0; i < aqiList.size(); i++) {
	model = aqiList.get(i);
	DataItem item = new DataItem();
	item.data = Integer.parseInt(model.value);
	item.name = model.DateTime.split(" ")[1];
	item.interval = JsonUtil.getInterVal(item.data, record.yFields);
	data[i] = item;
	if (i == count - 1) { // 对--的数据进行处理
		if ("--".equalsIgnoreCase(((TextView) findViewById(R.id.tvPrimaryName)).getText().toString())) {
			((TextView) findViewById(R.id.tvPrimaryValue)).setText("--");
		}
		else {
			((TextView) findViewById(R.id.tvPrimaryValue)).setText(model.value);
		}
	}
	}
	record.dataList.add(JsonUtil.formatDataItem(data)); // 添加到列表中
	if (mRecordView != null) {
	recordViewLayout.removeView(mRecordView);
	mRecordView = null;
	}
	mRecordView = new RecordView(getContext());
	mRecordView.setOnTouchListener(new OnTouchListener() {
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		isTouchRecordView = true;
		return false;
	}
	});
	mRecordView.setType(RecordView.RECORD_LINE);
	mRecordView.setShowColorVertical(false); // 设置颜色属性
	mRecordView.setShowColorRect(true);
	mRecordView.setAutoPaddingType(false);
	LinearLayout.LayoutParams chartLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	chartLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
	LogUtils.d("======= 111111111111");
	recordViewLayout.addView(mRecordView, chartLayoutParams);
	mRecordView.setData(record); // 加载到界面并且进行渲染
	}*/

    /***
     * 刷新24小时AQI图表
     *
     * @param aqiList
     */
    private void refresh24HourAQIRecordView(List<DateInfo> aqiList) {
        if (record.dataList == null) {
            record.dataList = new ArrayList<RecordData.DataItem[]>();
        } else {
            record.dataList.clear();
        }


        /**更新UI，2017年9月30日，scj 新增代码,重新写折线图控件,因为有滑动监听冲突*/
//        updateUIzhexiantu(aqiList);
        /**更新UI，2017年9月30日，scj 新增代码*/


        int count = aqiList.size();
        float[] values = new float[count];
        if ("co".equalsIgnoreCase(selectedFactor)) {
            for (int i = 0; i < count; i++) {
                try {
                    values[i] = (float) (Double.parseDouble(aqiList.get(i).value) * 10);
                } catch (Exception e) {
                    // TODO: handle exception
                    values[i] = -1;
                }

            }
        } else {
            for (int i = 0; i < count; i++) {
                try {
                    values[i] = Float.parseFloat(aqiList.get(i).value);
                } catch (Exception e) {
                    // TODO: handle exception
                    values[i] = -1;
                }

            }
        }
        record.yFields = JsonUtil.getCenterYFiledItem(JsonUtil.getMaxValue(values));
        DataItem[] data = new DataItem[aqiList.size()];
        DateInfo model = null;
        for (int i = 0; i < aqiList.size(); i++) {
            model = aqiList.get(i);
            DataItem item = new DataItem();
            if ("co".equalsIgnoreCase(selectedFactor)) {
                try {
                    item.data = (int) (Double.parseDouble(aqiList.get(i).value) * 10);
                } catch (Exception e) {
                    // TODO: handle exception
                    item.data = -1;
                }

            } else {

                try {
                    item.data = Integer.parseInt(model.value);
                } catch (Exception e) {
                    // TODO: handle exception
                    item.data = -1;
                }
            }
            item.name = model.DateTime.split(" ")[1];
            item.interval = JsonUtil.getInterVal(item.data, record.yFields);
            data[i] = item;
            if (i == count - 1) { // 对--的数据进行处理
                /*if ("--".equalsIgnoreCase(((TextView) findViewById(R.id.tvPrimaryName))
                        .getText().toString())) {
					((TextView) findViewById(R.id.tvPrimaryValue))
							.setText("--");
				} else {
					((TextView) findViewById(R.id.tvPrimaryValue))
							.setText(model.value);
				}*/
            }
        }
        record.dataList.add(JsonUtil.formatDataItem(data)); // 添加到列表中
        if (mRecordView != null) {
            recordViewLayout.removeView(mRecordView);
            mRecordView = null;
        }
        mRecordView = new RecordView(getContext());
        mRecordView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                isTouchRecordView = true;
                return false;
            }
        });
        if ("co".equalsIgnoreCase(selectedFactor)) {
            mRecordView.setCO(true);
        }
        mRecordView.setType(RecordView.RECORD_LINE);
        mRecordView.setShowColorVertical(false);
        mRecordView.setShowColorRect(false);
        mRecordView.setAutoPaddingType(true);
        LinearLayout.LayoutParams chartLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recordViewLayout.addView(mRecordView, chartLayoutParams);
        mRecordView.setLineWidth(getResources().getInteger(R.integer.chart_y_linewidth));

        mRecordView.setData(record); // 加载到界面并且进行渲染
        if (aqiList.size() == 0) {
            recordViewLayout.removeView(mRecordView);
        }

        mRecordView.post(new Runnable() {
            @Override
            public void run() {
                int changdu = mRecordView.getWidth();

                mRecordView.mDrawOffset = -((int) Math.round(changdu * 1.6));
                mRecordView.mSaveOffset = -((int) Math.round(changdu * 1.6));
                mRecordView.postInvalidate();
            }
        });

    }


    private void updateUIzhexiantu(final List<DateInfo> aqiList) {


//        mChart.setOnChartGestureListener(this);
//        mChart.setOnChartValueSelectedListener(this);
        mChart.setDrawGridBackground(false);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mChart.setScaleXEnabled(true);
        // mChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        // mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        // x-axis limit line
//        LimitLine llXAxis = new LimitLine(10f, "Index 10");
//        llXAxis.setLineWidth(4f);
//        llXAxis.enableDashedLine(10f, 10f, 0f);
//        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        xAxis.enableGridDashedLine(10f, 0f, 0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return DateInfoo.getriqiforstring2(aqiList.get((int) value).DateTime);
            }
        });
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setGridColor(Color.WHITE);
        xAxis.setAxisLineColor(Color.WHITE);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line
////        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
//
//        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//        ll1.setLineWidth(4f);
//        ll1.enableDashedLine(10f, 10f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//        ll1.setTextSize(10f);
////        ll1.setTypeface(tf);
//
//        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//        ll2.setLineWidth(4f);
//        ll2.enableDashedLine(10f, 10f, 0f);
//        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//        ll2.setTextSize(10f);
////        ll2.setTypeface(tf);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
//        leftAxis.setAxisMaximum(100f);
        leftAxis.setAxisMinimum(0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 0f, 0f);
        leftAxis.setDrawZeroLine(false);
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setGridColor(Color.WHITE);
        leftAxis.setAxisLineColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mChart.getViewPortHandler().setMaximumScaleY(2f);
        //mChart.getViewPortHandler().setMaximumScaleX(2f);

        // add data
        setData(aqiList);

//        mChart.setVisibleXRange(20);
//        mChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500);
        //mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChart.invalidate();

    }


    private void setData(final List<DateInfo> aqiList) {
        float val;
        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < aqiList.size(); i++) {
            if (aqiList.get(i).value.equals("--")) {
                val = 0.0f;
            } else {

                val = Float.parseFloat(aqiList.get(i).value);
            }


            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 0f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.WHITE);
            set1.setCircleColor(Color.rgb(172, 231, 255));
            set1.setDrawCircles(true);//图表上的数据点是否用小圆圈表示
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setCircleHoleRadius(2f);
            set1.setCircleColorHole(Color.rgb(13, 89, 157));
            set1.setDrawCircleHole(true);
            set1.setValueTextSize(9f);
//            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setValueTextColor(Color.WHITE);
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return aqiList.get((int) entry.getX()).value;
                }
            });
//
//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//                set1.setFillDrawable(drawable);
//            }
//            else {
            set1.setFillColor(Color.BLACK);
//            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets
            // create a data object with the datasets
            LineData data = new LineData(dataSets);
            // set data
            mChart.setData(data);
        }
    }

    /***
     * 更新界面上得数据 该方法属于基本的操作，对view进行赋值操作
     *
     * @param info
     */
    public void updateData(PortHourAQIInfo info) {
        ((TextView) findViewById(R.id.tvHealtip)).setText(AQIActivity.heal_tips[PullectUtils.ValueByLevel(getInt(info.AQI))]);
        healthTipAdapter = new HealthTipAdapter(HealTipUtil.getTipData(getInt(info.AQI)));
        gvHealthTips.setAdapter(healthTipAdapter);
        String lastTime = DateUtil.formatDate(DateUtil.getDate(info.DateTime, ""), "yyyy年MM月dd日 HH时");
        ((TextView) findViewById(R.id.date)).setText(lastTime);
        // ((TextView) findViewById(R.id.date)).setText(DateUtil
        // .getCurrentTime("yyyy年MM月dd日 HH时"));
        ((TextView) findViewById(R.id.tvAQI)).setText(info.AQI);
        ((TextView) findViewById(R.id.tvClass)).setText(info.Class);
        try {
            ((TextView) findViewById(R.id.tvAQI)).setTextColor(Color.parseColor(info.RGBValue));
            GradientDrawable myGrad = (GradientDrawable) ((LinearLayout) findViewById(R.id.lv2AQI)).getBackground();
            myGrad.setColor(Color.parseColor(info.RGBValue));
            if (info.Class.equals("良")) {
                ((TextView) findViewById(R.id.tvClass)).setTextColor(Color.parseColor("#4b2e01"));
                ((TextView) findViewById(R.id.aqi_tip)).setTextColor(Color.parseColor("#4b2e01"));
            } else {
                ((TextView) findViewById(R.id.tvClass)).setTextColor(Color.WHITE);
                ((TextView) findViewById(R.id.aqi_tip)).setTextColor(Color.WHITE);
            }
            ((TextView) findViewById(R.id.tvClass)).setText(info.Class);
        } catch (Exception e) {
            // TODO: handle exception
//			((TextView) findViewById(R.id.tvAQI)).setTextColor(Color.parseColor(info.RGBValue));
        }

        String ppName = "--".equals(info.PrimaryPollutant) ? "PM2.5" : info.PrimaryPollutant;
        if (ppName.equalsIgnoreCase("--")) {
            if (MyApplication.factorAirList.size() > 0) {
                selectedFactor = MyApplication.factorAirList.get(0);
            }
        } else {
            selectedFactor = ppName;
            for (int i = 0; i < MyApplication.factorAirList.size(); i++) {
                if (MyApplication.factorAirList.get(i).equals(ppName)) {
                    selectedFactor = ppName;
                    //scj 2017年8月11日修改
//					selectedFactor = MyApplication.factorAirList.get(4);
//					if ("".equals(selectedFactor))
                    break;
                }
            }
        }

        if (selectedFactor.equals("PM10"))
            btnSelectedfactor.setText(StringUtils.getPM10());
        if (selectedFactor.equals("PM2.5"))
            btnSelectedfactor.setText(StringUtils.getPM25());
        if (selectedFactor.equals("O3"))
            btnSelectedfactor.setText(StringUtils.getO3());
        if (selectedFactor.equals("NO2"))
            btnSelectedfactor.setText(StringUtils.getNO2());
        if (selectedFactor.equals("SO2"))
            btnSelectedfactor.setText(StringUtils.getSO2());
        if (selectedFactor.equals("CO"))
            btnSelectedfactor.setText(StringUtils.getCO());


        ((TextView) findViewById(R.id.tvPrimaryName)).setText(info.PrimaryPollutant);

        if (info.PrimaryPollutant.equals("PM10"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getPM10());
        if (info.PrimaryPollutant.equals("PM2.5"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getPM25());
        if (info.PrimaryPollutant.equals("O3"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getO3());
        if (info.PrimaryPollutant.equals("NO2"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getNO2());
        if (info.PrimaryPollutant.equals("SO2"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getSO2());
        if (info.PrimaryPollutant.equals("CO"))
            ((TextView) findViewById(R.id.tvPrimaryName)).setText(StringUtils.getCO());


//		btnSelectedfactor.setText(selectedFactor);

        if ("co".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvTitle)).setText(String.format(getContext().getString(R.string.pm25_title), ppName, getContext().getString(R.string.unit_co)));

        } else {
            ((TextView) findViewById(R.id.tvTitle)).setText(String.format(getContext().getString(R.string.pm25_title), ppName, getContext().getString(R.string.unit_pm)));

        }
        if ("PM2.5".equalsIgnoreCase(info.PrimaryPollutant)) { // 根据不同的数据进行显示
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.PM25_IAQI);
        } else if ("PM10".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.PM10_IAQI);
        } else if ("SO2".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.SO2_IAQI);
        } else if ("NO2".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.NO2_IAQI);
        } else if ("CO".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.CO_IAQI);
        } else if ("O3".equalsIgnoreCase(info.PrimaryPollutant)) {
            ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.O3_IAQI);
        } else {
            if ("--".equalsIgnoreCase(info.PrimaryPollutant)) {
                ((TextView) findViewById(R.id.tvPrimaryValue)).setText("--");
            } else {
                ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.PM25_IAQI);
            }
        }

        ((TextView) findViewById(R.id.tvPrimaryValue)).setText(info.PrimaryPollutantValue);

        mCursorView.setCurrentValue(getInt(info.AQI));
        ((TextView) findViewById(R.id.tvLevel)).setText(info.Class);
        try {
            ((TextView) findViewById(R.id.tvLevel)).setTextColor(Color.parseColor(info.RGBValue));
        } catch (Exception e) {
            // TODO: handle exception
        }

        ((ImageView) findViewById(R.id.logo)).setImageResource(PullectUtils.getPetLuBgByValue(getInt(info.AQI)));
        indexDataList.clear();
        // indexDataList.add(new IndexDataInfo(StringUtils.getPM10(), 400));
        indexDataList.add(new IndexDataInfo(StringUtils.getPM10(), getInt(info.PM10_IAQI)));
        indexDataList.add(new IndexDataInfo(StringUtils.getPM25(), getInt(info.PM25_IAQI)));
        indexDataList.add(new IndexDataInfo(StringUtils.getNO2(), getInt(info.NO2_IAQI)));
        indexDataList.add(new IndexDataInfo(StringUtils.getSO2(), getInt(info.SO2_IAQI)));
        indexDataList.add(new IndexDataInfo(StringUtils.getO3(), getInt(info.O3_IAQI)));
        indexDataList.add(new IndexDataInfo(StringUtils.getCO(), getInt(info.CO_IAQI)));
        gvIndexData.setNumColumns(indexDataList.size());
        indexDataAdapter.notifyDataSetChanged(); // 更新列表数据
    }

    @Override
    public void onClick(View v) {
        if (v == null)
            return;
        switch (v.getId()) {
            case R.id.health_bg_layout:
            case R.id.health_layout:
                if (topSlidingLayout.isOpenDown())
                    topSlidingLayout.closeDown();
                else
                    topSlidingLayout.openDown();
                break;
            case R.id.tvTip:
                // if (findViewById(R.id.viewTip).getVisibility() != View.VISIBLE)
                // findViewById(R.id.viewTip).setVisibility(View.VISIBLE);
                // else
                // findViewById(R.id.viewTip).setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    public void closeDown() {
        topSlidingLayout.closeDown();
    }

    // TODO test
    public void openDown() {
        topSlidingLayout.openDown();
    }

    public void setWeather(WeatherDBModel weatherResult) {

        ((TextView) findViewById(R.id.city_name)).setText(weatherResult.getCity() + "市天气");
        ((TextView) findViewById(R.id.fengxiang)).setText(weatherResult.getWindDirectionStart());
        ((TextView) findViewById(R.id.wendu)).setText(weatherResult.getTempMin() + "℃～" + weatherResult.getTempMax() + "℃");
        ((TextView) findViewById(R.id.shidu)).setText(weatherResult.getWeatherStart() + "转" + weatherResult.getWeatherEnd());

    }

	/*public void setChartJson(JSONObject json) {
        try {
			factorListInfo.parse(json);
			List<DateInfo> tempList = factorListInfo.HoursFactorData;
			LogUtils.d("======== setChartJson  tempList size: " + tempList.size());
			if (tempList != null && tempList.size() > 0) {
				refreshRecordView(tempList);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}*/

    /**
     * 解析24小时AQI值并且刷新24小时AQI值曲线图
     *
     * @param json
     */
    public void set24HourAQIChartJson(JSONObject json) {
        try {
            factorListInfo.parse(json);
            List<DateInfo> tempList = factorListInfo.HoursFactorData;
            if (tempList != null && tempList.size() > 0) {
                refresh24HourAQIRecordView(tempList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startMarq() {
        System.out.println("==start");
        findViewById(R.id.tvHealtip).setFocusable(true);
        ((TextView) findViewById(R.id.tvHealtip)).setEllipsize(TruncateAt.END);
        ((TextView) findViewById(R.id.tvHealtip)).setEllipsize(TruncateAt.MARQUEE);
        ((TextView) findViewById(R.id.tvHealtip)).setMarqueeRepeatLimit(-1);
        findViewById(R.id.tvHealtip).setFocusableInTouchMode(true);
        findViewById(R.id.tvHealtip).setSelected(true);
    }

    public void updateFace() {
//		findViewById(R.id.tvPrimaryName).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.first_pullect_value_bg));
//		findViewById(R.id.health_layout).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.health_prompt_bg));
//		findViewById(R.id.splitline).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.line));
//		findViewById(R.id.weather_layout).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.city_info_line));
//		findViewById(R.id.aqi_chart_layout).setBackgroundDrawable(StyleController.getDrawableByAttrId(R.attr.aqi_chart_bg));
    }

    /**
     * 显示因子选择对话框
     */
    public void showFactorDialog() {
        FactorDialog dialog = new FactorDialog(mContext, R.style.dialog, MyApplication.factorAirList);
        dialog.setFactorItemSelectListener(this);
        dialog.show();
    }

    /**
     * 选择因子
     */
    @Override
    public void selectFactorinfo(String selectFactorInfo) {
        selectedFactor = selectFactorInfo;
        if (btnSelectedfactor != null) {
            btnSelectedfactor.setText(selectedFactor);
            if (selectedFactor.equals("PM10"))
                btnSelectedfactor.setText(StringUtils.getPM10());
            else if (selectedFactor.equals("PM2.5"))
                btnSelectedfactor.setText(StringUtils.getPM25());
            else if (selectedFactor.equals("O3"))
                btnSelectedfactor.setText(StringUtils.getO3());
            else if (selectedFactor.equals("NO2"))
                btnSelectedfactor.setText(StringUtils.getNO2());
            else if (selectedFactor.equals("SO2"))
                btnSelectedfactor.setText(StringUtils.getSO2());
            else if (selectedFactor.equals("CO"))
                btnSelectedfactor.setText(StringUtils.getCO());

        }
        if (selectedFactor.equals("CO")) {
            ((TextView) findViewById(R.id.sssjdanwei)).setText("浓度单位:mg/m³");
        } else {
            ((TextView) findViewById(R.id.sssjdanwei)).setText("浓度单位:ug/m³");
        }

        request24HourAQIRecord();
    }

    /***
     * 请求24小时因子浓度接口方法
     */
    public void request24HourAQIRecord() {
        HashMap<String, String> chartsParams = new HashMap<String, String>();
        chartsParams.put("portId", MyApplication.currentAirPortInfo.PortId);
        chartsParams.put("factorName", selectedFactor);
        HttpClient.getJsonWithGetUrl(RequestAirActionName.Get24HoursFactorDataAir, chartsParams, this, null);
    }

    @Override
    public void requestSuccess(HttpResponse resData) {
        if (RequestAirActionName.Get24HoursFactorDataAir.equals(resData.getUri())) { // 24小时浓度污染
            if (AQIActivity.listViewPager.size() < 1)
                return;
            RealTimeShowView showView = AQIActivity.listViewPager.get(AQIActivity.currentItem);
            showView.set24HourAQIChartJson(resData.getJson());
            showView.mCursorView.setCurrentValue(showView.mCursorView.getCurrentValue());// 设置当前污染物因子的AQI值
            return;
        }
    }

    @Override
    public void requestFailed(HttpResponse resData) {
        showTextToast("暂无数据，请稍后刷新!");
        recordViewLayout.removeView(mRecordView);
    }
}
