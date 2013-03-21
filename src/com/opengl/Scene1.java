package com.opengl;

import javax.microedition.khronos.opengles.GL10;

import android.util.Log;

public class Scene1 extends AbsRender {
    public interface Ouch {
        void oOuch();

        void Heal();
    }

    private Ouch mOuch;
    Somefig some3;
    Somefig back;
    private float rquad;
    private float mRRate;
    private float mX = 0;
    private float mY = 0;

    private Somefig main;

    public void setOuchListener(Ouch listener) {
        mOuch = listener;
    }

    public Scene1() {
        some3 = new Somefig(0.3f);
        main = new Somefig(0.6f);
        back = new Somefig(5);
    }

    public void setRotateRate(float rate) {
        mRRate = rate;
    }

    public void positionForMain(float f, float g) {
        mX = -3 + 6 * f;
        mY = -(-3 + 6 * g);
        if (mX > 0.8f && mX < 1.2f && mY > 0.8f && mY < 1.2f) {

            mOuch.Heal();
        }
        if (-mX > 0.8f && -mX < 1.2f && -mY > 0.8f && -mY < 1.2f) {

            mOuch.Heal();
        }
        if (mX > 0.8f && mX < 1.2f && -mY > 0.8f && -mY < 1.2f) {
            mOuch.oOuch();

        }
        if (-mX > 0.8f && -mX < 1.2f && mY > 0.8f && mY < 1.2f) {
            mOuch.oOuch();

        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear Screen And Depth Buffer
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        main.setColore(0.5f, 0.1f, 1, 1);
        main.rotator(gl, rquad, mX, mY, -6);

        back.setColore(0.1f, 0.1f, 1, 1);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0, -10.0f);
        back.draw(gl);
        some3.setColore(0, 1, 1, 0.5f);
        some3.rotator(gl, -rquad, -1, 1, -6);
        some3.rotator(gl, -rquad, 1, -1, -6);
        some3.setColore(1, 1, 0, 0.5f);
        some3.rotator(gl, rquad, -1, -1, -6);
        some3.rotator(gl, rquad, 1, 1, -6);

        rquad -= mRRate;
    }

}