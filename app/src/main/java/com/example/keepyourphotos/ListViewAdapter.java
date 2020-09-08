package com.example.keepyourphotos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter {

    ArrayList<String> folderNameList;
    Activity context;

    public ListViewAdapter(ArrayList<String> folderNameList, Activity context) {
        super(context,R.layout.list_layout,folderNameList);

        this.folderNameList = folderNameList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();

        View customView = layoutInflater.inflate(R.layout.list_layout,null,true);

        TextView floderNameText = customView.findViewById(R.id.textView);

        floderNameText.setText(folderNameList.get(position));

        return customView;
    }
}
