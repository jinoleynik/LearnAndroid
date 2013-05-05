package com.shz.castledefence.unit;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.shz.castledefence.scene.MainActivity;

public class Model extends Sprite {

	public float thisX;
	public float thisY;
	protected float speed;
	protected float Xfinal;
	protected float Yfinal;
	protected float mRotate;
	public boolean mAtack;
	protected float mAttackRange;
	protected int HP;
	protected Sprite mTarget;
	public double angle;

	public Model(ITextureRegion pTextureRegion) {
		super(0, 0, pTextureRegion, MainActivity.BUFFER);
		init();
	}

	public Model(float x, float y, ITextureRegion pTextureRegion) {
		super(x, y, pTextureRegion, MainActivity.BUFFER);
		thisX = x;
		thisY = y;
		init();
	}

	private void init() {
		speed = (float) (5 + Math.random() * 10);
		Xfinal = 0;
		Yfinal = thisY;
	}

	/**
	 * 
	 * @param dam
	 * @return die - false;
	 */
	public boolean damage(int dam) {
		HP -= dam;
		return HP > 0;
	}

	public void setTarget(Sprite target) {
		mTarget = target;
	}

	public void move() {
		if (mTarget != null) {
			moveToPosition(mTarget.getX(), mTarget.getY());
		} else {
			moveToPosition(Xfinal, Yfinal);
		}
	}

	public void moveToTarget(Sprite target) {
		moveToPosition(target.getX(), target.getY());
	}

	public void setFinalPosition(float x, float y) {
		Xfinal = x;
		Yfinal = y;
	}

	public void moveToPosition(float x, float y) {
		double angle = Math.PI + Math.atan((thisY - y) / (thisX - x));
		if (thisX < x) {
			angle += Math.PI;
		}
		thisX += (float) (speed * Math.cos(angle));
		thisY += (float) (speed * Math.sin(angle));

		setPosition(thisX, thisY);
	}

	private float getRangeToTarget() {
		if (mTarget != null) {
			Xfinal = mTarget.getSceneCenterCoordinates()[0];
			Yfinal = mTarget.getSceneCenterCoordinates()[1];
		}
		return (float) Math.sqrt((Math.pow((getX() - Xfinal), 2))
				+ (Math.pow((getY() - Yfinal), 2)));
	}

	public void attackTarget() {
		// if (collidesWith(mTarget)) {
		if (getRangeToTarget() < mAttackRange) {
			mRotate += 14.4f;
			mAtack = false;
			setRotation(mRotate);
			if (mRotate > 360) {
				mRotate = 0;
				mAtack = true;
			}
		} else {
			mRotate = 0;
			setRotation(mRotate);
			move();
		}
	}

	public Sprite getTarget() {
		return mTarget;
	}

}
