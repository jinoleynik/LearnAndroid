package com.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Figure implements OnTouchListener {
	private final float TOUCH_SCALE = 0.2f;	
	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the color values */
	private FloatBuffer colorBuffer;
	/** The initial vertex definition */
	private float vertices[] = { 
					 	0.0f,  0.0f,  0.0f,		
						0.5f, 0.0f, 0.0f,		
						 1.0f, 0.5f, 0.0f,		
						 1.5f,  1.0f, 0.0f,
						 1.5f,  1.0f, 3.0f,
						 1.5f,  3.0f, 4.0f,
						 3.0f,  4.0f, 4.0f	,
						 4.0f,  5.0f, 0.0f	
											};
	private float colors[] = {
    		1.0f, 1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f, 1.0f, 
    		1.0f, 1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f, 1.0f,
			    					};
	private float lookupdown;
	private float heading;
	private float yrot;
	private float oldX;
	private float oldY;
	public Figure() {
		//
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		//
		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}
	public void draw(GL10 gl) {	
		//Set the face rotation
		gl.glFrontFace(GL10.GL_CW);
		float sceneroty = 360.0f - yrot;	
		gl.glRotatef(sceneroty, 0, 1.0f, 0);
		//Point to our buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		
		//Enable the vertex and color state
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		//Draw the vertices as triangles
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices.length / 3);
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
	boolean handled = false;
		
		//
		float x = event.getX();
        float y = event.getY();
        
        //If a touch is moved on the screen
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
        	//Calculate the change
        	float dx = x - oldX;
	        float dy = y - oldY;
        	        		
	        //Up and down looking through touch
    	    lookupdown += dy * TOUCH_SCALE;
    	    //Look left and right through moving on screen
    	    heading += dx * TOUCH_SCALE;
    	    yrot = heading;

    	    //We handled the event
            handled = true;
        }
        
        //Remember the values
        oldX = x;
        oldY = y;
        
        //
		return handled;
	
	}
}
