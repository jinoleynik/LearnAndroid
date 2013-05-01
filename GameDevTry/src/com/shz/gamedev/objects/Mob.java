package com.shz.gamedev.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;

public class Mob extends BaseEnemy {

	private Bitmap mBitmap;
	private int mImH;
	private int mImW;
	private float sec;
	private float MAXHP = 130;

	public Mob(Bitmap mob) {
		super();
		mPaint.setColor(Color.parseColor("#ffff0000"));
		mPaint.setStrokeWidth(3);
		Y = (int) (Math.random() * 400);
		BASESPEED = 5;
		MINSPEED = 1;
		speed = BASESPEED;
		hitpoints = MAXHP;
		size = 15;
		mBitmap = mob;
		mImH = mob.getHeight() / 3;
		mImW = mob.getWidth() / 3;

	}

	public void redraw(Canvas canvas) {
		update();
		sec += 0.2;
		if (sec > 2)
			sec = 0;
		int srcX = 30 * (int) sec;

		Rect src = new Rect(srcX, ROW * mImH, srcX + mImW, ROW * mImH + mImH);
		RectF dst = new RectF(X, Y, X + mImW, Y + mImH);
		canvas.drawBitmap(mBitmap, src, dst, null);
		float hbar =hitpoints / MAXHP;
		float hx =X + (float)mImW*hbar;
//		Log.d("my_log", "hbar = " + String.valueOf(hbar) + "   hx = " + String.valueOf(hx));
		canvas.drawLine(X, Y - size,  hx, Y - size, mPaint);
		// canvas.drawCircle(X, Y, size, mPaint);
		// canvas.drawText("hp=" + hitpoints, X + size, Y, mPaint);
	}
}
