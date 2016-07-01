package com.multicampus.todobrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText url;
    private Button move;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setEvent();
        init();
    }

    private void setView(){
        url = (EditText)findViewById(R.id.url);
        move = (Button)findViewById(R.id.move);
        webView = (WebView)findViewById(R.id.web);
    }

    private void setEvent(){
        // loadUrl이 호출될 때 시스템의 브라우저가 실행되는게 기본이므로 웹뷰가 직접 처리하기 위해서
        // WebViewClient의 shouldOverrideUrlLoading 구현함
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);  // WebView가 url을 직접 처리
                return true;
            }
        });
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movePage();
            }
        });

    }

    private void init(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        if(uri != null){
            Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
            webView.loadUrl(uri.toString());
        }else{
            webView.loadUrl("http://naver.com");
        }

    }

    public void movePage(){
        String pageUrl = url.getText().toString();
        if(!pageUrl.startsWith("http")){
            pageUrl = "http://" + pageUrl;
        }
        webView.loadUrl(pageUrl);
    }
}
