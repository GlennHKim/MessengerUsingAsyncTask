package com.multicampus.anddbsample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multicampus.anddbsample.R;

import java.util.ArrayList;

/**
 * Created by student on 2016-06-29.
 */
public class DialogItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DialogItem> data;
    private int layoutId;
    private LayoutInflater inflater;

    public DialogItemAdapter(Context context, ArrayList<DialogItem> data, int layoutId) {
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
    public DialogItem getItem(int position) {
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

        DialogItem item = getItem(position);

        title.setText(item.getTitle());
        content.setText(item.getContent());

        title.setComplete(item.isComplete());

        return convertView;
    }
}
