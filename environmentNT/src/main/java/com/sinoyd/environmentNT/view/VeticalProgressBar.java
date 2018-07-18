package com.sinoyd.environmentNT.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class VeticalProgressBar extends ProgressBar {

	public VeticalProgressBar(Context context) {
		super(context);
	}
	
	public VeticalProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override  
    protected synchronized void onDraw(Canvas canvas)  
    {  
        // TODO Auto-generated method stub  
        canvas.rotate(-90);//反转90度，将水平ProgressBar竖起来  
        canvas.translate(-getHeight(), 0);//将经过旋转后得到的VerticalProgressBar移到正确的位置,注意经旋转<span style="white-space:pre">                     </span>    后宽高值互换  
        super.onDraw(canvas);  
    }  
  
    @Override  
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);  
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());//互换宽高值  
    }  
      
    @Override  
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {  
        super.onSizeChanged(h, w, oldw, oldh);//互换宽高值  
    }  
	
}
