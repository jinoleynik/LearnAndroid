package com.opengl;

import javax.microedition.khronos.opengles.GL10;

public class Somefig extends AbsractOpenGLFigure {

    private float vertices[] = { -1.0f, -1.0f, 0.0f, // Bottom Left
            1.0f, -1.0f, 0.0f, // Bottom Right
            -1.0f, 1.0f, 0.0f, // Top Left
            1.0f, 1.0f, 0.0f // Top Right
    };
    private float colors[] = { 1, 1, 1, 1 };

    public Somefig(float size) {

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] *= size;
        }
    }

    public void setColore(float red, float green, float blue, float alpha) {
        colors[0] = red;
        colors[1] = green;
        colors[2] = blue;
        colors[3] = alpha;
    }

    public Somefig(float x, float y, float z) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] *= x;
        }
    }

    public void setSize(float size) {
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] *= size;
        }
    }

    public void fullRotateX() {

    }

    public void rotator(GL10 gl, float speed, float x, float y,
            float z) {
        gl.glLoadIdentity();
        // setSize(size);
        gl.glTranslatef(x, y, z);
        gl.glRotatef(speed, 0.0f, 0.5f, 2.0f);
        draw(gl);
    }

    @Override
    public float[] setVerts() {
        return vertices;
    }

    @Override
    public float[] setColor() {
        return colors;
    }

}
