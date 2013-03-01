package com.activity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.learn.R;
import com.system.MyConst;
import com.view.PageCurlView;

public class RVSActivity extends BaseActivity {
    private FrameLayout lnrmain;
    private PageCurlView pcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();  
        int height = display.getHeight();
        lnrmain = (FrameLayout) findViewById(R.id.main);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        lnrmain.addView(inflater.inflate(R.layout.one, null), MyConst.mLLP);
        lnrmain.addView(inflater.inflate(R.layout.two, null), MyConst.mLLP);
        pcv = new PageCurlView(RVSActivity.this,height, width);
        lnrmain.postDelayed(new Runnable() {

            @Override
            public void run() {
                pcv.setBits(getBitmapFromView(findViewById(R.id.oneid)),
                        getBitmapFromView(findViewById(R.id.twoid)));
                lnrmain.addView(pcv, MyConst.mLLP);
            }
        }, 0);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "right");
        menu.add(0, 2, 0, "left");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
            pcv.startAnimationPage(true);
            break;
        case 2:
            pcv.startAnimationPage(false);
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        
    }
}
