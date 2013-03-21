package com.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.learn.R;
import com.view.PathDrawerView;

public class PathActivity extends BaseActivity  {	

	private View mPd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_path);
		LinearLayout ll = (LinearLayout) findViewById(R.id.main);
		mPd = new PathDrawerView(this);
		ll.addView(mPd);
		mPd.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		mPd.invalidate();
		switch (v.getId()) {		


		}

	}
	
}
