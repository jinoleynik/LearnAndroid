package com.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.dao.GarmoshkaDao;

@SuppressLint("ViewConstructor")
public class GramoshkaView extends View implements OnTouchListener {

    private float mValue = 0;
    private int mStvorki = 5;
    private int mFWidth;
    private List<GarmoshkaDao> mList;
    private Paint paint;

    public GramoshkaView(Context context, int drawableid) {
        super(context);
        final Bitmap btm = BitmapFactory.decodeResource(getResources(),
                drawableid);
        init(btm);
    }

    public GramoshkaView(Context context, final Bitmap btm) {
        super(context);
        init(btm);
    }

    private void init(final Bitmap btm) {
        setOnTouchListener(this);
        paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(150);
        mList = new ArrayList<GarmoshkaDao>();
        postDelayed(new Runnable() {

            @Override
            public void run() {
                mFWidth = btm.getWidth() / mStvorki;
                for (int i = 0; i < mStvorki; i++) {
                    GarmoshkaDao garmon = new GarmoshkaDao(btm, i, mFWidth);
                    mList.add(garmon);
                }
                btm.recycle();
            }
        }, 1);
    }

    public void setFrames(int frames) {
        mStvorki = frames;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mStvorki; i++) {
            mList.get(i).drawBit(canvas, mValue);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            mValue = (1 - event.getX() / getWidth());
            invalidate();
        }
        return true;
    }

}
