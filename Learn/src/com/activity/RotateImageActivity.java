package com.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import com.example.learn.R;
import com.view.RotateImageView;

public class RotateImageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bitmap btm = BitmapFactory.decodeResource(getResources(), R.drawable.ironmanua);
        RotateImageView  riv = new RotateImageView(this, btm);
        setContentView(riv);
    }
    
    @Override
    public void onClick(View v) {
      
    }

}
