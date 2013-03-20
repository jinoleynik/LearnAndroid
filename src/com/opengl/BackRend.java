package com.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;

public class BackRend extends AbstractGL {

	private FloatBuffer mFVertexBuffer;
	private float[] mCoords = { -1f, -1f, -1f,
			-1f, 1f, -1f, 
			1f, 1f, -1f,
			1f, -1f, -1f
			 };
	
	public BackRend(Context context){
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(mCoords.length * 4);
		byteBuf.order(ByteOrder.nativeOrder());
		mFVertexBuffer = byteBuf.asFloatBuffer();
		mFVertexBuffer.put(mCoords);
		mFVertexBuffer.position(0);
	}
	@Override
	protected void draw(GL10 gl) {
		gl.glFrontFace(GL10.GL_CW);
		
		gl.glColor4f(1.0f, 1f, 0, 1f);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, mCoords.length / 3);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	}

}
