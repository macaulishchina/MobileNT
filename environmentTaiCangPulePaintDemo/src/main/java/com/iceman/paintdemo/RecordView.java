package com.iceman.paintdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iceman.paintdemo.RecordData.DataItem;
import com.iceman.paintdemo.RecordData.YField;

import java.util.ArrayList;
import java.util.Arrays;

public class RecordView extends View {
    /**
     * 隐藏该点
     */
    public static final int HIDE_POINT = -100;
    /**
     * 整体高度
     */
    private int mHeight;
    /**
     * 整体宽度
     */
    private int mWidth;
    /**
     * 数据源
     */
    private RecordData mData;
    /**
     * 绘图类型
     */
    private int type;
    /**
     * 根据数据计算出来所需要的宽度
     */
    private int mCalculateWidth;
    /**
     * 右边距
     */
    private int mRightPanding;
    /**
     * 绘制偏移量
     */
    public int mDrawOffset = 0;
    /**
     * 单根线模式
     */
    public static final int RECORD_LINE = 1;
    /**
     * AQI页面的CO浓度曲线图
     **/
    public static final int RECORD_CO_DENSITY_LINE = 8;
    /**
     * 柱形图模式
     */
    public static final int RECORD_COLUMN = 2;
    /**
     * 多线模式
     */
    public static final int RECORD_MULTI_LINE = 3;
    /**
     * 没有Y轴的图表
     */
    public static final int RECORD_NOT_Y_LINE = 4;
    /**
     * 显示水结构单线
     */
    public static final int RECORD_WATER_LINE = 5;
    /**
     * 实时水质柱形图
     */
    public static final int RECORD_RealTimeWater_COLUMN = 6;
    /**
     * 显示实时水质单线
     */
    public static final int RECORD_RealTimeWater_LINE = 7;

    /****显示AQI折线图 苏州定制加入横纵坐标显示值***/
    public static final int RECORD_AQI_LINE = 9;
    /****显示AQI柱状图 苏州定制加入横纵坐标显示值***/
    public static final int RECORD_AQI_COLUMN = 10;
    /**
     * 画笔
     */
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    /**
     * 绘图主体区域在Y方向上划分的格数
     */
    private int mYLines;
    /**
     * Y轴显示的数字
     */
    private ArrayList<Integer> mYStrings = new ArrayList<Integer>();
    /**
     * 画文字的时候,高度偏移量
     */
    private float mTextHeightOffset;
    /**
     * Y轴不同阶段所用的颜色
     */
    private int[] mPaintColors = new int[]{0xff03e000, 0xffffff00, 0xffff6b04, 0xfffb0200, 0xff86013a, 0xff670712};
    private int[] mWaterColors = {0xff00ffff, 0xff00ffff, 0xff00ff00, 0xffffff00, 0xffff7900, 0xffff0000};
    private int[] mAirColors = {0xff03e000, 0xffffff00, 0xffff6b04, 0xfffb0200, 0xff86013a, 0xff670712};
    /**
     * 多线的时候使用,每根线使用的颜色
     */
    private int[] mBottomIntroduceColors = new int[]{0xfffbba00, 0xff88c700, 0xff0cb7d3, Color.MAGENTA};
    private int mChartColor = Color.rgb(66, 184, 230);
    /**
     * 中文字的高度
     */
    private int mTextHeight;
    /**
     * 手指按下的x坐标
     */
    private int downX;
    /**
     * 移动的x方向位移
     */
    private int moveX;
    /**
     * 移动距离
     */
    private int distance;
    /**
     * 上次保存的移动距离
     */
    public int mSaveOffset;
    /**
     * Y方向上的字段
     */
    private YField[] mYField;
    /**
     * x方向上单格宽度
     */
    private int mSingleExcelWidth;
    /**
     * 屏幕上可滑动区域的宽度
     */
    private int mScrollAreaWidth;
    /**
     * 画多根线的时候用,表示下面要用几行
     */
    private int mBottomRowNum;
    /**
     * 是否显示色带
     */
    private boolean mShowColorVertical;
    /*** 滑动区域右边填充 */
    private int mPaddingRightWidth = 0;
    /**
     * 单线底部距离右边的边距
     */
    private int explainRectMarginRight = getPxFromDip(5);
    /**
     * 线条的宽度
     */
    private int mLineThick = 2;
    /**
     * 是否全部显示X周的线条
     */
    private boolean mShowGridX = false;
    /**
     * 是否显示图表底部的方框指示
     */
    private boolean mShowColorRect;
    /**
     * 界面填充模式，自动填充或者指定高度填充
     */
    private boolean mAutoPaddingType = true;
    /**
     * Y轴上显示罗马数字
     */
    private boolean mYNameRomanValue = false;
    private int mTextColor = Color.BLACK;
    private int mLineColor = Color.BLACK;
    private float sectionValue = 50;
    /**
     * X轴上可视范围多少个分段
     */
    private int xLineCount = 10;
    /**
     * 当设置mShowColorVertical为false的情况下 ,是否显示Y轴刻度坐标不显示
     **/
    private boolean isShowYKeduLine = true;
    /**
     * 图表坐标轴线条的宽度 默认为1
     **/
    private int lineWidth = 1;
    /**
     * 实时水质的柱形图的个数 1280*720值为13，960*540值为15 1920*1080值为11
     **/
    private int columnNumber = 13;
    /**
     * 实时AQI的判断是否为CO
     **/
    private boolean isCO = false;
    /**
     * 实时水质的浓度的因子名称
     **/
    private String factorName = "pH值";

    public String getFactorName() {
        return factorName;
    }

    public void setFactorName(String factorName) {
        this.factorName = factorName;
    }

    public boolean isCO() {
        return isCO;
    }

    public void setCO(boolean isCO) {
        this.isCO = isCO;
    }

    /**
     * 实时水质的柱形图的个数 1280*720值为13，960*540值为15 1920*1080值为11
     *
     * @return
     */
    public int getColumnNumber() {
        return columnNumber;
    }

