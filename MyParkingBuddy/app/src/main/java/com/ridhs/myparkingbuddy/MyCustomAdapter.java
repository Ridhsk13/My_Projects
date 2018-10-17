package com.ridhs.myparkingbuddy;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by RIDHS on 10/1/2017.
 */

public class MyCustomAdapter extends BaseAdapter {

    ArrayList<User> user ;
    //ArrayList<User> users= new ArrayList<User>(user);
    Activity context;

    public MyCustomAdapter(ArrayList<User> user, Activity context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public int getCount() {
        return user.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;

        if (convertView == null) {

            new View(context);
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                rowView = inflater.inflate(R.layout.adapter_list_item, parent, false);

        } else {
            rowView = convertView;
        }

        TextView textViewName = (TextView) rowView.findViewById(R.id.rv_tv_name);
        textViewName.setText(user.get(position).getUser_name());

        return rowView;
    }
}
