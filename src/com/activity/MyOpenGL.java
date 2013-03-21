package com.activity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.example.learn.R;
import com.opengl.Scene1;
import com.opengl.Scene1.Ouch;

public class MyOpenGL extends BaseActivity implements OnTouchListener,
        OnSeekBarChangeListener, Ouch {

    private GLSurfaceView mTestHarness;
    private Button mUp;
    private Button mDown;
    private TextView nSize;
    private float mZ = 0;
    private float mPosx;
    private float mPosy;
    private float mCurX;
    private float mCurY;
    private SeekBar mSeekBar;
    private Scene1 mScene1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gl);
        LinearLayout ll = (LinearLayout) findViewById(R.id.conteiner);
        mScene1 = new Scene1();
        mTestHarness = new GLSurfaceView(this);
        mTestHarness.setEGLConfigChooser(false);
        mTestHarness.setRenderer(mScene1);
        mTestHarness.setOnTouchListener(this);

        mUp = (Button) findViewById(R.id.z_up);
        mDown = (Button) findViewById(R.id.z_down);
        nSize = (TextView) findViewById(R.id.size);
        mUp.setOnClickListener(this);
        mDown.setOnClickListener(this);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBar.setOnSeekBarChangeListener(this);
        // Lesson04 less = new Lesson04(this);
        ll.addView(mTestHarness);
        // ll.addView(less);
        mScene1.setOuchListener(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTestHarness.onResume();
    
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTestHarness.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.z_up:
            mZ += 0.5;
            nSize.setText("" + mZ);

            break;
        case R.id.z_down:
            mZ -= 0.5;
            nSize.setText("" + mZ);

            break;
        }
    }

    private boolean touchCur(float x, float y) {
        boolean bo = false;
        if (mCurX + 30 > x && mCurX - 30 < x && mCurY + 30 > y
                && mCurY - 30 < y) {    
            bo =  true;
        } else {
            bo =   false;
        }
        return bo;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX() / mTestHarness.getHeight();
        float y = event.getY() / mTestHarness.getWidth();
        mCurX = mTestHarness.getWidth()/2;
       mCurY = mTestHarness.getHeight()/2;   
//        boolean moveable = false;
//        moveable = touchCur(event.getX(), event.getY());
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          
          

            break;
        case MotionEvent.ACTION_MOVE:
           // if (moveable) {
                mScene1.positionForMain(x, y);
            //}
            break;
        case MotionEvent.ACTION_UP:
            mPosx = x;
            mPosy = y;
            mCurX = x;
            mCurY = y;
            break;
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress,
            boolean fromUser) {
        progress += 1;

        mScene1.setRotateRate(360 / progress + 1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void oOuch(float rate) {  
        mZ -= (1-rate)*5;
    nSize.setText("" + mZ);
        
    }

    @Override
    public void Heal(float rate) {
        mZ += (1-rate)*5;
        nSize.setText("" + mZ);
        
    }

}
