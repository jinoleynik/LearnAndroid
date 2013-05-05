package com.shz.castledefence.scene;

import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.view.KeyEvent;

public class MainState extends Scene {
	private static MainMenuScene mMainMenu = new MainMenuScene();
	private static GameScene mGameScene = new GameScene();
	private static SceneType SceneT;

	public enum SceneType {
		MainMenu, Game
	}

	public MainState() {
		attachChild(mMainMenu);
		attachChild(mGameScene);
		showMainMenu();
	}

	public static void showMainMenu() {
		mMainMenu.Show();
		mGameScene.Hide();
		SceneT = SceneType.MainMenu;
	}

	public static void showGame() {
		mMainMenu.Hide();
		mGameScene.Show();
		SceneT = SceneType.Game;
	}

	
	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		switch (SceneT)
		{
		case MainMenu:
			mMainMenu.onSceneTouchEvent(pSceneTouchEvent);
			break;
		case Game:
			mGameScene.onSceneTouchEvent(pSceneTouchEvent);
			break;
		}
		return super.onSceneTouchEvent(pSceneTouchEvent);
	}

	public void KeyPressed(int keyCode, KeyEvent event) {
		switch (SceneT)
		{
		case MainMenu:
			MainActivity.CONTEXT.onBackPressed();
			break;
		case Game:
			showMainMenu();
			break;
		}
	}

	public void onUpdate() {
		if(SceneT == SceneType.Game){
			mGameScene.onUpdate();
		}
		
	}
}
