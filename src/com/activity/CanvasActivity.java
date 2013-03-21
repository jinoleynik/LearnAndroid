package com.activity;

import com.example.learn.R;
import com.view.GramoshkaView;

import android.os.Bundle;
import android.view.View;

public class CanvasActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		GramoshkaView cv = new GramoshkaView(this, R.drawable.ironmanua);
		cv.setFrames(5);
		setContentView(cv);
	}
	
	@Override
	public void onClick(View v) {

	}

}
