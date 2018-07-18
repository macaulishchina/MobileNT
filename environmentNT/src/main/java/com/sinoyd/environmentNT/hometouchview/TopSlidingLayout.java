package com.sinoyd.environmentNT.hometouchview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 滑动的view Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：TopSlidingLayout


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class TopSlidingLayout extends LinearLayout {


	/** 手指滑动需要达到的速度 **/
	public static final int SNAP_VELOCITY = 10;
	/** 用于计算手指滑动的速度 **/
	private VelocityTracker mVelocityTracker;
	private View downMenuView;
	private int duration = 200;
	/** 低端菜单布局的参数 **/
	private MarginLayoutParams downMenuLayoutParams;
	/** 屏幕高度值 **/
	private int screenHeight;
	/** 记录手指按下时的纵坐标 **/
	private float yDown;
	/** 记录手指移动时的纵坐标。 **/
	private float yMove;
	/** 记录手机抬起时的纵坐标 **/
	private float yUp;
	/** 记录当前的滑动状态 **/
	private int slideState;
	private Scroller mScroller;
	/** 手指在屏幕上没有移动 **/
	private static final int DO_NOTHING = 0;
	/** 手指在屏幕上向上移动 **/
	private static final int MOVE_UP = DO_NOTHING + 1;
	/** 手指在屏幕上向下移动 **/
	private static final int MOVE_DOWN = MOVE_UP + 1;

	public TopSlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOrientation(VERTICAL);
		mScroller = new Scroller(getContext(), sInterpolator);
		ViewConfiguration.get(context).getScaledTouchSlop();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

	private int maxScrollY = 0;

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		super.onWindowFocusChanged(hasWindowFocus);
//		getMaxScrollY();
	}

	@SuppressWarnings("deprecation")
	private void getMaxScrollY() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		screenHeight = wm.getDefaultDisplay().getHeight();
		if (downMenuView != null) {
			int[] location = new int[2];
			downMenuView.getLocationInWindow(location);
			maxScrollY = Math.abs((screenHeight - location[1]) - downMenuLayoutParams.height);
		}
	}

	/**
	 * 调用此方法滚动到目标位置
	 * 
	 * @param fy
	 * @param duration
	 */
	public void smoothScrollTo(int fy, int duration) {
		int dx = 0 - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy, duration);
	}

	/**
	 * 调用此方法设置滚动的相对偏移
	 * 
	 * @param dx
	 * @param dy
	 * @param duration
	 */
	public void smoothScrollBy(int dx, int dy, int duration) {
		mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy, duration);
		invalidate();
	}

	@Override
	public void computeScroll() {
		// 先判断mScroller滚动是否完成
		if (!mScroller.isFinished()) {
			if (mScroller.computeScrollOffset()) {
				// 这里调用View的scrollTo()完成实际的滚动
				int oldX = getScrollX();
				int oldY = getScrollY();
				int x = mScroller.getCurrX();
				int y = mScroller.getCurrY();
				if (oldX != x || oldY != y) {
					scrollTo(x, y);
				}
				// 必须调用该方法，否则不一定能看到滚动效果
				invalidate();
				return;
			}
		}
		super.computeScroll();
	}

	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			downMenuView = getChildAt(1);
			downMenuLayoutParams = (MarginLayoutParams) downMenuView.getLayoutParams();
		}
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		getMaxScrollY();
	}
	/**
	 * 创建VelocityTracker对象，并将触摸事件加入到VelocityTracker当中。
	 * 
	 * @param event滑动事件
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 获取手指在垂直方向上的滑动速度。
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollYVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getYVelocity();
		return Math.abs(velocity);
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	/**
	 * 根据手指移动的距离，判断当前用户的滑动意图，然后给slideState赋值成相应的滑动状态值。
	 * 
	 * @param moveDistanceY 纵向移动的距离
	 */
	private void checkSlideState(float lastY) {
		if (lastY > mLastY) {
			slideState = MOVE_DOWN;
		}
		else {
			slideState = MOVE_UP;
		}
	}

	private boolean isOpenDown;

	public boolean isOpenDown() {
		return isOpenDown;
	}

	public void openDown() {
		smoothScrollTo(maxScrollY, duration);
		isOpenDown = true;
	}

	public void closeDown() {
		smoothScrollTo(0, duration);
		isOpenDown = false;
	}

	private float mLastY;

