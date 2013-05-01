package com.shz.gamedev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;

import com.shz.gamedev.objects.Circle;

public class PathGame extends AbstractGameView {

    private Circle mCircle;
    private int mLenght;
    private int mY;
    private int mPos;
    private int mLastValue = 300;
    private List<Integer> mListint = new ArrayList<Integer>();
    private Paint mPaint;
    private Integer mTargetPos;
    private int mSpeed = 50;
    private float mTime = 0;
    private boolean mJump;
    private int mStartJump;
    private static final float G = 9.8f;
    private int mDist = 20;
    private int mPoints = 20;
    private int mWorldNumber = 0;
 private List<Integer> mList = new ArrayList<Integer>();
    public PathGame(Context context) {
        super(context);
//        for(int i=0; i<20; i++){
//            int rand = (int) (Math.random()*20);
//            mList.add(rand);
//        }
//       Iterator<Integer> iter = mList.iterator();
//       while(iter.hasNext()){
//           int u = iter.next();
//           if(u>10){
//               Log.d("my_log", "u > 10");
//           }else{
//               Log.d("my_log", "u < 10");
//           }
//       }
        for (int i = 0; i <= mPoints * 3; i++) {
            int value = (int) (-5 + Math.random() * 10);
            mLastValue += value;
            mListint.add(200 + mLastValue);
        }

        mPaint = new Paint();
        mPaint.setColor(Color.MAGENTA);
        mPaint.setStrokeWidth(2);
    }

    private float jumpHeight(float t) {
        float up = mSpeed * t;
        float down = G * t * t * 0.5f;
        float h = up - down;
        return h;
    }

    @Override
    public void reDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        mPaint.setColor(Color.MAGENTA);
        Path path = new Path();
        path.moveTo(0, getHeight());
        for (int i = 0; i < mPoints - 1; i++) {
            path.lineTo(i * mDist, mListint.get(i + mWorldNumber * mPoints));
            path.lineTo((i + 1) * mDist,
                    mListint.get(i + mWorldNumber * mPoints + 1));
        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        canvas.drawPath(path, mPaint);
        if (mJump) {
            mTime += 0.5;
        }

        // Log.d("my_log", "jumpHeight = " + jumpHeight(mTime));
        mTargetPos = (int) (mListint.get(mJump ? mStartJump : mPos) - jumpHeight(mTime));
        if (mTargetPos > mListint.get(mPos)) {
            mJump = false;
            mTime = 0;
        }
        if (mPos > mPoints)
            mPos = mPoints;
        if (mPos < 0)
            mPos = 0;
        int posx = mPos * mDist;
        if (posx > getWidth() - 10)
            posx = getWidth() - 10;
        if (posx < 10)
            posx = 10;
        mCircle.setPosition(posx, mTargetPos);
        mCircle.drawCircle(canvas);
    }

    @Override
    public void createView() {
        mDist = getWidth() / mPoints;
        mCircle = new Circle(Color.WHITE, 10);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("my_log", "mWorldNumber = " + mWorldNumber);
        if (event.getY() > getHeight() / 2) {
            if (event.getX() > getWidth() / 2) {
                mPos++;
            } else {
                mPos--;
            }
        } else {
            mSpeed = (int) (100 - 100 * (event.getY() + 1) / getHeight());
            mJump = true;
            mStartJump = mPos;
        }
        if (mPos > mPoints) {
            if (mWorldNumber < 4) {
                mPos = 0;
                mWorldNumber++;
                if (mWorldNumber > 3)
                    mWorldNumber = 3;
            }
        }
        if (mPos < 0) {
            if (mWorldNumber > 0) {
                mPos = mPoints;
                mWorldNumber--;
                if (mWorldNumber < 0)
                    mWorldNumber = 0;
            }
        }
        return true;
    }
}
