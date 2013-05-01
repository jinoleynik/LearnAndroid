package com.activity;

import java.net.URLEncoder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;

import com.example.learn.R;
import com.logik.LWebclient;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends BaseActivity implements OnClickListener {  
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new LWebclient(this));
        String player = "<html><head></head><body>"
                + "TEST TEXT<object bgcolor='#000000' data='http://www.twitch.tv/widgets/archive_embed_player.swf' height='300' id='clip_embed_player_flash' type='application/x-shockwave-flash' width='400'>\n<param name='movie' value='http://www.twitch.tv/widgets/archive_embed_player.swf' /><param name='allowScriptAccess' value='always' />\n<param name='allowNetworking' value='all' /><param name='allowFullScreen' value='true' /><param name='flashvars' value='channel=dendi&start_volume=25&title=Dendi%2BJan%2B31st&chapter_id=1892832&auto_play=false' /></object>"
                + "</body></html>";
        player = URLEncoder.encode(player);
        mWebView.loadData(player, "text/html; charset=UTF-8", "UTF-8");
        // mWebView.loadUrl("http://shz.16mb.com/");
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
        case R.id.btn_1:
            mWebView.loadUrl("http://shz.16mb.com/");
            break;
        case R.id.btn_2:
            mWebView.loadUrl("http://ru.twitch.tv/");
            break;
        case R.id.btn_3:
            mWebView.loadUrl("http://www.youtube.com/");
            break;
        default:
            break;
        }

    }

}
