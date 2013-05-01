package com.activity;

import com.example.learn.R;
import com.system.MyConst;
import com.system.SHZApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class QRactivity extends BaseActivity {

    protected String mText;
    private TextView mQRTextCodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        findViewById(R.id.btn_qrcode).setOnClickListener(this);
        mQRTextCodes = (TextView) findViewById(R.id.tv_codes);
    }

    @Override
    protected void onResume() {
        mQRTextCodes.setText(((SHZApplication)getApplication()).getQRText());
        IntentFilter filt = new IntentFilter(MyConst.ACTION_SEND_QR);
        registerReceiver(mReciver, filt);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mReciver);
        super.onPause();
    }

    private BroadcastReceiver mReciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
//           mText += "\n"+intent.getExtras().getString(MyConst.QRCODE);
           
        }
    };

    @Override
    public void onClick(View v) {
        final Intent intent = new Intent("com.capture.SCAN");
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        startActivity(intent);
        // startActivityForResult(intent, 0);
    }

}
