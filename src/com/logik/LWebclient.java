package com.logik;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LWebclient extends WebViewClient {
    ProgressDialog mProgress;
    private Context mContext;

    public LWebclient(Context context) {
mContext = context;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        mProgress = ProgressDialog.show(mContext, "loading", "loading");
        mProgress.setCancelable(true);
        mProgress.setCanceledOnTouchOutside(true);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        mProgress.cancel();
        super.onPageFinished(view, url);
    }
}