//	@Override
//	public boolean onTouchEvent(MotionEvent event) {
//		if (maxScrollY == 0)
//			getMaxScrollY();
//		RealTimeShowView.isTouchRecordView = false;
//		createVelocityTracker(event);
//		switch (event.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			// LogUtils.d("====== ACTION_DOWN");
//			yDown = event.getY();
//			mLastY = yDown;
//			event.getX();
//			slideState = DO_NOTHING;
//			break;
//		case MotionEvent.ACTION_MOVE:
//			// LogUtils.d("====== ACTION_MOVE");
//			yMove = event.getY();
//			event.getX();
//			checkSlideState(yMove);
//			int cha = (int) (mLastY == 0 ? (yMove - yDown) : (yMove - mLastY));
//			int condition = getScrollY() - cha;
//			switch (slideState) {
//			case MOVE_DOWN:
//				if (condition >= 0)
//					scrollTo(0, getScrollY() - cha);
//				break;
//			case MOVE_UP:
//				if (condition <= maxScrollY)
//					scrollTo(0, getScrollY() - cha);
//				break;
//			}
//			mLastY = yMove;
//			break;
//		case MotionEvent.ACTION_UP:
//			// LogUtils.d("====== ACTION_UP");
//			yUp = event.getY();
//			mScroller.setFinalY(getScrollY());
//			mScroller.abortAnimation();
//			mScroller.setFinalY(getScrollY());
//			if (event.getAction() == MotionEvent.ACTION_UP) {
//				// if (findViewById(R.id.viewTip).getVisibility() ==
//				// View.VISIBLE)
//				// findViewById(R.id.viewTip).setVisibility(View.GONE);
//			}
//			// 检查当前手指在屏幕上的滑动状态，如果是向上滑动且滑动距离超过指定距离则打开图标view
//			if (yUp > yDown) {
//				slideState = MOVE_DOWN;
//			}
//			else {
//				slideState = MOVE_UP;
//			}
//			// LogUtils.d("====== slideState  : " + slideState);
//			switch (slideState) {
//			case MOVE_DOWN:
//				if ((Math.abs(yDown - yUp) > downMenuLayoutParams.height / 5) || getScrollYVelocity() > SNAP_VELOCITY) {
//					closeDown();
//				}
//				else {
//					openDown();
//				}
//				// if (shouldScrollMenu()) {
//				// LogUtils.d("====== ACTION_UP MOVE_DOWN  closeDown--------");
//				// closeDown();
//				// } else {
//				// LogUtils.d("====== ACTION_UP MOVE_DOWN openDown-------");
//				// openDown();
//				// }
//				break;
//			case MOVE_UP:
//				if ((Math.abs(yDown - yUp) > downMenuLayoutParams.height / 5) || getScrollYVelocity() > SNAP_VELOCITY) {
//					openDown();
//				}
//				else {
//					closeDown();
//				}
//				// if (getScrollYVelocity() > SNAP_VELOCITY) {
//				//
//				// }
//				// if (shouldScrollMenu()) {
//				// LogUtils.d("====== ACTION_UP MOVE_UP  openDown--------");
//				// if (Math.abs(yDown - yUp) > downMenuLayoutParams.height / 5)
//				// {
//				// openDown();
//				// }
//				// } else {
//				// LogUtils.d("====== ACTION_UP MOVE_UP  closeDown--------");
//				// if (Math.abs(yDown - yUp) > downMenuLayoutParams.height / 5)
//				// {
//				// closeDown();
//				// }
//				// }
//				break;
//			}
//			mLastY = 0;
//			recycleVelocityTracker();
//			break;
//		}
//		return true;
//	}
}
