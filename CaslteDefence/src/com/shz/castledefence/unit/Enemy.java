package com.shz.castledefence.unit;

import com.shz.castledefence.util.LoadTexture;

public class Enemy extends Model {

	public Enemy() {
		super(LoadTexture.ENEMY);
		speed = 10;
		thisX=800;
		int row = (int) (Math.random() * 8);
		thisY = (float) (row * getHeight());
		HP= 50;
		mAttackRange = 0;
		setFinalPosition(0, thisY);
	}

}