    /**
     * 实时水质的柱形图的个数 1280*720值为13，960*540值为15 1920*1080值为11
     *
     * @param columnNumber
     */
    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }

    public int getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSectionValue(float sectionValue) {
        this.sectionValue = sectionValue;
    }

    /**
     * 当设置mShowColorVertical为false的情况下 ,是否显示Y轴刻度坐标不显示
     */
    public void setHideYKedu() {
        isShowYKeduLine = false;
    }

    /**
     * 设置X轴上的分段
     */
    public void setXLineCount(int xLineCount) {
        this.xLineCount = xLineCount;
    }

    /**
     * 设置Y轴画笔用水背景色还是气背景色
     *
     * @param YNameRomanValue
     */
    public void setYNameRomanValue(boolean YNameRomanValue) {
        this.mYNameRomanValue = YNameRomanValue;
        if (mYNameRomanValue) {
            mPaintColors = mWaterColors;
        } else {
            mPaintColors = mAirColors;
        }
    }

    public void setChartColor(int mChartColor) {
        this.mChartColor = mChartColor;
    }

    /**
     * 是否显示Y轴色带
     *
     * @param showVertical
     */
    public void setShowColorVertical(boolean showVertical) {
        this.mShowColorVertical = showVertical;
        if (Globe.fullScreenWidth < 500) { // 小于或者等于480情况
            mPaddingRightWidth = mShowColorVertical ? -45 : -15;
        } else {
            boolean b = (Globe.fullScreenWidth >= 500) && (Globe.fullScreenWidth <= 600);
            if (b) {
                mPaddingRightWidth = mShowColorVertical ? -125 : -25;
            } else {
                mPaddingRightWidth = mShowColorVertical ? -165 : -35;
            }
        }
    }

    /**
     * 是否显示图表底部的方框指示
     *
     * @param mShowColorRect
     */
    public void setShowColorRect(boolean mShowColorRect) {
        this.mShowColorRect = mShowColorRect;
    }

    /***
     * 填充模式
     *
     * @param
     */
    public void setAutoPaddingType(boolean autoPaddingType) {
        this.mAutoPaddingType = autoPaddingType;
    }

    public void setShowVerticalRefresh(boolean showVertical) {
        setShowColorVertical(showVertical);
        mSaveOffset = mDrawOffset = 0;
        invalidate();
    }

    public void setTypeRefresh(int type) {
        setType(type);
        mSaveOffset = mDrawOffset = 0;
        invalidate();
    }

    public void setData(RecordData data) {
        mYField = data.yFields;
        mData = data;
        switch (type) {
            case RECORD_LINE:
            case RECORD_CO_DENSITY_LINE:
            case RECORD_AQI_LINE:
            case RECORD_COLUMN:
            case RECORD_AQI_COLUMN:
            case RECORD_WATER_LINE:
            case RECORD_RealTimeWater_COLUMN:
                mYStrings.clear();
                mYStrings.add(0);
                int num = 0;
                mYLines = 0;
                for (int i = 0; i < mYField.length; i++) {
                    num += mYField[i].span;
                    mYLines += mYField[i].span / sectionValue;
                    mYStrings.add(num);
                }
                break;
            case RECORD_RealTimeWater_LINE:
                mYStrings.clear();
                mYStrings.add(0);
                num = 0;
                mYLines = 0;
                mYLines = mYField.length;
                for (int i = 0; i < mYField.length; i++) {
                    num += mYField[i].span;
//				mYLines += mYField[i].span / sectionValue;
                    mYStrings.add(num);
                }
                break;
            case RECORD_MULTI_LINE:
                mYStrings.clear();
                mBottomRowNum = data.dataList.size() / 2 + 1;
                mYLines = mYField.length;
                mYStrings.add(0);
                num = 0;
                for (int i = 0; i < mYField.length; i++) {
                    num += mYField[i].span;
                    mYStrings.add(num);
                }
                break;
            default:
                break;
        }
        mSaveOffset = mDrawOffset = 0;
        invalidate();
    }

    public RecordView(Context context) {
        super(context);
        init(context);


    }

    public RecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setShowColorVertical(true);
        setShowColorRect(false);
        setAutoPaddingType(true);
        mPaint.setTextSize(getPxFromDip(15));
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setStyle(Style.FILL);
        mTextHeightOffset = mPaint.getFontMetrics().bottom;
        mTextHeight = (int) (mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().ascent);
        mRightPanding = getPxFromDip(5);


    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);


        if (getType() == 9) {
            moveX = 400;

        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        switch (type) {
            case RECORD_CO_DENSITY_LINE:
                mPaint.setStrokeWidth((float) lineWidth);
                paintLineView(canvas);
                break;
            case RECORD_LINE:
                mPaint.setStrokeWidth((float) lineWidth);
                paintLineView(canvas);
                break;
            case RECORD_COLUMN:
                paintColumnView(canvas);
                break;
            case RECORD_RealTimeWater_COLUMN:
                paintRealTimeWaterColumnView(canvas);
                break;
            case RECORD_RealTimeWater_LINE:
                mPaint.setStrokeWidth((float) lineWidth);
                paintDensityLineView(canvas);
                break;
            case RECORD_MULTI_LINE:
                paintMultiLineView(canvas);
                break;
            case RECORD_WATER_LINE:
                paintWaterLineView(canvas);
                break;
            case RECORD_AQI_LINE:
                //趋势
                mPaint.setStrokeWidth((float) lineWidth);
                paintAQILineView(canvas);


                break;
            case RECORD_AQI_COLUMN:
                mPaint.setStrokeWidth((float) lineWidth);
                paintAQIColumnView(canvas);
                break;

            default:
                break;
        }

    }

    /**
     * 多线
     *
     * @param canvas
     */
    private void paintMultiLineView(Canvas canvas) {
        if (mYLines == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        int bottomWidth = mWidth / (mYField.length + 2);
        int mYWidth = (int) mPaint.measureText("00000");
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }
        int xLineHeight;
        if (mAutoPaddingType) {
            xLineHeight = mHeight - 7 * mTextHeight;
        } else {
            xLineHeight = mHeight - 7 * mTextHeight;
        }
        int yRectWidth = getPxFromDip(5);
        mScrollAreaWidth = mWidth - mYWidth - yRectWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xLineHeight - mTextHeight / 2) / mYLines;
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(mYWidth, (int) (xLineHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), mYWidth + yRectWidth, xLineHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span / sectionValue * singleExcelHeight;
        }
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        if (!mShowColorVertical) {
            mPaint.setColor(mLineColor);
            canvas.drawLine(mYWidth - mYWidth / 7, 0, mYWidth - mYWidth / 7, xLineHeight, mPaint);
        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }

        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);

            canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : String.valueOf(i * sectionValue), mYWidth - mYWidth / 5, xLineHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
            }
        }
        mPaint.setTextAlign(Align.LEFT);
        // for (int i = 0; i < mData.dataList.size(); i++) {
        // mPaint.setColor(mBottomIntroduceColors[i]);
        // if (i % 2 == 0) {
        // canvas.drawText("● " + mData.dataListNames.get(i), mWidth / 6,
        // xLineHeight + mTextHeight * (i / 2 + 2) - mTextHeightOffset, mPaint);
        // } else {
        // canvas.drawText("● " + mData.dataListNames.get(i), mWidth * 4 / 6,
        // xLineHeight + mTextHeight * (i / 2 + 2) - mTextHeightOffset, mPaint);
        // }
        // }

        int circleRadius = getPxFromDip(4);
        canvas.save();
        canvas.clipRect(mYWidth + yRectWidth, mTextHeight / 2, mWidth, xLineHeight + mTextHeight * 3);
        for (int i = 0; i < mData.dataList.size(); i++) {
            DataItem[] items = mData.dataList.get(i);
            for (int j = 0; j < items.length; j++) {
                mPaint.setColor(mTextColor);
                int xPositin = mYWidth + yRectWidth + mSingleExcelWidth * (j + 1) + mDrawOffset;
                DataItem item = items[j];
                // if (i % 3 == 0) {
                mPaint.setTextAlign(Align.CENTER);
                canvas.rotate(-45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
                canvas.drawText(item.name + "", xPositin, xLineHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);
                canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
                // }
                mPaint.setColor(mWaterColors[i]);
                mPaint.setStrokeWidth(getPxFromDip(3));
                circleRadius = getPxFromDip(5);

                float height = xLineHeight - item.data * singleExcelHeight / sectionValue;
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                mPaint.setColor(mPaintColors[item.interval]);
                canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                mPaint.setStyle(Style.FILL);

                if (j != items.length - 1) {
                    mPaint.setStrokeWidth(getPxFromDip(2));
                    float nextHeight = xLineHeight - items[j + 1].interval * singleExcelHeight - (items[j + 1].data - mYStrings.get(items[j + 1].interval)) * singleExcelHeight / mYField[items[j + 1].interval].span;
                    canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth, nextHeight, mPaint);
                }
                mPaint.setStrokeWidth(0);

            }
        }
        canvas.restore();

    }

    /**
     * 画柱形图
     *
     * @param canvas
     */
    private void paintColumnView(Canvas canvas) {
        if (mYLines == 0)
            return;
        int xLineHeight = mHeight - 3 * mTextHeight;// 实际绘图区域高度
        int mYWidth = (int) mPaint.measureText("00000");// Y轴刻度文字的宽度
        int yRectWidth = getPxFromDip(8);// Y轴色带的宽度
        mScrollAreaWidth = mWidth - mYWidth - yRectWidth - mRightPanding + mPaddingRightWidth;// 可滚动区域的宽度
        int singleExcelHeight = (xLineHeight - mTextHeight / 2) / mYLines;// 单个单元格的高度
        // 画Y轴的色带
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), mYWidth - mYWidth / 5, xLineHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
            }
        }

        if (mShowColorVertical) {
            int yTemp = 0;
            for (int i = 0; i < mYField.length; i++) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(mYWidth, (int) (xLineHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), mYWidth + yRectWidth, xLineHeight - yTemp), mPaint);
                yTemp += mYField[i].span / sectionValue * singleExcelHeight;
            }
        } else {
            mPaint.setColor(mLineColor);
            canvas.drawLine(mYWidth - mYWidth / 7, 0, mYWidth - mYWidth / 7, xLineHeight, mPaint);
        }

        DataItem[] data = mData.dataList.get(0);
        canvas.save();
        canvas.clipRect(mYWidth + yRectWidth, mTextHeight / 2, mWidth, xLineHeight + mTextHeight * 3);
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = mYWidth + yRectWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            DataItem item = data[i];
            // 画X轴上的刻度
            canvas.drawLine(xPositin, xLineHeight, xPositin, xLineHeight + mTextHeight - mTextHeight / 5, mPaint);
            // 绘制X轴的文本标签，旋转45度
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name, xPositin, xLineHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);

            if (item.data != -1) {
                // 绘制柱形图
                canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
//			mPaint.setColor(mChartColor); // 默认的颜色
                /***苏州项目要求****/
                if (item.interval < mPaintColors.length - 1)
                    mPaint.setColor(mPaintColors[item.interval + 1]);
                else
                    mPaint.setColor(mPaintColors[item.interval]);
                float height = xLineHeight - item.data * singleExcelHeight / sectionValue - singleExcelHeight;
                canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 4, (int) height, xPositin + mSingleExcelWidth / 4, xLineHeight), mPaint);

                // 绘制柱形图的锅盖头
                // 绘制柱形图的锅盖头,如果是最高等级，那么直接赋值
