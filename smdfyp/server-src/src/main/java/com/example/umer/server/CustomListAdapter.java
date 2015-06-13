package com.example.umer.server;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ammar on 6/6/2015.
 */
public class CustomListAdapter extends BaseAdapter {
    private ArrayList<TableData> listData;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<TableData> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.phonenumberview = (TextView) convertView.findViewById(R.id.ph);
            holder.detailsview = (TextView) convertView.findViewById(R.id.det);
            holder.longitudeview = (TextView) convertView.findViewById(R.id.lng);
            holder.latitudeview = (TextView) convertView.findViewById(R.id.lat);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.phonenumberview.setText("Phone Number :"+listData.get(position).getphonenumber());
        holder.detailsview.setText("Details :"+listData.get(position).getdetails());
        holder.longitudeview.setText("Longitude :"+listData.get(position).getlongitude());
        holder.latitudeview.setText("Latitude :"+listData.get(position).getlatitude());
        return convertView;
    }

    static class ViewHolder {
        TextView phonenumberview;
        TextView detailsview;
        TextView longitudeview;
        TextView latitudeview;
    }
}
