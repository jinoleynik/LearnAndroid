package com.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.learn.R;
import com.service.GameThead;

@SuppressLint("WrongCall")
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private GameThead mGameThead;
    private int mPosY = 1;
    private Paint mPaint;
    private static final int SP = 5;
    private Bitmap mBitmap;
    private int mPosX;
    private Sprite mSpite;
    private int mCurX;
    private int mCurY;
    private int mImW;
    private int mImH;
private int mFrame;
private Bitmap mBack;
    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
     
    }

    public void reDraw(Canvas canvas) {
        mFrame++;
        if(mFrame>2) mFrame=0;
        int srcY = 0;
   
        canvas.drawBitmap(mBack, 0, 0, null);
        if (mCurX != mPosX) {
             mCurX = mCurX > mPosX ? mCurX - SP : mCurX + SP;
            srcY =  mCurX > mPosX? 1: 2;
            
        }
        if (mCurY != mPosY) {
            mCurY = mCurY > mPosY ? mCurY - SP : mCurY + SP;
        }
   
       if(Math.abs(mCurY-mPosY)<SP) mCurY = mPosY;
       if(Math.abs(mPosX-mCurX)<SP) mCurX = mPosX;
       int srcX = mImW*mFrame;
       Rect src = new Rect(srcX, srcY*mImH, srcX + mImW, srcY*mImH + mImH);
       Rect dst = new Rect(mCurX, mCurY, mCurX + mImW, mCurY + mImH);
       canvas.drawBitmap(mBitmap, src, dst, null);
 //       canvas.drawBitmap(mBitmap, mCurX, mCurY, null);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mPosX = (int) event.getX();
        mPosY = (int) event.getY();
        // if(event.getX()>getWidth()/2) mPosX=mPosX+5;
        // if(event.getX()<getWidth()/2) mPosX=mPosX-5;
        // if(mPosX>getWidth()) mPosX=getWidth();
        // if(mPosX<0) mPosX=0;

        return true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.hero_1);        
        mBack =Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.backgame), getWidth(), getHeight(), true);    
        mImW = mBitmap.getWidth()/3;
        mImH = mBitmap.getHeight()/4;
        mSpite = new Sprite(this, mBitmap);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);
        mPaint.setAlpha(200);
        mGameThead = new GameThead(this);
        mGameThead.setRunning(true);
        mGameThead.start();

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