//						if(item.interval<mPaintColors.length-1)
//						mPaint.setColor(mPaintColors[item.interval+1]);
//						else
//							mPaint.setColor(mPaintColors[item.interval]);
// 			mPaint.setColor(mPaintColors[item.interval]);
                // 绘制柱形图的锅盖头
                /***苏州项目要求****/
                if (item.interval < mPaintColors.length - 1)
                    mPaint.setColor(mPaintColors[item.interval + 1]);
                else
                    mPaint.setColor(mPaintColors[item.interval]);
                canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 4, (int) height, xPositin + mSingleExcelWidth / 4, (int) height + getPxFromDip(5)), mPaint);


                mPaint.setColor(Color.BLACK);
                mPaint.setTextAlign(Align.CENTER);
                // 绘制数据值
                canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 10, height - 5, mPaint);

            } else {

                canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);

            }
        }
        canvas.restore();

    }

    /**
     * 画实时水质水质等级柱形图
     *
     * @param canvas
     */
    private void paintRealTimeWaterColumnView(Canvas canvas) {
        if (mYLines == 0)
            return;
        int xPaintHeight = mHeight - 4 * mTextHeight;// 实际绘图区域高度
        int yLabelWidth = (int) mPaint.measureText("00000");// Y轴刻度文字的宽度
        int yLineWidth = getPxFromDip(5);// Y轴色带的宽度
//		int mYWidth = (int) mPaint.measureText("00000");
//		mScrollAreaWidth = mWidth - mYWidth - yLineWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xPaintHeight - mTextHeight / 2) / mYLines;// 单个单元格的高度
        // 画Y轴的色带
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), yLabelWidth - yLabelWidth / 5, xPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(yLabelWidth - yLabelWidth / 10, xPaintHeight - singleExcelHeight * i, mWidth, xPaintHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 10, xPaintHeight - singleExcelHeight * i, mWidth, xPaintHeight - singleExcelHeight * i, mPaint);
            }
        }

        if (mShowColorVertical) {
            int yTemp = 0;
            for (int i = 0; i < mYField.length; i++) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(yLabelWidth, (int) (xPaintHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), yLabelWidth + yLineWidth, xPaintHeight - yTemp), mPaint);
                yTemp += mYField[i].span / sectionValue * singleExcelHeight;
            }
        } else {
            mPaint.setColor(mLineColor);
            canvas.drawLine(yLabelWidth - yLabelWidth / 7, 0, yLabelWidth - yLabelWidth / 7, xPaintHeight, mPaint);
        }

        DataItem[] data = mData.dataList.get(0);
        canvas.save();
        canvas.clipRect(yLabelWidth + yLineWidth, mTextHeight / 2, mWidth,
                xPaintHeight + mTextHeight * 4);

        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = yLabelWidth + yLineWidth + 15 + mSingleExcelWidth * (i + 1) + mDrawOffset + i * 50;
            DataItem item = data[i];
            // 画X轴上的刻度
            // canvas.drawLine(xPositin, xLineHeight, xPositin, xLineHeight
            // + mTextHeight - mTextHeight / 5, mPaint);
            // 绘制X轴的文本标签
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(0, xPositin, xPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name, xPositin, xPaintHeight + mTextHeight + 15 - mTextHeightOffset, mPaint);
            canvas.drawText(item.value, xPositin, xPaintHeight + (mTextHeight - mTextHeightOffset + 15) * 2, mPaint);
            // 绘制柱形图
//			mPaint.setColor(mChartColor); // 默认的颜色
            /***苏州项目要求****/
//			if(item.interval<mPaintColors.length-1)
//				mPaint.setColor(mPaintColors[item.interval+1]);
//				else
            mPaint.setColor(mPaintColors[item.interval]);
            float height = xPaintHeight - item.data * singleExcelHeight / sectionValue + mTextHeight;
            canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 2, (int) height, xPositin + mSingleExcelWidth / 3, xPaintHeight), mPaint);
            // 绘制柱形图的锅盖头,如果是最高等级，那么直接赋值
