package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 滑动view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MyScrollLayout


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MyScrollLayout extends ViewGroup implements OnTouchListener {
	private static final String TAG = "ScrollLayout";
	private VelocityTracker mVelocityTracker; // 用于判断甩动手势
	private static final int SNAP_VELOCITY = 600;
	private Scroller mScroller; // 滑动控制
	private int mCurScreen;
	private int mDefaultScreen = 0;
	private float mLastMotionX;
	private OnViewChangeListener mOnViewChangeListener;

	public MyScrollLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyScrollLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public MyScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		mCurScreen = mDefaultScreen;
		setOnTouchListener(this);
		// mTouchSlop =
		// ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mScroller = new Scroller(context);
	}

	/***
	 * view改变
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		if (changed) {
			int childLeft = 0;
			final int childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
				if (childView.getVisibility() != View.GONE) {
					final int childWidth = childView.getMeasuredWidth();
					childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
					childLeft += childWidth;
				}
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		scrollTo(mCurScreen * width, 0);
	}

	/***
	 * 计算
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	/***
	 * 计算屏幕
	 * 
	 * @param whichScreen
	 */
	public void snapToScreen(int whichScreen) {
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0, Math.abs(delta) * 2);
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout
			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mCurScreen);
			}
		}
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	/***
	 * 是否能移动
	 * 
	 * @param deltaX
	 * @return
	 */
	private boolean IsCanMove(int deltaX) {
		if (getScrollX() <= 0 && deltaX < 0) {
			return false;
		}
		if (getScrollX() >= (getChildCount() - 1) * getWidth() && deltaX > 0) {
			return false;
		}
		return true;
	}

	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	/***
	 * 滑动事件
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		final int action = event.getAction();
		final float x = event.getRawX();
		switch (action) {
		case MotionEvent.ACTION_DOWN: // 按下时间
			Log.i("", "onTouchEvent  ACTION_DOWN");
			if (mVelocityTracker == null) { // 计算长度
				mVelocityTracker = VelocityTracker.obtain();
				mVelocityTracker.addMovement(event);
			}
			if (!mScroller.isFinished()) { // 停止动画
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			break;
		case MotionEvent.ACTION_MOVE: // 移动操作
			int deltaX = (int) (mLastMotionX - x); // 计算相差多少
			if (IsCanMove(deltaX)) {
				if (mVelocityTracker != null) {
					mVelocityTracker.addMovement(event); // 添加view
				}
				mLastMotionX = x;
				scrollBy(deltaX, 0);
			}
			break;
		case MotionEvent.ACTION_UP: // 触摸结束
			int velocityX = 0;
			if (mVelocityTracker != null) {
				mVelocityTracker.addMovement(event);
				mVelocityTracker.computeCurrentVelocity(1000); // 设置速率
				velocityX = (int) mVelocityTracker.getXVelocity();
			}
			if (velocityX > SNAP_VELOCITY && mCurScreen > 0) { // 滑动到上一个界面操作
				// Fling enough to move left
				Log.e(TAG, "snap left");
				snapToScreen(mCurScreen - 1);
			}
			else if (velocityX < -SNAP_VELOCITY && mCurScreen < getChildCount() - 1) {
				// Fling enough to move right
				Log.e(TAG, "snap right");
				snapToScreen(mCurScreen + 1); // 下一个界面
			}
			else {
				snapToDestination();
			}
			if (mVelocityTracker != null) { // 释放内存
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			break;
		}
		return true;
	}
}
