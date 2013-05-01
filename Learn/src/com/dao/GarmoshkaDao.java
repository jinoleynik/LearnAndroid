package com.dao;

import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class GarmoshkaDao {
    private Matrix mMatrix = new Matrix();;
    private Camera mCamera = new Camera();
    private Bitmap mBitmap;
    private float mCenter;
    private int mNumber;

    public GarmoshkaDao(Bitmap btm, int number, int widht) {
        mBitmap = Bitmap.createBitmap(btm, widht * number, 0, widht,
                btm.getHeight());
        mCenter = widht * number + widht / 2;
        mNumber = number;
    }

    public void drawBit(Canvas canvas, float deg) {
        mMatrix.reset();
        mCamera.save();       
        if (mNumber % 2 == 1) {
            mCamera.rotateY(90 * deg);
        } else {
            mCamera.rotateY(360 - 90 * deg);
        }
        mCamera.getMatrix(mMatrix);
        mCamera.restore();
        mMatrix.preScale(1, 0.8f);
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, 0);
        mMatrix.postTranslate(mCenter*(1 - deg), mBitmap.getHeight()*0.1f);
        canvas.drawBitmap(mBitmap, mMatrix, null);
    }
}