//			if(item.interval<mPaintColors.length-1)
//			mPaint.setColor(mPaintColors[item.interval+1]);
//			else
            mPaint.setColor(mPaintColors[item.interval]);
            canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 2, (int) height, xPositin + mSingleExcelWidth / 3, (int) height + getPxFromDip(5)), mPaint);
            mPaint.setColor(Color.BLACK);
            mPaint.setTextAlign(Align.CENTER);
            // 绘制数据值
            canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 10, height - 5, mPaint);
        }
        canvas.restore();
    }

    /**
     * 画实时水质浓度曲线
     *
     * @param canvas
     */
    private void paintDensityLineView(Canvas canvas) {

        if (mYLines == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        //mSingleExcelWidth 横轴单元间隔宽度补
//		mSingleExcelWidth=(int) mPaint.measureText("00000000000000");
        int bottomWidth = mWidth / (mYField.length + 2);
        //yLabelWidth Y轴单元字符宽度
        int yLabelWidth = (int) mPaint.measureText("00000000");
        if (getFactorName().equals("电导率")) {
            yLabelWidth = (int) mPaint.measureText("000000");
        }
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }
        int chartPaintHeight;
        if (mAutoPaddingType) {
            chartPaintHeight = mHeight - 3 * mTextHeight;
        } else {
            chartPaintHeight = mHeight - 7 * mTextHeight;
        }
//		chartPaintHeight=(int)(chartPaintHeight- 2*mTextHeight - mTextHeightOffset);
        int yLineWidth = getPxFromDip(5);
        //Y轴线宽度
        //int yLineWidth = yLabelWidth;
        //水平滑动区域的宽度,扣除Y轴坐标文字宽度，Y轴线宽度 右边距 滑动区域右边填充
        mScrollAreaWidth = mWidth - yLabelWidth - yLineWidth - mRightPanding + mPaddingRightWidth - 300;
        //Y轴坐标每个单元间隔
        int singleExcelHeight = (int) ((chartPaintHeight - 1.2 * mTextHeight - mTextHeightOffset) / mYLines);
        //Y轴坐标每个单元高度
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            //是否显示底部方框
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            //是否显示色带
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(yLabelWidth, (int) (chartPaintHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), yLabelWidth + yLineWidth, chartPaintHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span * i * singleExcelHeight;
        }
        //是否显示底部方框
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        //是否显示色带画Y轴
        if (!mShowColorVertical) {
            mPaint.setColor(mLineColor);
            canvas.drawLine(yLabelWidth, 0, yLabelWidth, chartPaintHeight, mPaint);

        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }

        //绘制Y轴单元格
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
//			if (getFactorName().equals("pH值") || getFactorName().equals("溶解氧") || getFactorName().equals("总氮") || getFactorName().equals("总有机碳") || getFactorName().equals("叶绿素")) {
//				if (i % 4 == 0) {
//					canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) :JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue / 100)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//				}
//			}
//			if (getFactorName().equals("电导率")) {
//				if (i % 4 == 0) {
//					canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//				}
//			}
//			if (getFactorName().equals("水温")) {
//				if (i % 4 == 0) {
//					canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue / 10)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//				}
//			}
//			if (getFactorName().equals("浑浊度") || getFactorName().equals("蓝绿藻")) {
//				if (i % 2 == 0) {
//					canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue / 100)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//				}
//			}
//			if (getFactorName().equals("氨氮") || getFactorName().equals("总磷")) {
//				if (mYLines * sectionValue < 100) {
//					yLabelWidth = (int) mPaint.measureText("00000");
//					if (i % 4 == 0) {
//						canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) :JsonUtil.subZeroAndDot(String.valueOf((double) i * sectionValue / 100.00)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//					}
//				} else {
//					yLabelWidth = (int) mPaint.measureText("0000");
//					if (i % 4 == 0) {
//						canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue / 100)), yLabelWidth - yLabelWidth / 3, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
//					}
//				}
//			}

            java.math.BigDecimal b = new java.math.BigDecimal(i * mYField[0].span);
            double num2 = b.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
            if (b.doubleValue() < 0.01)//针对比较小的数值比如叶绿素
                num2 = b.setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
            //从最大值开始画
// 				canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String .format("%.2f",String.valueOf(i * mYField[0].span))), yLabelWidth, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            //canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * mYField[0].span)), yLabelWidth, singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (mYField[0].span != 0 || (mYField[0].span == 0 && i == 0))//最大值为0时，不标注刻度
            {
                if (mYField[0].span > 0)
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(num2)), yLabelWidth, chartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);

                else {
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(num2)), yLabelWidth, singleExcelHeight * i + mTextHeight / 2 + mTextHeightOffset, mPaint);
                }
            }


            if (!mShowGridX) {
                // 不显示图表上的水平网格线
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(yLabelWidth, chartPaintHeight - singleExcelHeight * i, mWidth, chartPaintHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                // 显示图表上的水平网格线
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 10, chartPaintHeight - singleExcelHeight * i, mWidth, chartPaintHeight - singleExcelHeight * i, mPaint);
            }
        }

        DataItem[] data = mData.dataList.get(0);
        int circleRadius = getPxFromDip(5);

        canvas.save();
//		canvas.clipRect(0, mTextHeight / 2, mWidth, chartPaintHeight + mTextHeight * 3);
        canvas.clipRect(yLabelWidth + yLineWidth, mTextHeight / 2, mWidth,
                chartPaintHeight + mTextHeight * 3);
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            //数据在X轴坐标
            int xPositin = 0;
            if (getFactorName().equals("电导率")) {
                xPositin = yLabelWidth + yLineWidth + mSingleExcelWidth * (i + 1) + mDrawOffset + i * 60 + 60;
            } else {
                xPositin = yLabelWidth + yLineWidth + mSingleExcelWidth * (i + 1) + mDrawOffset + i * 40 + 40;
            }
            DataItem item = data[i];
            if (item == null)
                break;
            mPaint.setTextAlign(Align.CENTER);
            // 绘制X轴坐标的标签
            canvas.drawText(item.name, xPositin, chartPaintHeight + (mTextHeight - mTextHeightOffset) * 2, mPaint);
            // 绘制X轴上的刻度


            canvas.drawLine(xPositin, chartPaintHeight, xPositin, chartPaintHeight + mTextHeight - mTextHeight / 5, mPaint);


//			mPaint.setStrokeWidth(getPxFromDip(3));
//			int height = chartPaintHeight - Integer.valueOf((int)(item.data)) * singleExcelHeight / sectionValue;
            //int height= (int)(chartPaintHeight - Integer.valueOf((int)(item.data)) * singleExcelHeight / sectionValue);
//			int height=(int)(item.data/(mYField[0].span*6)*chartPaintHeight);
            //int height= (int)(chartPaintHeight - Integer.valueOf((int)(item.data)) * singleExcelHeight/6 );
            //int height=(int) (chartPaintHeight- (int)(item.data/(mYField[0].span*4)*(chartPaintHeight- 1.2*mTextHeight - mTextHeightOffset)));

            int height = (int) ((chartPaintHeight) - (int) (item.data / (mYField[0].span * 4) * ((chartPaintHeight - 1.2 * mTextHeight - mTextHeightOffset))));
            if (mYField[0].span < 0)
                height = (int) (item.data / (mYField[0].span * 4) * ((chartPaintHeight - 1.2 * mTextHeight - mTextHeightOffset)));

            //
