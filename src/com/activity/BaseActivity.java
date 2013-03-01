package com.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener {

    
    public void startActivity(Class<?> activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
    public void registerOnClickListener(int[] ids, View inview){
        for (int i : ids) {          
            final View view = inview == null ? findViewById(i): inview.findViewById(i);
            if ( view == null ) throw new NullPointerException("Can't get view with id "+i);
            view.setOnClickListener(this);
        }
    }

 
}
