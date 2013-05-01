package com.shz.gamedev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.shz.gamedev.objects.BaseEnemy;
import com.shz.gamedev.objects.Mob;
import com.shz.gamedev.objects.Pew;
import com.shz.gamedev.objects.Tanke;

public class PewPewView extends AbstractGameView {
	private Paint mPoint;
	private List<Pew> mPewList = new ArrayList<Pew>();
	private int mT = 0;
	private int mCont = 10;
	private List<BaseEnemy> mEnemyList = new ArrayList<BaseEnemy>();
	private int mLife = 1000;
	private int mScore = 0;
	private boolean mGameOver;
	private Bitmap mBackGround;
	private Matrix mMatrix;
	private long mLastClick;
	private long mDalay = 500;
	private long mMax;
	private Bitmap mEnemyMob;
	private Bitmap mArrows;
	private Bitmap mTank;

	public PewPewView(Context context) {
		super(context);
		mPoint = new Paint();
		mPoint.setTextSize(20);
		mMatrix = new Matrix();
		mMatrix.setScale(1.5f, 1.5f);
	}

	@Override
	public void reDraw(Canvas canvas) {
		long t1 = System.currentTimeMillis();
		if (mLife > 0) {
			mPoint.setColor(Color.WHITE);
			canvas.drawBitmap(mBackGround, mMatrix, mPoint);

			canvas.drawText("score = " + mScore, 10, 10, mPoint);
			canvas.drawText("Life = " + mLife, 10, 30, mPoint);
			mT++;
			if (mT >= mCont) {
				mCont = (int) (10 + Math.random() * 20);
				mT = 0;
				if(Math.random()>0.5){
					mEnemyList.add(new Mob(mEnemyMob));
				}else{
					mEnemyList.add(new Tanke(mTank));
					
				}
			}

			Iterator<Pew> pew = mPewList.iterator();
			while (pew.hasNext()) {
				Pew p = pew.next();
				if (p.X1 < 0 || p.X1 > getWidth() || p.Y1 < 0
						|| p.Y1 > getHeight()) {
					pew.remove();
				} else {
					Iterator<BaseEnemy> enem = mEnemyList.iterator();
					while (enem.hasNext()) {
						BaseEnemy e = enem.next();
						if ((Math.abs(p.X1 - e.X) <= (20 + e.size * 2) / 2f)
								&& (Math.abs(p.Y1 - e.Y) <= (20 + e.size * 2) / 2f)) {
							if (e.damage(p.damage) < 0) {
								enem.remove();
							}
							e.setDebuff(p.type);
							pew.remove();
							mScore++;
						}
					}
					p.redraw(canvas);
				}

			}
			Iterator<BaseEnemy> enem = mEnemyList.iterator();
			while (enem.hasNext()) {
				BaseEnemy e = enem.next();
				if (e.hitpoints < 0) {
					enem.remove();
				} else {
					if (e.X < 50) {
						enem.remove();
						mLife--;
					} else {
						e.redraw(canvas);
					}
				}
			}

		} else {
			mGameOver = true;
			mPoint.setTextSize(50);
			canvas.drawColor(Color.RED);
			mPoint.setColor(Color.BLACK);
			canvas.drawText("GAME OVER", 200, 200, mPoint);
		}
		long t2 = System.currentTimeMillis();
		long t3 = t2 - t1;
		if (t3 > mMax)
			mMax = t3;
		canvas.drawText("FPS = " + t3, 20, 100, mPoint);
		canvas.drawText("MAXdalay = " + mMax, 20, 200, mPoint);

	}

	@Override
	public void createView() {
		mBackGround = BitmapFactory.decodeResource(getResources(),
				R.drawable.bb_game_area_sand);
		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.enemy_base);
		mEnemyMob = Bitmap.createScaledBitmap(bmp, 90, 90, true);
		bmp.recycle();
		Bitmap bmp2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.arrows);
		mArrows = Bitmap.createScaledBitmap(bmp2, 50, 50, true);
		bmp2.recycle();
		Bitmap bmp3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.enemy_tanke);
		mTank = Bitmap.createScaledBitmap(bmp3, 90, 90, true);
		bmp3.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mLastClick < System.currentTimeMillis() - mDalay) {
			mLastClick = System.currentTimeMillis();
			if (mGameOver) {
				mGameOver = false;
				mLife = 20;
				mPoint.setTextSize(20);
				mEnemyList.clear();
				mPewList.clear();
			} else {
				float x = event.getX();
				float y = event.getY();
				double angle = Math.atan((double) (200 - y) / (-x));
				mPewList.add(new Pew(angle, mArrows));
			}
		}
		return true;
	}

}
