package com.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;

/**
 * Вью позволяющия изменять размеры изображения и двигать увеличеное
 * изображение.
 * 
 * @author Eugen Oleynik jinoleynik@gmail.com
 * 
 */
public class ZoomView extends View {
    private Bitmap mImage = null;
    private final GestureDetector mGestureDetector;
    private final ScaleGestureDetector mScaleGestureDetector;
    private final Paint mPaint = new Paint();
    private int mImageHeight, mImageWidth;
    private float mScaleFactor;
    private ZoomViewListener mListener;
    private float mCanvasHeight;
    private float mCanvasWidth;
    private int mViewHeight;
    private int mViewWidth;
    private int mDeltaX;
    private int mDeltaY;

    public interface ZoomViewListener {
        public void onBackTouch(MotionEvent event);
    }

    private int getScaledWidth() {
        return (int) (getWidth() * mScaleFactor);
    }

    private int getScaledHeight() {
        return (int) (((double) getWidth() / mImageWidth) * mImageHeight * mScaleFactor);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(context,
                new MyScaleGestureListener());
        setHorizontalScrollBarEnabled(true);
        setVerticalScrollBarEnabled(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(false);
        mScaleFactor = 1;
    }

    public void setImage(Bitmap bmp) {
        mImage = bmp;
        mImageHeight = mImage.getHeight();
        mImageWidth = mImage.getWidth();
    }

    @SuppressLint("DrawAllocation")
    @Override
    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);// зумируем канвас
        canvas.drawBitmap(mImage, mDeltaX, mDeltaY, mPaint);
        canvas.restore();
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return getScaledWidth();
    }

    public void resetView() {
        mScaleFactor = 1;
        scrollTo(0, 0);
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected int computeVerticalScrollRange() {
        return getScaledHeight();
    }

    public boolean zoomed() {
        return mScaleFactor > 1 ? true : false;
    }

    public void setBackListener(View rvs) {
        mListener = (ZoomViewListener) rvs;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
        if (mScaleFactor > 1) {
            mGestureDetector.onTouchEvent(event);
        }
        if (mListener != null) {
            if (mScaleFactor == 1) {
                mListener.onBackTouch(event);
            }
        }
        return true;
    }

    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                float distanceX, float distanceY) {
            if ((getScrollX() + distanceX < mCanvasWidth - mViewWidth)
                    && (getScrollX() + distanceX > 0)) {
                scrollBy((int) distanceX, 0);
            }
            // не даем канвасу показать края по вертикали
            if ((getScrollY() + distanceY < mCanvasHeight - mViewHeight)
                    && (getScrollY() + distanceY > 0)) {
                scrollBy(0, (int) distanceY);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                float velocityY) {
            return true;
        }
    }

    private class MyScaleGestureListener implements OnScaleGestureListener {
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();// получаем значение
                                                          // зума относительно
                                                          // предыдущего
                                                          // состояния
            // получаем координаты фокальной точки - точки между пальцами
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();
            // следим чтобы канвас не уменьшили меньше исходного размера и не
            // допускаем увеличения больше чем в 2 раза
            if (mScaleFactor * scaleFactor > 1
                    && mScaleFactor * scaleFactor < 3) {
                mScaleFactor *= detector.getScaleFactor();
                mCanvasHeight = mViewHeight * mScaleFactor;// изменяем хранимое
                                                           // в памяти значение
                                                           // размера канваса
                mCanvasWidth = mViewWidth * mScaleFactor;// изменяем хранимое в
                                                         // памяти значение
                                                         // размера канваса
                // используется при расчетах
                // по умолчанию после зума канвас отскролит в левый верхний
                // угол.
                // Скролим канвас так, чтобы на экране оставалась
                // область канваса, над которой был жест зума
                // Для получения данной формулы достаточно школьных знаний
                // математики (декартовы координаты).
                int scrollX = (int) ((getScrollX() + focusX) * scaleFactor - focusX);
                scrollX = Math.min(Math.max(scrollX, 0), (int) mCanvasWidth
                        - mViewWidth);
                int scrollY = (int) ((getScrollY() + focusY) * scaleFactor - focusY);
                scrollY = Math.min(Math.max(scrollY, 0), (int) mCanvasHeight
                        - mViewHeight);
                scrollTo(scrollX, scrollY);
            }
            // вызываем перерисовку принудительно
            invalidate();
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            prepareView(right - left, bottom - top);
        }
    }

    private void prepareView(int viewWidth, int viewHeight) {
        mViewHeight = viewHeight;
        mViewWidth = viewWidth;
        mScaleFactor = 1f;// значение зума по умолчанию
        mCanvasHeight = (int) (mViewHeight * mScaleFactor);// определяем размер
                                                           // канваса
        mCanvasWidth = (int) (mViewWidth * mScaleFactor);// определяем размер
                                                         // канваса
        float koefX = (float) mImageWidth / mViewWidth;
        float koefY = (float) mImageHeight / mViewHeight;
        if (koefX > koefY) {
            mImage = getResizedBitmap(mImage, (int) (mImageHeight / koefX),
                    (int) (mImageWidth / koefX));
        } else {
            mImage = getResizedBitmap(mImage, (int) (mImageHeight / koefY),
                    (int) (mImageWidth / koefY));
        }
        mDeltaX = (mViewWidth - mImageWidth) / 2;
        mDeltaY = (mViewHeight - mImageHeight) / 2;
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        mImageHeight = newHeight;
        mImageWidth = newWidth;
        return resizedBitmap;
    }
}