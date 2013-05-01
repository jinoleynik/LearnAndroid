package com.shz.gamedev;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.shz.gamedev.objects.Circle;

public class TryView extends AbstractGameView {

	private Circle mCircleCyan;
	private int mX1;
	private int mY1;
	private int mHeight;
	private int mWidth;
	private boolean mSideY2, mSideX1;
	private Paint mPaint;
	private Circle mCircleRed;
	private int mCounter;
	private int mX2;
	private int mY2;
	private boolean mSideX2;
	private boolean mSideY1;

	public TryView(Context context) {
		super(context);
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);
		mPaint.setTextSize(20);
		mPaint.setStrokeWidth(2);

	}

	@Override
	public void reDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		canvas.drawText("" + mCounter, 0, 0, mPaint);
		int rand1 = (int) (Math.random() * 20 + 10);
		int rand2 = (int) (Math.random() * 20 + 10);
		int rand3 = (int) (Math.random() * 20 + 10);
		mX1 = mSideX1 ? mX1 + rand1 : mX1 - rand1;
		mY1 = mSideY1 ? mY1 + rand1 : mY1 - rand1;
		mX2 = mSideX1 ? mX2 + rand2 : mX2 - rand2;
		mY2 = mSideY2 ? mY2 + rand2 : mY2 - rand2;
		if (mX1 > mWidth) {
			mX1 = mWidth;
			mSideX1 = false;
		} else if (mX1 < 0) {
			mX1 = 0;
			mSideX1 = true;
		}

		if (mY1 > getHeight()) {
			mY1 = getHeight();
			mSideY1 = false;
		} else if (mY1 < 0) {
			mY1 = 0;
			mSideY1 = true;
		}
		if (mX2 > mWidth) {
			mX2 = mWidth;
			mSideX2 = false;
		} else if (mX2 < 0) {
			mX2 = 0;
			mSideX2 = true;
		}

		if (mY2 > getHeight()) {
			mY2 = getHeight();
			mSideY2 = false;
		} else if (mY2 < 0) {
			mY2 = 0;
			mSideY2 = true;
		}
		mCircleCyan.setPosition(mX1, mY1);
		mCircleRed.setPosition(mX2, mY2);
		if (mCircleCyan.collision(mCircleRed)) {
			mX1 = mSideX1 ? mX1 -60: mX1+60;
			mX2 = mSideX2 ? mX2 -60: mX2+60;
			mCircleCyan.setPosition(mX1, mY1);
			mCircleRed.setPosition(mX2, mY2);
			mCounter++;
			Log.d("my_log", "STICHKA!  # "+mCounter);
			mSideX2 = mSideX1;	
			mSideX1 = !mSideX1;
		}
		mCircleCyan.drawCircle(canvas);
		mCircleRed.drawCircle(canvas);
	}

	@Override
	public void createView() {
		mCircleCyan = new Circle(Color.CYAN, 20);
		mCircleRed = new Circle(Color.RED, 30);
		mHeight = getHeight();
		mWidth = getWidth();
	}

}
