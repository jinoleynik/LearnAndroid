package com.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.learn.R;
import com.view.ZoomView;

public class ActivityZoom extends BaseActivity {

    private ZoomView mZoomview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        mZoomview = (ZoomView) findViewById(R.id.zoom_view);
        Bitmap back = BitmapFactory.decodeResource(getResources(), R.drawable.ironmanua);
        mZoomview.setImage(back);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }
}
