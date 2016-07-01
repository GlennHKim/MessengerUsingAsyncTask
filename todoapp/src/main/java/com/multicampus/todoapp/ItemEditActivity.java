package com.multicampus.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ItemEditActivity extends AppCompatActivity {

    Button btnSave;
    Button btnCancel;
    Button btnList;

    EditText editTxtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        setView();
        setEvent();
    }

    private void setView(){
        btnSave = (Button)findViewById(R.id.btnEditSave);
        btnCancel = (Button)findViewById(R.id.btnEditCancel);
        btnList = (Button)findViewById(R.id.btnEditList);
        editTxtContent = (EditText)findViewById(R.id.edittxtContent);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Intent.EXTRA_TITLE);
        String content = intent.getStringExtra(Intent.EXTRA_TEXT);

        if(title == null || title.trim().length() == 0){
            title = "제목 없음";
        }

        if(content == null || content.trim().length() == 0){
            content = "";
        }

        setTitle(title);
        editTxtContent.setText(content);
    }

    private void setEvent(){
        btnCancel.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vibrator.vibrate(1000*2);
                finish();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ItemEditActivity.this, ItemListActivity.class);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2016-06-30 일단 저장
                finish();
            }
        });
    }
}
