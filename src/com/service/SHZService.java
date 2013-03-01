package com.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.system.SHZApplication;

public class SHZService extends Service{
    // =========================================================
    // Constants
    // =========================================================
    private static final String TAG = "my_log";
    public static final String KEY_LOCAL = "local";
    public static final String KEY_REMOTE = "remote";
    public static final String KEY_ACTION = "action";

    // =========================================================
    // Class fields
    // =========================================================
    private Looper mServiceLooper;
    private ServiceHandler mHandler;
    private NotificationManager mNoticationService;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Message msg = new Message();
        msg.arg1 = startId;
        msg.obj = intent;
        mHandler.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        mNoticationService = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        final HandlerThread thread = new HandlerThread("SHZServiceHandler",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mHandler = new ServiceHandler(mServiceLooper, this,
                (SHZApplication) getApplication());
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
