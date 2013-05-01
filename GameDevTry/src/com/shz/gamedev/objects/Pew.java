package com.shz.gamedev.objects;

import com.shz.gamedev.objects.BaseEnemy.Debuff;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class Pew {

	private Paint mPaint;
	public int X1;
	public int Y1;
	public double speed = 20;
	public double angle;
	public int damage = 25;
	private int X0;
	private int Y0;

	private float slowChanse = 0.3f;
	private float stunChanse = 0.3f;
	private float maulChanse = 0.3f;
	public Debuff type;
	private Bitmap mArrow;
	private int mImW;
	private int mImH;
	private int ROW;
	private Matrix mMatrix;

	public Pew(double ang, Bitmap arrows) {
		mArrow = arrows;
		mImW= arrows.getWidth();
		mImH = arrows.getHeight()/5;
		mMatrix = new Matrix();
		mMatrix.setRotate((float) ang);
	
		mPaint = new Paint();
		mPaint.setStrokeWidth(2);
		Y1 = 200;
		angle = ang;
		if (Math.random() < 0.2) {
			damage *= 2;
			ROW= 4;
		} else {
			if (Math.random() < 0.6) {
				type = Debuff.BURN;
				ROW =2;
			} else {
				if (Math.random() < slowChanse) {
					type = Debuff.SLOW;
					mPaint.setColor(Color.GREEN);
					ROW = 1;
				} else {
					if (Math.random() < stunChanse) {
						type = Debuff.STUN;
						ROW = 3;
					} else {
						if (Math.random() < maulChanse) {
							type = Debuff.MAUL;
							
						} else {
							type = Debuff.NORMAL;
						}
						ROW = 0;
					}
					mPaint.setColor(Color.YELLOW);
				}

			}
		}
		//mArrow = Bitmap.createBitmap(arrows, 0, ROW*mImH, mImW, mImH);
	}

	private void update() {
		
		X0 = X1;
		Y0 = Y1;
		X1 += (float) (speed * Math.cos(angle));
		Y1 += (float) (speed * Math.sin(angle));
	}

	public void redraw(Canvas canvas) {
		update();
		Rect src = new Rect(0, ROW * mImH,  mImW, ROW * mImH + mImH);
		RectF dst = new RectF(X1, Y1, X1 + mImW, Y1 + mImH);
		
		canvas.drawBitmap(mArrow, src, dst, mPaint);
//		mMatrix.setTranslate(X1, Y1);
//		canvas.drawBitmap(mArrow, mMatrix, mPaint);
//		canvas.drawLine(X0, Y0, X1, Y1, mPaint);
	}
}
