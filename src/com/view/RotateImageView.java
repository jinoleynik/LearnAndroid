package com.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

public class RotateImageView extends View {
    private Bitmap mBitmap;
    private Matrix mMatrix;
    private ScaleGestureDetector mGestureDetector;
    private float mDeg = 1;
    private float mPosX1;
    private float mPosX2;
    private float mPosY2;
    private float mPosY1;
    private float mScale = 0.5f;

    public RotateImageView(Context context, Bitmap btm) {
        super(context);
        mGestureDetector = new ScaleGestureDetector(context, new MyGesture());

        Bitmap b = Bitmap.createScaledBitmap(btm, 500, 500, true);
        btm.recycle();
        mBitmap = b;
        mMatrix = new Matrix();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mMatrix.reset();
        mMatrix.setRotate(mDeg - 1, mBitmap.getWidth() / 2,
                mBitmap.getHeight() / 2);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            if (event.getPointerCount() > 1) {
                mScale = 0.5f;
                invalidate();
            }
            break;
        case MotionEvent.ACTION_MOVE:
            if (event.getPointerCount() > 1) {
                // mGestureDetector.onTouchEvent(event);
                float y1 = event.getY(0);
                float y2 = event.getY(1);
                float x1 = event.getX(0);
                float x2 = event.getX(1);
                float koefY = y1 < y2 ? -y2 / y1 : y1 / y2;
                float koefX = x1 < x2 ? -x2 / x1 : x1 / x2;
                if(koefY>5) koefY =5;
                if(koefX>5) koefX =5;
                if(koefY<-5) koefY =-5;
                if(koefX<-5) koefX =-5;
                mDeg += koefX + koefY;
                if (mDeg > 360)
                    mDeg = 0;
                Log.d("my_log", "deg="+mDeg);
                invalidate();
            }
            break;
        case MotionEvent.ACTION_UP:
            if(mDeg<0){
                mDeg = 360+mDeg;
                
            }
            if (mDeg > 45 && mDeg < 135) {
                mDeg = 91;
            } else if (mDeg > 135 && mDeg < 225) {
                mDeg = 181;
            } else if (mDeg > 225 && mDeg < 315) {
                mDeg = 271;

            } else {
                mDeg = 1;
            }
            mScale = 1f;

            invalidate();
            break;
        }
        return true;
    }

    private class MyGesture implements OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mDeg += detector.getScaleFactor() * 2;
            if (mDeg > 360)
                mDeg = 0;
            mScale = 0.5f;
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }

    }

}
