package com.example.learn;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ZoomView extends ImageView implements OnTouchListener{   
   
    private LinearLayout mZoomImage;

    public ZoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.zoom_view, null);
        mZoomImage = (LinearLayout) findViewById(R.id.image_view_zoom); 
        
    }
   
//    private void init() {
//        inflate(getContext(), R.layout.view_zoom_image, null);
//        WebView zoomImage = (WebView) findViewById(R.id.web_zoom_image); 
//    }
    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
       switch (arg1.getAction()) {
    case MotionEvent.ACTION_MOVE:
      
        break;
    default:
        break;
    }
        return false;
    }
    
}
