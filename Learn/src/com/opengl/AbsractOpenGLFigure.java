package com.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public abstract class AbsractOpenGLFigure {
    private FloatBuffer vertexBuffer;
 
    
    public AbsractOpenGLFigure(){  
    }
    public  void draw(GL10 gl) {       
        gl.glFrontFace(GL10.GL_CW);        
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, createBuffer(setVerts()));           
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);            
        gl.glColor4f(setColor()[0], setColor()[1], setColor()[2], setColor()[3]);     
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, setVerts().length / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
    public abstract float[] setColor();
    public abstract  float[]  setVerts();
    
    private FloatBuffer createBuffer(float[] verts){
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(verts.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuf.asFloatBuffer();
        vertexBuffer.put(verts);
        vertexBuffer.position(0);
        return vertexBuffer;
    }
}
