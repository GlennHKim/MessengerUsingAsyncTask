package com.multicampus.todoapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by student on 2016-06-30.
 */
public class TitleTextView extends TextView {

    private boolean complete;
    private int completeColor;
    private float completeWidth;

    public TitleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleTextView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray att = context.obtainStyledAttributes(attrs, R.styleable.TitleTextView);
        complete = att.getBoolean(R.styleable.TitleTextView_complete, false);
        completeColor = att.getColor(R.styleable.TitleTextView_completeColor, Color.RED);
        completeWidth = att.getDimension(R.styleable.TitleTextView_completeWidth, 6);
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(complete){
            Paint paint = new Paint();
            float x=0;
            float y=getHeight()/2;
            float dx = getWidth();
            float dy = y;
            paint.setColor(completeColor);
            paint.setAlpha(0x99);
            paint.setStrokeWidth(completeWidth);
            canvas.drawLine(x,y,dx,dy,paint);
        }

    }
}
