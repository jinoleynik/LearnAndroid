package com.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * 
 * @author Moritz 'Moss' Wundke (b.thax.dcg@gmail.com)
 * 
 */
public class PageCurlView extends View {

    private static final double TIMESTEP = 0.05;

    /** Fixed update time used to create a smooth curl animation */
    private static final int UPDATERATE = 10;

    /** Handler used to auto flip time based */
    private FlipAnimationHandler mAnimationHandler;

    /** Page curl edge */
    private Paint mCurlEdgePaint;

    /** Our points used to define the current clipping paths in our draw call */
    private float mAx, mAy, mBx ,
            mBy, mCx, mCy , mDx,
            mDy ;

    /** Defines the flip direction that is currently considered */
    private boolean bFlipRight;

    /** LAGACY The current foreground */
    private Bitmap mForeground;

    /** LAGACY The current background */
    private Bitmap mBackground;

    /** LAGACY List of pages, this is just temporal */
    private ArrayList<Bitmap> mPages;

    /** LAGACY Current selected page */
    private int mIndex = 0;

    private double mCurlSpeed;

    private int mHeight;

    private int mWidth;

    /**
     * Inner class used to make a fixed timed animation of the curl effect.
     */
    class FlipAnimationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            PageCurlView.this.FlipAnimationStep();
        }

        public void sleep(long millis) {
            this.removeMessages(0);
            sendMessageDelayed(obtainMessage(0), millis);
        }
    }

    /**
     * Base
     * 
     * @param context
     * @param width 
     * @param height 
     */
    public PageCurlView(Context context, int height, int width) {
        super(context);
        mHeight = height;
        mWidth = width;
        ResetClipEdge();
        mAnimationHandler = new FlipAnimationHandler();
        mCurlEdgePaint = new Paint();
        mCurlEdgePaint.setColor(Color.TRANSPARENT);
        mCurlEdgePaint.setAntiAlias(true);
        mCurlEdgePaint.setStyle(Paint.Style.FILL);
        mCurlEdgePaint.setShadowLayer(10, -5, 5, 0x99000000);

    }

    public void setBits(Bitmap bit1, Bitmap bit2) {

        mPages = new ArrayList<Bitmap>();
        mPages.add(bit1);
        mPages.add(bit2);
        mForeground = mPages.get(0);
        mBackground = mPages.get(1);

    }

    public void ResetClipEdge() {
        mCurlSpeed = 0;
        mAx = 0;
        mAy = mHeight;
        mBx = mWidth;
        mBy = mHeight;
        mCx = 0;
        mCy = mHeight;
        mDx = mWidth;
        mDy = mHeight;
    }
  
    /**
     * Execute a step of the flip animation
     */
    public void FlipAnimationStep() {
        float curlspeed = 0;
        mCurlSpeed += TIMESTEP;
        if (!bFlipRight) {
            curlspeed = (float) mCurlSpeed;
        } else {
            curlspeed = (float) (1 - mCurlSpeed);
        }
        doSimpleCurl(curlspeed);
        if (mCurlSpeed <= 1 && mCurlSpeed > 0) {
            mAnimationHandler.sleep(UPDATERATE);
        } else {
            if (bFlipRight) {
                nextView();
            }
            ResetClipEdge();
        }
        this.invalidate();
    }

    private void Log(Object obj, String capt) {
        Log.d("my_log", capt + " : " + String.valueOf(obj));
    }

    private void Log(Object obj) {
        Log.d("my_log", String.valueOf(obj));
    }

    private void doSimpleCurl(float curlspeed) {
       
        float hh = curlspeed * mHeight;
        if (mHeight - hh >= 0) {
            mAx = 0;
            mCx = 0;
            if (hh < mHeight * 0.8) {
                mAy = (float) (hh + (mHeight - mHeight * 0.8)) * curlspeed * 4;
                mCy = mAy - hh;

            } else {
                mAy = mHeight;

            }
            mBx = mWidth;
            if (curlspeed < 0.2) {
                mBy = hh - 100 * (1 - curlspeed);
            } else {
                mBy = hh;
            }
            if (curlspeed < 0.5) {
                mDx = (float) ((mWidth + 100) * curlspeed - 100 * (1 - curlspeed));
                mDy = (float) (hh - (mHeight - hh) * Math.cos(30) - 200 * (1 - curlspeed));
            } else {
                mDx = (float) ((mWidth + 100) * curlspeed - 50);
                mDy = (float) (hh - (mHeight - hh) * Math.cos(30));
            }
        }
    }

    /**
     * Swap to next view
     */
    private void nextView() {
        int foreIndex = mIndex + 1;
        if (foreIndex >= mPages.size()) {
            foreIndex = 0;
        }
        int backIndex = foreIndex + 1;
        if (backIndex >= mPages.size()) {
            backIndex = 0;
        }
        mIndex = foreIndex;
        setViews(foreIndex, backIndex);
    }

    /**
     * Swap to previous view
     */
    private void previousView() {
        int backIndex = mIndex;
        int foreIndex = backIndex - 1;
        if (foreIndex < 0) {
            foreIndex = mPages.size() - 1;
        }
        mIndex = foreIndex;
        setViews(foreIndex, backIndex);
    }

    /**
     * Set current fore and background
     * 
     * @param foreground
     *            - Foreground view index
     * @param background
     *            - Background view index
     */
    private void setViews(int foreground, int background) {
        mForeground = mPages.get(foreground);
        mBackground = mPages.get(background);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        Rect rect = new Rect();
        rect.left = 0;
        rect.top = 0;
        rect.bottom = mHeight; 
        rect.right = mWidth;
        Paint paint = new Paint();
        drawForeground(canvas, rect, paint);
        drawBackground(canvas, rect, paint);
        drawCurlEdge(canvas);
    }

    /**
     * Draw the foreground
     * 
     * @param canvas
     * @param rect
     * @param paint
     */
    private void drawForeground(Canvas canvas, Rect rect, Paint paint) {
        canvas.drawBitmap(mForeground, null, rect, paint);
    }

    /**
     * Create a Path used as a mask to draw the background page
     * 
     * @return
     */
    private Path createBackgroundPath() {
        Path path = new Path();
        path.moveTo(mAx, mAy);
//        path.lineTo(mBx, mBy);
        path.quadTo(mDx / 2, mAy + 50, mBx, mBy);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.lineTo(mAx, mAy);
        return path;
    }

    /**
     * Draw the background image.
     * 
     * @param canvas
     * @param rect
     * @param paint
     */
    private void drawBackground(Canvas canvas, Rect rect, Paint paint) {
        Path mask = createBackgroundPath();
        canvas.save();
        canvas.clipPath(mask);
        canvas.drawBitmap(mBackground, null, rect, paint);
        canvas.restore();
    }

    /**
     * Creates a path used to draw the curl edge in.
     * 
     * @return
     */
    private Path createCurlEdgePath() {
        Path path = new Path();
        path.moveTo(mAx, mAy);
        path.lineTo(mCx, mCy);
        path.lineTo(mDx, mDy);
//        path.lineTo(mBx, mBy);
//        path.lineTo(mAx, mAy);
        path.quadTo(mBx + 15, mBy + 15, mBx, mBy);
        path.quadTo(mDx / 2, mAy + 50, mAx, mAy);
        return path;
    }

    /**
     * Draw the curl page edge
     * 
     * @param canvas
     */
    private void drawCurlEdge(Canvas canvas) {
        Path path = createCurlEdgePath();
        canvas.drawPath(path, mCurlEdgePaint);
    }

    public void startAnimationPage(boolean side) {
        if (side) {
            bFlipRight = true;
            this.invalidate();
        } else {
            previousView();
            bFlipRight = false;
            this.invalidate();
        }
        FlipAnimationStep();
    }
}
