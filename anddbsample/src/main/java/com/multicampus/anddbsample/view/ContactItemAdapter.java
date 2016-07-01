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
public class ContactItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ContactItem> data;
    private int layoutId;
    private LayoutInflater inflater;

    public ContactItemAdapter(Context context, ArrayList<ContactItem> data, int layoutId) {
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
    public ContactItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            // 최초 호출하게될 때
            convertView = inflater.inflate(layoutId, parent, false);    // view 생성
        }

        TitleTextView title = (TitleTextView)convertView.findViewById(R.id.titleTxt);
        TextView content = (TextView)convertView.findViewById(R.id.contentTxt);

        ContactItem item = getItem(position);

        title.setText(item.getContact().getId());
        content.setText(item.getContact().getName());

        return convertView;
    }
}
