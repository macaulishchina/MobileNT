package com.sinoyd.environmentNT.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sinoyd.environmentNT.R;
import com.sinoyd.environmentNT.util.SystemUtil;

/**
 * 底部菜单按钮 Copyright (c) 2015 江苏远大信息股份有限公司
 * 
 * @类型名称：MenuButton


 * @维护人员：
 * @维护日期：
 * @功能摘要：
 */
@SuppressLint("Recycle")
public class MenuButton extends LinearLayout implements Checkable {
	private ImageView imgIcon;
	private TextView tvName;
	private int textColor;

	public int marginleft,width,height;
	public MenuButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.tabWidget);
		imgIcon = new ImageView(context);
		imgIcon.setImageResource(a.getResourceId(R.styleable.tabWidget_icon2, R.drawable.logo));
		tvName = new TextView(context);
		tvName.setText(a.getString(R.styleable.tabWidget_text));
		tvName.setGravity(Gravity.CENTER);
		tvName.setSingleLine(true);
		tvName.setTextSize(a.getDimension(R.styleable.tabWidget_textSize, 10));
		textColor = a.getColor(R.styleable.tabWidget_textColor, 0XFFFFFFFF) | a.getInteger(R.styleable.tabWidget_textColor, 0XFFFFFFFF);
		tvName.setTextColor(textColor);
		
		 
//		 context.getTheme().resolveAttribute(android.R.attr.layout_height, typedValue, true);
		 
		if (isInEditMode()) { return; }
	    
		addView(imgIcon, new LayoutParams(a.getLayoutDimension(R.styleable.tabWidget_iconWidth, 30), a.getLayoutDimension(R.styleable.tabWidget_iconHeight, 30)));
		addView(tvName, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		 
		
		 TypedValue typedValue = new TypedValue();
		 context.getTheme().resolveAttribute(android.R.attr.layout_height, typedValue, true);
		 int[] attribute = new int[] { android.R.attr.layout_height,android.R.attr.layout_width,android.R.attr.layout_marginLeft };
		 TypedArray array = context.obtainStyledAttributes(attrs,  attribute);
//		 height = array.getDimensionPixelSize(0 /* index */, -1 /* default size */);
		 width = array.getDimensionPixelSize(1 /* index */, SystemUtil.dipToPx(context, 60) /* default size */);
		 marginleft = array.getDimensionPixelSize(2  , 0 );
		 
		 array.recycle();
		 
	}

	private boolean checked = false;

	public void setText(String text) {
		tvName.setText(text);
	}

	public void setICON(int resId) {
		imgIcon.setImageResource(resId);
	}

	@Override
	public void setChecked(boolean checked) {
		this.checked = checked;
		if (checked) {
			tvName.setTextColor(getResources().getColor(R.color.radio_text_select));
			imgIcon.setImageState(new int[] { android.R.attr.state_focused, android.R.attr.state_pressed }, true);
		}
		else {
			tvName.setTextColor(textColor);
			imgIcon.setImageState(new int[] {}, true);
		}
	}

	@Override
	public boolean isChecked() {
		return checked;
	}

	@Override
	public void toggle() {
		setChecked(!checked);
	}

	@Override
	public boolean performClick() {
		toggle();
		return super.performClick();
	}
	
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		
//		setMeasuredDimension(width, height);
//	}

}