//			(int)(chartPaintHeight - Integer.valueOf((int)(item.data)) * singleExcelHeight / sectionValue);
            if (i != data.length - 1) {
                if (data[i + 1].data != -1 && data[i].data != -1) {
                    mPaint.setStrokeWidth(getPxFromDip(1));
//					int nextHeight = chartPaintHeight - Integer.valueOf((int)(data[i + 1].data)) * singleExcelHeight / sectionValue;
//					int nextHeight = (int)(chartPaintHeight - Integer.valueOf((int)(data[i + 1].data)) * singleExcelHeight/sectionValue );
//					int nextHeight = (int)(data[i + 1].data/(mYField[0].span*6)*chartPaintHeight);
//					int nextHeight = (int)(chartPaintHeight - Integer.valueOf((int)(data[i + 1].data)) * singleExcelHeight/6 );
//					int nextHeight = (int) (chartPaintHeight- (int)((data[i + 1].data)/(mYField[0].span*4)*(chartPaintHeight-1.2*mTextHeight - mTextHeightOffset)));
                    int nextHeight = (int) ((chartPaintHeight) - (int) ((data[i + 1].data) / (mYField[0].span * 4) * ((chartPaintHeight - 1.2 * mTextHeight - mTextHeightOffset))));
                    if (mYField[0].span < 0)
                        nextHeight = (int) ((data[i + 1].data) / (mYField[0].span * 4) * ((chartPaintHeight - 1.2 * mTextHeight - mTextHeightOffset)));
                    mPaint.setColor(mChartColor);
                    canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth + 40, nextHeight, mPaint);
                }
            }
            if (!item.hideValue) {
                if (data[i].data != -1) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                    if (mShowColorVertical) {
                        mPaint.setColor(mPaintColors[item.interval]);
                    } else {
                        mPaint.setColor(mChartColor);
                    }
                    canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                    mPaint.setStyle(Style.FILL);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextAlign(Align.CENTER);
                    canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.value)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                }
            }

        }
        canvas.restore();

    }

    /**
     * 绘制CO的浓度曲线
     *
     * @param canvas
     */
    private void paintCODensityView(Canvas canvas) {
        if (mYLines == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        int bottomWidth = mWidth / (mYField.length + 2);
        int yLabelWidth = (int) mPaint.measureText("00000");
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }
        int xChartPaintHeight;// 图表除了X轴以外的可绘高度
        if (mAutoPaddingType) {
            xChartPaintHeight = mHeight - 3 * mTextHeight;
        } else {
            xChartPaintHeight = mHeight - 7 * mTextHeight;
        }
        int yLineWidth = getPxFromDip(5);
        mScrollAreaWidth = mWidth - yLabelWidth - yLineWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xChartPaintHeight - mTextHeight / 2) / mYLines;
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(yLabelWidth, (int) (xChartPaintHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), yLabelWidth + yLineWidth, xChartPaintHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span / sectionValue * singleExcelHeight;
        }
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        if (!mShowColorVertical) {
            if (isShowYKeduLine) {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 7, 0, yLabelWidth - yLabelWidth / 7, xChartPaintHeight, mPaint);
            }
        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            if (isShowYKeduLine) {

                canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : String.valueOf(i * sectionValue), yLabelWidth - yLabelWidth / 5, xChartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            }
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
            }
        }
        DataItem[] data = mData.dataList.get(0);
        int circleRadius = getPxFromDip(5);

        canvas.save();
        if (isShowYKeduLine) {
            canvas.clipRect(yLabelWidth + yLineWidth, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        } else {
            canvas.clipRect(0, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        }
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = 0;
            if (isShowYKeduLine) {
                xPositin = yLabelWidth + yLineWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            } else {
                xPositin = mSingleExcelWidth * (i + 1) + mDrawOffset;
            }
            DataItem item = data[i];
            if (item == null)
                break;
            canvas.drawLine(xPositin, xChartPaintHeight, xPositin, xChartPaintHeight + mTextHeight - mTextHeight / 5, mPaint);
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name + "", xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);
            canvas.rotate(45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            float height = xChartPaintHeight - item.data * singleExcelHeight / sectionValue;
            if (i != data.length - 1) {
                float nextHeight = xChartPaintHeight - data[i + 1].data * singleExcelHeight / sectionValue;
                mPaint.setColor(mChartColor);
                canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth, nextHeight, mPaint);
            }
            if (!item.hideValue) {
                mPaint.setColor(Color.WHITE);
                canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                if (mShowColorVertical) {
                    mPaint.setColor(mPaintColors[item.interval]);
                } else {
                    mPaint.setColor(mChartColor);
                }
                canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                mPaint.setStyle(Style.FILL);
                // mPaint.setStrokeWidth(0);
                mPaint.setColor(Color.BLACK);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYNameRomanValue ? "" : item.data + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
            }
        }
        canvas.restore();
    }

    /**
     * 线性图表
     *
     * @param canvas
     */
    private void paintLineView(Canvas canvas) {
        if (mYLines == 0 || mData.dataList.size() == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        int bottomWidth = mWidth / (mYField.length + 2);
        int yLabelWidth = (int) mPaint.measureText("00000");
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }


        int xChartPaintHeight;// 图表除了X轴以外的可绘高度
        if (mAutoPaddingType) {
            xChartPaintHeight = mHeight - 3 * mTextHeight;
        } else {
            xChartPaintHeight = mHeight - 7 * mTextHeight;
        }
        mPaint.setColor(Color.WHITE);
//		canvas.drawText("A", yLabelWidth/5, (xChartPaintHeight  - mTextHeightOffset)/2, mPaint);
//		canvas.drawText("Q", yLabelWidth/5, (xChartPaintHeight  - mTextHeightOffset)/2+mTextHeight, mPaint);
//		canvas.drawText("I", yLabelWidth/5, (xChartPaintHeight  - mTextHeightOffset)/2+2*mTextHeight, mPaint);
//		canvas.drawText("值", yLabelWidth/5, (xChartPaintHeight  - mTextHeightOffset)/2+3*mTextHeight, mPaint);
//	    
//		canvas.drawText(item.name + "", xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);

        int yLineWidth = getPxFromDip(5);
        mScrollAreaWidth = mWidth - yLabelWidth - yLineWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xChartPaintHeight - mTextHeight / 2) / mYLines;
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(yLabelWidth, (int) (xChartPaintHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), yLabelWidth + yLineWidth, xChartPaintHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span / sectionValue * singleExcelHeight;
        }
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        if (!mShowColorVertical) {
            if (isShowYKeduLine) {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 7, 0, yLabelWidth - yLabelWidth / 7, xChartPaintHeight, mPaint);
            }
        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            if (isShowYKeduLine) {
                if (isCO) {
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf((i * sectionValue) / 10)), yLabelWidth - yLabelWidth / 5, xChartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
                } else {
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), yLabelWidth - yLabelWidth / 5, xChartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
                }

            }
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
            }
        }
        DataItem[] data = mData.dataList.get(0);
        int circleRadius = getPxFromDip(5);

        canvas.save();
        if (isShowYKeduLine) {
            canvas.clipRect(yLabelWidth + yLineWidth, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        } else {
            canvas.clipRect(0, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        }
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = 0;
            if (isShowYKeduLine) {
                xPositin = yLabelWidth + yLineWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            } else {
                xPositin = mSingleExcelWidth * (i + 1) + mDrawOffset;
            }
            DataItem item = data[i];
            if (item == null)
                break;
            canvas.drawLine(xPositin, xChartPaintHeight, xPositin, xChartPaintHeight + mTextHeight - mTextHeight / 5, mPaint);
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name + "", xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);
            canvas.rotate(45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            float height = xChartPaintHeight - item.data * singleExcelHeight / sectionValue;
            if (i != data.length - 1) {
                if (data[i + 1].data != -1 && data[i].data != -1) {
                    float nextHeight = xChartPaintHeight - data[i + 1].data * singleExcelHeight / sectionValue;
                    mPaint.setColor(mChartColor);
                    canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth, nextHeight, mPaint);
                }
            }
            if (!item.hideValue) {
                if (data[i].data != -1) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                    if (mShowColorVertical) {
                        mPaint.setColor(mPaintColors[item.interval]);
                    } else {
                        mPaint.setColor(mChartColor);
                    }
                    canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                    mPaint.setStyle(Style.FILL);
                    // mPaint.setStrokeWidth(0);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextAlign(Align.CENTER);
                    if (isCO) {
                        canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf((double) item.data / 10)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                    } else {
                        canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                    }
                }
            }
        }
        canvas.restore();
    }


    /**
     * AQI折现图为苏州定制加入横坐标和纵坐标提示
     *
     * @param canvas
     */
    private void paintAQILineView(Canvas canvas) {
        if (mYLines == 0 || mData.dataList.size() == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        int bottomWidth = mWidth / (mYField.length + 2);
        int yLabelWidth = (int) mPaint.measureText("000000");
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }


        int xChartPaintHeight;// 图表除了X轴以外的可绘高度
        if (mAutoPaddingType) {
            xChartPaintHeight = mHeight - 6 * mTextHeight;
        } else {
            xChartPaintHeight = mHeight - 7 * mTextHeight;
        }
        /***纵坐标提示****/
        mPaint.setColor(Color.WHITE);
        canvas.drawText("A", yLabelWidth / 8, (xChartPaintHeight - mTextHeightOffset) / 2, mPaint);
        canvas.drawText("Q", yLabelWidth / 8, (xChartPaintHeight - mTextHeightOffset) / 2 + mTextHeight, mPaint);
        canvas.drawText("I", yLabelWidth / 8, (xChartPaintHeight - mTextHeightOffset) / 2 + 2 * mTextHeight, mPaint);
        canvas.drawText("值", yLabelWidth / 8, (xChartPaintHeight - mTextHeightOffset) / 2 + 3 * mTextHeight, mPaint);


        int yLineWidth = getPxFromDip(5);
        mScrollAreaWidth = mWidth - yLabelWidth - yLineWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xChartPaintHeight - mTextHeight / 2) / mYLines;
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(yLabelWidth, (int) (xChartPaintHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), yLabelWidth + yLineWidth, xChartPaintHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span / sectionValue * singleExcelHeight;
        }
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        if (!mShowColorVertical) {
            if (isShowYKeduLine) {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 7, 0, yLabelWidth - yLabelWidth / 7, xChartPaintHeight, mPaint);
            }
        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            if (isShowYKeduLine) {
                if (isCO) {
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf((i * sectionValue) / 10)), yLabelWidth - yLabelWidth / 5, xChartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
                } else {
                    canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), yLabelWidth - yLabelWidth / 5, xChartPaintHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
                }

            }
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(yLabelWidth - yLabelWidth / 10, xChartPaintHeight - singleExcelHeight * i, mWidth, xChartPaintHeight - singleExcelHeight * i, mPaint);
            }
        }
        DataItem[] data = mData.dataList.get(0);
        int circleRadius = getPxFromDip(5);

        canvas.save();

