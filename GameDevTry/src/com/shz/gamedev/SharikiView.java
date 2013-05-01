package com.shz.gamedev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;

import com.shz.gamedev.objects.Sharik;

public class SharikiView extends AbstractGameView {

    private List<Sharik> mSharikList = new ArrayList<Sharik>();

    public SharikiView(Context context) {
        super(context);

    }

    @Override
    public void reDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        Iterator<Sharik> sharik = mSharikList.iterator();
        while (sharik.hasNext()) {
            Sharik shar = sharik.next();
            if (shar.X < getWidth() || shar.Y < getHeight() || shar.Y > 0
                    || shar.X > 0) {
                shar.drawThis(canvas);
            } else {
                sharik.remove();
            }
        }
    }

    @Override
    public void createView() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float w = getWidth() / 2;
        float h = getHeight() / 2;
        double angle;
        angle = Math.toDegrees(Math.atan2(y, x));
        Log.d("my_log", "angle start = "+ angle);
        if (x < w && y < h) {
            angle += 270;
        }
        if (x > w && y < h) {
            angle += 180;
        }
        if (x > w && y > h) {
            angle += 90;
        }
        Log.d("my_log", "angle = "+ angle);
        mSharikList.add(new Sharik(angle, Color.RED, 10, getWidth(),
                getHeight()));
        return super.onTouchEvent(event);
    }

}
