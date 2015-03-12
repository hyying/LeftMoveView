package com.huangying.leftmoveview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class MoveView extends ViewGroup {

    private static int windowWidth;
    private static int windowHeight;

	public MoveView(Context context) {
		super(context);
        init(context);
	}

	public MoveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        init(context);
	}

	public MoveView(Context context, AttributeSet attrs) {
		super(context, attrs);
        init(context);
	}

    private void init(Context context) {

        // --------获取窗体信息---------
        WindowManager wm = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        windowWidth = display.getWidth();
        windowHeight = display.getHeight();
    }

    private int startX = 0;
	private int startY = 0;
	private int dx;
	private int dy;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			super.onTouchEvent(event);
			startX = (int) event.getRawX();
			startY = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			int newX = (int) event.getRawX();
			int newY = (int) event.getRawY();

			dx = newX - startX;
			dy = newY - startY;
			if (Math.abs(dx) > Math.abs(dy)) {
				getParent().requestDisallowInterceptTouchEvent(true);
			} else {
				getParent().requestDisallowInterceptTouchEvent(false);
			}

			int l = getLeft() + dx;
			int t = getTop();
			int r = getRight() + dx;
			int b = getBottom();
			if (r >windowWidth
					|| r < windowWidth / 2) {
				break;
			}

			layout(l, t, r, b);
			startX = newX;
			startY = newY;
			break;

		case MotionEvent.ACTION_UP:

			if (Math.abs(dx) == 0) {
				super.onTouchEvent(event);
			}
			if (getRight() > windowWidth * 3 / 4) {
				layout(0, getTop(), windowWidth,
						getBottom());
			} else {
				layout(windowWidth/ 2 - getWidth(),
						getTop(), windowWidth / 2,
						getBottom());
			}
		}

		invalidate();
		return true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);
			// 测量child
			child.measure(widthMeasureSpec, heightMeasureSpec);
		}

		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			// view.getWidth(); 值为0

			view.layout(0, 0, getWidth(), getHeight());
			// 此时view.getWidth(); 才能得到view真实的大小
		}
	}

}
