package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;
import com.sinoyd.environmentNT.R;

public class CursorView extends LinearLayout {
	private Scroller mScroller;

	public CursorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mScroller = new Scroller(getContext(), sInterpolator);
		MAX_MOVE_200 = getContext().getResources().getInteger(R.integer.max_move_200);
		cursor_scale = Double.parseDouble(getContext().getString(R.string.cursor_scale));
		cursor_scale_1 = Double.parseDouble(getContext().getString(R.string.cursor_scale_1));
	}

	private double cursor_scale = 0; // 200-300刻度增长率
	private double cursor_scale_1 = 0; // 300-500刻度增长率
	private int MAX_MOVE_200 = 84;// 0-200的高度px
	private final static int MAX_LEVEL_200 = 200;

	public void setLevel(int level) {
		double scrollValue = 0;
		if (getHeight() > 0) {
			if (level <= 200) {
				scrollValue = MAX_MOVE_200 * level / MAX_LEVEL_200;
			}
			else if (level > 200 && level <= 300) {
				scrollValue = MAX_MOVE_200 + (level - 200) * cursor_scale;
			}
			else if (level > 300 && level <= 500) {
				scrollValue = MAX_MOVE_200 + 100 * cursor_scale + (level - 300) * cursor_scale_1;
			}
			else {
				scrollValue = MAX_MOVE_200 * level / MAX_LEVEL_200;
			}
			System.out.println("========level " + level);
			smoothScrollTo((int) scrollValue, 2000);
		}
	}

	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}
	};

	// 调用此方法滚动到目标位置
	public void smoothScrollTo(int fy, int duration) {
		int dx = 0 - mScroller.getFinalX();
		int dy = fy - mScroller.getFinalY();
		smoothScrollBy(dx, dy, duration);
	}

	// 调用此方法设置滚动的相对偏移
	private void smoothScrollBy(int dx, int dy, int duration) {
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
}
