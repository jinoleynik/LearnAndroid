package com.shz.gamedev.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public abstract class BaseEnemy {
	protected Paint mPaint;
	protected int BASESPEED;
	protected int MINSPEED;
	public float X;
	public float Y;
	public double speed;
	public float hitpoints;
	public int size;
	private boolean mSlow;
	private float slowtime;
	private float slowtimer;
	private boolean mStun;
	private float stuntime;
	private float stuntimer;
	private boolean mBurn;
	private float burntime;
	private float burntimer;
	protected int ROW;

	public enum Debuff {
		SLOW, STUN, MAUL, NORMAL, BURN
	}

	public BaseEnemy() {
		mPaint = new Paint();
		mPaint.setColor(Color.parseColor("#ffff0000"));
		Y = (int) (Math.random() * 400);
		X = 900;

	}

	public void setDebuff(Debuff type) {
		switch (type) {
		case STUN:
			mStun = true;
			speed = 0;
			stuntime = 1;
			break;
		case SLOW:
			if (!mStun) {
				mSlow = true;
				speed *= 0.5;
				if (speed < MINSPEED)
					speed = MINSPEED;
				slowtimer = 0;
				slowtime = 1.2f;
			}
			break;
		case MAUL:
			X += 50;
			break;
		case NORMAL:

			break;
		case BURN:
			mBurn = true;
			burntime = 3;
			burntimer = 0;	
			break;
		default:
			break;
		}
	}

	protected void update() {
		if (mStun) {
			stuntimer += 0.02;
			if (stuntimer > stuntime) {
				mStun = false;
				stuntime = 0;
				stuntimer = 0;
				speed = BASESPEED;
			}
		}
		if (mSlow) {
			slowtimer += 0.02f;
			if (slowtimer > slowtime) {
				mSlow = false;
				slowtime = 0;
				slowtimer = 0;
				speed = BASESPEED;
			}
			ROW = 1;

		} else {
			ROW = 0;
		}
		if (mBurn) {
			burntimer += 0.02;
			if (burntimer > burntime) {
				mBurn = false;
				burntime = 0;
				burntimer = 0;
			}
			ROW = 2;
			damage(10);
		}

		X -= speed;

	}

	public int damage(int dam) {
		hitpoints -= dam;
		return (int)hitpoints;
	}

	public abstract void redraw(Canvas canvas);
}
