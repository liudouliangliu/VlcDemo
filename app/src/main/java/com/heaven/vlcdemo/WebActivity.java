package com.heaven.vlcdemo;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
    }

    private void initView() {
        WebView webView = findViewById(R.id.map);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "file:////android_asset/index.html";
        webView.loadUrl(url);

    }
}
