package com.service;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.system.SHZApplication;

public class ServiceHandler extends Handler {

    public ServiceHandler(Looper mServiceLooper, SHZService shzService,
            SHZApplication application) {
       
    }
    
    @Override
    public void handleMessage(Message msg) {
        if (msg.obj != null) {
            final Intent intent = (Intent) msg.obj;
            final String action = intent.getAction();
         
        }      
    }

}
