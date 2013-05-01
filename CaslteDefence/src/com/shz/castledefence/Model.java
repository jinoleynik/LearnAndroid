package com.shz.castledefence;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Model extends Sprite {

	public float thisX = 900;
	public float thisY;
	private float speed;

	private float mRotate;
	public boolean mAtack;

	public int HP = 98;
	private Sprite mTarget;
	public double angle;

	public Model(ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) {
		super(0, 0, pTextureRegion, pVertexBufferObject);
		thisY = (float) (Math.random() * 480);
		speed = (float) (5 + Math.random() * 10);
	}
 
	public void setTarget(Sprite target) {
		mTarget = target;
	}

	public void moveToTarget() {
		moveToPosition(mTarget.getX(), mTarget.getY());
	}

	public void moveToTarget(Sprite target) {
		moveToPosition(target.getX(), target.getY());
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

	public void attackTarget(Sprite target) { 
		mTarget = target;
		if (collidesWith(target)) {
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
			moveToTarget(); 
		} 
	}

	public Sprite getTarget() {
		return mTarget;
	}

}
