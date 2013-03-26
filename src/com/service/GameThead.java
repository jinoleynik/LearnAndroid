package com.service;

import android.graphics.Canvas;

import com.view.GameView;

public class GameThead extends Thread {
    private GameView mGameView;
    private boolean mRunning;

    public GameThead(GameView view) {
        mGameView = view;
    }

    public void setRunning(boolean run) {
        mRunning = run;
    }

    @Override
    public void run() {
        super.run();
        while (mRunning) {
            try {
                sleep(40);
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
