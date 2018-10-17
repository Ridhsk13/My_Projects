package com.ridhs.mydigicatlog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by noesisimac on 9/21/16.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;
    private  final String[] text;
    private final int[] imageId;


    // ,int[] imageid to add in adapter constructor
    public GridViewAdapter(Context c, String[] text, int[] imageid)
    {
        context=c;
        this.text=text;
        this.imageId=imageid;
    }

    @Override
    public int getCount() {
        return text.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {

            grid = new View(context);
            Typeface normal = Typeface.createFromAsset(context.getAssets(), "fonts/CenturySchoolbook.otf");

            grid = inflater.inflate(R.layout.timepass, null);
            TextView textView = (TextView) grid.findViewById(R.id.TEST);
            textView.setText(text[i]);
            textView.setTypeface(normal);
            textView.setBackgroundResource(imageId[i]);
            //textView.setBackgroundDrawable(imageId[i]);
            //textView.setCompoundDrawablesWithIntrinsicBounds(0,imageId[i],0,0);
/*            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_img);
            textView.setText(text[i]);
            imageView.setImageResource(imageId[i]);*/
        } else {
            grid = (View) view;
        }

        return grid;    }
}
