package com.ztc1997.kernelhacker.widget;

import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;

public class DrawRectView extends View
{
	private Point startPoint, endPoint;
	private OnRectDrawListener mOnRectDrawListener;
	public int left, top, right, buttom;
    private Paint paint1 = new Paint(), paint2 = new Paint();
    
    public DrawRectView(Context context){
        super(context);
        initView();
    }
	
	public DrawRectView(Context context, AttributeSet attr){
		super(context, attr);
        initView();
	}
    
    private void initView(){
        paint1.setColor(Color.BLUE);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(5f);
        paint1.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.BLUE);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setAlpha(50);
        setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View view, MotionEvent event){
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startPoint = new Point((int) event.getX(), (int) event.getY());
                        break;

                    case MotionEvent.ACTION_UP:
                        if (mOnRectDrawListener != null)
                            mOnRectDrawListener.onRectDrawed(left, top, right, buttom);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        endPoint = new Point((int) event.getX(), (int) event.getY());
                        left = Math.min(startPoint.x, endPoint.x);
                        right = Math.max(startPoint.x, endPoint.x);
                        top = Math.min(startPoint.y, endPoint.y);
                        buttom = Math.max(startPoint.y, endPoint.y);
                        postInvalidate();
                        if (mOnRectDrawListener != null)
                            mOnRectDrawListener.onRectDrawing(left, top, right, buttom);
                        break;
                }
                return true;
            }
        });
    }
	
	public void clean(){
		left = right = top = buttom = 0;
		postInvalidate();
	}

	public void setOnRectDrawingListener(OnRectDrawListener mOnRectDrawListener)
	{
		this.mOnRectDrawListener = mOnRectDrawListener;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO: Implement this method
		super.onDraw(canvas);
        if (left == right || top == buttom)
            return;
		canvas.save();
		canvas.drawRect(left, top, right, buttom, paint1);
		canvas.drawRect(left, top, right, buttom, paint2);
		canvas.restore();
	}
	
	public static interface OnRectDrawListener{
		public abstract void onRectDrawing(int left, int top, int right, int buttom);
		public abstract void onRectDrawed(int left, int top, int right, int buttom);
	}
	
}