//        canvas.translate(-400, 0);

        if (isShowYKeduLine) {
            canvas.clipRect(yLabelWidth + yLineWidth, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        } else {
            canvas.clipRect(0, mTextHeight / 2, mWidth, xChartPaintHeight + mTextHeight * 3);
        }
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = 0;
            if (isShowYKeduLine) {
                xPositin = yLabelWidth + yLineWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            } else {
                xPositin = mSingleExcelWidth * (i + 1) + mDrawOffset;
            }
            DataItem item = data[i];
            if (item == null)
                break;
            canvas.drawLine(xPositin, xChartPaintHeight, xPositin, xChartPaintHeight + mTextHeight - mTextHeight / 5, mPaint);
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name + "", xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);
            canvas.rotate(45, xPositin, xChartPaintHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            float height = xChartPaintHeight - item.data * singleExcelHeight / sectionValue;
            if (i != data.length - 1) {
                if (data[i + 1].data != -1 && data[i].data != -1) {
                    float nextHeight = xChartPaintHeight - data[i + 1].data * singleExcelHeight / sectionValue;
                    mPaint.setColor(mChartColor);
                    canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth, nextHeight, mPaint);
                }
            }
            if (!item.hideValue) {
                if (data[i].data != -1) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                    if (mShowColorVertical) {
                        mPaint.setColor(mPaintColors[item.interval]);
                    } else {
                        mPaint.setColor(mChartColor);
                    }
                    canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                    mPaint.setStyle(Style.FILL);
                    // mPaint.setStrokeWidth(0);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextAlign(Align.CENTER);
                    if (isCO) {
                        canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf((double) item.data / 10)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                    } else {
                        canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                    }
                }
            }
        }

        canvas.restore();

        canvas.translate(0, 0);


        mPaint.setColor(Color.WHITE);
        /***横坐标提示****/
        canvas.drawText("时间", mWidth / 2, xChartPaintHeight + 4 * mTextHeight, mPaint);

    }


    /**
     * AQI柱状图为苏州定制加入横坐标和纵坐标提示
     *
     * @param canvas
     */
    private void paintAQIColumnView(Canvas canvas) {
        if (mYLines == 0)
            return;
        int xLineHeight = mHeight - 6 * mTextHeight;// 实际绘图区域高度
        int mYWidth = (int) mPaint.measureText("0000000");// Y轴刻度文字的宽度
        int yRectWidth = getPxFromDip(8);// Y轴色带的宽度
        mScrollAreaWidth = mWidth - mYWidth - yRectWidth - mRightPanding + mPaddingRightWidth;// 可滚动区域的宽度
        int singleExcelHeight = (xLineHeight - mTextHeight / 2) / mYLines;// 单个单元格的高度
        // 画Y轴的色带
        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            canvas.drawText(mYNameRomanValue ? JsonUtil.getLuoMaByLevel(i) : JsonUtil.subZeroAndDot(String.valueOf(i * sectionValue)), mYWidth - mYWidth / 5, xLineHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
            }
        }

        if (mShowColorVertical) {
            int yTemp = 0;
            for (int i = 0; i < mYField.length; i++) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(mYWidth, (int) (xLineHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), mYWidth + yRectWidth, xLineHeight - yTemp), mPaint);
                yTemp += mYField[i].span / sectionValue * singleExcelHeight;
            }
        } else {
            mPaint.setColor(mLineColor);
            canvas.drawLine(mYWidth - mYWidth / 7, 0, mYWidth - mYWidth / 7, xLineHeight, mPaint);
        }

        /***纵坐标提示****/
        mPaint.setColor(Color.WHITE);
        canvas.drawText("A", mYWidth / 3, (xLineHeight - mTextHeightOffset) / 2, mPaint);
        canvas.drawText("Q", mYWidth / 3, (xLineHeight - mTextHeightOffset) / 2 + mTextHeight, mPaint);
        canvas.drawText("I", mYWidth / 3, (xLineHeight - mTextHeightOffset) / 2 + 2 * mTextHeight, mPaint);
        canvas.drawText("值", mYWidth / 3, (xLineHeight - mTextHeightOffset) / 2 + 3 * mTextHeight, mPaint);


        DataItem[] data = mData.dataList.get(0);
        canvas.save();
        canvas.clipRect(mYWidth + yRectWidth, mTextHeight / 2, mWidth, xLineHeight + mTextHeight * 3);
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = mYWidth + yRectWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            DataItem item = data[i];
            // 画X轴上的刻度
            canvas.drawLine(xPositin, xLineHeight, xPositin, xLineHeight + mTextHeight - mTextHeight / 5, mPaint);
            // 绘制X轴的文本标签，旋转45度
            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name, xPositin, xLineHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);

            if (item.data != -1) {
                // 绘制柱形图
                canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
//			mPaint.setColor(mChartColor); // 默认的颜色
                /***苏州项目要求****/
//			if(item.interval<mPaintColors.length-1)
//				mPaint.setColor(mPaintColors[item.interval+1]);
//				else
                mPaint.setColor(mPaintColors[item.interval]);
                float height = xLineHeight - item.data * singleExcelHeight / sectionValue;
                canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 4, (int) height, xPositin + mSingleExcelWidth / 4, xLineHeight), mPaint);

                // 绘制柱形图的锅盖头
                // 绘制柱形图的锅盖头,如果是最高等级，那么直接赋值
