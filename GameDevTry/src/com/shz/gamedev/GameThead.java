package com.shz.gamedev;

import android.graphics.Canvas;

public class GameThead extends Thread {
    private AbstractGameView mGameView;
    private boolean mRunning;
	private int mFps=40;

    public GameThead(AbstractGameView view) {
        mGameView = view;
    }
   

	public void setRunning(boolean run) {
        mRunning = run;
    }

	public void setFPS(int fps){
		mFps= 1000/fps;
	}
    @Override
    public void run() {
        super.run();
        while (mRunning) {
            try {
                sleep(mFps);
                Canvas canvas = null;
                try {
                    canvas = mGameView.getHolder().lockCanvas();
                    synchronized (mGameView.getHolder()) {
                        mGameView.reDraw(canvas);
                    }
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        mGameView.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
            } catch (InterruptedException e1) {

                e1.printStackTrace();
            }

        }

    }
}
