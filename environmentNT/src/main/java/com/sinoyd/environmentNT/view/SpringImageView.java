package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 站点管理的view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：SpringImageView


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class SpringImageView extends ImageView {
	final static ColorMatrixColorFilter mSelectFilter = new ColorMatrixColorFilter(new float[] { 1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 });
	final static Paint paint = new Paint();
	final static int DOWN_COLOR = 0xffff8800;
	final static int UP_COLOR = Color.TRANSPARENT;

	public SpringImageView(Context context) {
		this(context, null);
	}

	/***
	 * 初始化过程
	 * 
	 * @param context
	 * @param attrs
	 */
	public SpringImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setClickable(true);
		this.setFocusable(true);
		paint.setStrokeWidth(4.0f);
		paint.setColor(UP_COLOR);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.ROUND);
		setScaleType(ImageView.ScaleType.FIT_XY);
	}

	public void setStrokeWidth(float width) {
		paint.setStrokeWidth(width);
	}

	final Rect rect = new Rect();
	Drawable mBDrawable;

	/***
	 * 刷新状态
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void refreshDrawableState() {
		if (mBDrawable == null)
			return;
		if (isPressed()) {
			mBDrawable.setColorFilter(mSelectFilter);
		}
		else {
			mBDrawable.setColorFilter(null);
		}
		super.setBackgroundDrawable(mBDrawable);
		// super.refreshDrawableState();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setBackgroundDrawable(Drawable d) {
		super.setBackgroundDrawable(d);
		mBDrawable = d;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setImageDrawable(Drawable drawable) {
		super.setBackgroundDrawable(drawable);
		mBDrawable = drawable;
	}

	/***
	 * 渲染颜色
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.getClipBounds(rect);
		// rect.inset(2, 2);
		if (isPressed()) {
			paint.setColor(DOWN_COLOR);
		}
		else {
			paint.setColor(UP_COLOR);
		}
		// canvas.drawRect(rect, paint);
	}
}
