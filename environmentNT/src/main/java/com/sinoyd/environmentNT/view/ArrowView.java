package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.sinoyd.environmentNT.R;

/**
 * 底部白色圆圈选择效果视图 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：ArrowView

 * @创建日期：2015-1-26
 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class ArrowView extends LinearLayout {
	private int checked_resId = R.drawable.large_slider;
	private int check_resId = R.drawable.slider;

	private class Arrow extends ImageView implements Checkable {
		public Arrow(Context context) {
			super(context);
		}

		@Override
		public void setChecked(boolean checked) {
			if (checked)
				setImageResource(checked_resId);
			else
				setImageResource(check_resId);
		}

		@Override
		public boolean isChecked() {
			return false;
		}

		@Override
		public void toggle() {
		}
	}

	private int arrowNumber = 4;
	private int defaultArrow = 0;
	private int padding = 5;

	public interface OnItemClickListener {
		void onItem(int position);
	}

	private OnItemClickListener onItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public ArrowView(Context context, int bumber, int checked_res, int check_res) {
		super(context);
		this.arrowNumber = bumber;
		this.check_resId = check_res;
		this.checked_resId = checked_res;
		initView();
	}

	public ArrowView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(HORIZONTAL);
		setGravity(Gravity.CENTER);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.arrowView);
		check_resId = a.getResourceId(R.styleable.arrowView_arrowDefaultIcon, R.drawable.slider);
		checked_resId = a.getResourceId(R.styleable.arrowView_arrowIcon, R.drawable.large_slider);
		arrowNumber = a.getInteger(R.styleable.arrowView_arrowNumber, 2);
		defaultArrow = a.getInteger(R.styleable.arrowView_arrowDefault, 0);
		padding = a.getLayoutDimension(R.styleable.arrowView_arrowPadding, 5);
		a.recycle();
		initView();
	}

	private void initView() {
		removeAllViews();
		for (int i = 0; i < arrowNumber; i++) {
			Arrow img = new Arrow(getContext());
			img.setTag(i);
			img.setImageResource(check_resId);
			LinearLayout.LayoutParams pm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			pm.leftMargin = pm.rightMargin = padding;
			addView(img, pm);
		}
		if (defaultArrow > arrowNumber - 1)
			defaultArrow = 0;
		((Arrow) getChildAt(defaultArrow)).setChecked(true);
	}

	public void setNumber(int number) {
		if (arrowNumber == number || number <= 0) {
			return;
		}
		this.arrowNumber = number;
		initView();
	}

	private Rect frame = new Rect();
	private int mTouchChild;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (onItemClickListener == null)
			return true;
		final float xf = event.getX();
		final float yf = event.getY();
		final int action = event.getAction();
		if (MotionEvent.ACTION_MOVE == action || MotionEvent.ACTION_DOWN == action) {
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				final View child = getChildAt(i);
				child.getHitRect(frame);
				frame.inset(-20, -20);
				if (frame.contains((int) xf, (int) yf)) {
					mTouchChild = i;
					break;
				}
			}
		}
		else if (MotionEvent.ACTION_UP == action) {
			if (onItemClickListener != null) {
				setCheck(mTouchChild);
				onItemClickListener.onItem(mTouchChild);
			}
		}
		return true;
	}

	public void setCheck(int index) {
		if (index < getChildCount() && index != defaultArrow) {
			((Arrow) getChildAt(defaultArrow)).setChecked(false);
			((Arrow) getChildAt(index)).setChecked(true);
			defaultArrow = index;
		}
	}
}