//						if(item.interval<mPaintColors.length-1)
//						mPaint.setColor(mPaintColors[item.interval+1]);
//						else
//							mPaint.setColor(mPaintColors[item.interval]);
// 			mPaint.setColor(mPaintColors[item.interval]);
                // 绘制柱形图的锅盖头
                /***苏州项目要求****/
//			if(item.interval<mPaintColors.length-1)
//				mPaint.setColor(mPaintColors[item.interval+1]);
//				else
                mPaint.setColor(mPaintColors[item.interval]);
                canvas.drawRect(new Rect(xPositin - mSingleExcelWidth / 4, (int) height, xPositin + mSingleExcelWidth / 4, (int) height + getPxFromDip(5)), mPaint);
                mPaint.setColor(Color.BLACK);
                mPaint.setTextAlign(Align.CENTER);
                // 绘制数据值
                canvas.drawText(mYNameRomanValue ? "" : JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 20, height - 5, mPaint);
            } else {
                canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            }
        }
        canvas.restore();
        mPaint.setColor(Color.WHITE);
        /***横坐标提示****/
        canvas.drawText("时间", mWidth / 2, xLineHeight + 4 * mTextHeight, mPaint);


    }


    /**
     * 画水单线
     */
    private void paintWaterLineView(Canvas canvas) {
        if (mYLines == 0)
            return;
        mPaint.setAntiAlias(true); // 是否抗锯齿
        int bottomWidth = mWidth / (mYField.length + 2);
        int mYWidth = (int) mPaint.measureText("00000");
        int rectLeft = 0;
        if (mData.extraStage != null) {
            rectLeft = (mWidth - (mYField.length + 1) * bottomWidth) / 2 - explainRectMarginRight;
        } else {
            rectLeft = (mWidth - mYField.length * bottomWidth) / 2 - explainRectMarginRight;
        }
        int xLineHeight;
        if (mAutoPaddingType) {
            xLineHeight = mHeight - 3 * mTextHeight;
        } else {
            xLineHeight = mHeight - 7 * mTextHeight;
        }
        int yRectWidth = getPxFromDip(5);
        mScrollAreaWidth = mWidth - mYWidth - yRectWidth - mRightPanding + mPaddingRightWidth;
        int singleExcelHeight = (xLineHeight - mTextHeight / 2) / mYLines;
        int yTemp = 0;
        for (int i = 0; i < mYField.length; i++) {
            if (mShowColorRect) {
                mPaint.setColor(mTextColor);
                canvas.drawText(mYStrings.get(i) + "", rectLeft + i * bottomWidth, mHeight - mTextHeightOffset, mPaint);
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(rectLeft + i * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + i * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);
                mPaint.setColor(mTextColor);
                mPaint.setTextAlign(Align.CENTER);
                canvas.drawText(mYField[i].bottomChar, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

                if (mYField[i].bottomState.length() > 2) {
                    canvas.drawText(mYField[i].bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                    canvas.drawText(mYField[i].bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
                } else {
                    canvas.drawText(mYField[i].bottomState, rectLeft + (bottomWidth / 2) + i * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
                }
            }
            if (mShowColorVertical) {
                mPaint.setColor(mPaintColors[i]);
                canvas.drawRect(new Rect(mYWidth, (int) (xLineHeight - yTemp - (mYField[i].span / sectionValue) * singleExcelHeight), mYWidth + yRectWidth, xLineHeight - yTemp), mPaint);
            }
            yTemp += mYField[i].span / sectionValue * singleExcelHeight;
        }
        if (mShowColorRect) {
            mPaint.setColor(mTextColor);
            canvas.drawText(mYStrings.get(mYField.length) + "", rectLeft + mYField.length * bottomWidth, mHeight - mTextHeightOffset, mPaint);
        }
        if (!mShowColorVertical) {
            mPaint.setColor(mLineColor);
            canvas.drawLine(mYWidth - mYWidth / 7, 0, mYWidth - mYWidth / 7, xLineHeight, mPaint);
        }
        if (mData.extraStage != null) {
            mPaint.setColor(mPaintColors[mYField.length]);
            canvas.drawRect(new Rect(rectLeft + mYField.length * bottomWidth, mHeight - mTextHeight * 2, rectLeft + bottomWidth + mYField.length * bottomWidth, (int) (mHeight - mTextHeight)), mPaint);

            mPaint.setColor(mTextColor);
            canvas.drawText(mData.extraStage.bottomChar, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight - mTextHeightOffset), mPaint);

            if (mData.extraStage.bottomState.length() > 2) {
                canvas.drawText(mData.extraStage.bottomState.substring(0, 2), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 3 - mTextHeightOffset), mPaint);
                canvas.drawText(mData.extraStage.bottomState.substring(2, 4), rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset), mPaint);
            } else {
                canvas.drawText(mData.extraStage.bottomState, rectLeft + (bottomWidth / 2) + mYField.length * bottomWidth, (int) (mHeight - mTextHeight * 2 - mTextHeightOffset - 10), mPaint);
            }
        }

        for (int i = 0; i < mYLines + 1; i++) {
            mPaint.setTextAlign(Align.RIGHT);
            mPaint.setColor(mTextColor);
            canvas.drawText(JsonUtil.getLuoMaByLevel(i), mYWidth - mYWidth / 5, xLineHeight - singleExcelHeight * i + mTextHeight / 2 - mTextHeightOffset, mPaint);
            if (!mShowGridX) {
                if (i == 0) {
                    mPaint.setColor(mLineColor);
                    canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
                }
            } else {
                mPaint.setColor(mLineColor);
                canvas.drawLine(mYWidth - mYWidth / 10, xLineHeight - singleExcelHeight * i, mWidth, xLineHeight - singleExcelHeight * i, mPaint);
            }
        }

        DataItem[] data = mData.dataList.get(0);
        int circleRadius = getPxFromDip(5);

        canvas.save();
        canvas.clipRect(mYWidth + yRectWidth, mTextHeight / 2, mWidth, xLineHeight + mTextHeight * 3);
        for (int i = 0; i < data.length; i++) {
            mPaint.setColor(mTextColor);
            int xPositin = mYWidth + yRectWidth + mSingleExcelWidth * (i + 1) + mDrawOffset;
            DataItem item = data[i];
            canvas.drawLine(xPositin, xLineHeight, xPositin, xLineHeight + mTextHeight - mTextHeight / 5, mPaint);

            mPaint.setTextAlign(Align.CENTER);
            canvas.rotate(-45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);
            canvas.drawText(item.name, xPositin, xLineHeight + mTextHeight * 2 - mTextHeightOffset, mPaint);
            canvas.rotate(45, xPositin, xLineHeight + mTextHeight * 2 - mTextHeight / 2 - mTextHeightOffset);

            mPaint.setStrokeWidth(getPxFromDip(3));
            float height = xLineHeight - item.data * singleExcelHeight / sectionValue;
            if (i != data.length - 1) {
                if (data[i + 1].data != -1 && data[i].data != -1) {
                    mPaint.setStrokeWidth(getPxFromDip(1));
                    float nextHeight = xLineHeight - data[i + 1].data * singleExcelHeight / sectionValue;
                    mPaint.setColor(mChartColor);
                    mPaint.setStrokeWidth(mLineThick);
                    canvas.drawLine(xPositin, height, xPositin + mSingleExcelWidth, nextHeight, mPaint);
                }
            }
            if (!item.hideValue) {
                if (data[i].data != -1) {
                    mPaint.setColor(Color.WHITE);
                    canvas.drawCircle(xPositin, height, circleRadius, mPaint);
                    if (mShowColorVertical) {
                        mPaint.setColor(mPaintColors[item.interval]);
                    } else {
                        mPaint.setColor(mChartColor);
                    }
                    canvas.drawCircle(xPositin, height, circleRadius - 1.2f, mPaint);
                    mPaint.setStyle(Style.FILL);

                    mPaint.setStrokeWidth(0);
                    mPaint.setColor(Color.BLACK);
                    mPaint.setTextAlign(Align.CENTER);
                    canvas.drawText(JsonUtil.subZeroAndDot(String.valueOf(item.data)) + "", xPositin - mSingleExcelWidth / 4 + 5, height - 8, mPaint);
                }
            }
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthSpecMode == MeasureSpec.EXACTLY || widthSpecMode == MeasureSpec.AT_MOST) {
            mWidth = widthSpecSize;
        } else {
            mWidth = 0;
        }
        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            mHeight = 0;
        } else {
            mHeight = heightSpecSize;
        }
        calculateLayout();
        System.out.println("measuredHeight:" + mHeight);
        System.out.println("measuredWidth:" + mWidth);
        setMeasuredDimension(mWidth, mHeight);
    }

    private void calculateLayout() {
        if (mData == null || mData.dataList.size() == 0 || mData.dataList.get(0) == null || mData.dataList.get(0).length == 0) {
            mWidth = mHeight = 0;
            return;
        }
        switch (type) {
            case RECORD_LINE:
            case RECORD_AQI_LINE:
            case RECORD_WATER_LINE:
                mSingleExcelWidth = mWidth / xLineCount;
                mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                break;
            case RECORD_COLUMN:
                mSingleExcelWidth = mWidth / xLineCount;
                mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                mRightPanding = getPxFromDip(5) + mSingleExcelWidth / 4;
                break;
            case RECORD_AQI_COLUMN:
                mSingleExcelWidth = mWidth / xLineCount;
                mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                mRightPanding = getPxFromDip(5) + mSingleExcelWidth / 4;
                break;
            case RECORD_RealTimeWater_COLUMN:
                mSingleExcelWidth = mWidth / columnNumber;
                /**修改于2017-7-6  scj*/
                mSingleExcelWidth = mWidth / xLineCount;
                mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                mCalculateWidth = (getPxFromDip(5) + (int) mPaint.measureText("00000") + 15 + mSingleExcelWidth + 100) * mData.dataList.get(0).length;
                mRightPanding = getPxFromDip(5);
                break;
            case RECORD_RealTimeWater_LINE:
                mSingleExcelWidth = mWidth / xLineCount;
//			mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                mCalculateWidth = (getPxFromDip(5) + (int) mPaint.measureText("00000") + 60 + mSingleExcelWidth + 60) * mData.dataList.get(0).length;

                mRightPanding = getPxFromDip(5);
                break;
            case RECORD_MULTI_LINE:
                mSingleExcelWidth = mWidth / xLineCount;
                mCalculateWidth = mSingleExcelWidth * mData.dataList.get(0).length;
                mRightPanding = getPxFromDip(5);
                break;
            default:
                mWidth = mHeight = 0;
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mWidth >= mCalculateWidth) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                mSaveOffset = mDrawOffset;
                break;
            case MotionEvent.ACTION_MOVE:

                moveX = (int) event.getX();
                distance = moveX - downX;
                mDrawOffset = mSaveOffset + distance;
                if (mDrawOffset > 0) {
                    mDrawOffset = 0;
                } else if (mDrawOffset < mScrollAreaWidth - mCalculateWidth) {
                    mDrawOffset = mScrollAreaWidth - mCalculateWidth;
                }


                postInvalidate();
                break;

            default:
                break;
        }
        super.onTouchEvent(event);
        return true;
    }

    private int getPxFromDip(int dip) {
        return (int) (dip * Globe.density / 1.5);
    }

    @Override
    public String toString() {
        return "RecordView [mHeight=" + mHeight + ", mWidth=" + mWidth + ", mData=" + mData + ", type=" + type + ", mCalculateWidth=" + mCalculateWidth + ", mRightPanding=" + mRightPanding + ", mDrawOffset=" + mDrawOffset + ", mPaint=" + mPaint + ", mYLines=" + mYLines + ", mYStrings=" + mYStrings + ", mTextHeightOffset=" + mTextHeightOffset + ", mPaintColors=" + Arrays.toString(mPaintColors) + ", mBottomIntroduceColors=" + Arrays.toString(mBottomIntroduceColors) + ", mTextHeight=" + mTextHeight + ", downX=" + downX + ", moveX=" + moveX + ", distance=" + distance + ", mSaveOffset=" + mSaveOffset + ", mYField=" + Arrays.toString(mYField) + ", mSingleExcelWidth=" + mSingleExcelWidth + ", mScrollAreaWidth=" + mScrollAreaWidth + ", mBottomRowNum=" + mBottomRowNum + "]";
    }
}
