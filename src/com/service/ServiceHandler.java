package com.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.network.GetMemory;
import com.system.MyConst;
import com.system.SHZApplication;

public class ServiceHandler extends Handler {

    private Context mContext;
	public ServiceHandler(Looper mServiceLooper, Context context,
            SHZApplication application) {
       mContext = context;
    }
    
    @Override
    public void handleMessage(Message msg) {
        if (msg.obj != null) {
            final Intent intent = (Intent) msg.obj;
            final String action = intent.getAction();
         new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(MyConst.ACTION_GET_MEMORY.equals(action)){
					getMemory();
				}
			}

		
		}).start();
        }      
    }
	private void getMemory() {
		GetMemory getmem = new GetMemory("http://shz.16mb.com/api/memlist/");
		getmem.execute();
		Intent outdata = new Intent();
		outdata.setAction(MyConst.OUT_MEMORY);
		outdata.putExtra(MyConst.OUTDATA, getmem.getResult());
		mContext.sendBroadcast(outdata);		
	}
}
