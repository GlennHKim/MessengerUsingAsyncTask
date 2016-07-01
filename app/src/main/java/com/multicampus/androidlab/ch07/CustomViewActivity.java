package com.multicampus.androidlab.ch07;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.multicampus.androidlab.R;

public class CustomViewActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ch07_activity_custom_view);

        CircleView circle = (CircleView)findViewById(R.id.circle);

//        CircleView circle = new CircleView(this);
//        setContentView(circle);


        CircleClickListener circleClickListener = new CircleClickListener();
        circle.setOnClickListener(circleClickListener);

        circle.setOnClickListener(this);

        circle.setOnClickListener(circle);

        View.OnClickListener listner = new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getSimpleName(), "2-4. 익명 inner 클래스로 리스너 인터페이스 구현");
            }
        };

        circle.setOnClickListener(listner);

        circle.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.d(this.getClass().getSimpleName(), "2-5. 리스너 인터페이스 그냥 바로 추가");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(this.getClass().getSimpleName(), "2-2. 액티비티 클래스를 리스너 인터페이스로 구현");
    }

    class CircleClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d(this.getClass().getSimpleName(), "2-1. 리스너 인터페이스 구현 클래스 정의");
        }
    }
}
