package com.example.huadong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsContentActivity extends AppCompatActivity {
    private WebView webView ;
    private String url ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_content);
        webView = (WebView) findViewById(R.id.web_view);
        Intent intent = getIntent() ;
        Bundle bundle = intent.getExtras() ;
        url = bundle.getString("url") ;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}
