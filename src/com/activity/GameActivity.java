package com.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.learn.R;
import com.view.GameView;

public class GameActivity extends BaseActivity {
    
    private LinearLayout mConteiner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dummy);
    mConteiner = (LinearLayout) findViewById(R.id.dummy_conteiner);
    GameView gv = new GameView(this);
    mConteiner.addView(gv);
    }

    @Override
    public void onClick(View v) {
      

    }

}
