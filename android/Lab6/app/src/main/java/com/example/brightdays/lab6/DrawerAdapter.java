package com.example.brightdays.lab6;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DrawerAdapter extends BaseAdapter {

    String[] titles;
    LayoutInflater lInflater;

    public DrawerAdapter(Context context, int textViewResourceId, String[] titles) {
        this.titles = titles;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.left_menu_item_row, parent, false);
        }
        ((TextView) view.findViewById(R.id.titleLeftMenu)).setText(titles[position]);
        return view;
    }
}
