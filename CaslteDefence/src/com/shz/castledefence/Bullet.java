package com.shz.castledefence;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Bullet extends Sprite {

	public float X;
	public float Y;
	public double speed = 10;
	public double angle;

	public Bullet(float x, float y,ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObject) {
		super(0, 0, pTextureRegion, pVertexBufferObject);
		X = x;
		Y = y;
	}

	public void update() {
		X += (float) (speed * Math.cos(angle));
		Y += (float) (speed * Math.sin(angle));
		setPosition(X, Y);
	}
}
