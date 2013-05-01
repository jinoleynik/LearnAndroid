package com.shz.gamedev.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle extends BaseFigure {

	private int mColor;
	private int mWeight;
	private Paint mPaint;

	public Circle(int color, int size) {
		mPaint = new Paint();
		mColor = color;
		mPaint.setColor(mColor);
		mSize = size;
	}

	public void setRadius(int radius) {
		mSize = radius;
	}

	public void setWeight(int weight) {
		mWeight = weight;
	}

	public void drawCircle(Canvas canvas) {
		canvas.drawCircle(mPosX, mPosY, mSize, mPaint);
	}
}
