package com.example.brightdays.lab6;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User-PC on 01.04.2015.
 */
public class ViewPagerContentAdapter extends BaseAdapter {

    ArrayList<Finance> struct;
    LayoutInflater lInflater;
    String criteria;

    public ViewPagerContentAdapter(Context context, int textViewResourceId, ArrayList<Finance> struct, String criteria) {
        this.struct = struct;
        lInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.criteria = criteria;
    }

    @Override
    public int getCount() {
        return struct.size();
    }

    @Override
    public Object getItem(int i) {
        return struct.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        TextView category;
        TextView value;
        TextView date;
    }

    private void setHolderColor(ViewHolder holder, int color) {
        holder.category.setTextColor(color);
        holder.value.setTextColor(color);
        holder.date.setTextColor(color);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.finance_item, null);
            final ViewHolder holder = new ViewHolder();
            holder.category = (TextView) convertView.findViewById(R.id.finance_category);
            holder.value = (TextView) convertView.findViewById(R.id.finance_value);
            holder.date = (TextView) convertView.findViewById(R.id.finance_date);
            convertView.setTag(holder);
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        if (criteria.equals(DBHelper.TAG_INCOME))
            setHolderColor(holder, Color.rgb(62, 174, 118));
        else
            setHolderColor(holder, Color.RED);
        holder.category.setText(struct.get(position).getCategory());
        holder.value.setText(String.valueOf(struct.get(position).getValue()));
        holder.date.setText(struct.get(position).getDate());
        return convertView;
    }

}
