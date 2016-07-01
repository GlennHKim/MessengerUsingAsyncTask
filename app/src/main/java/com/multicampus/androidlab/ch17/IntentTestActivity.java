package com.multicampus.androidlab.ch17;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.multicampus.androidlab.R;

public class IntentTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch17_activity_intent_test);
   }

    public void makeIntent(){
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel://010-3933-0035"));
        startActivity(intent);
    }
}
