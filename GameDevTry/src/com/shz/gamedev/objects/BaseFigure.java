package com.shz.gamedev.objects;

import android.util.Log;

public class BaseFigure {

	protected int mPosX = 0;
	protected int mPosY = 0;
	protected int mSize;

	public void setPosition(int x, int y) {
		mPosX = x;
		mPosY = y;
	}

	public void setSize(int size) {
		mSize = size;
	}

	public int getSize() {
		return mSize;
	}

	public int getX() {
		return mPosX;
	}

	public int getY() {
		return mPosY;
	}

	public boolean collision(BaseFigure figure) {
		boolean coly = lineColis(mPosY, figure.getY(), figure.getSize());
		boolean colx = lineColis(mPosX, figure.getX(), figure.getSize());	
		return (coly && colx);
	}

	private boolean lineColis(int val1, int val2, int size2) {
		int x2 = val1 + mSize;
		int x1 = val1 - mSize;
		int z2 = val2 + size2;
		int z1 = val2 - size2;
		if ((x1 < z1 && z1 < x2) || (x1 < z2 && z2 < x2)) {
			return true;
		} else {
			return false;
		}
	}

}
