package com.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class SimpleTriangle extends AbstractGL {

	private FloatBuffer mFVertexBuffer;

//	private float[] mCoords = { -0.5f, -0.5f, 0,
//			0.5f, -0.5f, 0,
//			0.0f, 0.5f, 0,
//			
//			-0.5f, -0.5f, 1, 
//			0.5f, -0.5f, 1,
//			0.0f, 0.5f, 1
//		};
	private float[] mCoords = { -1f, 0f, 0.5f,
			-1.0f, -1.0f, -1.0f,	//lower back left (0)
            1.0f, -1.0f, -1.0f,		//lower back right (1)
            1.0f,  1.0f, -1.0f,		//upper back right (2)
            -1.0f, 1.0f, -1.0f,		//upper back left (3)
            -1.0f, -1.0f,  1.0f,	//lower front left (4)
            1.0f, -1.0f,  1.0f,		//lower front right (5)
            1.0f,  1.0f,  1.0f,		//upper front right (6)
            -1.0f,  1.0f,  1.0f	//upper front left (7)
		};
	private float mPositY;
	private float mPositX;
	private float mRotate;
	private float colors[] = {
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f
          
    								};
	private float mPositZ;

	private FloatBuffer colorBuffer;

	public SimpleTriangle(Context context) {
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(mCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		
		mFVertexBuffer = byteBuf.asFloatBuffer();
		mFVertexBuffer.put(mCoords);
//		mFVertexBuffer.put(mBack);
		mFVertexBuffer.position(0);
		
		byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		colorBuffer = byteBuf.asFloatBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);
	}

	public void setZPos(float pos){
		mPositZ = pos;
	}
	public void redraw(float positX, float positY) {
		mPositY = -1 + positY * 2;
		mPositX = -1 + positX * 2;
	}

	public void reRot(float f) {
		mRotate = f;
	}

	@Override
	protected void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CW);
		gl.glTranslatef(-mPositX, -mPositY, -mPositZ);
		gl.glRotatef(mRotate, 0, 1.0f, 0);
		gl.glColor4f(1.0f, 0, 0, 0.5f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, mCoords.length / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

	}

}
