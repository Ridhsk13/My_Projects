package com.ridhs.myblackboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by macminii5 on 28/06/16.
 */
public class MainActivityCustomGridAdapter extends BaseAdapter {

    private Context mContext;
    private final String[] _web;
    private final int[] _Imageid;

    public MainActivityCustomGridAdapter(Context c, String[] web, int[] Imageid ) {
        mContext = c;
        this._Imageid = Imageid;
        this._web = web;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return this._web.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;


        if (convertView == null) {

            new View(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.activity_main_grid_single, null);

        } else {
            grid = convertView;
        }
        TextView textView = (TextView) grid.findViewById(R.id.main_grid_text);
        ImageView imageView = (ImageView)grid.findViewById(R.id.main_grid_img);
        textView.setText(_web[position]);
        imageView.setImageResource(_Imageid[position]);

        return grid;
    }
}
