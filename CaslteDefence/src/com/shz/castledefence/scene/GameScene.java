package com.shz.castledefence.scene;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.andengine.entity.scene.CameraScene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.adt.pool.GenericPool;

import android.util.Log;

import com.shz.castledefence.unit.Bullet;
import com.shz.castledefence.unit.Enemy;
import com.shz.castledefence.util.LoadTexture;

public class GameScene extends CameraScene implements IOnSceneTouchListener {

	private Sprite mMario;
	private List<Bullet> mBulletList;
	private long mTime;
	private double second;
	private List<Enemy> mListEnemy;
	private static final long BAT = 300;
	private GenericPool<Bullet> mBulletPool = new GenericPool<Bullet>() {

		@Override
		protected Bullet onAllocatePoolItem() {
			Bullet bul = new Bullet();
			GameScene.this.attachChild(bul);
			return bul;
		}
	};

	public GameScene() {
		super(MainActivity.CAMERA);
		Sprite sp = new Sprite(0, 0, MainActivity.CAMERA_WIDTH,
				MainActivity.CAMERA_HEIGHT, LoadTexture.BACK,
				MainActivity.BUFFER);
		SpriteBackground spb = new SpriteBackground(sp);
		setBackground(spb);
		setOnSceneTouchListener(this);
		mMario = new Sprite(50, 50, LoadTexture.HERO, MainActivity.BUFFER);
		// attachChild(mMario);
		mBulletList = new ArrayList<Bullet>();
		mListEnemy = new ArrayList<Enemy>();
	}

	public void onUpdate() {
		second += 0.04;

		if (second > 1) {
			second = 0;
			if (mListEnemy.size() < 6) {
				Enemy e = new Enemy();
				mListEnemy.add(e);
				attachChild(e);
			}
		}

		Iterator<Bullet> bul = mBulletList.iterator();
		while (bul.hasNext()) {
			Bullet b = bul.next();
			b.update();
			if (b.getX() > 800 || b.getY() > 500 || b.getY() < -20) {
				removeBullet(b, bul);
				break;
			}
			Iterator<Enemy> enem = mListEnemy.iterator();
			while (enem.hasNext()) {
				Enemy e = enem.next();
				if (b.collidesWith(e)) {
					removeItem(e, enem);
					removeBullet(b, bul);
					break;
				}
			}
		}
		Iterator<Enemy> enem = mListEnemy.iterator();
		while (enem.hasNext()) {
			Enemy e = enem.next();
			e.move();
			if (e.getX() < 0) {
				removeItem(e, enem);
				break;
			}
		}
	}

	private void removeItem(final Sprite sp, Iterator it) {
		MainActivity.CONTEXT.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				detachChild(sp);

			}
		});
		it.remove();
	}

	private void removeBullet(final Bullet sp, Iterator it) {
		MainActivity.CONTEXT.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				//detachChild(sp);
				sp.setVisible(false);
				
				mBulletPool.recyclePoolItem(sp);
			}
		});
		it.remove();
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
		float pX = pSceneTouchEvent.getX();
		float pY = pSceneTouchEvent.getY();
		double valX = mMario.getX() - pX;
		double valY = mMario.getY() - pY;
		double angle = Math.PI + Math.atan2(valY, valX);
		if (mTime < System.currentTimeMillis() - BAT) {
			mTime = System.currentTimeMillis();
			Bullet bullet = mBulletPool.obtainPoolItem();
			bullet.setVisible(true);
			// Bullet bullet = new Bullet();
			bullet.angle = angle;
			bullet.thisX=0;
			bullet.thisY = MainActivity.CAMERA_HEIGHT/2;
			//attachChild(bullet);
			mBulletList.add(bullet);
			Log.d("my_log", "bullets = "+mBulletList.size());
		}
		return false;

	}
}
