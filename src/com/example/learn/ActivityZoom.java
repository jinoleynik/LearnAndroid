package com.example.learn;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class ActivityZoom extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        WebView webv = (WebView) findViewById(R.id.web_view_zoom);
        webv.setBackgroundResource(R.drawable.categories);
    }
}
