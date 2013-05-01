package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DegView extends View {

	private double mDegre;
	private double speed = 200;
	private Paint paint;

	public DegView(Context context) {
		super(context);
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(5);	
	}

	@Override
	protected void onDraw(Canvas canvas) {
		float startX = getWidth() / 2;
		float startY = getHeight() / 2;
		float endX = startX + (float) (speed * Math.cos(mDegre));
		float endY = startY + (float) (speed * Math.sin(mDegre));
		canvas.drawLine(startX, startY, endX, endY, paint);
		super.onDraw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		double dx = (getWidth() / 2) - event.getX();
		// Minus to correct for coord re-mapping
		double dy = (getHeight() / 2) - event.getY();

		mDegre = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
//		if (mDegre < 0)
//			mDegre = Math.abs(mDegre);
//		else
			mDegre = Math.PI + mDegre;

		Log.d("my_log", "deg = " + Math.toDegrees(mDegre));
		invalidate();
		return true;
	}
}
