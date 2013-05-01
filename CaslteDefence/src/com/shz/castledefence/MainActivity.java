package com.shz.castledefence;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.andengine.engine.Engine;
import org.andengine.engine.LimitedFPSEngine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.color.Color;

import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnTouchListener;

public class MainActivity extends SimpleBaseGameActivity implements
		IUpdateHandler, ITouchArea {

	private static final float CAMERA_WIDTH = 800;
	private static final float CAMERA_HEIGHT = 480;
	private static final long BAT = 300;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TextureRegion mImage;
	private Camera mCamera;
	private DigitalOnScreenControl mDigitalOnScreenControl;
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private float mXpos;
	private float mYpos;

	private Sprite mMario;
	private float mRotation;
	private float second;
	private long T;

	private List<Model> mListModel;
	private List<Sprite> mListBox;
	private TextureRegion mBullet;
	private ArrayList<Bullet> mBulletList;
	private Scene mMainScene;
	private int mCount;
	private long mTime;
	private TextureRegion mEnemy;
	private int mHphero = 360;

	@Override
	protected void onCreate(Bundle pSavedInstanceState) {

		super.onCreate(pSavedInstanceState);
	}

	// private GenericPool<Bullet> mBulletPool = new GenericPool<Bullet>() {
	//
	// @Override
	// protected Bullet onAllocatePoolItem() {
	// return new Bullet(mBullet, getVertexBufferObjectManager());
	// }
	// };
	// private GenericPool<Model> mModelPool = new GenericPool<Model>() {
	//
	// @Override
	// protected Model onAllocatePoolItem() {
	// return new Model(mBullet, getVertexBufferObjectManager());
	// }
	// };
	private TextureRegion mBox;
	private TextureRegion mSup;
	private Model mGather;
	private BitmapTextureAtlas mBitmapTextureAtlasBack;
	private TextureRegion mBack;
	private Model mHome;
	private TextureRegion mButtonImage;
	private Sprite mButton;

	@Override
	public Engine onCreateEngine(EngineOptions pEngineOptions) {
		return new LimitedFPSEngine(pEngineOptions, 25);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// mCamera = new SmoothCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, 1000,
		// 500,
		// 0.5f);
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		// engineOptions.getAudioOptions().setNeedsSound(true);

		return engineOptions;

	}

	@Override
	protected void onCreateResources() {
		mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
				512, 64);
		mImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "image.png", 0, 0);
		mEnemy = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "drakon.png", 60, 0);
		mBullet = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "bullet.png", 120, 0);
		mBox = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "box.png", 150, 0);
		mSup = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "sup.png", 210, 0);
		mButtonImage = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlas, this, "button.png", 270, 0);
		this.mBitmapTextureAtlas.load();

		mBitmapTextureAtlasBack = new BitmapTextureAtlas(
				this.getTextureManager(), 512, 256);
		mBack = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				this.mBitmapTextureAtlasBack, this, "back.png", 0, 0);
		this.mBitmapTextureAtlasBack.load();
		this.mOnScreenControlTexture = new BitmapTextureAtlas(
				this.getTextureManager(), 256, 128, TextureOptions.BILINEAR);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);
		this.mOnScreenControlTexture.load();
	}

	@Override
	protected Scene onCreateScene() {
		mListBox = new ArrayList<Sprite>();
		mBulletList = new ArrayList<Bullet>();
		mMainScene = new Scene();
		mMainScene.registerTouchArea(this);
		mMainScene.registerUpdateHandler(this);
		mMainScene.registerUpdateHandler(new FPSLogger());
		mListModel = new ArrayList<Model>();
		mHome = new Model(mSup, getVertexBufferObjectManager());
		mGather = new Model(mSup, getVertexBufferObjectManager());
		mGather.thisX = 0;
		mGather.thisY = 0;
		mButton = new ButtonSprite(100, 100, mButtonImage,
				getVertexBufferObjectManager()) {
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Log.d("my_log", "boom");
				return false;

			}
		};
		
		mMainScene.registerTouchArea(mButton);
		mMario = new Sprite(200, 200, mImage, getVertexBufferObjectManager());
		mDigitalOnScreenControl = new DigitalOnScreenControl(0, CAMERA_HEIGHT
				- mOnScreenControlBaseTextureRegion.getHeight(), this.mCamera,
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.04f,
				this.getVertexBufferObjectManager(),
				new IOnScreenControlListener() {
					@Override
					public void onControlChange(
							final BaseOnScreenControl pBaseOnScreenControl,
							final float pValueX, final float pValueY) {
						if (pValueX == 1) {
							mXpos += 10;
						} else if (pValueX == -1) {
							mXpos -= 10;
						} else if (pValueY == 1) {
							mYpos += 10;
						} else if (pValueY == -1) {
							mYpos -= 10;
						}

						mMario.setPosition(mXpos, mYpos);
						// mCamera.setCenter(mXpos, mYpos);
					}
				});

		/* Make the controls semi-transparent. */
		this.mDigitalOnScreenControl.getControlBase().setBlendFunction(
				GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		this.mDigitalOnScreenControl.getControlBase().setAlpha(0.5f);
		Sprite sb = new Sprite(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, mBack,
				getVertexBufferObjectManager());
		SpriteBackground spback = new SpriteBackground(sb);
		mMainScene.setBackground(spback);
		mMainScene.setChildScene(this.mDigitalOnScreenControl);
		mMainScene.attachChild(mButton);
		// mMainScene.registerTouchArea(mButton);
		// scene.attachChild(sprite2);
		mMainScene.attachChild(mMario);
		mMainScene.attachChild(mHome);
		mMainScene.attachChild(mGather);

		return mMainScene;
	}

	private void removeBullet(final Sprite sp, Iterator it) {
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mMainScene.detachChild(sp);

			}
		});
		it.remove();
	}

	private void removeModel(final Sprite sp, Iterator it) {
		runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				mMainScene.detachChild(sp);
			}
		});
		it.remove();
	}

	@Override
	public void onUpdate(float tup) {
		mCount++;
		second += tup;

		if (second > 1) {
			second = 0;
			mCount = 0;
			if (mListModel.size() < 5) {
				Model m = new Model(mEnemy, getVertexBufferObjectManager());
				mMainScene.attachChild(m);
				mListModel.add(m);
				// Log.d("my_log", "size = " + mListModel.size());
			}
		}
		// mBulletPool.obtainPoolItem().update();

		Iterator<Bullet> bul = mBulletList.iterator();
		while (bul.hasNext()) {
			Bullet b = bul.next();
			b.update();
			if (b.X > 800) {
				removeBullet(b, bul);
				break;
			}
			Iterator<Model> mod = mListModel.iterator();
			while (mod.hasNext()) {
				Model m = mod.next();
				if (m.collidesWith(b)) {
					removeBullet(b, bul);
					m.HP -= 40;
					if (m.HP < 0) {
						if (Math.random() < 0.5) {
							Sprite ps = new Sprite(m.thisX, m.thisY, mBox,
									getVertexBufferObjectManager());
							mMainScene.attachChild(ps);
							mListBox.add(ps);
						}
						removeModel(m, mod);
					}
					break;
				}
			}
		}
		Iterator<Model> mod = mListModel.iterator();
		while (mod.hasNext()) {
			Model m = mod.next();
			if (m.thisX < 0 || m.thisY > 400 || m.thisY < -200) {
				m.thisX = 800;
				m.thisY = 200;
				// removeModel(m, mod);
				// break;
			}

			m.attackTarget(mMario);
			if (m.collidesWith(mMario)) {
				if (m.mAtack == true) {
					// Log.d("my_log", m.mAtack + "");
					// mMario.setRotation(mRotation += 5);
					mHphero -= 5;
				}
			}
		}
		if (mListBox.size() > 0 && mGather.getTarget() == null) {
			mGather.setTarget(mListBox.get(0));
		}
		if (mGather.getTarget() != null) {
			if (mGather.collidesWith(mGather.getTarget())) {
				if (mGather.getTarget() == mHome) {
					mGather.setTarget(null);
				} else {
					mMainScene.detachChild(mGather.getTarget());
					mListBox.remove(mGather.getTarget());
					mGather.setTarget(mHome);
				}
			} else {
				// Log.d("my_log", mGather.getTarget().getX() + "    "+
				// mGather.getTarget().getY());
				mGather.moveToTarget();
			}
		}
		// Log.d("my_log", mGather.getX() + "  ---- " + mGather.getY() +
		// " angle = "+mGather.angle);
		Iterator<Sprite> box = mListBox.iterator();
		if (mHphero < 0) {
			mHphero = 360;
			// Log.d("my_log", "dead");
		}
	}

	@Override
	public void reset() {

	}

	@Override
	public boolean contains(float pX, float pY) {
		double valX = mMario.getX() - pX;
		double valY = mMario.getY() - pY;
		double angle = Math.PI + Math.atan2(valY, valX);
		if (mTime < System.currentTimeMillis() - BAT) {
			mTime = System.currentTimeMillis();
			// Bullet bullet = mBulletPool.obtainPoolItem();
			Bullet bullet = new Bullet(mMario.getX(), mMario.getY(), mBullet,
					getVertexBufferObjectManager());
			bullet.angle = angle;
			mMainScene.attachChild(bullet);
			mBulletList.add(bullet);
			Log.d("my_log", "deg = " + Math.toDegrees(angle));
		}
		return true;
	}

	@Override
	public float[] convertSceneToLocalCoordinates(float pX, float pY) {
		return new float[] { pX, pY };
	}

	@Override
	public float[] convertLocalToSceneCoordinates(float pX, float pY) {
		// TODO Auto-generated method stub
		return new float[] { pX, pY };
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {

		return false;
	}
}