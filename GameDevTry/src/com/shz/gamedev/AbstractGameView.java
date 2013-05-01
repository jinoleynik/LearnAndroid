package com.shz.gamedev;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class AbstractGameView extends SurfaceView implements
		SurfaceHolder.Callback {

	private GameThead mGameThead;

	public AbstractGameView(Context context) {
		super(context);
		getHolder().addCallback(this);
	}

	public abstract void reDraw(Canvas canvas);
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}
	
	public abstract void createView();
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mGameThead = new GameThead(this);
		mGameThead.setFPS(50);
		mGameThead.setRunning(true);
		mGameThead.start();
		createView();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mGameThead.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				retry = false;
				mGameThead.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
