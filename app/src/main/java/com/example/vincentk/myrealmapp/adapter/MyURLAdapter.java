package com.example.vincentk.myrealmapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vincentk.myrealmapp.R;
import com.example.vincentk.myrealmapp.model.MyURL;

import java.util.List;

/**
 * Created by vincentk on 26/06/2017.
 */

public class MyURLAdapter extends ArrayAdapter<MyURL> {

    public MyURLAdapter(Context context, List<MyURL> urlList) {
        super(context, 0, urlList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_myurl,parent, false);
        }

        MyURLViewHolder viewHolder = (MyURLViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MyURLViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.url = (TextView) convertView.findViewById(R.id.url);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.updateDate = (TextView) convertView.findViewById(R.id.updateDate);
            convertView.setTag(viewHolder);
        }

        MyURL myURL = getItem(position);

        viewHolder.name.setText(myURL.getName());
        viewHolder.url.setText(myURL.getUrl());
        viewHolder.description.setText(myURL.getDescription());
        viewHolder.updateDate.setText(myURL.getUpdateDate().toString());

        return convertView;
    }

    public class MyURLViewHolder {
        public TextView name;
        public TextView url;
        public TextView description;
        public TextView updateDate;
    }
}