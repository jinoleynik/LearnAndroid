/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qrcapture;

import java.io.IOException;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.activity.BaseActivity;
import com.example.learn.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.service.SHZService;
import com.system.MyConst;
import com.system.SHZApplication;

/**
 * The barcode reader activity itself. This is loosely based on the
 * CameraPreview example included in the Android SDK.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements
        SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    private static final String PRODUCT_SEARCH_URL_PREFIX = "http://www.google";
    private static final String PRODUCT_SEARCH_URL_SUFFIX = "/m/products/scan";
    private static final String ZXING_URL = "http://zxing.appspot.com/scan";

    /**
     * 
     * @author
     * 
     */
    private enum Source {
        NATIVE_APP_INTENT, PRODUCT_SEARCH_LINK, ZXING_LINK, NONE
    }

    private CaptureActivityHandler handler;

    private ViewfinderView viewfinderView;
    private Result lastResult;
    private boolean hasSurface;
    private Source source;
    private String sourceUrl;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private SHZApplication mApp;

    private String mResText="";

    ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mApp = (SHZApplication) getApplication();

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_capture);

        CameraManager.init(mApp);
        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        handler = null;
        lastResult = null;
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still
            // exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the
            // camera.
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        Intent intent = getIntent();
        String action = intent == null ? null : intent.getAction();
        String dataString = intent == null ? null : intent.getDataString();
        if (intent != null && action != null) {
            if (action.equals(Intents.Scan.ACTION)) {
                // Scan the formats the intent requested, and return the result
                // to the calling activity.
                source = Source.NATIVE_APP_INTENT;
                decodeFormats = DecodeFormatManager.parseDecodeFormats(intent);
            } else if (dataString != null
                    && dataString.contains(PRODUCT_SEARCH_URL_PREFIX)
                    && dataString.contains(PRODUCT_SEARCH_URL_SUFFIX)) {
                // Scan only products and send the result to mobile Product
                // Search.
                source = Source.PRODUCT_SEARCH_LINK;
                sourceUrl = dataString;
                decodeFormats = DecodeFormatManager.PRODUCT_FORMATS;
            } else if (dataString != null && dataString.startsWith(ZXING_URL)) {
                // Scan formats requested in query string (all formats if none
                // specified).
                // If a return URL is specified, send the results there.
                // Otherwise, handle it ourselves.
                source = Source.ZXING_LINK;
                sourceUrl = dataString;
                Uri inputUri = Uri.parse(sourceUrl);
                decodeFormats = DecodeFormatManager
                        .parseDecodeFormats(inputUri);
            } else {
                // Scan all formats and handle the results ourselves (launched
                // from Home).
                source = Source.NONE;
                decodeFormats = null;
            }
            characterSet = intent.getStringExtra(Intents.Scan.CHARACTER_SET);
        } else {
            source = Source.NONE;
            decodeFormats = null;
            characterSet = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (source == Source.NATIVE_APP_INTENT) {
                setResult(RESULT_CANCELED);
                finish();
                return true;
            } else if ((source == Source.NONE || source == Source.ZXING_LINK)
                    && lastResult != null) {
                restartScan();
                return true;
            }
        } else if (keyCode == KeyEvent.KEYCODE_FOCUS
                || keyCode == KeyEvent.KEYCODE_CAMERA) {
            // Handle these events so they don't launch the Camera app
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        // Do nothing, this is to prevent the activity from being restarted when
        // the keyboard opens.
        super.onConfigurationChanged(config);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show
     * the results.
     * 
     * @param rawResult
     *            The contents of the barcode.
     * @param barcode
     *            A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode) {
        if (Constants.DEBUG)
            Log.d(TAG, "handleDecode: " + rawResult.getText());
        inactivityTimer.onActivity();
        lastResult = rawResult;
        // check ticket
        // Intent intent = new Intent();
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        final Bitmap barcodeSmall = Bitmap.createBitmap(barcode, 0, 0,
                barcode.getWidth(), barcode.getHeight(), matrix, true);
        barcode.recycle();
        String res = rawResult.getText();
        if (!mResText.equals(res)) {
            mResText = res;
            Intent sendcode = new Intent(this, SHZService.class);
            sendcode.setAction(MyConst.ACTION_SEND_QR);
            sendcode.putExtra(MyConst.QRCODE, res);
            sendBroadcast(sendcode);
            ((SHZApplication) getApplication()).addQRText(rawResult.getText());
            Toast.makeText(this, "add res", Toast.LENGTH_SHORT).show();
            playResource(R.raw.ugu);
        }
        restartScan();
        // startService(sendcode);
        // intent.putExtra(MyConst.EXTRA_CODE_TEXT, rawResult.getText());
        // setResult(Activity.RESULT_OK, intent);
        // finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);
            displayFrameworkBugMessageAndExit();
            return;
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializating camera", e);
            displayFrameworkBugMessageAndExit();
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.msg_camera_framework_bug));
        builder.setPositiveButton(android.R.string.ok, new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void restartScan() {
        resetStatusView();
        if (handler != null) {
            handler.sendEmptyMessage(R.id.restart_preview);
        }
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
        lastResult = null;
    }

    @Override
    public void onClick(View arg0) {
        // TODO Auto-generated method stub

    }

}
