package com.system;

import android.app.Application;

public class SHZApplication extends Application {
    private String mTextQR = "";

    public void addQRText(String text) {
        mTextQR += "\n" + text;
    }

    public String getQRText() {
        return mTextQR;
    }

}
