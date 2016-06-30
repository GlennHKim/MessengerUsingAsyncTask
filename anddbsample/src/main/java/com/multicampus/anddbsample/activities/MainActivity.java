package com.multicampus.anddbsample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.multicampus.anddbsample.R;
import com.multicampus.anddbsample.restIF.GetBear;
import com.multicampus.anddbsample.restIF.PostBear;
import com.multicampus.anddbsample.vo.Bear;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit;
    Button btnList;

    GetBear getBear = new GetBear();
    PostBear postBear = new PostBear();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setDefaultEvent();
    }

    private void setView(){
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnList = (Button)findViewById(R.id.btnList);
    }

    private void setDefaultEvent(){
        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                getBear.getBear("57721c41aa42685017000001");
            }
        });

        btnList.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                postBear.postBear(new Bear("AI-BaoBao"));
            }
        });
    }
}
