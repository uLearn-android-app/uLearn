package com.example.ulearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPageActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        webView = findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        Intent web_page_intent = getIntent();

        String web_url = "https://"+web_page_intent.getStringExtra("url");

        webView.loadUrl(web_url);
    }
}