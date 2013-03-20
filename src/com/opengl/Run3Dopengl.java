package com.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * The initial Android Activity, setting and initiating
 * the OpenGL ES Renderer Class @see Lesson05.java
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Run3Dopengl extends Activity {

	/** The OpenGL View */
	private GLSurfaceView glSurface;
	private Lesson05 mRendre;

	/**
	 * Initiate the OpenGL View and set our own
	 * Renderer (@see Lesson05.java)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mRendre = new Lesson05(this);
		//Create an Instance with this Activity
		glSurface = new GLSurfaceView(this);
		//Set our own Renderer
		glSurface.setRenderer(mRendre);
		//Set the GLSurface as View to this Activity	
		setContentView(glSurface);
	}

	/**
	 * Remember to resume the glSurface
	 */
	@Override
	protected void onResume() {
		super.onResume();
		glSurface.onResume();
	}

	/**
	 * Also pause the glSurface
	 */
	@Override
	protected void onPause() {
		super.onPause();
		glSurface.onPause();
	}



}