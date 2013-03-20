package com.activity;

import com.example.learn.R;
import com.opengl.BackRend;
import com.opengl.SimpleTriangle;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MyOpenGL extends BaseActivity implements OnTouchListener, OnSeekBarChangeListener {

	private GLSurfaceView mTestHarness;
	private SimpleTriangle mRender;
	private Button mUp;
	private Button mDown;
	private TextView nSize;
private float mZ=0;
private float mPosx;
private float mPosy;
private SeekBar mSeekBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_gl);
		LinearLayout ll = (LinearLayout) findViewById(R.id.conteiner);
		mRender = new SimpleTriangle(this);
		mTestHarness = new GLSurfaceView(this);
		mTestHarness.setEGLConfigChooser(false);
		mTestHarness.setRenderer(mRender);	
		mTestHarness.setOnTouchListener(this);
		mUp = (Button) findViewById(R.id.z_up);
		mDown = (Button) findViewById(R.id.z_down);
		nSize = (TextView) findViewById(R.id.size);
		mUp.setOnClickListener(this);
		mDown.setOnClickListener(this);
		mSeekBar = (SeekBar) findViewById(R.id.seekBar1);
		mSeekBar.setOnSeekBarChangeListener(this);

		ll.addView(mTestHarness);
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
			mZ+=0.5;
			nSize.setText(""+mZ);
			mRender.setZPos(mZ);
			break;
		case R.id.z_down:
			mZ-=0.5;
			nSize.setText(""+mZ);
			mRender.setZPos( mZ);
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		float x = event.getX() / mTestHarness.getHeight();
		float y = event.getY() / mTestHarness.getWidth();
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			mRender.redraw(x, y);
			break;
		case MotionEvent.ACTION_UP:
			mPosx = x;
			mPosy = y;
			break;	
		}	
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		mRender.reRot(progress);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

}
