package com.system;

import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class MyConst {
    public static final LinearLayout.LayoutParams mLLP = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    public static final String EXTRA_CODE_TEXT = "EXTRA_CODE_TEXT";
    public static final String QRCODE = "shzdroid.QRCODE";
    public static final String ACTION_SEND_QR = "shzdroid.ACTION_SEND_QR";
	public static final String ACTION_GET_MEMORY = "shzdroid.ACTION_GET_MEMORY";
	public static final String OUT_MEMORY = "shzdroid.OUT_MEMORY";
	public static final String OUTDATA = "shzdroid.OUTDATA";
}
