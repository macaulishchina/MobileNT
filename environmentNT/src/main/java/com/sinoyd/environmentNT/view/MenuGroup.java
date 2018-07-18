package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义Group控件，用于底部菜单结构 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MenuGroup


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
public class MenuGroup extends LinearLayout {
	private int mCurrentItem = -1;
	private List<MenuButton> buttons = new ArrayList<MenuButton>();
	private OnMenuItemClickListener onMenuItemClickListener;

	public void setOnMenuItemClickListener(
			OnMenuItemClickListener onMenuItemClickListener) {
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	Context mcontext;

	/***
	 * 菜单处理
	 * 
	 * @author smz
	 * 
	 */
	public interface OnMenuItemClickListener {
		/***
		 * 选中的菜单处理
		 * 
		 * @param position
		 * @param v
		 */
		void onItemClick(int position, View v);
	}

	int height = 0;
	int width = 0;
	int ScreenWidth = 0;

	public MenuGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedValue typedValue = new TypedValue();
		mcontext = context;
		// context.getTheme().resolveAttribute(android.R.attr.layout_height,
		// typedValue, true);
		int[] attribute = new int[] { android.R.attr.layout_height,
				android.R.attr.layout_width };
		TypedArray array = context.obtainStyledAttributes(attrs, attribute);
		height = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
		// width = array.getDimensionPixelSize(1 /* index */, -1 /* default size
		// */);
		array.recycle();
		WindowManager mWm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Log.d("屏幕宽度", Integer.toString(mWm.getDefaultDisplay().getWidth()));
		Log.d("屏幕高度", Integer.toString(mWm.getDefaultDisplay().getHeight()));
		// context.getTheme().obtainStyledAttributes(attrs, typedValue, true);
		// DisplayMetrics metrics=new DisplayMetrics();
		// WindowManager mWm =
		// (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		ScreenWidth = mWm.getDefaultDisplay().getWidth();
		// (
		// (WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
		// typedValue.getDimension(metrics);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		width = 0;
		if (changed) {
			buttons.clear();
			if (getChildCount() > 0) {
				for (i = 0; i < getChildCount(); i++) {
					// 动态获取子View实例
					View view = getChildAt(i);
					// 放置子View，宽高都是100
					// view.layout(l, t, r , b);
					// MarginLayoutParams lp =
					// (MarginLayoutParams)getChildAt(i).getLayoutParams();
					// l=l+getChildAt(i).getWidth();
					// r=r+getChildAt(i).getWidth()+lp.leftMargin;
					//
					buttons.add((MenuButton) getChildAt(i));
				}
			}
			if (buttons.size() > 0) {
				setItem(0);
				mCurrentItem = 0;
				for (i = 0; i < getChildCount(); i++) {
					buttons.get(i).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							gotoIndex(buttons.indexOf(v));
						}
					});
				}
			}
		}
	}

	/***
	 * 跳转到第几个Radio
	 * 
	 * @param position
	 */
	public void gotoIndex(int position) {
		setItem(position);
		if (getChildCount() > position && position != mCurrentItem) {
			mCurrentItem = position;
			if (onMenuItemClickListener != null) {
				onMenuItemClickListener.onItemClick(mCurrentItem,
						buttons.get(position));
			}
		}
	}

	int i = 0;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		width = 0;
		if (getChildCount() > 0) {
			for (i = 0; i < getChildCount(); i++) {
				// 动态获取子View实例
				View view = getChildAt(i);
				// 放置子View，宽高都是100
				width = width + ((MenuButton) getChildAt(i)).marginleft + ((MenuButton) getChildAt(i)).width;
			}
			width += 100;
		}
		if (ScreenWidth > width)
			width = ScreenWidth;
		setMeasuredDimension(width, height);
	}

	/***
	 * 设置item
	 * 
	 * @param position
	 */
	private void setItem(int position) {
		if (mCurrentItem > -1)
			((MenuButton) buttons.get(mCurrentItem)).setChecked(false);
		((MenuButton) buttons.get(position)).setChecked(true);
	}
}
