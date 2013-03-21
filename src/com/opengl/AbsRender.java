package com.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;

public abstract class AbsRender implements Renderer {


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(height == 0) {                       //Prevent A Divide By Zero By
            height = 1;                         //Making Height Equal One
        }

        gl.glViewport(0, 0, width, height);     //Reset The Current Viewport
        gl.glMatrixMode(GL10.GL_PROJECTION);    //Select The Projection Matrix
        gl.glLoadIdentity();                    //Reset The Projection Matrix

        //Calculate The Aspect Ratio Of The Window
        GLU.gluPerspective(gl, 45.0f, (float)width / (float)height, 0.1f, 100.0f);

        gl.glMatrixMode(GL10.GL_MODELVIEW);     //Select The Modelview Matrix
        gl.glLoadIdentity();                    //Reset The Modelview Matrix
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //Settings
        gl.glEnable(GL10.GL_TEXTURE_2D);                    //Enable Texture Mapping
        gl.glShadeModel(GL10.GL_SMOOTH);                    //Enable Smooth Shading
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);            //Black Background
        gl.glClearDepthf(1.0f);                             //Depth Buffer Setup
    
        //Really Nice Perspective Calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        
        gl.glEnable(GL10.GL_BLEND);                         //Enable blending
        gl.glDisable(GL10.GL_DEPTH_TEST);                   //Disable depth test
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);     //Set The Blending Function For Translucency        
                

        
    }

}
