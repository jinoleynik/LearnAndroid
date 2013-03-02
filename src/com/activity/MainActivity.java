package com.activity;

import android.os.Bundle;
import android.view.View;

import com.example.learn.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        registerOnClickListener(new int[] { R.id.btn_wisp, R.id.btn_zoom,
                 R.id.btn_qr, R.id.btn_path, R.id.btn_memory}, this.getCurrentFocus());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.btn_wisp:
            startActivity(WispActivity.class);
            break;
        case R.id.btn_zoom:
            startActivity(ActivityZoom.class);
            break;
      
        case R.id.btn_qr:
            startActivity(QRactivity.class);
            break;
        case R.id.btn_path:
            startActivity(PathActivity.class);
            break;
        case R.id.btn_memory:
            startActivity(MemoryActivity.class);
            break;
        default:
            break;
        }

    }
}
