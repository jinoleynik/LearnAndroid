package com.example.learn;
/**
 * 
 * @author Oleynik Eugeniy  jinoleynik@gmail.com
 *
 */
//import de.marcreichelt.android.RealViewSwitcher;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

public class Second extends Activity{




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.second);

		RealViewSwitcher realViewSwitcher = new RealViewSwitcher(getApplicationContext());


		final int[] backgroundColors = { Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW };
		for (int i = 0; i < 5; i++) {
			TextView textView = new TextView(getApplicationContext());
			textView.setText(Integer.toString(i + 1));
			textView.setTextSize(100);
			textView.setTextColor(Color.BLACK);
			textView.setGravity(Gravity.CENTER);
			textView.setBackgroundColor(backgroundColors[i]);
			realViewSwitcher.addView(textView);	}





		setContentView(realViewSwitcher);
		realViewSwitcher.setOnScreenSwitchListener(onScreenSwitchListener);
		/*LinearLayout mainLayout = (LinearLayout) findViewById(R.id.secondtwo);
		mainLayout.setOnTouchListener(this);
		flipper = (ViewFlipper) findViewById(R.id.flipper);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		int layouts[] = new int[] { R.layout.one, R.layout.two };
		for (int layout : layouts)
			flipper.addView(inflater.inflate(layout, null));
		 */
	}
	private final RealViewSwitcher.OnScreenSwitchListener onScreenSwitchListener = new RealViewSwitcher.OnScreenSwitchListener() {

		@Override
		public void onScreenSwitched(int screen) {
			// this method is executed if a screen has been activated, i.e. the screen is completely visible
			//  and the animation has stopped (might be useful for removing / adding new views)
			Log.d("RealViewSwitcher", "switched to screen: " + screen);
		}

	};
	/*
	public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
            fromPosition = event.getX();
            break;
        case MotionEvent.ACTION_MOVE:
        	float toPosition = event.getX();
        	 if ((fromPosition - MOVE_LENGTH) > toPosition)
        	    {

        	    	fromPosition = toPosition;
        	        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_in));
        	        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_next_out));
        	        flipper.showNext();
        	    }
        	    else if ((fromPosition + MOVE_LENGTH) < toPosition)
        	    {
        	    	fromPosition = toPosition;
        	        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in));
        	        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out));
        	        flipper.showPrevious();
        	    }
        default:
            break;
        }
        return true;
    }*/

}
