package com.logik;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;

public class PathDrawer extends View {
	Paint paint;
	private int mYMax;
	private int mXMax;

	public PathDrawer(Context context) {
		super(context);
		paint = new Paint();
		setBackgroundColor(Color.BLACK);
		setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));

	}

	public void refresh() {
		invalidate();
	}

	private List<XY> generator() {
		List<XY> list = new ArrayList<XY>();
		list.add(new XY(1, 1));
		int max = 100;
		for (int i = 1; i < max; i++) {
			double rand = Math.random();

			int x = 0, y = 0;
			if (rand > 0.5) {
				y = 0;
				// if (Math.random() > 0.5) {
				// x = 1;
				// } else {
				// if (list.get(i-1).mX > 0) {
				// x = -1;
				// }
				// }
				x = 1;
			} else {
				x = 0;
				if (Math.random() > 0.5) {
					y = 1;
				} else {
					if (list.get(i - 1).mY > 0) {
						y = -1;
					}
				}
				// y=1;
			}
			list.add(new XY(list.get(i - 1).mX + x, list.get(i - 1).mY + y));
			mYMax = list.get(i).mY > mYMax ? list.get(i).mY : mYMax;
			mXMax = list.get(i).mX > mXMax ? list.get(i).mX : mXMax;
		}

		list.add(new XY(list.get(list.size() - 1).mX, 1));
		return list;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		paint.setStrokeWidth(3);
		paint.setColor(Color.YELLOW);
		List<XY> lll = generator();
		float x = getWidth() / lll.get(lll.size() - 1).mX;
		float y = getHeight() / mYMax;
		mXMax = 0;
		mYMax = 0;
		canvas.drawLine(lll.get(0).mX * x, 0, lll.get(0).mX * x, lll.get(0).mY
				* y, paint);
		for (int i = 1; i < lll.size(); i++) {
			float corpointX = ((lll.get(i).mX - lll.get(i - 1).mX) / 2 + lll
					.get(i - 1).mX) * x;
			float corpointY = ((lll.get(i).mY - lll.get(i - 1).mY) / 2 + lll
					.get(i - 1).mY) * y;

			canvas.drawCircle(corpointX, corpointY, 5, paint);
			canvas.drawLine(lll.get(i - 1).mX * x, lll.get(i - 1).mY * y,
					lll.get(i).mX * x, lll.get(i).mY * y, paint);
		}
		canvas.drawLine(lll.get(lll.size() - 1).mX * x,
				lll.get(lll.size() - 1).mY * y, lll.get(lll.size() - 1).mX * x,
				0, paint);
		super.onDraw(canvas);
	}

}
