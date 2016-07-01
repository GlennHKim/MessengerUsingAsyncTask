package com.multicampus.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by student on 2016-06-29.
 */
public class TodoItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<TodoItem> data;
    private int layoutId;
    private LayoutInflater inflater;

    public TodoItemAdapter(Context context, ArrayList<TodoItem> data, int layoutId) {
        this.context = context;
        this.data = data;
        this.layoutId = layoutId;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TodoItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)data.get(position).getTodoId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // 최초 호출하게될 때
            convertView = inflater.inflate(layoutId, parent, false);    // view 생성
        }

        TitleTextView title = (TitleTextView)convertView.findViewById(R.id.titleTxt);
        TextView content = (TextView)convertView.findViewById(R.id.contentTxt);

        TodoItem item = getItem(position);

        title.setText(item.getTitle());
        content.setText(item.getContent());

        title.setComplete(item.isComplete());

        return convertView;
    }
}
