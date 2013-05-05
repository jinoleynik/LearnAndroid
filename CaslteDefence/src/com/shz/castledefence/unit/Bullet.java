package com.shz.castledefence.unit;

import com.shz.castledefence.scene.MainActivity;
import com.shz.castledefence.util.LoadTexture;

public class Bullet extends Model {


	public double speed = 10;
	public double angle;

	public Bullet(){
		super(0, MainActivity.CAMERA_HEIGHT/2, LoadTexture.BULLET);		
	}

	public void update() {
		thisX += (float) (speed * Math.cos(angle));
		thisY += (float) (speed * Math.sin(angle));
		setPosition(thisX, thisY);
	}
}
