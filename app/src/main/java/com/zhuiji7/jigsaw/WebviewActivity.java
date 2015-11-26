package com.zhuiji7.jigsaw;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

/**
 * Created by cww on 15-11-26.
 */
public class WebviewActivity extends AppCompatActivity {
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.webview_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.ac_toolbar_toolbar);
        toolbar.setTitle(getString(R.string.settings_4));
        setSupportActionBar(toolbar);
        webView = (WebView)findViewById(R.id.webview);
        webView.loadUrl(getString(R.string.project_url));
    }
}
