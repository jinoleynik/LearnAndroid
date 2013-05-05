package com.shz.castledefence.scene;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.shz.castledefence.util.LoadTexture;

public class MainActivity extends SimpleBaseGameActivity implements IUpdateHandler {

	public static final float CAMERA_WIDTH = 800;
	public static final float CAMERA_HEIGHT = 480;
	public static Camera CAMERA;
	public static Engine ENGINE;

	public static VertexBufferObjectManager BUFFER;
	public static MainActivity CONTEXT;
	private MainState mMainstate;
	private boolean _GameLoaded;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {
		super.onCreate(pSavedInstanceState);
	}

 

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 25);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		CAMERA = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), CAMERA);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		ENGINE = mEngine;
		CONTEXT = this;
		BUFFER = getVertexBufferObjectManager();
		LoadTexture.LoadGFX();
	}

	


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			if (!_GameLoaded ) return true;
			if (mMainstate != null && _GameLoaded) {
				mMainstate.KeyPressed(keyCode, event);
				return true;
			}
			return true;
		}
	    return super.onKeyDown(keyCode, event);
	}
	@Override
	protected Scene onCreateScene() {
		_GameLoaded = true;
		mMainstate = new MainState();
		mMainstate.registerUpdateHandler(this);
		mMainstate.registerUpdateHandler(new FPSLogger());
		return mMainstate;
	}

	
	
	@Override
	public void onUpdate(float tup) {
		mMainstate.onUpdate();
	}

	@Override
	public void reset() {

	}

}