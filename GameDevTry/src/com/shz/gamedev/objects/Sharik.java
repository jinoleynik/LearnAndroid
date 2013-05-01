package com.shz.gamedev.objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Sharik {
    private Paint mPaint;
    private int mSize;
    public float Y;
    public float X;
    public int speed;
    public double angle;
    public float time;
    private int mHeight;
    private int mWidth;

    public Sharik(double angle,int color, int size, int w, int h) {
        mHeight = h;
        mWidth = w;
        mPaint = new Paint();
        speed  = (int) (10+Math.random()*50);
//        angle = 10+Math.random()*30;
        this.angle= 0;
        Log.d("my_log", "angle = "+angle);
        Log.d("my_log", "speed = "+speed);
        mPaint.setColor(color);
        mSize = size;
        time=0;
    }

    private void update() {
        time+=0.5;
        X+= (float) (speed * Math.cos(angle));
        Y+= (float) (speed * Math.sin(angle));
//        X = (float) (speed * time * Math.cos(angle));
//        Y = (float) (speed * time * Math.sin(angle) - time * time / 4.9);
    }

    public void drawThis(Canvas canvas) {
        update();
        canvas.drawCircle(X+mWidth/2, Y+mHeight/2, mSize, mPaint);
    }
}
