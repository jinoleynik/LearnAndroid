package com.shz.castledefence.scene;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

import com.shz.castledefence.util.LoadTexture;

public class MainMenuScene extends CameraScene implements IOnSceneTouchListener {

	
	public MainMenuScene(){
		super(MainActivity.CAMERA);
		Sprite sp = new Sprite(0, 0, MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_HEIGHT, LoadTexture.BOX, MainActivity.BUFFER);
		SpriteBackground spb = new SpriteBackground(sp);
		setBackground(spb);
		ButtonSprite button = new ButtonSprite(100, 100, LoadTexture.BUTTONIMAGE,
				MainActivity.BUFFER) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.d("my_log", "boom");
				MainState.showGame();
				return true;

			}
		};	
		attachChild(button);
		registerTouchArea(button);
		setOnSceneTouchListener(this);
	}
	
	public void Show() {
		setVisible(true);
		setIgnoreUpdate(false);
		
	}
	
	public void Hide() {
		setVisible(false);
		setIgnoreUpdate(true);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		//MainState.showGame();
		return false;
	}


}
